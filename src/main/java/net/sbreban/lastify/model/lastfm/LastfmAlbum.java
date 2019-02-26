package net.sbreban.lastify.model.lastfm;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = LastfmAlbumDeserializer.class)
public class LastfmAlbum {
  private String artist;
  private String name;

  public LastfmAlbum() {
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getArtist() {
    return artist;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "LastfmAlbum{" +
        "artist='" + artist + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
