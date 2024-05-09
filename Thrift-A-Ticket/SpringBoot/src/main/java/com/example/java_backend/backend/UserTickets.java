package com.example.java_backend.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "usertickets")
@Table(name = "usertickets")
@Data
public class UserTickets {
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
  private String price;

  @Column(nullable=false)
  private String purchase_link;

  @Column(nullable=false)
  private String img_url;

  public UserTickets(){
    this.email = "NULL";
    this.artist = "NULL";
    this.venue = "NULL";
    this.date = "NULL";
    this.time = "NULL";
    this.price = "NULL";
    this.purchase_link = "NULL";
    this.img_url = "NULL";
  }

  public UserTickets(String email, String artist, String venue, String date, String time, String price, String purchase_link, String img_url)
  {
    this.email = email;
    this.artist = artist;
    this.venue = venue;
    this.date = date;
    this.time = time;
    this.price = price;
    this.purchase_link = purchase_link;
    this.img_url = img_url;
  }

  public String return_as_string() {
    return email + "/" + artist + "/" + venue+ "/" +date + "/" + time + "/" + price + "/" + purchase_link + "/" + img_url + "/";
  }
}
