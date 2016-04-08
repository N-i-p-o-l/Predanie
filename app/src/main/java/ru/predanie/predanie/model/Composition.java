package ru.predanie.predanie.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by NArtur on 07.04.2016.
 */
public class Composition {

  @SerializedName("Id")
  private long id;

  @SerializedName("author_name")
  private String Author;

  @SerializedName("name")
  private String Name;

  @SerializedName("img_s")
  private String ImageUrl;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAuthor() {
    return Author;
  }

  public void setAuthor(String author) {
    Author = author;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getImageUrl() {
    return ImageUrl;
  }

  public void setImageUrl(String imageUrl) {
    ImageUrl = imageUrl;
  }

}
