package net.sbreban.lastify.model.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LastfmTopAlbumsResponse {

  @JsonProperty("topalbums")
  private LastfmAlbumList topAlbums;

  public LastfmAlbumList getTopAlbums() {
    return topAlbums;
  }

  @Override
  public String toString() {
    return "LastfmTopAlbumsResponse{" +
        "topAlbums=" + topAlbums +
        '}';
  }
}
