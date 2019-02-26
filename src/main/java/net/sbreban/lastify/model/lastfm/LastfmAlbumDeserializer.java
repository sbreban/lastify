package net.sbreban.lastify.model.lastfm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class LastfmAlbumDeserializer extends StdDeserializer<LastfmAlbum> {

  public LastfmAlbumDeserializer() {
    this(null);
  }

  public LastfmAlbumDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public LastfmAlbum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode productNode = jp.getCodec()
        .readTree(jp);
    LastfmAlbum lastfmAlbum = new LastfmAlbum();
    lastfmAlbum.setName(productNode.get("name").textValue());
    lastfmAlbum.setArtist(productNode.get("artist").get("name").textValue());
    return lastfmAlbum;
  }
}
