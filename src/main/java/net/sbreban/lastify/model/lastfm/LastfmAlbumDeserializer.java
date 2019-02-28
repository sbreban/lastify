package net.sbreban.lastify.model.lastfm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class LastfmAlbumDeserializer extends StdDeserializer<LastfmAlbum> {

  private static final String ALBUM_NAME_FIELD = "name";
  private static final String ARTIST_FIELD = "artist";
  private static final String ARTIST_NAME_FIELD = "name";

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
    lastfmAlbum.setName(productNode.get(ALBUM_NAME_FIELD).textValue());
    lastfmAlbum.setArtist(productNode.get(ARTIST_FIELD).get(ARTIST_NAME_FIELD).textValue());
    return lastfmAlbum;
  }
}
