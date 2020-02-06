package com.thoughtworks;

public class Food {
  private String id;
  private String name;
  private double price;

  public Food(String id, String name, double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }
}
