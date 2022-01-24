package com.example.covdefense;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class Tower extends Basis {
  
  private final double attack_range;
  private final int attack_damage;
  private final int attack_speed;
  private final double projectile_speed;
  private int cooldown;
  
  public static final int REZO = 0;
  public static final int DROSTEN = 1;
  public static final int LAUTERBACH = 2;
  public static final int SPAHN = 3;
  public static final int MERKEL = 4;
  
  
  public Tower(double x, double y, Tower_parameters params) {
    super(new Image(params.sprite_path), x, y);
    this.attack_range = params.attack_range;
    this.attack_damage = params.attack_damage;
    this.attack_speed = params.attack_speed;
    this.projectile_speed = params.projectile_speed;
    this.cooldown = 0;
    this.getIV().setFitHeight(WindowDimensions.HEIGHT / 7.95);
    this.getIV().setFitWidth(WindowDimensions.WIDTH / 11.84);
    this.setX(this.getX() - (WindowDimensions.WIDTH / 23.68));
    this.setY(this.getY() - (WindowDimensions.HEIGHT / 15.9));
    this.setTypeId(params.type_id);
  }

  public static Tower_parameters load_tower_parameters(String tower_src_file_path) {
    Tower_parameters params = new Tower_parameters();
    try {
      File tower_src_file = new File(tower_src_file_path);
      Scanner sc = new Scanner(tower_src_file);
  
      while (sc.hasNextLine()) { 
        String text = sc.nextLine();
        String[] split = text.split(":");
        params.add(split[1].strip());
      }  
      sc.close();      
    } catch(FileNotFoundException e) {
        System.out.println("Error, tower-file missing");   
    }
    return params;
  }
  
  public static String id_to_path(int id) {
    switch (id) {
      case 0: 
        return "towers/Rezo.txt";
      case 1: 
        return "towers/Drosten.txt";
      case 2: 
        return "towers/Lauterbach.txt";
      case 3: 
        return "towers/Spahn.txt";
      case 4: 
        return "towers/Merkel.txt";
      default: 
        return null;
    }
  }

  public int get_attack_damage() {
    return this.attack_damage;
  }
  
  public int get_attack_speed() {
    return this.attack_speed;
  }

  public double get_projectile_speed() {
    return this.projectile_speed;
  }

  public void set_cooldown(int new_cooldown) {
    this.cooldown = new_cooldown;
  }
  
  public Enemy get_enemies_in_range(ArrayList<Enemy> enemies){
    for (Enemy enemy : enemies) {
      double tmpX = enemy.getX();
      double tmpY = enemy.getY();
      // Compare radius of circle with
      // distance of its center from
      // given point
      double distX;
      double distY;
      if (tmpX - this.getX() >= 0) {
        distX = tmpX - this.getX();
      } else {
        distX = this.getX() - tmpX;
      }

      if (tmpY - this.getY() >= 0) {
        distY = tmpY - this.getY();
      } else {
        distY = this.getY() - tmpY;
      }

      double hypothenuse = Math.sqrt(Math.pow((distX), 2) + Math.pow((distY), 2));
      //System.out.println("" + String.valueOf(hypothenuse));
      if (hypothenuse <= this.attack_range) {
        return enemy;
      }

    }
    return null;
  }
  
  public boolean can_attack() {
    return this.cooldown == 0;
  }
  
  public void tick_cooldown() {
    if (this.cooldown > 0) {
      this.cooldown--;
    }
  }
}
