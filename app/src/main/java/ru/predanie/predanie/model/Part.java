package ru.predanie.predanie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NArtur on 11.04.2016.
 */
public class Part {

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
