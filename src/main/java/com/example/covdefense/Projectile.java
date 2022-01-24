package com.example.covdefense;

import javafx.scene.image.Image;
import java.lang.Math;
import java.util.ArrayList;

class Projectile extends Basis {
  
  private int damage;
  private final double speed;
  private final double targetX;
  private final double targetY;
  private double stepX;
  private double stepY;
  private boolean hasPassedX;
  private boolean hasPassedY;
  private final int deviation;
  private final double originX;
  private final double originY;

  public Projectile (Image sprite, Tower src_tower, Enemy target) {
    super(sprite, src_tower.getX(), src_tower.getY());
    this.originX = src_tower.getX();
    this.originY = src_tower.getY();
    this.getIV().setFitHeight(WindowDimensions.HEIGHT / 15.9);
    this.getIV().setFitWidth(WindowDimensions.WIDTH / 23.68);
    this.damage = src_tower.get_attack_damage();
    this.speed = src_tower.get_projectile_speed();
    this.targetX = target.getX();
    this.targetY = target.getY();
    this.deviation = (int) (WindowDimensions.WIDTH / 78.9);
    
    this.calc_steps();
  }
  
  public double getSpeed() {
    return this.speed;
  }

  public int getDamage() {
    return this.damage;
  }
  
  public void setDamage(int newDamage) {
    this.damage = newDamage;
  }

  public void calc_steps() {
    double total_x = Math.abs(this.targetX - this.getX());
    double total_y = Math.abs(this.targetY - this.getY());
    
    double deg_alpha = Math.atan(total_x / total_y);
    
    this.stepX = Math.sin(deg_alpha) * this.getSpeed();
    this.stepY = Math.cos(deg_alpha) * this.getSpeed();
  }
  
  public void move() {
    if (this.targetX - this.originX >= 0) {
      this.setX(this.getX() + this.stepX);
      if (this.getX() - this.deviation >= targetX) {
        this.hasPassedX = true;
      }
    }
    else {
      this.setX(this.getX() - this.stepX);
      if (this.getX() + this.deviation <= targetX) {
        this.hasPassedX = true;
      }
    }
    
    if (this.targetY - this.originY >= 0) {
      this.setY(this.getY() + this.stepY);
      if (this.getY() - this.deviation >= targetY) {
        this.hasPassedY = true;
      }
    }
    else {
      this.setY(this.getY() - this.stepY);
      if (this.getY() + this.deviation <= targetY) {
        this.hasPassedY = true;
      }
    }        
  }
  
  public boolean hasPassedTarget() {
    return this.hasPassedX && this.hasPassedY;
  }
  
  public Enemy checkIsEnemyOnProjectile(ArrayList<Enemy> enemies){
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

      if (Math.pow((distX), 2) + Math.pow((distY), 2) <= Math.pow(WindowDimensions.WIDTH / 59.2, 2)) {
        return enemy;
      }

    }
    return null;
  }
  
  public String toString() { 
      return "Projektil vom Typ " + this.getTypeId() + " an den Koordinaten " + this.getX() + " und " + this.getY() + " gespawnt.";
  } 
}                         
