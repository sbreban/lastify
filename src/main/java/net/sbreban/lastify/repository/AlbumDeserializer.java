package net.sbreban.lastify.repository;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import net.sbreban.lastify.model.Album;

import java.io.IOException;

public class AlbumDeserializer extends StdDeserializer<Album> {

  public AlbumDeserializer() {
    this(null);
  }

  public AlbumDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Album deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode productNode = jp.getCodec()
        .readTree(jp);
    Album album = new Album();
    album.setName(productNode.get("name").textValue());
    album.setName(productNode.get("name").textValue());
    album.setArtist(productNode.get("artist").get("name").textValue());
    return album;
  }
}
