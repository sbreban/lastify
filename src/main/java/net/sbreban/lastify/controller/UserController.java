package net.sbreban.lastify.controller;

import net.sbreban.lastify.model.lastfm.LastfmAlbum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

  private final static Logger logger = LoggerFactory.getLogger(UserController.class);

  private final AlbumController albumController;

  @Autowired
  public UserController(AlbumController albumController) {
    this.albumController = albumController;
  }

  @GetMapping("/user/{username}/albums")
  public String topAlbums(Model model, @PathVariable String username) {
    List<LastfmAlbum> lastfmAlbums = albumController.topAlbums(username);
    model.addAttribute("albums", lastfmAlbums);
    return "album-list";
  }

  @GetMapping("/user/{username}/albums/{streaming-service}")
  public String missingAlbums(Model model, @PathVariable String username, @PathVariable("streaming-service") String streamingService) {
    List<LastfmAlbum> missingAlbums = albumController.missingAlbums(username, streamingService);
    model.addAttribute("albums", missingAlbums);
    return "album-list";
  }
}
