import javafx.scene.image.Image;

class Projectile extends Basis {
  
  private int damage;
  private double speed;
  
  public Projectile (Image sprite, double x, double y, double speed, int damage) {
    super(sprite, x, y);
    this.getIV().setFitHeight(25);
    this.getIV().setFitWidth(25);
    this.damage = damage;
    this.speed = speed;
  }
  
  public double getSpeed() {
    return this.speed;
  }
  
  public void setSpeed(double newSpeed) {
    this.speed = newSpeed; 
  }
  
  public int getDamage() {
    return this.Damage;
  }
  
  public void setDamage(int newDamage) {
    this.damage = newDamage;
  }
}                         
