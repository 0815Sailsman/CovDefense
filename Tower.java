import javafx.scene.image.Image;
import java.util.ArrayList;

class Tower extends Basis {
  
  private double attackRange;
  private int attackDamage;
  private int attackSpeed;
  private double projectileSpeed;
  private int cooldown;
  
  public Tower(Image sprite, double x, double y, double attackRange, int attackDamage, int attackSpeed, double projectileSpeed, int typeId) {
    super(sprite, x, y);
    this.attackRange = attackRange;
    this.attackDamage = attackDamage;
    this.attackSpeed = attackSpeed;
    this.projectileSpeed = projectileSpeed;
    this.cooldown = 0;
    this.getIV().setFitHeight(100);
    this.getIV().setFitWidth(100);
    this.setX(this.getX()-50);
    this.setY(this.getY()-50);
    this.setTypeId(typeId);
  }
  
  public double getAttackRange() {
    return this.attackRange;
  }
  
  public int getAttackDamage() {
    return this.attackDamage;
  }
  
  public int getAttackSpeed() {
    return this.attackSpeed;
  }
  
  public void setAttackRange(double newAttackRange) {
    this.attackRange = newAttackRange;
  }
  
  public void setAttackDamage(int newAttackDamage) {
    this.attackDamage = newAttackDamage;
  }
  
  public double getProjectileSpeed() {
    return this.projectileSpeed;
  }
  
  public void setProjectileSpeed(double newProjectileSpeed) {
    this.projectileSpeed = newProjectileSpeed;
  }
  
  public void setAttackSpeed(int newAttackSpeed) {
    this.attackSpeed = newAttackSpeed;
  }
  
  public int getCooldown() {
    return this.cooldown;
  }
  
  public void setCooldown(int newCooldown) {
    this.cooldown = newCooldown;
  }
  
  public Enemy checkIsEnemyInRange(ArrayList<Enemy> enemies){
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
      
      double hypothenuse = Math.sqrt(Math.pow((distX), 2) + Math.pow((distY), 2));
      //System.out.println("" + String.valueOf(hypothenuse));
      if (hypothenuse <= this.getAttackRange()){
        return enemies.get(i);
      }
      
    }
    return null;
  }
  
  public boolean canAttack() {
    if (this.cooldown == 0) {
      return true;
    }
    return false;
  }
  
  public void tickCooldown() {
    if (this.cooldown > 0) {
      this.cooldown--;
    }
  }
}
