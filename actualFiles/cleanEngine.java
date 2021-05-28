import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
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
  
  private Enemy testEnemy;
  private Enemy fastEnemy;
  private Enemy slowEnemy;
  private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  
  int tick;
  // Ende Attribute
  
  public void start(Stage primaryStage) { 
    
    Scene scene = new Scene(root, 1184, 762);
    // Anfang Komponenten
    button1.setOnAction(
      (event) -> {button1_Action(event);} 
    );
    button1.setLayoutX(8);
    button1.setLayoutY(728);
    button1.setPrefHeight(25);
    button1.setPrefWidth(75);
    root.getChildren().add(button1);
    button2.setLayoutX(104);
    button2.setLayoutY(725);
    button2.setPrefHeight(25);
    button2.setPrefWidth(75);
    button2.setOnAction(
      (event) -> {button2_Action(event);} 
    );
    root.getChildren().add(button2);
    
    // Ende Komponenten
    //add_img();
    
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    primaryStage.setTitle("cleanEngine");
    primaryStage.setScene(scene);
    primaryStage.show();
  } // end of public 
  
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
      
    testEnemy = new Enemy(new Image("hum.jpg"), 550.0, 1.0, 25.0, 1, 1, 1);
    testEnemy.getIV().setFitHeight(70);
    testEnemy.getIV().setFitWidth(120);
    enemies.add(testEnemy);  
    root.getChildren().add(testEnemy.getIV());
    
    fastEnemy = new Enemy(new Image("hum.jpg"), 550.0, 1.0, 35.0, 1, 1, 1);
    fastEnemy.getIV().setFitHeight(70);
    fastEnemy.getIV().setFitWidth(120);
    enemies.add(fastEnemy);  
    root.getChildren().add(fastEnemy.getIV());
    
    slowEnemy = new Enemy(new Image("hum.jpg"), 550.0, 1.0, 15.0, 1, 1, 1);
    slowEnemy.getIV().setFitHeight(70);
    slowEnemy.getIV().setFitWidth(120);
    enemies.add(slowEnemy); 
    root.getChildren().add(slowEnemy.getIV());
    
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
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
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
            
            // Der Check hier muss sp�ter auch die noch nicht gespawnten Gegner betrachten
            if (enemies.size() == 0) {
              timer.cancel();
              timer.purge();
              System.out.println("Round over!");
            }
          } 
        } // end of for
    
        tick++;
        }
    }, 0, 33); 
  }
  
  public void button1_Action(Event evt) {
    init_img();
  } // end of button1_Action

  public void button2_Action(Event evt) {
    move_img();
  } // end of button2_Action


  // Ende Methoden
} // end of class rendertest1
