package ru.predanie.predanie.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by NArtur on 07.04.2016.
 */
public class Composition {

  @SerializedName("id")
  private long id;

  @SerializedName("author_name")
  private String author;

  @SerializedName("author_id")
  private String author_id;

  @SerializedName("name")
  private String name;

  @SerializedName("desc")
  private String desc;

  @SerializedName("img_s")
  private String ImageUrl;

  @SerializedName("img_medium")
  private String ImageMediumUrl;

  @SerializedName("img_big")
  private String ImageBigUrl;

  public Track[] getTracks() {
    return tracks;
  }

  public void setTracks(Track[] tracks) {
    this.tracks = tracks;
  }

  public Part[] getParts() {
    return parts;
  }

  public void setParts(Part[] parts) {
    this.parts = parts;
  }

  private Track[] tracks;
  private Part[] parts;

  public String getAuthor_id() {
    return author_id;
  }

  public void setAuthor_id(String author_id) {
    this.author_id = author_id;
  }

  public String getImageMediumUrl() {
    return ImageMediumUrl;
  }

  public void setImageMediumUrl(String imageMediumUrl) {
    ImageMediumUrl = imageMediumUrl;
  }

  public String getImageBigUrl() {
    return ImageBigUrl;
  }

  public void setImageBigUrl(String imageBigUrl) {
    ImageBigUrl = imageBigUrl;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getImageUrl() {
    return ImageUrl;
  }

  public void setImageUrl(String imageUrl) {
    ImageUrl = imageUrl;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
