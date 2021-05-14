class Object{
  private int posX;
  private int posY;
  private int typeid;
  private Image sprite;
  
  public int gettypeid(){
    return this.typeid;
  }
 
  public int getposX(){
    return this.posX;
  }
  
  public int getposY(){
    return this.posY;
  } 
  
  public void setSprite(){
    Image imgIcon = new Image(getClass().getResource("Pfad/icon.png").toExternalForm());
  }
}
