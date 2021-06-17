import javafx.scene.image.Image;
import java.lang.Math;
import java.util.ArrayList;

class Projectile extends Basis {
  
  private int damage;
  private double speed;
  private double targetX;
  private double targetY;
  private double stepX;
  private double stepY;
  private boolean hasPassedX;
  private boolean hasPassedY;
  private int deviation;
  private double originX;
  private double originY;
  
  public Projectile (Image sprite, double x, double y, double speed, int damage, double targetX, double targetY) {
    super(sprite, x, y);
    this.originX = x;
    this.originY = y;
    this.getIV().setFitHeight(50);
    this.getIV().setFitWidth(50);
    this.damage = damage;
    this.speed = speed;
    this.targetX = targetX;
    this.targetY = targetY;
    this.deviation = 15;
  }
  
  public double getSpeed() {
    return this.speed;
  }
  
  public void setSpeed(double newSpeed) {
    this.speed = newSpeed; 
  }
  
  public int getDamage() {
    return this.damage;
  }
  
  public void setDamage(int newDamage) {
    this.damage = newDamage;
  }
  
  public double getTargetX() {
    return this.targetX;
  }
  
  public void setTargetX(double newTargetX) {
    this.targetX = newTargetX;
  }
  
  public double getTargetY() {
    return this.targetY;
  }
  
  public void setTargetY(double newTargetY) {
    this.targetY = newTargetY;
  }
  
  public void calcSteps() {
    double totalx = Math.abs(this.targetX - this.getX());
    double totaly = Math.abs(this.targetY - this.getY());                                         
    
    double deg_alpha = Math.atan(totalx / totaly);
    
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
    if (this.hasPassedX && this.hasPassedY) {
      return true;
    }
    return false;
  }
  
  public Enemy checkIsEnemyOnProjectile(ArrayList<Enemy> enemies){
    for (int i = 0;i < enemies.size(); i++) {
      double tmpX = enemies.get(i).getX();
      double tmpY = enemies.get(i).getY();
      // Compare radius of circle with
      // distance of its center from
      // given point
      double distX;
      double distY;
      if (tmpX - this.getX() >= 0) {
        distX = tmpX - this.getX();
      }
      else {
        distX = this.getX() - tmpX;
      }
      
      if (tmpY - this.getY() >= 0) {
        distY = tmpY - this.getY();
      }
      else {
        distY = this.getY() - tmpY;
      }   
      
      if (Math.pow((distX), 2) + Math.pow((distY), 2) <= Math.pow(20, 2)){
        return enemies.get(i);
      }
      
    }
    return null;
  }
  
  public String toString() { 
      return "Projektil vom Typ " + this.getTypeId() + " an den Koordinaten " + this.getX() + " und " + this.getY() + " gespawnt.";
  } 
}                         