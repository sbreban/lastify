package net.sbreban.lastify.repository;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpotifyCredentialsProvider {

  private final static Logger logger = LoggerFactory.getLogger(SpotifyCredentialsProvider.class);

  private static final String SPOTIFY_CLIENT_ID = "spotify.clientId";
  private static final String SPOTIFY_CLIENT_SECRET = "spotify.clientSecret";

  private static ClientCredentialsRequest clientCredentialsRequest;

  @Autowired
  public SpotifyCredentialsProvider(Environment environment) {
    String clientId = environment.getProperty(SPOTIFY_CLIENT_ID);
    String clientSecret = environment.getProperty(SPOTIFY_CLIENT_SECRET);
    SpotifyApi spotifyApi = new SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .build();
    clientCredentialsRequest = spotifyApi.clientCredentials()
        .build();
  }

  public ClientCredentials getCredentials() {
    ClientCredentials clientCredentials = null;
    try {
      clientCredentials = clientCredentialsRequest.execute();
    } catch (IOException | SpotifyWebApiException e) {
      logger.error("Error getting credentials", e);
    }

    return clientCredentials;
  }
}
