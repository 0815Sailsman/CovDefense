import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageView.*;

   
class Basis{
  private double x;
  private double y;
  private int typeId;
  private Image sprite;
  private ImageView iv;
  
  public Basis(Image sprite, double x, double y) {
    this.sprite = sprite;
    this.x = x;
    this.y = y;
    this.iv = new ImageView();
    this.iv.setImage(sprite);
    this.iv.setX(x);
    this.iv.setY(y);
  }
  
  public int getTypeId(){
    return this.typeId;
  }
 
  public double getX(){
    return this.x;
  }
  
  public double getY(){
    return this.y;
  }
  
  public Image getSprite() {
    return this.sprite; 
  }
  
  public ImageView getIV() {
    return this.iv;
  }
  
  public void setTypeId(int newTypeId) {
    this.typeId = typeId;
  }
  
  public void setX(double newX) {
    this.x = newX;
    this.iv.setX(newX);
  }
  
  public void setY(double newY){
    this.y = newY;
    this.iv.setY(newY);
  }
  
  public void setSprite(Image newSprite) {
    this.sprite = newSprite;
  }
  
  public void setIV(ImageView newIV) {
    this.iv = newIV;
  }
}
