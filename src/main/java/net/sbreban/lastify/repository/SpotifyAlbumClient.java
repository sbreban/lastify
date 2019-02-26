package net.sbreban.lastify.repository;

import net.sbreban.lastify.model.spotify.SpotifyAlbum;
import net.sbreban.lastify.model.spotify.SpotifyAlbumSearchResult;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.uri.UriTemplate;
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
import java.util.List;
import java.util.Optional;

@Component
public class SpotifyAlbumClient {

  private static final String SPOTIFY_TOKEN = "spotify.token";
  private static final String SPOTIFY_SEARCH_URI
      = "https://api.spotify.com/v1/search?q={album}&type=album&limit=1";

  private final Environment environment;
  private Client spotifyClient = ClientBuilder.newBuilder().register(JacksonFeature.class).register(ObjectMapperProvider.class).build();

  @Autowired
  public SpotifyAlbumClient(Environment environment) {
    this.environment = environment;
  }

  public Optional<SpotifyAlbum> searchAlbum(String albumName) {
    String token = environment.getProperty(SPOTIFY_TOKEN);
    UriTemplate searchArtistTemplate = new UriTemplate(SPOTIFY_SEARCH_URI);

    String searchArtistUri = null;
    try {
      searchArtistUri = searchArtistTemplate.createURI(URLEncoder.encode(albumName, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    SpotifyAlbumSearchResult spotifyAlbumSearchResult = null;
    try {
      Response spotifyResponse = spotifyClient.target(searchArtistUri)
          .request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
      spotifyAlbumSearchResult = spotifyResponse.readEntity(SpotifyAlbumSearchResult.class);
    } catch (Exception e) {
      System.out.println("Exception while searching for album: " + albumName);
    }

    Optional<SpotifyAlbum> spotifyAlbum = Optional.empty();
    if (spotifyAlbumSearchResult != null && spotifyAlbumSearchResult.getAlbums() != null) {
      List<SpotifyAlbum> albums = spotifyAlbumSearchResult.getAlbums().getItems();
      if (albums != null && !albums.isEmpty()) {
        spotifyAlbum = Optional.of(albums.get(0));
      }

    }

    return spotifyAlbum;
  }
}
