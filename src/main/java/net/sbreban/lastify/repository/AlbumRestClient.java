package net.sbreban.lastify.repository;

import net.sbreban.lastify.model.TopAlbums;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.uri.UriTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
public class AlbumRestClient {

  private static final String LASTFM_USER = "lastfm.user";
  private static final String LASTFM_APIKEY = "lastfm.apikey";

  private final Environment environment;

  private static final String REST_URI
      = "http://ws.audioscrobbler.com/2.0/?method=user.gettopalbums&user={user}&api_key={apikey}&format=json";

  private Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).register(ObjectMapperProvider.class).build();

  @Autowired
  public AlbumRestClient(Environment environment) {
    this.environment = environment;
  }

  public TopAlbums getTopAlbums() {
    String user = environment.getProperty(LASTFM_USER);
    String apiKey = environment.getProperty(LASTFM_APIKEY);
    UriTemplate uriTemplate = new UriTemplate(REST_URI);
    String uri = uriTemplate.createURI(user, apiKey);
    Response response = client
        .target(uri)
        .request(MediaType.APPLICATION_JSON).get();
    TopAlbums topAlbums = response.readEntity(TopAlbums.class);
    return topAlbums;
  }
}
