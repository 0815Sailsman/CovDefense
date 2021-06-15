import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageView.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.animation.PauseTransition;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;
import javafx.scene.layout.BackgroundImage;
import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.util.Duration;
import java.util.Scanner;
import javafx.scene.input.MouseEvent;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.layout.*;


/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 18.05.2021
 * @author 
 */

public class cleanEngine extends Application {
  // Anfang Attribute
  Pane root = new Pane();
  private Button button_play = new Button();
  private ImageView iv1 = new ImageView();
  private ImageView ivbg = new ImageView();
  private ImageView ivbum = new ImageView();
  
  private map_point mp1 = new map_point(555.0, 0.0);
  private map_point mp2 = new map_point(555.0, 230.0);
  private map_point mp3 = new map_point(440.0, 300.0);
  private map_point mp4 = new map_point(430.0, 500.0);
  private map_point mp5 = new map_point(160.0, 550.0);
  private map_point mp6 = new map_point(310.0, 250.0);
  private map_point mp7 = new map_point(400.0, 210.0);
  private map_point mp8 = new map_point(440.0, 220.0);
  private map_point mp9 = new map_point(400.0, 300.0);
  private map_point mp10 = new map_point(555.0, 220.0);
  private map_point mp11 = new map_point(670.0, 300.0);
  private map_point mp12 = new map_point(730.0, 530.0);
  private map_point mp13 = new map_point(980.0, 550.0);
  private map_point mp14 = new map_point(740.0, 220.0);
  private map_point mp15 = new map_point(700.0, 220.0);
  private map_point mp16 = new map_point(710.0, 290.0);
  private map_point mp17 = new map_point(560.0, 220.0);
  private map_point mp18 = new map_point(550.0, 600.0);
  private map_point mp19 = new map_point(400.0, 530.0);
  private map_point mp20 = new map_point(310.0, 550.0);
  private map_point mp21 = new map_point(140.0, 530.0);
  private map_point mp22 = new map_point(420.0, 310.0);
  private map_point mp23 = new map_point(440.0, 230.0);
  private map_point mp24 = new map_point(680.0, 400.0);
  private map_point mp25 = new map_point(690.0, 470.0);
  private map_point mp26 = new map_point(740.0, 550.0);
  private map_point mp27 = new map_point(1000.0, 520.0);
  private map_point mp29 = new map_point(890.0, 390.0);
  private map_point mp30 = new map_point(800.0, 260.0);
  private map_point mp31 = new map_point(680.0, 300.0);
  
  private map_point[] path = {
  mp1, mp2, mp3, mp4, mp19, mp20, mp5, mp21, mp6, mp7, mp8, mp23, mp9, mp22, mp10, mp11, mp24, mp25, mp12, mp26, 
  mp13, mp27, mp29, mp30, mp14, mp15, mp16, mp31, mp17, mp18 
    };
  private ArrayList<Runde> rounds = new ArrayList<Runde>();
  private int roundCount = 0;
  
  private Timeline loop;
  
  private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  private ArrayList<Tower> towers = new ArrayList<Tower>();
  private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
  
  int tick;
  private int hp = 100;
  private int money = 100;
  private Button button_Spahn = new Button();
  
  private boolean running = false;
  private boolean bumming = false;
  private Label labelHP = new Label();
  private Button button_Drosten = new Button();
  private Button button_Merkel = new Button();
  private Button button_Rezo = new Button();
  private Button button_Lauterbach = new Button();
  private Label labelMoney = new Label();
  private Label label1 = new Label();
  private Label label2 = new Label();
  private Label label4 = new Label();
  private Label label6 = new Label();
  private Label label7 = new Label();
  // Ende Attribute
  
  public void start(Stage primaryStage) { 
    
    Scene scene = new Scene(root, 1184, 785);
    // Anfang Komponenten
    Image bg = new Image("assets/bg.png");
    ivbg.setImage(bg);
    ivbg.setFitHeight(700);
    ivbg.setFitWidth(1200);
    root.getChildren().add(ivbg); 
    
    button_play.setLayoutX(16);
    button_play.setLayoutY(725);
    button_play.setPrefHeight(25);
    button_play.setPrefWidth(75);
    button_play.setOnAction(
      (event) -> {button_play_Action(event);} 
    );
    button_play.setText("play");
    root.getChildren().add(button_play);
    
    button_Spahn.setLayoutX(465);
    button_Spahn.setLayoutY(725);
    button_Spahn.setPrefHeight(25);
    button_Spahn.setPrefWidth(75);
    button_Spahn.setOnAction(
      (event) -> {button_Spahn_Action(event);} 
    );
    button_Spahn.setText("");
    root.getChildren().add(button_Spahn);
    labelHP.setLayoutX(1036);
    labelHP.setLayoutY(13);
    labelHP.setPrefHeight(28);
    labelHP.setPrefWidth(126);
    labelHP.setText("HP: 100");
    labelHP.setAlignment(Pos.CENTER);
    labelHP.setFont(Font.font("Dialog", 22));
    labelHP.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    root.getChildren().add(labelHP);
    button_Drosten.setLayoutX(295);
    button_Drosten.setLayoutY(725);
    button_Drosten.setPrefHeight(25);
    button_Drosten.setPrefWidth(75);
    button_Drosten.setOnAction(
      (event) -> {button_Drosten_Action(event);} 
    );
    root.getChildren().add(button_Drosten);
    button_Merkel.setLayoutX(550);
    button_Merkel.setLayoutY(725);
    button_Merkel.setPrefHeight(25);
    button_Merkel.setPrefWidth(75);
    button_Merkel.setOnAction(
      (event) -> {button_Merkel_Action(event);} 
    );
    root.getChildren().add(button_Merkel);
    button_Rezo.setLayoutX(210);
    button_Rezo.setLayoutY(725);
    button_Rezo.setPrefHeight(25);
    button_Rezo.setPrefWidth(75);
    button_Rezo.setOnAction(
      (event) -> {button_Rezo_Action(event);} 
    );
    root.getChildren().add(button_Rezo);
    button_Lauterbach.setLayoutX(380);
    button_Lauterbach.setLayoutY(725);
    button_Lauterbach.setPrefHeight(25);
    button_Lauterbach.setPrefWidth(75);
    button_Lauterbach.setOnAction(
      (event) -> {button_Lauterbach_Action(event);} 
    );
    root.getChildren().add(button_Lauterbach);
    labelMoney.setLayoutX(1033);
    labelMoney.setLayoutY(57);
    labelMoney.setPrefHeight(28);
    labelMoney.setPrefWidth(140);
    labelMoney.setText("Money: 100$");
    labelMoney.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    labelMoney.setAlignment(Pos.CENTER);
    labelMoney.setFont(Font.font("Dialog", 22));
    root.getChildren().add(labelMoney);
    label1.setLayoutX(230);
    label1.setLayoutY(705);
    label1.setPrefHeight(20);
    label1.setPrefWidth(78);
    label1.setText("100$");
    root.getChildren().add(label1);
    label2.setLayoutX(315);
    label2.setLayoutY(705);
    label2.setPrefHeight(20);
    label2.setPrefWidth(78);
    label2.setText("500$");
    root.getChildren().add(label2);
    label4.setLayoutX(400);
    label4.setLayoutY(705);
    label4.setPrefHeight(20);
    label4.setPrefWidth(78);
    label4.setText("1500$");
    root.getChildren().add(label4);
    label6.setLayoutX(485);
    label6.setLayoutY(705);
    label6.setPrefHeight(20);
    label6.setPrefWidth(78);
    label6.setText("2500$");
    root.getChildren().add(label6);
    label7.setLayoutX(570);
    label7.setLayoutY(705);
    label7.setPrefHeight(20);
    label7.setPrefWidth(78);
    label7.setText("5000$");
    root.getChildren().add(label7);
    // Ende Komponenten
    //add_img();
    
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    primaryStage.setTitle("cleanEngine");
    primaryStage.setScene(scene);
    primaryStage.show();
    
    load_rounds();
   
    Image img_SpahnButton = new Image("assets/Spahn.png");
    ImageView SpahnButtonView = new ImageView(img_SpahnButton);
    SpahnButtonView.setFitHeight(50);
    SpahnButtonView.setFitWidth(50);
    button_Spahn.setGraphic(SpahnButtonView);
    
    Image img_DrostenButton = new Image("assets/Drosten.png");
    ImageView DrostenButtonView = new ImageView(img_DrostenButton);
    DrostenButtonView.setFitHeight(50);
    DrostenButtonView.setFitWidth(50);
    button_Drosten.setGraphic(DrostenButtonView);
    
    Image img_MerkelButton = new Image("assets/Merkel.png");
    ImageView MerkelButtonView = new ImageView(img_MerkelButton);
    MerkelButtonView.setFitHeight(50);
    MerkelButtonView.setFitWidth(50);
    button_Merkel.setGraphic(MerkelButtonView);
    
    Image img_RezoButton = new Image("assets/Rezo.png");
    ImageView RezoButtonView = new ImageView(img_RezoButton);
    RezoButtonView.setFitHeight(50);
    RezoButtonView.setFitWidth(50);
    button_Rezo.setGraphic(RezoButtonView);
    
    Image img_LauterbachButton = new Image("assets/Lauterbach.png");
    ImageView LauterbachButtonView = new ImageView(img_LauterbachButton);
    LauterbachButtonView.setFitHeight(50);
    LauterbachButtonView.setFitWidth(50);
    button_Lauterbach.setGraphic(LauterbachButtonView);
  }
  // Anfang Methoden
  
  public static void main(String[] args) {
    launch(args);
  } // end of main
  
  public void move_img() {
    tick = 0;   
    loop = new Timeline(new KeyFrame(Duration.millis(33), new EventHandler<ActionEvent>() {  
        @Override
        public void handle(final ActionEvent t) {
        // Tick towers if they find enemies and spawn projectiles when needed
        for (int i = 0; i < towers.size(); i++) {
          Tower current_tower = towers.get(i);
          Enemy target = current_tower.checkIsEnemyInRange(enemies);
          if (target != null && current_tower.canAttack()) {
            // Spawn projectile flying towards enemy
            /*Typ1 ist Spritze für Spahn
              Typ2 ist Desifektionsmittel für Drosten
              Typ3
              Typ4
              Typ5
            */
            Image projectileImage = new Image("assets/hum.jpg"); 
            if (current_tower.getTypeId()==1) {
              projectileImage = new Image("Projektile/Spritze.png"); 
            } else if (current_tower.getTypeId()==2) {
              projectileImage = new Image("Projektile/Desinfektionsmittel.png");   
            } else if (current_tower.getTypeId()==3) {
              projectileImage = new Image("Projektile/Lockdown.png");  
            } else if (current_tower.getTypeId()==4) {
              projectileImage = new Image("Projektile/GG.png");  
            } else if (current_tower.getTypeId()==5) {
              projectileImage = new Image("Projektile/Maske.png");  
            } 
            Projectile projectile = new Projectile(projectileImage, current_tower.getX(), current_tower.getY(), current_tower.getProjectileSpeed(), current_tower.getAttackDamage(), target.getX(), target.getY());
            projectile.calcSteps();
            root.getChildren().add(projectile.getIV());
            projectiles.add(projectile);
            current_tower.setCooldown(current_tower.getAttackSpeed());
          }
          current_tower.tickCooldown();
        }
        
        // Tick projectiles to move, check collision and potentially
        for (int i = 0; i < projectiles.size(); i++) {
          Projectile current_projectile = projectiles.get(i);
          if (current_projectile != null) {
            
            current_projectile.move();
            
            // Check for collision
            Enemy temp = current_projectile.checkIsEnemyOnProjectile(enemies);
            if (temp != null) {
              temp.setHp(temp.getHp() - current_projectile.getDamage());
              if (temp.getHp() <= 0) {
                money = money + temp.getMoneyValue();
                labelMoney.setText("Money: " + String.valueOf(money));
                root.getChildren().remove(root.getChildren().indexOf(temp.getIV()));
                enemies.remove(temp);
              }
            }
            
            // Check for despawn
            if (current_projectile.hasPassedTarget()) {
              root.getChildren().remove(root.getChildren().indexOf(current_projectile.getIV()));
              projectiles.remove(current_projectile);
            }
          }
        } 
        
        // Here are the new enemies being spawned
        int enemyIdToSpawn = rounds.get(roundCount).getEnemyOnFrame(tick);
        if (enemyIdToSpawn == 1) {
          Enemy redEnemy = new Enemy(new Image("assets//red.png"), 555.0, 1.0, 7.0, 1, 1, 5);
          enemies.add(redEnemy);  
          root.getChildren().add(redEnemy.getIV());
        }
        else if (enemyIdToSpawn == 2) {
          Enemy cyanEnemy = new Enemy(new Image("assets//cyan.png"), 555.0, 1.0, 9.0, 3, 2, 10);
          enemies.add(cyanEnemy);  
          root.getChildren().add(cyanEnemy.getIV());
        }
        else if (enemyIdToSpawn == 3) {
          Enemy yellowEnemy = new Enemy(new Image("assets//yellow.png"), 555.0, 1.0, 12.0, 5, 5, 20);
          enemies.add(yellowEnemy);  
          root.getChildren().add(yellowEnemy.getIV());
        }
        else if (enemyIdToSpawn == 4) {
          Enemy greenEnemy = new Enemy(new Image("assets//green.png"), 555.0, 1.0, 20.0, 5, 5, 30);
          enemies.add(greenEnemy);  
          root.getChildren().add(greenEnemy.getIV());
        }
        else if (enemyIdToSpawn == 5) {
          Enemy pinkEnemy = new Enemy(new Image("assets//pink.png"), 555.0, 1.0, 5.0, 150, 100, 1000);
          enemies.add(pinkEnemy);  
          root.getChildren().add(pinkEnemy.getIV());
        }
        else if (enemyIdToSpawn == 6) {
          Enemy purpleEnemy = new Enemy(new Image("assets//purple.png"), 555.0, 1.0, 12.0, 200, 50, 100);
          // centering vom großen anpassen, sonst den hal klein lassn
          purpleEnemy.getIV().setFitHeight(100);
          purpleEnemy.getIV().setFitWidth(170);
          enemies.add(purpleEnemy);  
          root.getChildren().add(purpleEnemy.getIV());
        }
        
        /*
        Eigentliche Konstellation der EnemyIds sind
        Hier geordnet nach ID und gleichzeitig mit steigender Stärke
        1 -> Rot
        2 -> Cyan
        3 -> Gelb
        4 -> Grün
        5 -> Pink
        6 -> Lila
        */
        
        // Here are all enemies being moved
        for (int i = 0; i < enemies.size(); i++) {
          Enemy current_enemy = enemies.get(i);
          current_enemy.move(path);
          
          // Gegner erreicht Ende
          if (current_enemy.getTarget() == path.length) {
            if (bumming == false) {
              Image bum = new Image("assets/bum.png");
              ivbum.setImage(bum);
              ivbum.setX(500.0);
              ivbum.setY(550.0);
              ivbum.setFitHeight(100);
              ivbum.setFitWidth(200);
              root.getChildren().add(ivbum);
              bumming = true;
            }
            hp = hp - current_enemy.getDamage();
            labelHP.setText("HP: " + String.valueOf(hp));
            root.getChildren().remove(root.getChildren().indexOf(current_enemy.getIV()));
            enemies.remove(enemies.get(i));
          }
        } // end of for
    
        // Keine Gegner mehr auf dem Feld und keine mehr zu spawnen
        if (enemies.size() == 0 && rounds.get(roundCount).getRoundLength() < tick) {
          loop.stop();
          System.out.println("Round " + String.valueOf(roundCount) + " over!");
          
          // Add round reward
          money = money + 100 + 25 * roundCount;
          labelMoney.setText("Money: " + String.valueOf(money));
          roundCount++;
          running = false;
          
          // Remove explosion sprite
          if (bumming) {
            bumming = false;
            root.getChildren().remove(root.getChildren().indexOf(ivbum));
          }
          
          // Remove projectiles from screen
          for (int i=0; i<projectiles.size(); i++) {
            Projectile current_projectile = projectiles.get(i);
            root.getChildren().remove(root.getChildren().indexOf(current_projectile.getIV()));
          }
          
          // Remove projectiles from array
          for (int i = 0; i < projectiles.size(); i++) {
            Projectile current_projectile = projectiles.get(i);
            projectiles.remove(current_projectile);
          }
          projectiles = new ArrayList<Projectile>();
          
        } 
        
        tick++;
        }
    }));
  
    loop.setCycleCount(Timeline.INDEFINITE);
    loop.play();
  }
  
  public void load_rounds() {
    int fileNr = 1;
    boolean worked = true;
    do {
      Runde tempRunde = new Runde(10);
      worked = tempRunde.loadRoundFromFile("Runden/Runde" + String.valueOf(fileNr) + ".txt");
      rounds.add(tempRunde);
      fileNr++;         
    } while (worked);
    System.out.println("Alle vorhandenen Runden geladen.");
  }

  public void button_play_Action(Event evt) {
    if (running == false && roundCount < rounds.size()) {
      running = true; 
      move_img();
    }
  } // end of button_play_Action


  public void button_Spahn_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double x = event.getSceneX();
        double y = event.getSceneY();
        if (checkIsTowerOnPath(x,y)==false && checkIsTowerOnTower(x,y)==false) {
          if (y > 700){
            root.setOnMouseClicked(null);
            return;
          }
          if (x < 50) {
            x = 50;
          }
          if (x > 1150) {
            x = 1150;
          } 
          if (y < 50) {
            y = 50;
          } 
          if (y > 650) {
            y = 650;
          }
          // Kosten der Türme werden hier einzeln angepasst
          if (money >= 2500) {
            money = money - 2500;
            labelMoney.setText("Money: " + String.valueOf(money));
            Tower temptower = new Tower(new Image("assets/Spahn.png"), x, y, 200.0, 5, 1, 15.0, 1);
            towers.add(temptower); 
            root.getChildren().add(temptower.getIV());
          }
          else {
            System.out.println("Dir fehlt Kohle diggi");
          }
          
        }
        else {
          System.out.println("Platzieren des Turms fehlgeschlagen!");
        }
        root.setOnMouseClicked(null);
      }
    });
    
  } // end of button_Spahn_Action
  
  public boolean checkIsTowerOnPath(double x, double y){
    for (int i = 0;i < path.length; i++) {
      double tmpX = path[i].getX();
      double tmpY = path[i].getY();
      if ((x > (tmpX-25)&&x < (tmpX+25))&&(y > (tmpY-25)&&y < (tmpY+25))) {
        return true; 
      } // end of if
      
    } // end of for
    return false;
  }
  
  public boolean checkIsTowerOnTower(double x, double y){
    for (int i = 0;i < towers.size(); i++) {
      double tmpX = towers.get(i).getX();
      double tmpY = towers.get(i).getY();
      if ((x > (tmpX-50)&&x < (tmpX+150))&&(y > (tmpY-50)&&y < (tmpY+150))) {
        return true; 
      } // end of if
      
    } // end of for
    return false;
  }
  
  public boolean checkIsTowerOutWindowUp(double x, double y){
    if (y < 50) {
      return true;
    } else {
      return false;  
    } 
  }
  
  public boolean checkIsTowerOutWindowDown(double x, double y){
    if (y > 650) {
      return true;
    } else {
      return false;  
    } 
  }
  
  public boolean checkIsTowerOutWindowRight(double x, double y){
    if (x > 1150) {
      return true;
    } else {
      return false;  
    } 
  }
  
  public boolean checkIsTowerOutWindowLeft(double x, double y){
    if (x < 50) {
      return true;
    } else {
      return false;  
    } 
  }
  
  public boolean checkIsTowerInWindow(double x, double y){
    if (checkIsTowerOutWindowUp(x,y)==false && checkIsTowerOutWindowDown(x,y)==false && checkIsTowerOutWindowRight(x,y)==false && checkIsTowerOutWindowLeft(x,y)==false) {
      return true;
    } else {
      return false;  
    } // end of if-else
      
  }
  

  public void button_Drosten_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double x = event.getSceneX();
        double y = event.getSceneY();

        if (checkIsTowerOnPath(x,y)==false && checkIsTowerOnTower(x,y)==false) {
          if (y > 700){
            root.setOnMouseClicked(null);
            return;
          }
          if (x < 50) {
            x = 50;
          }
          if (x > 1150) {
            x = 1150;
          } 
          if (y < 50) {
            y = 50;
          } 
          if (y > 650) {
            y = 650;
          }
          if (money >= 500) {
            money = money - 500;
            labelMoney.setText("Money: " + money);
            Tower temptower = new Tower(new Image("assets/Drosten.png"), x, y, 200.0, 5, 1, 15.0, 2); 
            towers.add(temptower); 
            root.getChildren().add(temptower.getIV());
          }
          else {
            System.out.println("Dir fehlt Kohle diggi");
          }
        }
        else {
          System.out.println("Platzieren des Turms fehlgeschlagen!");
        }
        root.setOnMouseClicked(null);
      }
    });
    
  } // end of button_Drosten_Action

  public void button_Merkel_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double x = event.getSceneX();
        double y = event.getSceneY();
        if (checkIsTowerOnPath(x,y)==false && checkIsTowerOnTower(x,y)==false) {
          if (y > 700){
            root.setOnMouseClicked(null);
            return;
          }
          if (x < 50) {
            x = 50;
          }
          if (x > 1150) {
            x = 1150;
          } 
          if (y < 50) {
            y = 50;
          } 
          if (y > 650) {
            y = 650;
          }
          if (money >= 5000) {
            money = money - 5000;
            labelMoney.setText("Money: " + money);
            Tower temptower = new Tower(new Image("assets/Merkel.png"), x, y, 200.0, 5, 1, 15.0, 3); 
            towers.add(temptower); 
            root.getChildren().add(temptower.getIV());
          }
          else {
            System.out.println("Dir fehlt Kohle diggi");
          }
        }
        else {
          System.out.println("Platzieren des Turms fehlgeschlagen!");
        }
        root.setOnMouseClicked(null);
      }
    });
    
  } // end of button_Merkel_Action

  public void button_Rezo_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double x = event.getSceneX();
        double y = event.getSceneY();
        if (checkIsTowerOnPath(x,y)==false && checkIsTowerOnTower(x,y)==false) {
          if (y > 700){
            root.setOnMouseClicked(null);
            return;
          }
          if (x < 50) {
            x = 50;
          }
          if (x > 1150) {
            x = 1150;
          } 
          if (y < 50) {
            y = 50;
          } 
          if (y > 650) {
            y = 650;
          }
          if (money >= 100) {
            money = money - 100;
            labelMoney.setText("Money: " + money);
            Tower temptower = new Tower(new Image("assets/Rezo.png"), x, y, 200.0, 5, 1, 15.0, 4); 
            towers.add(temptower); 
            root.getChildren().add(temptower.getIV());
          }
          else {
            System.out.println("Dir fehlt Kohle diggi");
          } // end of if-else
          
        }
        else {
          System.out.println("Platzieren des Turms fehlgeschlagen!");
        }
        root.setOnMouseClicked(null);
      }
    });
    
  } // end of button_Rezo_Action

  public void button_Lauterbach_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double x = event.getSceneX();
        double y = event.getSceneY();
        if (checkIsTowerOnPath(x,y)==false && checkIsTowerOnTower(x,y)==false) {
          if (y > 700){
            root.setOnMouseClicked(null);
            return;
          }
          if (x < 50) {
            x = 50;
          }
          if (x > 1150) {
            x = 1150;
          } 
          if (y < 50) {
            y = 50;
          } 
          if (y > 650) {
            y = 650;
          }
          if (money >= 1000) {
            money = money - 1000;
            labelMoney.setText("Money: " + money);
            Tower temptower = new Tower(new Image("assets/Lauterbach.png"), x, y, 200.0, 5, 1, 15.0, 5); 
            towers.add(temptower); 
            root.getChildren().add(temptower.getIV());
          }
          else {
            System.out.println("Dir fehlt Kohle diggi");
          } // end of if-else
          
        }
        else {
          System.out.println("Platzieren des Turms fehlgeschlagen!");
        }
        root.setOnMouseClicked(null);
      }
    });
    
  } // end of button_Lauterbach_Action

  // Ende Methoden
} // end of class rendertest1

