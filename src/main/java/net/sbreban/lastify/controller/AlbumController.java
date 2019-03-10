package net.sbreban.lastify.controller;

import net.sbreban.lastify.model.lastfm.LastfmAlbum;
import net.sbreban.lastify.repository.LastfmAlbumClient;
import net.sbreban.lastify.repository.SpotifyAlbumClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
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

  @GetMapping("/user/{username}/albums")
  public String topAlbums(Model model, @PathVariable String username) {
    List<LastfmAlbum> lastfmAlbums = lastfmAlbumClient.getTopAlbums(username);
    model.addAttribute("albums", lastfmAlbums);
    return "album-list";
  }

  @GetMapping("/user/{username}/albums/{streaming-service}")
  public String missingAlbums(Model model, @PathVariable String username, @PathVariable("streaming-service") String streamingService) {
    List<LastfmAlbum> lastfmAlbums = lastfmAlbumClient.getTopAlbums(username);
    List<LastfmAlbum> missingAlbums = new ArrayList<>();
    if (streamingService.equals(SPOTIFY)) {
      missingAlbums = spotifyAlbumClient.getMissingAlbumsAsync(lastfmAlbums);
    }
    model.addAttribute("albums", missingAlbums);
    return "album-list";
  }

}
