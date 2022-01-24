package com.example.covdefense;

import java.util.Objects;

class Enemy_parameters {
  
  public String sprite_path;
  public double x;
  public double y;
  public double speed;
  public int hp;
  public int damage;
  public int money_value;
  
  public Enemy_parameters() {
    this.sprite_path = "";
    this.x = -1.0;
    this.y = -1.0;
    this.speed = -1.0;
    this.hp = -1;
    this.damage = -1;
    this.money_value = -1;
  }
  
  public void add(String file_reading) {
    if (Objects.equals(this.sprite_path, "")) {
      this.sprite_path = file_reading;
    }
    else if (this.x == -1.0) {
      this.x = Double.parseDouble(file_reading);
    }
    else if (this.y == -1.0) {
      this.y = Double.parseDouble(file_reading);
    }
    else if (this.speed == -1.0) {
      this.speed = Double.parseDouble(file_reading);
    }
    else if (this.hp == -1) {
      this.hp = Integer.parseInt(file_reading);
    }
    else if (this.damage == -1) {
      this.damage = Integer.parseInt(file_reading);
    }
    else if (this.money_value == -1) {
      this.money_value = Integer.parseInt(file_reading);
    }
  }

  
}

