import javafx.scene.image.Image;
import java.lang.Math;

class Projectile extends Basis {
  
  private int damage;
  private double speed;
  private double targetX;
  private double targetY;
  private double stepX;
  private double stepY;
  private boolean hasPassedX;
  private boolean hasPassedY;
  
  public Projectile (Image sprite, double x, double y, double speed, int damage, double targetX, double targetY) {
    super(sprite, x, y);
    this.getIV().setFitHeight(50);
    this.getIV().setFitWidth(50);
    this.damage = damage;
    this.speed = speed;
    this.targetX = targetX;
    this.targetY = targetY;
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
    double totalx = this.targetX - this.getX();
    double totaly = this.targetY - this.getY();                                         
    
    double deg_alpha = Math.atan(totalx / totaly);
    
    this.stepX = Math.sin(deg_alpha) * this.getSpeed();
    this.stepY = Math.cos(deg_alpha) * this.getSpeed();
  }
  
  public void move() {
    if (this.targetX - this.getX() >= 0) {
      this.setX(this.getX() + Math.abs(stepX));
      if (this.getX() >= targetX) {
        this.hasPassedX = true;
      }
    }
    else {
      this.setX(this.getX() - Math.abs(stepX));
      if (this.getX() <= targetX) {
        this.hasPassedX = true;
      }
    }
    
    if (this.targetY - this.getY() >= 0) {
      this.setY(this.getY() + Math.abs(stepY));
      if (this.getY() >= targetY) {
        this.hasPassedY = true;
      }
    }
    else {
      this.setY(this.getY() - Math.abs(stepY));
      if (this.getY() <= targetY) {
        this.hasPassedY = true;
      }
    }        
  }
  
  public boolean hasPassedTarget() {
    if (hasPassedX && hasPassedY) {
      return true;
    }
    return false;
  }
}                         
