package com.example.covdefense;

import javafx.scene.image.Image;
import java.lang.Math;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class Enemy extends Basis{
  private int hp;
  private int damage;
  private final int money_value;
  private final double speed;
  private int target;
  
  public static final int ENEMY_RED = 0;
  public static final int ENEMY_CYAN = 1;
  public static final int ENEMY_YELLOW = 2;
  public static final int ENEMY_GREEN = 3;
  public static final int ENEMY_PINK = 4;
  public static final int ENEMY_PURPLE = 5;
  
  public Enemy(Enemy_parameters params) {
    super(new Image(params.sprite_path), (WindowDimensions.WIDTH / (1184.0 / params.x)), (WindowDimensions.HEIGHT / (795.0 / params.y)));
    this.getIV().setFitHeight(WindowDimensions.HEIGHT / 15.9);
    this.getIV().setFitWidth(WindowDimensions.WIDTH / 13.93);
    this.speed = params.speed;
    this.hp = params.hp;
    this.damage = params.damage;
    this.money_value = params.money_value;
    this.target = 0;
  }
  
  public Enemy(int enemy_id) {
    this(load_enemy_parameters(id_to_path(enemy_id)));
  }

      
  public static Enemy_parameters load_enemy_parameters(String enemy_src_file_path) {
    Enemy_parameters params = new Enemy_parameters();
    try {
      File enemy_src_file = new File(enemy_src_file_path);
      Scanner sc = new Scanner(enemy_src_file);
  
      while (sc.hasNextLine()) { 
        String text = sc.nextLine();
        String[] split = text.split(":");
        params.add(split[1].strip());
      }  
      sc.close();      
    } catch(FileNotFoundException e) {
        System.out.println("Error, enemy-file missing");   
    }
    return params;
  }
  
  private static String id_to_path(int id) {
    switch (id) {
      case 0: 
        return "enemies/red_enemy.txt";
      case 1: 
        return "enemies/cyan_enemy.txt";
      case 2:
        return "enemies/yellow_enemy.txt";
      case 3:
        return "enemies/green_enemy.txt";
      case 4:
        return "enemies/pink_enemy.txt";
      case 5:
        return "enemies/purple_enemy.txt";
      default: 
        return null;
    }
  }

  
  public int getHp(){
    return this.hp;
  }
  
  public int getDamage(){
    return this.damage;
  }
  
  public int getMoneyValue(){
    return this.money_value;
  }
  
  public double getSpeed(){
    return this.speed;
  }
  
  public int getTarget() {
    return this.target;
  }
  
  public void setHp(int newHp) {
    this.hp = newHp;
  }
  
  public void setDamage(int newDamage) {
    this.damage = newDamage;
  }

  public void setTarget(int newTarget) {
    this.target = newTarget;
  }
  
  public void move(ArrayList<map_point> path) {
    double total_x = path.get(this.getTarget()).getX() - this.getX();
    double total_y = path.get(this.getTarget()).getY() - this.getY();
    
    double deg_alpha = Math.atan(total_x / total_y);
    
    double target_x = Math.sin(deg_alpha) * this.getSpeed();
    double target_y = Math.cos(deg_alpha) * this.getSpeed();
    
    boolean x_pass = false;
    boolean y_pass = false;
    if (total_x >= 0) {
      this.setX(this.getX() + Math.abs(target_x));
      if (this.getX() >= path.get(this.getTarget()).getX()) {
        x_pass = true;
      }
    }
    else {
      this.setX(this.getX() - Math.abs(target_x));
      if (this.getX() <= path.get(this.getTarget()).getX()) {
        x_pass = true;
      }
    }
    
    if (total_y >= 0) {
      this.setY(this.getY() + Math.abs(target_y));
      if (this.getY() >= path.get(this.getTarget()).getY()) {
        y_pass = true;
      }
    }
    else {
      this.setY(this.getY() - Math.abs(target_y));
      if (this.getY() <= path.get(this.getTarget()).getY()) {
        y_pass = true;
      }
    }        
    
    if (x_pass && y_pass) {
      this.setTarget(this.getTarget() + 1);
    }
  }
}
