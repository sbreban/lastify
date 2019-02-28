package net.sbreban.lastify.model.spotify;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = SpotifyAlbumDeserializer.class)
public class SpotifyAlbum {
  private String name;
  private String releaseDate;
  private String artist;

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

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  @Override
  public String toString() {
    return "SpotifyAlbum{" +
        "name='" + name + '\'' +
        ", releaseDate='" + releaseDate + '\'' +
        ", artist='" + artist + '\'' +
        '}';
  }
}
