class Enemy extends Objeckt{
  private int type;
  private float speed;
  
  public int getType(){
    return this.type;
  }
  
  public float getSpeed(){
    return this.speed;
  }
  
  public void setType(int newType) {
    this.type = newType;
  }
  
  public void setSpeed(int newSpeed) {
    this.speed = newSpeed;
  }
  
}
