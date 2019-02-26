package net.sbreban.lastify.model.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LastfmAlbumList {

  @JsonProperty("album")
  private List<LastfmAlbum> lastfmAlbums;

  public List<LastfmAlbum> getLastfmAlbums() {
    return lastfmAlbums;
  }

  @Override
  public String toString() {
    return "LastfmAlbumList{" +
        "lastfmAlbums=" + lastfmAlbums +
        '}';
  }
}
