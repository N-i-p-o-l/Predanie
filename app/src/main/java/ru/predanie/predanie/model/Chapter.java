package ru.predanie.predanie.model;

/**
 * Created by NArtur on 07.04.2016.
 */
public class Chapter {
  private long id;
  private String Name;
  private String AudioUrl;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getAudioUrl() {
    return AudioUrl;
  }

  public void setAudioUrl(String audioUrl) {
    AudioUrl = audioUrl;
  }
}
