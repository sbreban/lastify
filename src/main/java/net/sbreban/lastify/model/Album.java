package net.sbreban.lastify.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.sbreban.lastify.repository.AlbumDeserializer;

@JsonDeserialize(using = AlbumDeserializer.class)
public class Album {
  private String artist;
  private String name;

  public Album() {
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
    return "Album{" +
        "artist='" + artist + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
