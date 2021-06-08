import javafx.scene.image.Image;
import java.util.ArrayList;

class Tower extends Basis {
  
  private double attackRange;
  private double attackDamage;
  private double attackSpeed;
  
  public Tower(Image sprite, double x, double y, double attackRange, double attackDamage, double attackSpeed) {
    super(sprite, x, y);
    this.attackRange = attackRange;
    this.attackDamage = attackDamage;
    this.attackSpeed = attackSpeed;
    this.getIV().setFitHeight(100);
    this.getIV().setFitWidth(100);
    this.setX(this.getX()-50);
    this.setY(this.getY()-50);
  }
  
  public double getAttackRange() {
    return this.attackRange;
  }
  
  public double getAttackDamage() {
    return this.attackDamage;
  }
  
  public double getAttackSpeed() {
    return this.attackSpeed;
  }
  
  public void setAttackRange(double newAttackRange) {
    this.attackRange = newAttackRange;
  }
  
  public void setAttackDamage(double newAttackDamage) {
    this.attackDamage = newAttackDamage;
  }
  
  public void setAttackSpeed(double newAttackSpeed) {
    this.attackSpeed = newAttackSpeed;
  }
  
  public Enemy checkIsEnemyInRange(ArrayList<Enemy> enemies){
    for (int i = 0;i < enemies.size(); i++) {
      double tmpX = enemies.get(i).getX();
      double tmpY = enemies.get(i).getY();
      // Compare radius of circle with
      // distance of its center from
      // given point
      if (Math.pow((tmpX - this.getX()+50), 2) + Math.pow((tmpY - this.getY()+50), 2) <= Math.pow(getAttackRange(), 2)){
        return enemies.get(i);
      }
      
    }
    return null;
  }
  
  }
