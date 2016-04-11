package ru.predanie.predanie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NArtur on 11.04.2016.
 */
public class Track {

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getParent() {
    return parent;
  }

  public void setParent(int parent) {
    this.parent = parent;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @SerializedName("id")
  private long id;

  @SerializedName("parent")
  private int parent;

  @SerializedName("name")
  private String name;

  @SerializedName("url")
  private String url;
}
