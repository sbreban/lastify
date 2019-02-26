package net.sbreban.lastify.model.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpotifyAlbumSearchItems {

  @JsonProperty("items")
  List<SpotifyAlbum> items;

  public List<SpotifyAlbum> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "SpotifyAlbumSearchItems{" +
        "items=" + items +
        '}';
  }
}
