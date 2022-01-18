import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageView.*;
import javafx.scene.input.MouseEvent;

public class Game {
  
  private ArrayList<map_point> path;
  private ArrayList<Runde> rounds;
  private int round_count;
  private ArrayList<Enemy> enemies;
  private ArrayList<Tower> towers;
  private ArrayList<Projectile> projectiles;
  private int hp;
  private int money;
  
  
  public Game() {
    path = load_map();
    rounds = load_rounds();;
    round_count = 0;
    
    enemies = new ArrayList<Enemy>();
    towers = new ArrayList<Tower>();
    projectiles = new ArrayList<Projectile>();
    
    hp = 100;
    money = 100;
  }

  private ArrayList<map_point> load_map() {
    ArrayList<map_point> path = new ArrayList<map_point>();
    try {
      File map_points_file = new File("map.txt");
      Scanner sc = new Scanner(map_points_file);
  
      while (sc.hasNextLine()) { 
        String text = sc.nextLine();
        String[] split = text.split(",");
        path.add(new map_point(split));
      }  
      sc.close();
    } catch(FileNotFoundException e) {
        System.out.println("Error, map-file missing");   
    }
    return path;
  }  
  
  private ArrayList<Runde> load_rounds() {
    ArrayList<Runde> rounds = new ArrayList<Runde>();
    int fileNr = 1;
    boolean worked = true;
    while (worked) {
      Runde tempRunde = new Runde(10);
      worked = tempRunde.loadRoundFromFile("Runden/Runde" + String.valueOf(fileNr) + ".txt");
      if (worked) {
        rounds.add(tempRunde);
      }
      else {
      }
      fileNr++;
    }
    return rounds;
  }    
  
  // Returns a list of projectiles that need to be drawn onto the root Pane
  public ArrayList<ImageView> tick_towers() {
    ArrayList<ImageView> results = new ArrayList<ImageView>();
    // Tick towers if they find enemies and spawn projectiles when needed
    for (int i = 0; i < this.towers.size(); i++) {
      Tower current_tower = this.towers.get(i);
      Enemy target = current_tower.get_enemies_in_range(enemies);
      if (target != null && current_tower.can_attack()) {
        // Spawn projectile flying towards enemy
        /*Typ1 ist Spritze für Spahn
          Typ2 ist Desifektionsmittel für Drosten
          Typ3
          Typ4
          Typ5
        */
        Image projectile_image;
        switch (current_tower.getTypeId()) {
          case 1: 
            projectile_image = new Image("projectiles/Spritze.png");
            break;
          case 2: 
            projectile_image = new Image("projectiles/Desinfektionsmittel.png");
            break;
          case 3:
            projectile_image = new Image("projectiles/Lockdown.png");
            break;
          case 4:
            projectile_image = new Image("projectiles/GG.png");
            break;
          case 5:
            projectile_image = new Image("projectiles/Maske.png");
            break;
          default:
            projectile_image = new Image("assets/hum.jpg");
            break;
        }

        Projectile projectile = new Projectile(projectile_image, current_tower, target);
        results.add(projectile.getIV());
        this.projectiles.add(projectile);
        current_tower.set_cooldown(current_tower.get_attack_speed());
      }
      current_tower.tick_cooldown();
    }
    return results;
  }
  
  public ArrayList<ImageView> tick_projectiles() {
    ArrayList<ImageView> results = new ArrayList<ImageView>();
    for (int i = 0; i < this.projectiles.size(); i++) {
      Projectile current_projectile = this.projectiles.get(i);
      if (current_projectile != null) {
        
        current_projectile.move();
        
        // Check for collision
        Enemy temp = current_projectile.checkIsEnemyOnProjectile(this.enemies);
        if (temp != null) {
          temp.setHp(temp.getHp() - current_projectile.getDamage());
          if (temp.getHp() <= 0) {
            this.money = this.money + temp.getMoneyValue();
            results.add(temp.getIV());
            this.enemies.remove(temp);
          }
        }
        
        // Check for despawn
        if (current_projectile.hasPassedTarget()) {
          results.add(current_projectile.getIV());
          this.projectiles.remove(current_projectile);
        }
      }
    } 
    return results;
  }

  public ArrayList<ImageView> spawn_enemies(int tick) {
    ArrayList<ImageView> results = new ArrayList<ImageView>();
    int new_enemy_ID = this.rounds.get(this.round_count).getEnemyOnFrame(tick);
    Enemy new_enemy;
    switch (new_enemy_ID) {
      case 1: 
        new_enemy = new Enemy(Enemy.ENEMY_RED);
        break;
      case 2: 
        new_enemy = new Enemy(Enemy.ENEMY_CYAN);
        break;
      case 3:
        new_enemy = new Enemy(Enemy.ENEMY_YELLOW);
        break;
      case 4:
        new_enemy = new Enemy(Enemy.ENEMY_GREEN);
        break;
      case 5:
        new_enemy = new Enemy(Enemy.ENEMY_PINK);
        break;
      case 6:
        new_enemy = new Enemy(Enemy.ENEMY_PURPLE);
        break;
      default:
        new_enemy = null;
    }
    if (new_enemy != null) {
      this.enemies.add(new_enemy);
      results.add(new_enemy.getIV()); 
    }
    return results;
  }
  
  public ArrayList<ImageView> tick_enemies() {
    ArrayList<ImageView> results = new ArrayList<ImageView>();
    for (int i = 0; i < this.enemies.size(); i++) {
      Enemy current_enemy = this.enemies.get(i);
      current_enemy.move(this.path);
      // Enemy reaches end
      if (current_enemy.getTarget() == (this.path.size()-1)) {
        this.hp = this.hp - current_enemy.getDamage();
        results.add(current_enemy.getIV());
        this.enemies.remove(this.enemies.get(i));
      }
    }
    return results;
  }
  
  public ArrayList<ImageView> check_for_round_end(int tick) {
    ArrayList<ImageView> results = new ArrayList<ImageView>();
    // Round over
    if (this.enemies.isEmpty() && this.rounds.get(round_count).length() < tick) {            
      // Add round reward
      this.money = this.money + 100 + 25 * this.round_count;
      this.round_count++;
      
      // Remove projectiles from screen
      for (int i = 0; i < this.projectiles.size(); i++) {
        Projectile current_projectile = projectiles.get(i);
        results.add(current_projectile.getIV());
      }
      
      // Remove projectiles from array
      for (int i = 0; i < this.projectiles.size(); i++) {
        Projectile current_projectile = projectiles.get(i);
        this.projectiles.remove(current_projectile);
      }
      this.projectiles = new ArrayList<Projectile>();
    }
    else {
      return null;
    } 
    return results;
  }

  public Tower place_tower_logic(MouseEvent event, Tower_parameters params) {
    double x = this.normalize_x(event.getSceneX());
    double y = this.normalize_y(event.getSceneY());
    
    if (y == -1){
        return null;
    }
    
    if (!coords_on_path(x,y) && !coords_on_tower(x,y)) { 
      if (this.money >= params.cost) {
        this.money = this.money - params.cost;
        Tower temptower = new Tower(x, y, params);
        this.towers.add(temptower);
        return temptower; 
      }      
    }
    return null;
  }  
  
  private double normalize_x(double old_x) {
    return old_x < 50 ? 50 : (old_x > WindowDimensions.WIDTH - 50 ? WindowDimensions.WIDTH - 50 : old_x);
  }
  
  private double normalize_y(double old_y) {
    return old_y < 50 ? 50 : (old_y > WindowDimensions.HEIGHT / 1.1357 ? -1 : (old_y > (WindowDimensions.HEIGHT / 1.1357) - 50 ? (WindowDimensions.HEIGHT / 1.1357) - 50 : old_y));
  }
  
  public boolean coords_on_path(double x, double y){
    int radius = (int) (WindowDimensions.WIDTH / 29.6);
    for (int i = 0;i < this.get_path().size(); i++) {
      double tmpX = this.get_path().get(i).getX();
      double tmpY = this.get_path().get(i).getY();
      if ((x > (tmpX-radius)&&x < (tmpX+radius))&&(y > (tmpY-radius)&&y < (tmpY+radius))) {
        return true; 
      }
      
    }
    return false;
  }
  
  public boolean coords_on_tower(double x, double y){
    for (int i = 0;i < this.get_towers().size(); i++) {
      double tmpX = this.get_towers().get(i).getX();
      double tmpY = this.get_towers().get(i).getY();
      if ((x > (tmpX + (WindowDimensions.WIDTH / (-23.68))) && (x < (tmpX + (WindowDimensions.WIDTH / 7.893)))) && (y > (tmpY + (WindowDimensions.WIDTH / (-23.68))) && (y < (tmpY + (WindowDimensions.WIDTH / 7.893))))) {
        return true; 
      }
      
    }
    return false;
  }
  
  //**********************************
  // AB HIER NURNOCH GETTER UND SETTER
  //**********************************
  public int get_money() {
    return this.money;
  }
  
  public void set_money(int new_value) {
    this.money = new_value;
  }

  public int get_hp() {
    return this.hp;
  }
  
  public void set_hp(int new_hp) {
    this.hp = hp;
  }
  
  public ArrayList<map_point> get_path() {
    return this.path;
  }

  public ArrayList<Runde> get_rounds() {
    return this.rounds;
  }
  
  public int get_round_count() {
    return this.round_count;
  }

  public void set_round_count(int new_round_count) {
    this.round_count = new_round_count;
  }
  
  public ArrayList<Enemy> get_enemies() {
    return this.enemies;
  }

  public ArrayList<Tower> get_towers() {
    return this.towers;
  }

  public ArrayList<Projectile> get_projectiles() {
    return this.projectiles;
  }
  
}
