package net.sbreban.lastify.model.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LastfmAlbumList {

  @JsonProperty("album")
  private List<LastfmAlbum> lastfmAlbums;

  @JsonProperty("@attr")
  private PageData pageData;

  public List<LastfmAlbum> getLastfmAlbums() {
    return lastfmAlbums;
  }

  public PageData getPageData() {
    return pageData;
  }

  @Override
  public String toString() {
    return "LastfmAlbumList{" +
        "lastfmAlbums=" + lastfmAlbums +
        '}';
  }
}
