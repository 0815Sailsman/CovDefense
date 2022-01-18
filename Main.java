import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.*;
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


public class Main extends Application {
  Pane root = new Pane();
  
  public Game game_engine = new Game();
  
  private Button button_play = new Button();
  private ImageView ivbg = new ImageView();
  private ImageView ivbum = new ImageView();
  
  private Timeline loop;
  
  int tick;
  
  private boolean running = false;
  private boolean bumming = false;
  private Label label_HP = new Label();

  private Tower_spawner rezo_spawner = new Tower_spawner(Tower.REZO, 0, root, this);
  private Tower_spawner drosten_spawner = new Tower_spawner(Tower.DROSTEN, 1, root, this);
  private Tower_spawner lauterbach_spawner = new Tower_spawner(Tower.LAUTERBACH, 2, root, this);
  private Tower_spawner spahn_spawner = new Tower_spawner(Tower.SPAHN, 3, root, this);
  private Tower_spawner merkel_spawner = new Tower_spawner(Tower.MERKEL, 4, root, this);
  
  private Label label_money = new Label();
  private Label label_Runde = new Label();
  private Label label_status_out = new Label();
  
  public void start(Stage primaryStage) { 
    // Were 1184 and 795
    Scene scene = new Scene(root, WindowDimensions.WIDTH, WindowDimensions.HEIGHT);
    // Anfang Komponenten
    System.out.println("" + WindowDimensions.WIDTH);
    System.out.println("" + WindowDimensions.HEIGHT);       
    Image bg = new Image("assets/bg.png");
    ivbg.setImage(bg);
    ivbg.setFitHeight(WindowDimensions.HEIGHT / 1.1357); // Was 700
    ivbg.setFitWidth(WindowDimensions.WIDTH); // Was 1200
    root.getChildren().add(ivbg); 
    
    button_play.setLayoutX(WindowDimensions.WIDTH / 80.0); // Was 16
    button_play.setLayoutY(WindowDimensions.HEIGHT / 1.0965); // Was 725
    button_play.setPrefHeight(WindowDimensions.HEIGHT / 31.8);
    button_play.setPrefWidth(WindowDimensions.WIDTH / 15.786);
    button_play.setOnAction(
      (event) -> {button_play_Action(event);} 
    );
    button_play.setText("play");
    root.getChildren().add(button_play);
    
    label_HP.setLayoutX(WindowDimensions.WIDTH / 1127);
    label_HP.setLayoutY(WindowDimensions.HEIGHT / 79.5);
    label_HP.setPrefHeight(WindowDimensions.HEIGHT / 26.5);
    label_HP.setPrefWidth(WindowDimensions.WIDTH / 11.84);
    label_HP.setText("HP: 100");
    label_HP.setAlignment(Pos.CENTER);
    label_HP.setFont(Font.font("Dialog", 22));
    label_HP.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    root.getChildren().add(label_HP);
    label_money.setLayoutX(WindowDimensions.WIDTH / 1.149);
    label_money.setLayoutY(WindowDimensions.HEIGHT / 15.9);
    label_money.setPrefHeight(WindowDimensions.HEIGHT / 30);
    label_money.setPrefWidth(WindowDimensions.WIDTH / 8.457);
    label_money.setText("Money: 100$");
    label_money.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    label_money.setAlignment(Pos.CENTER);
    label_money.setFont(Font.font("Dialog", 22));
    root.getChildren().add(label_money);
    label_Runde.setLayoutX(WindowDimensions.WIDTH / 1.133);
    label_Runde.setLayoutY(WindowDimensions.HEIGHT / 8.83);
    label_Runde.setPrefHeight(WindowDimensions.HEIGHT / 26.5);
    label_Runde.setPrefWidth(WindowDimensions.WIDTH / 10.76);
    label_Runde.setText("Runde: 0");
    label_Runde.setAlignment(Pos.CENTER);
    label_Runde.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    label_Runde.setFont(Font.font("Dialog", 22));
    root.getChildren().add(label_Runde);
    label_status_out.setLayoutX(WindowDimensions.WIDTH / 1.416);
    label_status_out.setLayoutY(WindowDimensions.HEIGHT / 1.0965);
    label_status_out.setPrefHeight(WindowDimensions.HEIGHT / 39.75);
    label_status_out.setPrefWidth(WindowDimensions.WIDTH / 4.97);
    label_status_out.setText("");
    label_status_out.setAlignment(Pos.CENTER);
    root.getChildren().add(label_status_out);
    
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
    //primaryStage.setMaximized(true);
    primaryStage.setFullScreen(true);
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
        label_money.setText("Money: " + String.valueOf(game_engine.get_money()));
        
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
          label_HP.setText("HP: " + String.valueOf(game_engine.get_hp()));
        }
        remove_results(results);
    
        // Game lost
        // This wont be moved into the game class because this (for now)
        // only affects the GUI
        if (game_engine.get_hp() <= 0) {
          Image defeat = new Image("assets/defeat.png");
          ImageView defeat_view = new ImageView();
          defeat_view.setImage(defeat);
          defeat_view.setX(WindowDimensions.WIDTH / -23.68);
          root.getChildren().add(defeat_view);
          loop.stop();
          
          disable_buttons();
        }
        
        results = game_engine.check_for_round_end(tick);
        // If results has something... yada yada yada
        if (results != null) {
          loop.stop();
          label_money.setText("Money: " + game_engine.get_money());
          running = false;
          turn_off_explosion();
          remove_results(results);
        }
        
        // Check for victory
        // This only has GUI stuff for now aswell, so not moved into game class
        if (game_engine.get_round_count() == game_engine.get_rounds().size()) {
            Image victory = new Image("assets/victory.png");
            ImageView victory_view = new ImageView();
            victory_view.setImage(victory);
            victory_view.setX(WindowDimensions.WIDTH / -23.68);
            root.getChildren().add(victory_view);
            loop.stop();
            disable_buttons();
        }
        
        tick++;
        }
    }));
  
    loop.setCycleCount(Timeline.INDEFINITE);
    loop.play();
  }
                                       
  public void button_play_Action(Event evt) {
    if (!running) {
      int actual_round = game_engine.get_round_count() + 1;
      label_Runde.setText("Runde: " + actual_round);
      running = true; 
      game_loop();
    }
  }
  
  public void turn_on_explosion() {
    if (this.bumming == false) {
      Image explosion_img = new Image("assets/bum.png");
      ivbum.setImage(explosion_img);
      ivbum.setX(WindowDimensions.WIDTH / 2.368);
      ivbum.setY(WindowDimensions.HEIGHT / 1.445);
      ivbum.setFitHeight(WindowDimensions.HEIGHT / 7.95);
      ivbum.setFitWidth(WindowDimensions.WIDTH / 5.92);
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
      label_status_out.setText("Turm wurde nicht platziert!");
    }
    else {
      label_money.setText("Money: " + game_engine.get_money());
      root.getChildren().add(new_tower.getIV());
      label_status_out.setText("Turm wurde platziert!");
    }
    root.setOnMouseClicked(null);
  }
  
  public void disable_buttons() {
    button_play.setDisable(true);
    
    rezo_spawner.get_button().setDisable(true);
    drosten_spawner.get_button().setDisable(true);
    lauterbach_spawner.get_button().setDisable(true);
    spahn_spawner.get_button().setDisable(true);
    merkel_spawner.get_button().setDisable(true);
  }
  

} 

