import javafx.scene.image.Image;
import java.lang.Math;


class Enemy extends Basis{
  private int hp;
  private int damage;
  private int moneyValue;
  private double speed;
  private int target;
  
  public Enemy(Image sprite, double x, double y, double speed, int hp, int damage, int moneyValue) {
    super(sprite, x, y);
    this.speed = speed;
    this.hp = hp;
    this.damage = damage;
    this.moneyValue = moneyValue;
    this.target = 0;
  }
  
  public int getHp(){
    return this.hp;
  }
  
  public int getDamage(){
    return this.damage;
  }
  
  public int getMoneyValue(){
    return this.moneyValue;
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
  
  public void setMoneyValue(int newMoneyValue) {
    this.moneyValue = newMoneyValue;
  }
  
  public void setSpeed(double newSpeed) {
    this.speed = newSpeed;
  }
  
  public void setTarget(int newTarget) {
    this.target = newTarget;
  }
  
  public void move(map_point[] path) {
    double totalx = path[this.getTarget()].getX() - this.getX();
    double totaly = path[this.getTarget()].getY() - this.getY();                                         
    
    double deg_alpha = Math.atan(totalx / totaly);
    
    double targetx = Math.sin(deg_alpha) * this.getSpeed();
    double targety = Math.cos(deg_alpha) * this.getSpeed();
    
    boolean x_pass = false;
    boolean y_pass = false;
    if (totalx >= 0) {
      this.setX(this.getX() + Math.abs(targetx));
      if (this.getX() >= path[this.getTarget()].getX()) {
        x_pass = true;
      }
    }
    else {
      this.setX(this.getX() - Math.abs(targetx));
      if (this.getX() <= path[this.getTarget()].getX()) {
        x_pass = true;
      }
    }
    
    if (totaly >= 0) {
      this.setY(this.getY() + Math.abs(targety));
      if (this.getY() >= path[this.getTarget()].getY()) {
        y_pass = true;
      }
    }
    else {
      this.setY(this.getY() - Math.abs(targety));
      if (this.getY() <= path[this.getTarget()].getY()) {
        y_pass = true;
      }
    }        
    
    if (x_pass == true && y_pass == true) {
      this.setTarget(this.getTarget() + 1);
    }
  }
}
