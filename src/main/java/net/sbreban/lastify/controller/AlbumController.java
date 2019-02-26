package net.sbreban.lastify.controller;

import net.sbreban.lastify.model.lastfm.LastfmAlbum;
import net.sbreban.lastify.model.spotify.SpotifyAlbum;
import net.sbreban.lastify.repository.LastfmAlbumClient;
import net.sbreban.lastify.repository.SpotifyAlbumClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AlbumController {

  private final LastfmAlbumClient lastfmAlbumClient;
  private final SpotifyAlbumClient spotifyAlbumClient;

  @Autowired
  public AlbumController(LastfmAlbumClient lastfmAlbumClient, SpotifyAlbumClient spotifyAlbumClient) {
    this.lastfmAlbumClient = lastfmAlbumClient;
    this.spotifyAlbumClient = spotifyAlbumClient;
  }

  @GetMapping("/album")
  public String topAlbums(Model model) {
    List<LastfmAlbum> lastfmAlbums = lastfmAlbumClient.getTopAlbums();
    List<LastfmAlbum> missingAlbums = new ArrayList<>();
    lastfmAlbums.forEach((LastfmAlbum album) -> {
      Optional<SpotifyAlbum> spotifyAlbum = spotifyAlbumClient.searchAlbum(album.getName());
      if (!spotifyAlbum.isPresent()) {
        missingAlbums.add(album);
      }
    });
    model.addAttribute("albums", missingAlbums);
    return "album-list";
  }

}