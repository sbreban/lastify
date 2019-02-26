package net.sbreban.lastify.model.spotify;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class SpotifyAlbumDeserializer extends StdDeserializer<SpotifyAlbum> {

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
    album.setName(productNode.get("name").textValue());
    album.setReleaseDate(productNode.get("release_date").textValue());
    return album;
  }
}
