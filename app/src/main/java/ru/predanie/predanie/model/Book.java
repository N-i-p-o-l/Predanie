package ru.predanie.predanie.model;

import java.util.List;

/**
 * Created by NArtur on 07.04.2016.
 */
public class Book {
  private long id;

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

  public List<Chapter> getChapterList() {
    return chapterList;
  }

  public void setChapterList(List<Chapter> chapterList) {
    this.chapterList = chapterList;
  }

  private String Name;
  private List<Chapter> chapterList;
}
