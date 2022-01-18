public class map_point {
  
  private double x = 0.0;
  private double y = 0.0;
  
  public map_point(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public map_point(String[] split) {
    this(WindowDimensions.WIDTH / (1184 / Float.parseFloat(split[0])), WindowDimensions.HEIGHT / (795.0 / Float.parseFloat(split[1])));
  }

  
  public double getX() {
    return this.x;
  }
  
  public double getY() {
    return this.y;
  }
  
  public void setX(double newX) {
    this.x = newX;
  }
  
  public void setY(double newY) {
    this.y = newY;
  }
}  

