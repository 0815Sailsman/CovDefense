class Tower extends Basis {
  
  private double attackRange;
  private double attackDamage;
  private double attackSpeed;
  
  public Tower(Image sprite, double x, double y, double attackRange, double attackDamage, double attackSpeed) {
    super(sprite, x, y);
    this.attackRange = attackRange;
    this.attackDamage = attackDamage;
    this.attackSpeed = attackSpeed;
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
  
  }
