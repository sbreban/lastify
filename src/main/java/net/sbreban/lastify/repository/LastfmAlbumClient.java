package net.sbreban.lastify.repository;

import net.sbreban.lastify.model.lastfm.LastfmAlbum;
import net.sbreban.lastify.model.lastfm.LastfmTopAlbumsResponse;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.uri.UriTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
public class LastfmAlbumClient {

  private static final String LASTFM_USER = "lastfm.user";
  private static final String LASTFM_APIKEY = "lastfm.apikey";

  private static final String LASTFM_TOP_ALBUMS_URI
      = "http://ws.audioscrobbler.com/2.0/?method=user.gettopalbums&user={user}&api_key={apikey}&format=json&limit=100";

  private final Environment environment;
  private Client lastfmClient = ClientBuilder.newBuilder().register(JacksonFeature.class).register(ObjectMapperProvider.class).build();

  @Autowired
  public LastfmAlbumClient(Environment environment) {
    this.environment = environment;
  }

  public List<LastfmAlbum> getTopAlbums() {
    String user = environment.getProperty(LASTFM_USER);
    String apiKey = environment.getProperty(LASTFM_APIKEY);
    UriTemplate uriTemplate = new UriTemplate(LASTFM_TOP_ALBUMS_URI);
    String uri = uriTemplate.createURI(user, apiKey);
    Response response = lastfmClient
        .target(uri)
        .request(MediaType.APPLICATION_JSON).get();
    LastfmTopAlbumsResponse lastfmTopAlbumsResponse = response.readEntity(LastfmTopAlbumsResponse.class);

    return lastfmTopAlbumsResponse.getTopAlbums().getLastfmAlbums();
  }
}