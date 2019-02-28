package net.sbreban.lastify.repository;

import net.sbreban.lastify.model.lastfm.LastfmAlbum;
import net.sbreban.lastify.model.lastfm.LastfmTopAlbumsResponse;
import net.sbreban.lastify.model.lastfm.PageData;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.uri.UriTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
public class LastfmAlbumClient {

  private final static Logger logger = LoggerFactory.getLogger(LastfmAlbumClient.class);

  private static final String LASTFM_USER = "lastfm.user";
  private static final String LASTFM_APIKEY = "lastfm.apikey";

  private static final String LASTFM_TOP_ALBUMS_URI
      = "http://ws.audioscrobbler.com/2.0/?method=user.gettopalbums&user={user}&api_key={apikey}&format=json&limit=100&page={page}";

  private final Environment environment;
  private Client lastfmClient = ClientBuilder.newBuilder().register(JacksonFeature.class).register(ObjectMapperProvider.class).build();

  @Autowired
  public LastfmAlbumClient(Environment environment) {
    this.environment = environment;
  }

  public List<LastfmAlbum> getTopAlbums() {
    UriTemplate uriTemplate = new UriTemplate(LASTFM_TOP_ALBUMS_URI);
    String user = environment.getProperty(LASTFM_USER);
    String apiKey = environment.getProperty(LASTFM_APIKEY);

    LastfmTopAlbumsResponse lastfmTopAlbumsResponse = getLastfmTopAlbumsResponse(1, uriTemplate, user, apiKey);

    List<LastfmAlbum> lastfmAlbums = new ArrayList<>();
    PageData pageData = lastfmTopAlbumsResponse.getTopAlbums().getPageData();
    int totalPages = pageData.getTotalPages();

    if (totalPages > 0) {
      for (int page = pageData.getPage(); page < totalPages; page++) {
        logger.debug("Loaded page " + page + ": " + lastfmTopAlbumsResponse.getTopAlbums().getLastfmAlbums());
        lastfmAlbums.addAll(lastfmTopAlbumsResponse.getTopAlbums().getLastfmAlbums());
        lastfmTopAlbumsResponse = getLastfmTopAlbumsResponse(page, uriTemplate, user, apiKey);
      }
    }

    return lastfmAlbums;
  }

  private LastfmTopAlbumsResponse getLastfmTopAlbumsResponse(int page, UriTemplate uriTemplate, String user, String apiKey) {
    String uri = uriTemplate.createURI(user, apiKey, String.valueOf(page));
    Response response = lastfmClient
        .target(uri)
        .request(MediaType.APPLICATION_JSON).get();
    return response.readEntity(LastfmTopAlbumsResponse.class);
  }
}
