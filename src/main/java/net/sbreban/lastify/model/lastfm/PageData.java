package net.sbreban.lastify.model.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageData {

  @JsonProperty("page")
  private int page;

  @JsonProperty("totalPages")
  private int totalPages;

  public int getPage() {
    return page;
  }

  public int getTotalPages() {
    return totalPages;
  }

  @Override
  public String toString() {
    return "PageData{" +
        "page=" + page +
        ", totalPages=" + totalPages +
        '}';
  }
}
