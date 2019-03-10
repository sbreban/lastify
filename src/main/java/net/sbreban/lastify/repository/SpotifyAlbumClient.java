package net.sbreban.lastify.repository;

import net.sbreban.lastify.model.lastfm.LastfmAlbum;
import net.sbreban.lastify.model.spotify.SpotifyAlbum;
import net.sbreban.lastify.model.spotify.SpotifyAlbumSearchResult;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.uri.UriTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SpotifyAlbumClient {

  private final static Logger logger = LoggerFactory.getLogger(SpotifyAlbumClient.class);

  private static final String SPOTIFY_TOKEN = "spotify.token";
  private static final String SPOTIFY_SEARCH_URI
      = "https://api.spotify.com/v1/search?q=album%3A{album}%20artist%3A{artist}&type=album&limit=1";
  private static final int ALBUM_BATCH_SIZE = 4000;

  private final Environment environment;
  private Client spotifyClient = ClientBuilder.newBuilder().register(JacksonFeature.class).register(ObjectMapperProvider.class).build();

  @Autowired
  public SpotifyAlbumClient(Environment environment) {
    this.environment = environment;
  }

  private Optional<SpotifyAlbum> searchAlbum(LastfmAlbum album, UriTemplate searchArtistTemplate, String token) {

    String searchArtistUri = null;
    try {
      searchArtistUri = searchArtistTemplate.createURI(URLEncoder.encode(album.getName(), "UTF-8"), URLEncoder.encode(album.getArtist(), "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      logger.error("Error creating URI", e);
    }

    SpotifyAlbumSearchResult spotifyAlbumSearchResult = null;
    try {
      Response spotifyResponse = spotifyClient.target(searchArtistUri)
          .request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
      Response.StatusType statusInfo = spotifyResponse.getStatusInfo();
      logger.debug("Search status: " + statusInfo);
      if (statusInfo.getStatusCode() == 200) {
        spotifyAlbumSearchResult = spotifyResponse.readEntity(SpotifyAlbumSearchResult.class);
      }
    } catch (Exception e) {
      if (logger.isTraceEnabled()) {
        logger.error("Exception while searching for album: " + album, e);
      } else {
        logger.error("Exception while searching for album: " + album + ", " + e.getMessage());
      }
    }

    Optional<SpotifyAlbum> spotifyAlbum = Optional.empty();
    if (spotifyAlbumSearchResult != null && spotifyAlbumSearchResult.getAlbums() != null) {
      List<SpotifyAlbum> albums = spotifyAlbumSearchResult.getAlbums().getItems();
      if (albums != null && !albums.isEmpty()) {
        SpotifyAlbum firstAlbum = albums.get(0);
        spotifyAlbum = Optional.of(firstAlbum);
      }

    }

    return spotifyAlbum;
  }

  private List<LastfmAlbum> searchAlbums(List<LastfmAlbum> albums, UriTemplate searchArtistTemplate, String token) {
    List<LastfmAlbum> missingAlbums = new ArrayList<>();
    albums.forEach((LastfmAlbum album) -> {
      Optional<SpotifyAlbum> spotifyAlbum = searchAlbum(album, searchArtistTemplate, token);
      if (!spotifyAlbum.isPresent()) {
        logger.debug("Found missing album: " + album.getName());
        missingAlbums.add(album);
      }
    });
    return missingAlbums;
  }

  public List<LastfmAlbum> getMissingAlbumsAsync(List<LastfmAlbum> lastfmAlbums) {
    String token = environment.getProperty(SPOTIFY_TOKEN);
    List<LastfmAlbum> missingAlbums = new ArrayList<>();

    try {
      UriTemplate searchArtistTemplate = new UriTemplate(SPOTIFY_SEARCH_URI);

      List<CompletableFuture<List<LastfmAlbum>>> futures = new ArrayList<>();
      for (int i = 0; i < lastfmAlbums.size(); i = i + ALBUM_BATCH_SIZE) {
        int startIndex = i;
        int endIndex = Math.min(startIndex + ALBUM_BATCH_SIZE, lastfmAlbums.size());
        CompletableFuture<List<LastfmAlbum>> future = CompletableFuture.supplyAsync(() -> searchAlbums(lastfmAlbums.subList(startIndex, endIndex), searchArtistTemplate, token));
        futures.add(future);
      }

      CompletableFuture<List<LastfmAlbum>>[] array = futures.toArray(new CompletableFuture[0]);
      CompletableFuture completableFuture = CompletableFuture.allOf(array);
      completableFuture.get();
      missingAlbums = Stream.of(array).map(CompletableFuture::join).flatMap(List::stream).collect(Collectors.toList());
    } catch (InterruptedException | ExecutionException e) {
      if (logger.isTraceEnabled()) {
        logger.error("Error while getting results from futures", e);
      } else {
        logger.error("Error while getting results from futures: " + e.getMessage());
      }
    }

    return missingAlbums;
  }
}
