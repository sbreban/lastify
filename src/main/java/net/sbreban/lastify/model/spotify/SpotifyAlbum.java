package net.sbreban.lastify.model.spotify;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = SpotifyAlbumDeserializer.class)
public class SpotifyAlbum {
  private String name;
  private String releaseDate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  @Override
  public String toString() {
    return "SpotifyAlbum{" +
        "name='" + name + '\'' +
        ", releaseDate='" + releaseDate + '\'' +
        '}';
  }
}
