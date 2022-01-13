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
import javafx.scene.layout.BackgroundImage;
import javafx.event.*;
import javafx.animation.PauseTransition;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;    
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class cleanEngine extends Application {
  Pane root = new Pane();
  
  public Game game_engine = new Game();
  
  private Button button_play = new Button();
  private ImageView ivbg = new ImageView();
  private ImageView ivbum = new ImageView();
  
  private Timeline loop;
  
  int tick;
  
  private boolean running = false;
  private boolean bumming = false;
  private Label labelHP = new Label();

  private Tower_spawner rezo_spawner = new Tower_spawner(Tower.TOWER_REZO, 0, root, this);
  private Tower_spawner drosten_spawner = new Tower_spawner(Tower.TOWER_DROSTEN, 1, root, this);
  private Tower_spawner lauterbach_spawner = new Tower_spawner(Tower.TOWER_LAUTERBACH, 2, root, this);
  private Tower_spawner spahn_spawner = new Tower_spawner(Tower.TOWER_SPAHN, 3, root, this);
  private Tower_spawner merkel_spawner = new Tower_spawner(Tower.TOWER_MERKEL, 4, root, this);
  
  private Label labelMoney = new Label();
  private Label lRunde0 = new Label();
  private Label labelStatusOuput = new Label();
  
  public void start(Stage primaryStage) { 
    
    Scene scene = new Scene(root, 1184, 795);
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
    
    labelHP.setLayoutX(1050);
    labelHP.setLayoutY(10);
    labelHP.setPrefHeight(30);
    labelHP.setPrefWidth(100);
    labelHP.setText("HP: 100");
    labelHP.setAlignment(Pos.CENTER);
    labelHP.setFont(Font.font("Dialog", 22));
    labelHP.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    root.getChildren().add(labelHP);
    labelMoney.setLayoutX(1030);
    labelMoney.setLayoutY(50);
    labelMoney.setPrefHeight(30);
    labelMoney.setPrefWidth(140);
    labelMoney.setText("Money: 100$");
    labelMoney.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    labelMoney.setAlignment(Pos.CENTER);
    labelMoney.setFont(Font.font("Dialog", 22));
    root.getChildren().add(labelMoney);
    lRunde0.setLayoutX(1045);
    lRunde0.setLayoutY(90);
    lRunde0.setPrefHeight(30);
    lRunde0.setPrefWidth(110);
    lRunde0.setText("Runde: 0");
    lRunde0.setAlignment(Pos.CENTER);
    lRunde0.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    lRunde0.setFont(Font.font("Dialog", 22));
    root.getChildren().add(lRunde0);
    labelStatusOuput.setLayoutX(836);
    labelStatusOuput.setLayoutY(725);
    labelStatusOuput.setPrefHeight(20);
    labelStatusOuput.setPrefWidth(238);
    labelStatusOuput.setText("");
    labelStatusOuput.setAlignment(Pos.CENTER);
    root.getChildren().add(labelStatusOuput);
    
    // Tower Spawner stuff
    root.getChildren().add(rezo_spawner.get_button());
    root.getChildren().add(rezo_spawner.get_label());

    root.getChildren().add(drosten_spawner.get_button());
    root.getChildren().add(drosten_spawner.get_label());
    
    root.getChildren().add(lauterbach_spawner.get_button());
    root.getChildren().add(lauterbach_spawner.get_label());
    
    root.getChildren().add(spahn_spawner.get_button());
    root.getChildren().add(spahn_spawner.get_label());
    
    root.getChildren().add(merkel_spawner.get_button());
    root.getChildren().add(merkel_spawner.get_label());
    
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    primaryStage.setTitle("CovDefense");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  public static void main(String[] args) {
    launch(args);
  }
  
  public void game_loop() {
    tick = 0;   
    loop = new Timeline(new KeyFrame(Duration.millis(33), new EventHandler<ActionEvent>() {  
        @Override
        public void handle(final ActionEvent t) {
        // Tick towers and draw the resulting projectiles
        draw_results(game_engine.tick_towers());
        
        // Tick projectiles and remove colliding enemies and projectiles
        // aswell as despawning projectiles
        remove_results(game_engine.tick_projectiles());                
        labelMoney.setText("Money: " + String.valueOf(game_engine.get_money()));
        
        // Here are the new enemies being spawned
        draw_results(game_engine.spawn_enemies(tick));
        
        // Here are all enemies being moved
        // This time the results are temporarily saved in variable because
        // they are needed more than once
        ArrayList results = game_engine.tick_enemies();
        // If tick enemies returns somthing then turn
        // on explosion and update hp afterwards
        if (results.size() > 0) {
          turn_on_explosion();
          labelHP.setText("HP: " + String.valueOf(game_engine.get_hp()));
        }
        remove_results(results);
    
        // Game lost
        // This wont be moved into the game class because this (for now)
        // only affects the GUI
        if (game_engine.get_hp() <= 0) {
          Image defeat = new Image("assets/defeat.png");
          ImageView defeatView = new ImageView();
          defeatView.setImage(defeat);
          defeatView.setX(-50);
          root.getChildren().add(defeatView);
          loop.stop();
          button_play.setDisable(true);
          
          rezo_spawner.get_button().setDisable(true);
          drosten_spawner.get_button().setDisable(true);
          lauterbach_spawner.get_button().setDisable(true);
          spahn_spawner.get_button().setDisable(true);
          merkel_spawner.get_button().setDisable(true);
        }
        
        results = game_engine.check_for_round_end(tick);
        // If results has something... yada yada yada
        if (results != null) {
          loop.stop();
          labelMoney.setText("Money: " + String.valueOf(game_engine.get_money()));
          running = false;
          turn_off_explosion();
          remove_results(results);
        }
        
        // Check for victory
        // This only has GUI stuff for now aswell, so not moved into game class
        if (game_engine.get_round_count() == game_engine.get_rounds().size()) {
            Image victory = new Image("assets/victory.png");
            ImageView victoryView = new ImageView();
            victoryView.setImage(victory);
            victoryView.setX(-50);
            root.getChildren().add(victoryView);
            loop.stop();
        }
        
        tick++;
        }
    }));
  
    loop.setCycleCount(Timeline.INDEFINITE);
    loop.play();
  }
                                       
  public void button_play_Action(Event evt) {
    if (running == false && game_engine.get_round_count() < game_engine.get_rounds().size()) {
      int actualRound = game_engine.get_round_count() + 1;
      lRunde0.setText("Runde: " + actualRound);
      running = true; 
      game_loop();
      System.out.println("Round started");
    }
  }
  
  // Todo Refactor methods below
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
    }
      
  }
  
  // TODO Rework Tower Placement in general. This is ugly.
  // Do value loading from file (like with the enemies).
  // Therefore the logic for this will be moved into game class
  // Would remove alot of redundant code... :thinking:

  // TOdo this is prob. the one to be moved into game class
  // And please redo these parameters... :puke:
  // Todo Projektile auch in eigene Dateien auslagern, Turm kennt Path dafür
  // int cost, Image sprite, double attackRange, int attackDamage, int attackSpeed, double projectileSpeed, int typeId  
  
  public void turn_on_explosion() {
    if (this.bumming == false) {
      Image bum = new Image("assets/bum.png");
      ivbum.setImage(bum);
      ivbum.setX(500.0);
      ivbum.setY(550.0);
      ivbum.setFitHeight(100);
      ivbum.setFitWidth(200);
      root.getChildren().add(ivbum);
      bumming = true;
    }
  }
  
  public void turn_off_explosion() {
    if (bumming) {
      bumming = false;
      root.getChildren().remove(root.getChildren().indexOf(ivbum));
    }    
  }
  
  public void draw_results(ArrayList<ImageView> results) {
    for (int i = 0; i < results.size(); i++) {
      root.getChildren().add(results.get(i));
    }
  }
  
  public void remove_results(ArrayList results) {
    for (int i = 0; i < results.size(); i++) {
      root.getChildren().remove(root.getChildren().indexOf(results.get(i)));
    }
  }
  
  public void place_tower(Tower new_tower) {
    if (new_tower == null) {
      labelStatusOuput.setText("Turm wurde nicht platziert!");
    }
    else {
      labelMoney.setText("Money: " + game_engine.get_money());
      root.getChildren().add(new_tower.getIV());
      labelStatusOuput.setText("Turm wurde platziert!");
    }
    root.setOnMouseClicked(null);
  }

} 

