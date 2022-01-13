import javafx.scene.image.Image;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class Tower extends Basis {
  
  private double attack_range;
  private int attack_damage;
  private int attack_speed;
  private double projectile_speed;
  private int cooldown;
  
  public static final int REZO = 0;
  public static final int DROSTEN = 1;
  public static final int LAUTERBACH = 2;
  public static final int SPAHN = 3;
  public static final int MERKEL = 4;
  
  
  public Tower(double x, double y, Tower_parameters params) {
    super(new Image(params.sprite_path), x, y);
    this.attack_range = params.attack_range;
    this.attack_damage = params.attack_damage;
    this.attack_speed = params.attack_speed;
    this.projectile_speed = params.projectile_speed;
    this.cooldown = 0;
    this.getIV().setFitHeight(100);
    this.getIV().setFitWidth(100);
    this.setX(this.getX()-50);
    this.setY(this.getY()-50);
    this.setTypeId(params.type_id);
  }

  public static Tower_parameters load_tower_parameters(String tower_src_file_path) {
    Tower_parameters params = new Tower_parameters();
    try {
      File tower_src_file = new File(tower_src_file_path);
      Scanner sc = new Scanner(tower_src_file);
  
      while (sc.hasNextLine()) { 
        String text = sc.nextLine();
        String[] split = text.split(":");
        params.add(split[1].strip());
      }  
      sc.close();      
    } catch(FileNotFoundException e) {
        System.out.println("Error, tower-file missing");   
    }
    return params;
  }
  
  public static String id_to_path(int id) {
    switch (id) {
      case 0: 
        return "towers/Rezo.txt";
      case 1: 
        return "towers/Drosten.txt";
      case 2: 
        return "towers/Lauterbach.txt";
      case 3: 
        return "towers/Spahn.txt";
      case 4: 
        return "towers/Merkel.txt";        
      default: 
        return null;
    }
  }
  
  public double get_attack_range() {
    return this.attack_range;
  }
  
  public int get_attack_damage() {
    return this.attack_damage;
  }
  
  public int get_attack_speed() {
    return this.attack_speed;
  }
  
  public void set_attack_range(double new_attack_range) {
    this.attack_range = new_attack_range;
  }
  
  public void set_attack_damage(int new_attack_damage) {
    this.attack_damage = new_attack_damage;
  }
  
  public double get_projectile_speed() {
    return this.projectile_speed;
  }
  
  public void set_projectile_speed(double new_projectile_speed) {
    this.projectile_speed = new_projectile_speed;
  }
  
  public void set_attack_speed(int new_attack_speed) {
    this.attack_speed = new_attack_speed;
  }
  
  public int get_cooldown() {
    return this.cooldown;
  }
  
  public void set_cooldown(int new_cooldown) {
    this.cooldown = new_cooldown;
  }
  
  public Enemy get_enemies_in_range(ArrayList<Enemy> enemies){
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
      if (hypothenuse <= this.attack_range){
        return enemies.get(i);
      }
      
    }
    return null;
  }
  
  public boolean can_attack() {
    if (this.cooldown == 0) {
      return true;
    }
    return false;
  }
  
  public void tick_cooldown() {
    if (this.cooldown > 0) {
      this.cooldown--;
    }
  }
}
