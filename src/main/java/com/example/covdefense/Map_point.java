package com.example.covdefense;

public class Map_point {
  
  private final double x;
  private final double y;
  
  public Map_point(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public Map_point(String[] split) {
    this(WindowDimensions.WIDTH / (1184 / Float.parseFloat(split[0])), WindowDimensions.HEIGHT / (795.0 / Float.parseFloat(split[1])));
  }

  
  public double getX() {
    return this.x;
  }
  
  public double getY() {
    return this.y;
  }

}

