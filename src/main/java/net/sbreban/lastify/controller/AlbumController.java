package net.sbreban.lastify.controller;

import net.sbreban.lastify.repository.AlbumRestClient;
import net.sbreban.lastify.model.TopAlbums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlbumController {

  private final AlbumRestClient client;

  @Autowired
  public AlbumController(AlbumRestClient client) {
    this.client = client;
  }

  @GetMapping("/album")
  public String topAlbums(Model model) {
    TopAlbums topAlbums = client.getTopAlbums();
    model.addAttribute("albums", topAlbums.getTopAlbums().getAlbums());
    return "album-list";
  }

}
