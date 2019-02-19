package net.sbreban.lastify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopAlbums {

  @JsonProperty("topalbums")
  private AlbumList topAlbums;

  public AlbumList getTopAlbums() {
    return topAlbums;
  }

  @Override
  public String toString() {
    return "TopAlbums{" +
        "topAlbums=" + topAlbums +
        '}';
  }
}
