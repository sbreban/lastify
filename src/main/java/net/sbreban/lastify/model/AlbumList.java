package net.sbreban.lastify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AlbumList {

  @JsonProperty("album")
  private List<Album> albums;

  public List<Album> getAlbums() {
    return albums;
  }

  @Override
  public String toString() {
    return "AlbumList{" +
        "albums=" + albums +
        '}';
  }
}
