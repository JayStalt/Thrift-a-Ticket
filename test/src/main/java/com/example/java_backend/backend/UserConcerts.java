package com.example.java_backend.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "userconcerts")
@Table(name = "userconcerts")
@Data
public class UserConcerts {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String email;

  @Column(nullable=false)
  private String artist;

  @Column(nullable=false)
  private String venue;

  @Column(nullable=false)
  private String date;

  @Column(nullable=false)
  private String time;

  @Column(nullable=false)
  private String cityState;

  public UserConcerts(){
    this.email = "NULL";
    this.artist = "NULL";
    this.venue = "NULL";
    this.date = "NULL";
    this.time = "NULL";
    this.cityState = "NULL";
  }

  public UserConcerts(String email, String artist, String venue, String date, String time, String city_state)
  {
    this.email = email;
    this.artist = artist;
    this.venue = venue;
    this.date = date;
    this.time = time;
    this.cityState = city_state;
  }

  public String return_as_string() {
    return email + "/" + artist + "/" + venue+ "/" +date + "/" + time + "/" + cityState + "/";
  }
}
