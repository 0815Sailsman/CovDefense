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
  private Button button1 = new Button();
  private Button button2 = new Button();
  private ImageView iv1 = new ImageView();
  private ImageView ivbg = new ImageView();
  private ImageView ivbum = new ImageView();
  
  private map_point mp1 = new map_point(550.0, 0.0);
  private map_point mp2 = new map_point(550.0, 250.0);
  private map_point mp3 = new map_point(400.0, 300.0);
  private map_point mp4 = new map_point(450.0, 550.0);
  private map_point mp5 = new map_point(100.0, 550.0);
  private map_point mp6 = new map_point(300.0, 200.0);
  private map_point mp7 = new map_point(400.0, 200.0);
  private map_point mp8 = new map_point(400.0, 300.0);
  private map_point mp9 = new map_point(550.0, 250.0);
  private map_point mp10 = new map_point(700.0, 300.0);
  private map_point mp11 = new map_point(700.0, 550.0);
  private map_point mp12 = new map_point(1000.0, 550.0);
  private map_point mp13 = new map_point(700.0, 200.0);
  private map_point mp14 = new map_point(650.0, 250.0);
  private map_point mp15 = new map_point(550.0, 250.0);
  private map_point mp16 = new map_point(550.0, 600.0);
  
  private map_point[] path = {
  mp1, mp2, mp3, mp4, mp5 ,mp6, mp7, mp8, mp9, mp10, mp11, mp12, mp13, mp14, mp15, mp16
    };
  
  private ArrayList<Runde> rounds = new ArrayList<Runde>();
  private int roundCount = 0;
  
  private Timeline loop;
  
  private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  private ArrayList<Tower> towers = new ArrayList<Tower>();
  
  int tick;
  private Button button3 = new Button();
  // Ende Attribute
  
  public void start(Stage primaryStage) { 
    
    Scene scene = new Scene(root, 1200, 800);
    // Anfang Komponenten
    button1.setOnAction(
      (event) -> {button1_Action(event);} 
    );
    button1.setLayoutX(8);
    button1.setLayoutY(728);
    button1.setPrefHeight(25);
    button1.setPrefWidth(75);
    button1.setText("Initialisiere");
    root.getChildren().add(button1);
    button2.setLayoutX(104);
    button2.setLayoutY(725);
    button2.setPrefHeight(25);
    button2.setPrefWidth(75);
    button2.setOnAction(
      (event) -> {button2_Action(event);} 
    );
    button2.setText("play");
    root.getChildren().add(button2);
    
    button3.setLayoutX(196);
    button3.setLayoutY(725);
    button3.setPrefHeight(25);
    button3.setPrefWidth(75);
    button3.setOnAction(
      (event) -> {button3_Action(event);} 
    );
    button3.setText("TURMI");
    root.getChildren().add(button3);
    // Ende Komponenten
    //add_img();
    
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    primaryStage.setTitle("cleanEngine");
    primaryStage.setScene(scene);
    primaryStage.show();
    
    Runde testRunde1 = new Runde(10);
    testRunde1.loadRoundFromFile("Runde1.txt");
    rounds.add(testRunde1);
    Runde testRunde2 = new Runde(10);
    testRunde2.loadRoundFromFile("Runde2.txt");
    rounds.add(testRunde2);
    Runde testRunde3 = new Runde(10);
    testRunde3.loadRoundFromFile("Runde3.txt");
    rounds.add(testRunde3);
    Runde testRunde4 = new Runde(10);
    testRunde4.loadRoundFromFile("Runde4.txt");
    rounds.add(testRunde4);
   
  }
  // Anfang Methoden
  
  public static void main(String[] args) {
    launch(args);
  } // end of main
  
  public void init_img() {
   
    Image bg = new Image("bg.png");
    ivbg.setImage(bg);
    ivbg.setFitHeight(700);
    ivbg.setFitWidth(1200);
    root.getChildren().add(ivbg); 
    
    Image num = new Image("bum.png");
    ivbum.setImage(num);
    ivbum.setX(5500.0);
    ivbum.setY(6000.0);
    ivbum.setFitHeight(100);
    ivbum.setFitWidth(200);
    root.getChildren().add(ivbum);

  }
  
  public void move_img() {
    tick = 0;   
    loop = new Timeline(new KeyFrame(Duration.millis(33), new EventHandler<ActionEvent>() {  
        @Override
        public void handle(final ActionEvent t) {
        // Here are the new enemies being spawned
        int enemyIdToSpawn = rounds.get(roundCount).getEnemyOnFrame(tick);
        if (enemyIdToSpawn == 1) {
          Enemy testEnemy = new Enemy(new Image("hum.jpg"), 550.0, 1.0, 25.0, 1, 1, 1);
          testEnemy.getIV().setFitHeight(70);
          testEnemy.getIV().setFitWidth(120);
          enemies.add(testEnemy);  
          root.getChildren().add(testEnemy.getIV());
        }
        else if (enemyIdToSpawn == 2) {
          Enemy fastEnemy = new Enemy(new Image("hum.jpg"), 550.0, 1.0, 35.0, 1, 1, 1);
          fastEnemy.getIV().setFitHeight(70);
          fastEnemy.getIV().setFitWidth(120);
          enemies.add(fastEnemy);  
          root.getChildren().add(fastEnemy.getIV());
        }
        else if (enemyIdToSpawn == 3) {
          Enemy slowEnemy = new Enemy(new Image("hum.jpg"), 550.0, 1.0, 15.0, 1, 1, 1);
          slowEnemy.getIV().setFitHeight(70);
          slowEnemy.getIV().setFitWidth(120);
          enemies.add(slowEnemy); 
          root.getChildren().add(slowEnemy.getIV());
        }
        
        // Here are all enemies being moved
        for (int i = 0; i < enemies.size(); i++) {
          Enemy current_enemy = enemies.get(i);
          current_enemy.move(path);
          
          if (current_enemy.getTarget() == 16) {
            // DAS HIER IST KOMPLETT SHIT, ABER WENN ICH ES ANDERS MACHE
            // DANN CRASHT DAS PROGRAMM!!!
            ivbum.setX(500.0);
            ivbum.setY(550.0);
            current_enemy.setX(5500.0);
            current_enemy.setY(6000.0);
            enemies.remove(enemies.get(i));
            
            // Der Check hier muss später auch die noch nicht gespawnten Gegner betrachten
            if (enemies.size() == 0 && rounds.get(roundCount).getRoundLength() < tick) {
              loop.stop();
              System.out.println("Round over!");
              roundCount++;
            }
          } 
        } // end of for
    
        tick++;
        }
    }));
  
    loop.setCycleCount(Timeline.INDEFINITE);
    loop.play();
  }
  
  public void button1_Action(Event evt) {
    init_img();
  } // end of button1_Action

  public void button2_Action(Event evt) {
    move_img();
  } // end of button2_Action


  public void button3_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double x = event.getSceneX();
        double y = event.getSceneY();
        System.out.println(x);
        System.out.println(y);
        System.out.println(checkIsTowerOnPath(x,y));
        System.out.println(checkIsTowerOnTower(x,y));
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
          Tower temptower = new Tower(new Image("Spahn.png"), x, y, 1.0, 1.0, 1.0); 
            towers.add(temptower); 
            root.getChildren().add(temptower.getIV());
          
        }
        root.setOnMouseClicked(null);
      }
    });
    
  } // end of button3_Action
  
  public boolean checkIsTowerOnPath(double x, double y){
    for (int i = 0;i < path.length; i++) {
      double tmpX = path[i].getX();
      double tmpY = path[i].getY();
      if ((x > (tmpX-50)&&x < (tmpX+50))&&(y > (tmpY-50)&&y < (tmpY+50))) {
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
  // Ende Methoden
} // end of class rendertest1

