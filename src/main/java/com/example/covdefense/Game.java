package com.example.covdefense;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
  
  private final ArrayList<Map_point> path;
  private final ArrayList<Runde> rounds;
  private int round_count;
  private final ArrayList<Enemy> enemies;
  private final ArrayList<Tower> towers;
  private ArrayList<Projectile> projectiles;
  private int hp;
  private int money;
  
  
  public Game() {
    path = load_map();
    rounds = load_rounds();
    round_count = 0;
    
    enemies = new ArrayList<>();
    towers = new ArrayList<>();
    projectiles = new ArrayList<>();
    
    hp = 100;
    money = 100;
  }

  private ArrayList<Map_point> load_map() {
    ArrayList<Map_point> path = new ArrayList<>();
    try {
      File map_points_file = new File("map.txt");
      Scanner sc = new Scanner(map_points_file);
  
      while (sc.hasNextLine()) { 
        String text = sc.nextLine();
        String[] split = text.split(",");
        path.add(new Map_point(split));
      }  
      sc.close();
    } catch(FileNotFoundException e) {
        System.out.println("Error, map-file missing");   
    }
    return path;
  }  
  
  private ArrayList<Runde> load_rounds() {
    ArrayList<Runde> rounds = new ArrayList<>();
    int fileNr = 1;
    boolean worked = true;
    while (worked) {
      Runde tempRunde = new Runde(10);
      worked = tempRunde.loadRoundFromFile("Runden/Runde" + fileNr + ".txt");
      if (worked) {
        rounds.add(tempRunde);
      }
      fileNr++;
    }
    return rounds;
  }    
  
  // Returns a list of projectiles that need to be drawn onto the root Pane
  public ArrayList<ImageView> tick_towers() {
    ArrayList<ImageView> results = new ArrayList<>();
    // Tick towers if they find enemies and spawn projectiles when needed
    for (Tower current_tower : this.towers) {
      Enemy target = current_tower.get_enemies_in_range(enemies);
      if (target != null && current_tower.can_attack()) {
        Image projectile_image;
        switch (current_tower.getTypeId()) {
          case 1:
            projectile_image = new Image("/Spritze.png");
            break;
          case 2:
            projectile_image = new Image("/Desinfektionsmittel.png");
            break;
          case 3:
            projectile_image = new Image("/Lockdown.png");
            break;
          case 4:
            projectile_image = new Image("/GG.png");
            break;
          case 5:
            projectile_image = new Image("/Maske.png");
            break;
          default:
            projectile_image = new Image("/hum.jpg");
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
    ArrayList<ImageView> results = new ArrayList<>();
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
    ArrayList<ImageView> results = new ArrayList<>();
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
    ArrayList<ImageView> results = new ArrayList<>();
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
    ArrayList<ImageView> results = new ArrayList<>();
    // Round over
    if (this.enemies.isEmpty() && this.rounds.get(round_count).length() < tick) {            
      // Add round reward
      this.money = this.money + 100 + 25 * this.round_count;
      this.round_count++;
      
      // Remove projectiles from screen
      for (Projectile current_projectile : this.projectiles) {
        results.add(current_projectile.getIV());
      }
      
      // Remove projectiles from array
      for (int i = 0; i < this.projectiles.size(); i++) {
        Projectile current_projectile = projectiles.get(i);
        this.projectiles.remove(current_projectile);
      }
      this.projectiles = new ArrayList<>();
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
      this.money = this.money - params.cost;
      Tower temp_tower = new Tower(x, y, params);
      this.towers.add(temp_tower);
      return temp_tower;
    }
    return null;
  }  
  
  private double normalize_x(double old_x) {
    return old_x < 50 ? 50 : (Math.min(old_x, WindowDimensions.WIDTH - 50));
  }
  
  private double normalize_y(double old_y) {
    return old_y < 50 ? 50 : (old_y > WindowDimensions.HEIGHT / 1.1357 ? -1 : (Math.min(old_y, (WindowDimensions.HEIGHT / 1.1357) - 50)));
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
      double radius1 = WindowDimensions.WIDTH / (-23.68);
      double radius2 = WindowDimensions.WIDTH / 7.893;
      if ((x > (tmpX + radius1) && (x < (tmpX + radius2))) && (y > (tmpY + radius1) && (y < (tmpY + radius2)))) {
        return true; 
      }
      
    }
    return false;
  }
  
  //**************************************
  // ONLY GETTERS AND SETTERS FROM HERE ON
  //**************************************
  public int get_money() {
    return this.money;
  }

  public int get_hp() {
    return this.hp;
  }

  public ArrayList<Map_point> get_path() {
    return this.path;
  }

  public ArrayList<Runde> get_rounds() {
    return this.rounds;
  }
  
  public int get_round_count() {
    return this.round_count;
  }

  public ArrayList<Tower> get_towers() {
    return this.towers;
  }

}
