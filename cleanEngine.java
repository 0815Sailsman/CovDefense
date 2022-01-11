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
  
  private Game game_engine = new Game();
  
  private Button button_play = new Button();
  private ImageView ivbg = new ImageView();
  private ImageView ivbum = new ImageView();
  
  private Timeline loop;
  
  int tick;
  
  private boolean running = false;
  private boolean bumming = false;
  private Label labelHP = new Label();
  
  private Button button_Drosten = new Button();
  private Button button_Merkel = new Button();
  private Button button_Rezo = new Button();
  private Button button_Lauterbach = new Button();
  private Button button_Spahn = new Button();
  
  private Label labelCostTower1 = new Label();
  private Label labelCostTower2 = new Label();
  private Label labelCostTower3 = new Label();
  private Label labelCostTower4 = new Label();
  private Label labelCostTower5 = new Label();
  
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
    
    button_Spahn.setLayoutX(465);
    button_Spahn.setLayoutY(725);
    button_Spahn.setPrefHeight(25);
    button_Spahn.setPrefWidth(75);
    button_Spahn.setOnAction(
      (event) -> {button_Spahn_Action(event);} 
    );
    button_Spahn.setText("");
    root.getChildren().add(button_Spahn);
    labelHP.setLayoutX(1050);
    labelHP.setLayoutY(10);
    labelHP.setPrefHeight(30);
    labelHP.setPrefWidth(100);
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
    labelMoney.setLayoutX(1030);
    labelMoney.setLayoutY(50);
    labelMoney.setPrefHeight(30);
    labelMoney.setPrefWidth(140);
    labelMoney.setText("Money: 100$");
    labelMoney.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    labelMoney.setAlignment(Pos.CENTER);
    labelMoney.setFont(Font.font("Dialog", 22));
    root.getChildren().add(labelMoney);
    labelCostTower1.setLayoutX(230);
    labelCostTower1.setLayoutY(705);
    labelCostTower1.setPrefHeight(20);
    labelCostTower1.setPrefWidth(78);
    labelCostTower1.setText("100$");
    root.getChildren().add(labelCostTower1);
    labelCostTower2.setLayoutX(315);
    labelCostTower2.setLayoutY(705);
    labelCostTower2.setPrefHeight(20);
    labelCostTower2.setPrefWidth(78);
    labelCostTower2.setText("500$");
    root.getChildren().add(labelCostTower2);
    labelCostTower3.setLayoutX(400);
    labelCostTower3.setLayoutY(705);
    labelCostTower3.setPrefHeight(20);
    labelCostTower3.setPrefWidth(78);
    labelCostTower3.setText("1500$");
    root.getChildren().add(labelCostTower3);
    labelCostTower4.setLayoutX(485);
    labelCostTower4.setLayoutY(705);
    labelCostTower4.setPrefHeight(20);
    labelCostTower4.setPrefWidth(78);
    labelCostTower4.setText("2500$");
    root.getChildren().add(labelCostTower4);
    labelCostTower5.setLayoutX(570);
    labelCostTower5.setLayoutY(705);
    labelCostTower5.setPrefHeight(20);
    labelCostTower5.setPrefWidth(78);
    labelCostTower5.setText("5000$");
    root.getChildren().add(labelCostTower5);
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
    
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    primaryStage.setTitle("cleanEngine");
    primaryStage.setScene(scene);
    primaryStage.show();
   
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
          button_Spahn.setDisable(true);
          button_Drosten.setDisable(true);
          button_Merkel.setDisable(true);
          button_Rezo.setDisable(true);
          button_Lauterbach.setDisable(true);
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
  
  public boolean checkIsTowerOnPath(double x, double y){
    int radius = 40;
    for (int i = 0;i < game_engine.get_path().size(); i++) {
      double tmpX = game_engine.get_path().get(i).getX();
      double tmpY = game_engine.get_path().get(i).getY();
      if ((x > (tmpX-radius)&&x < (tmpX+radius))&&(y > (tmpY-radius)&&y < (tmpY+radius))) {
        return true; 
      }
      
    }
    return false;
  }
  
  public boolean checkIsTowerOnTower(double x, double y){
    for (int i = 0;i < game_engine.get_towers().size(); i++) {
      double tmpX = game_engine.get_towers().get(i).getX();
      double tmpY = game_engine.get_towers().get(i).getY();
      if ((x > (tmpX-50)&&x < (tmpX+150))&&(y > (tmpY-50)&&y < (tmpY+150))) {
        return true; 
      }
      
    }
    return false;
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
  // And the buttons are also ugly. Maybe create a class that contains
  // the button and the label and handels most of these click actions.
  // Would remove alot of redundant code... :thinking:
  public void button_Spahn_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        //Image sprite, double x, double y, double attackRange, int attackDamage, int attackSpeed, double projectileSpeed, int typeId
        //placeTower(event, 2500, new Image("assets/Spahn.png"), 200.0, 5, 3, 15.0, 1);
        placeTower(event, Tower.TOWER_SPAHN);
        // Schieﬂt sehr schnell, aber wenig Schaden
      }
    });
    
  }
  

  public void button_Drosten_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        //Image sprite, double x, double y, double attackRange, int attackDamage, int attackSpeed, double projectileSpeed, int typeId
        //placeTower(event, 500, new Image("assets/Drosten.png"), 200.0, 2, 20, 25.0, 2);
        placeTower(event, Tower.TOWER_DROSTEN);
        // So semi gut, besonders ist hier die ProjectileSpeed 
      }
    });
    
  } // end of button_Drosten_Action

  public void button_Merkel_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {        
        //Image sprite, double x, double y, double attackRange, int attackDamage, int attackSpeed, double projectileSpeed, int typeId
        //placeTower(event, 5000, new Image("assets/Merkel.png"), 400.0, 10, 1, 40, 3);
        placeTower(event, Tower.TOWER_MERKEL);
        // OP, schieﬂt ziemlich h‰ufig und es klatscht auch richtig 
      }
    });
    
  } // end of button_Merkel_Action

  public void button_Rezo_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        //Image sprite, double x, double y, double attackRange, int attackDamage, int attackSpeed, double projectileSpeed, int typeId
        //placeTower(event, 100, new Image("assets/Rezo.png"), 200.0, 1, 25, 15.0, 4);
        placeTower(event, Tower.TOWER_REZO);
        // Basic Anfangs Turm     
      }
    });
    
  } // end of button_Rezo_Action

  public void button_Lauterbach_Action(Event evt) {
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        //Image sprite, double x, double y, double attackRange, int attackDamage, int attackSpeed, double projectileSpeed, int typeId
        //placeTower(event, 1500, new Image("assets/Lauterbach.png"), 200.0, 10, 40, 10, 5);
        placeTower(event, Tower.TOWER_LAUTERBACH);
        // Wenig Sch¸sse, viel Schaden 
      }
    });
    
  } // end of button_Lauterbach_Action
  
  // TOdo this is prob. the one to be moved into game class
  // And please redo these parameters... :puke:
  // Todo Projektile auch in eigene Dateien auslagern, Turm kennt Path daf¸r
  // int cost, Image sprite, double attackRange, int attackDamage, int attackSpeed, double projectileSpeed, int typeId
  public void placeTower(MouseEvent event, int tower_id) {
    double x = event.getSceneX();
    double y = event.getSceneY();
    Tower_parameters params = Tower.load_tower_parameters(Tower.id_to_path(tower_id));
    
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
      if (game_engine.get_money() >= params.cost) {
        game_engine.set_money(game_engine.get_money() - params.cost);
        labelMoney.setText("Money: " + game_engine.get_money());
        // sprite, x, y, attackRange, attackDamage, attackSpeed, projectileSpeed, typeId
        Tower temptower = new Tower(x, y, params);
        game_engine.get_towers().add(temptower); 
        root.getChildren().add(temptower.getIV());
        labelStatusOuput.setText("Turm wurde platziert!");
      }
      else {
        System.out.println("Dir fehlt Kohle diggi");
        labelStatusOuput.setText("Dir fehlt Kohle diggi!");
      } // end of if-else
      
    }
    else {
      System.out.println("Platzieren des Turms fehlgeschlagen!");
      labelStatusOuput.setText("Platzieren des Turms fehlgeschlagen!");
    }
    root.setOnMouseClicked(null);
  }
  
  
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
} 

