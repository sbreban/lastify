package net.sbreban.lastify.model.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifyAlbumSearchResult {

  @JsonProperty("albums")
  private SpotifyAlbumSearchItems albums;

  public SpotifyAlbumSearchItems getAlbums() {
    return albums;
  }

  @Override
  public String toString() {
    return "SpotifyAlbumSearchResult{" +
        "items=" + albums +
        '}';
  }
}
