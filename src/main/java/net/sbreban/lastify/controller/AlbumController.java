package net.sbreban.lastify.controller;

import net.sbreban.lastify.model.lastfm.LastfmAlbum;
import net.sbreban.lastify.repository.LastfmAlbumClient;
import net.sbreban.lastify.repository.SpotifyAlbumClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlbumController {

  private final static Logger logger = LoggerFactory.getLogger(AlbumController.class);
  private static final String SPOTIFY = "spotify";

  private final LastfmAlbumClient lastfmAlbumClient;
  private final SpotifyAlbumClient spotifyAlbumClient;

  @Autowired
  public AlbumController(LastfmAlbumClient lastfmAlbumClient, SpotifyAlbumClient spotifyAlbumClient) {
    this.lastfmAlbumClient = lastfmAlbumClient;
    this.spotifyAlbumClient = spotifyAlbumClient;
  }

  public List<LastfmAlbum> topAlbums(String username) {
    return lastfmAlbumClient.getTopAlbums(username);
  }

  public List<LastfmAlbum> missingAlbums(String username, String streamingService) {
    List<LastfmAlbum> lastfmAlbums = lastfmAlbumClient.getTopAlbums(username);
    List<LastfmAlbum> missingAlbums = new ArrayList<>();
    if (streamingService.equals(SPOTIFY)) {
      missingAlbums = spotifyAlbumClient.getMissingAlbums(lastfmAlbums);
    }
    return missingAlbums;
  }

}
