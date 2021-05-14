import javafx.scene.image.Image;


class Object{
  private int x;
  private int y;
  private int typeId;
  private Image sprite;
  
  public Object(Image sprite, int x, int y) {
    this.sprite = sprite;
    this.x = x;
    this.y = y;
  }
  
  public int getTypeId(){
    return this.typeid;
  }
 
  public int getX(){
    return this.x;
  }
  
  public int getY(){
    return this.y;
  }
  
  public Image getSprite() {
    return this.sprite; 
  }
  
  public void setTypeId(int newTypeId) {
    this.typeId = typeId;
  }
  
  public void setX(int newX) {
    this.x = newX;
  }
  
  public void setY(int newY){
    this.y = newY;
  }
  
  public void setSprite(Image newSprite) {
    this.sprite = newSprite;
  }
}
