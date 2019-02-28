package net.sbreban.lastify.model.spotify;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class SpotifyAlbumDeserializer extends StdDeserializer<SpotifyAlbum> {

  private static final String ALBUM_NAME_FIELD = "name";
  private static final String RELEASE_DATE_FIELD = "release_date";
  private static final String ARTISTS_FIELD = "artists";
  private static final String ARTIST_NAME_FIELD = "name";

  public SpotifyAlbumDeserializer() {
    this(null);
  }

  public SpotifyAlbumDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public SpotifyAlbum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode productNode = jp.getCodec()
        .readTree(jp);
    SpotifyAlbum album = new SpotifyAlbum();
    album.setName(productNode.get(ALBUM_NAME_FIELD).textValue());
    album.setReleaseDate(productNode.get(RELEASE_DATE_FIELD).textValue());
    album.setArtist(productNode.get(ARTISTS_FIELD).get(0).get(ARTIST_NAME_FIELD).textValue());
    return album;
  }
}
