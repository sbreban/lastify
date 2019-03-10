package net.sbreban.lastify.repository;

public class SpotifyCredentials {
  private final String accessToken;
  private final String tokenType;
  private final Integer expiresIn;

  public SpotifyCredentials(String accessToken, String tokenType, Integer expiresIn) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public Integer getExpiresIn() {
    return expiresIn;
  }

  @Override
  public String toString() {
    return "SpotifyCredentials{" +
        "accessToken='" + accessToken + '\'' +
        ", tokenType='" + tokenType + '\'' +
        ", expiresIn=" + expiresIn +
        '}';
  }
}
