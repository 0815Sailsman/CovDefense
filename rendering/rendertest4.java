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

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 18.05.2021
 * @author 
 */

public class rendertest4 extends Application {
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
  
  private int target_index = 0;
  private int speed = 25;
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
    primaryStage.setTitle("rendertest3");
    primaryStage.setScene(scene);
    primaryStage.show();
  } // end of public rendertest1
  
  // Anfang Methoden
  
  public static void main(String[] args) {
    launch(args);
  } // end of main
  
  public void add_img() {
   
    Image bg = new Image("bg.png");
    ivbg.setImage(bg);
    ivbg.setFitHeight(700);
    ivbg.setFitWidth(1200);
    root.getChildren().add(ivbg); 
    
    Image image = new Image("hum.jpg");
    iv1.setFitHeight(70);
    iv1.setFitWidth(120);
    iv1.setX(550.0);
    iv1.setY(1.0);
    iv1.setImage(image);
    System.out.println(String.valueOf(iv1.getX()));

    // Add our image/ImageView to the scene
    root.getChildren().add(iv1);
    
    Image num = new Image("bum.png");
    ivbum.setImage(num);
    ivbum.setX(5500.0);
    ivbum.setY(6000.0);
    ivbum.setFitHeight(100);
    ivbum.setFitWidth(200);
    root.getChildren().add(ivbum);

  }
  
  public void move_img() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {        
        System.out.println("x" + String.valueOf(iv1.getX()));
        System.out.println("y" + String.valueOf(iv1.getY()));
        System.out.println("target" + String.valueOf(target_index));
        double tempx = 0.0;
        double tempy = 0.0;
        double tempz = 0.0;
        
        tempx = path[target_index].getX();
        tempy = path[target_index].getY();
        System.out.println("x_temp" + String.valueOf(tempx));
        System.out.println("y_temp" + String.valueOf(tempy));
        
        // TODO: Anpassen je nach Bewegungsrichtung        
        double totalx = tempx - iv1.getX();
        double totaly = tempy - iv1.getY();
        System.out.println("x_dist" + String.valueOf(totalx));
        System.out.println("y_dist" + String.valueOf(totaly));
                                                  
        tempz = Math.sqrt((totalx * totalx) + (totaly * totaly));
        
        double deg_alpha = Math.atan(totalx / totaly);
        System.out.println("grad_zahl" + String.valueOf(deg_alpha));
        
        double targetx = Math.sin(deg_alpha) * speed;
        double targety = Math.cos(deg_alpha) * speed;
        System.out.println("movex" + String.valueOf(targetx));
        System.out.println("movey" + String.valueOf(targety));
        
        boolean x_pass = false;
        boolean y_pass = false;
        
        if (totalx >= 0) {
          iv1.setX(iv1.getX() + Math.abs(targetx));
          if (iv1.getX() >= path[target_index].getX()) {
            x_pass = true;
          }
        }
        else {
          iv1.setX(iv1.getX() - Math.abs(targetx));
          if (iv1.getX() <= path[target_index].getX()) {
            x_pass = true;
          }
        }
        
        if (totaly >= 0) {
          iv1.setY(iv1.getY() + Math.abs(targety));
          if (iv1.getY() >= path[target_index].getY()) {
            y_pass = true;
          }
        }
        else {
          iv1.setY(iv1.getY() - Math.abs(targety));
          if (iv1.getY() <= path[target_index].getY()) {
            y_pass = true;
          }
        }        
        
        if (x_pass == true && y_pass == true) {
          target_index++;
          if (target_index == 16) {
            // DAS HIER IST KOMPLETT SHIT, ABER WENN ICH ES ANDERS MACHE
            // DANN CRASHT DAS PROGRAMM!!!
            ivbum.setX(500.0);
            ivbum.setY(550.0);
            iv1.setX(5500.0);
            iv1.setY(6000.0);
          } 
        }
        }
    }, 0, 33); 
  }
  
  public void button1_Action(Event evt) {
    add_img();
    
  } // end of button1_Action

  public void button2_Action(Event evt) {
    // TODO hier Quelltext einfügen
    move_img();
  } // end of button2_Action


  // Ende Methoden
} // end of class rendertest1

