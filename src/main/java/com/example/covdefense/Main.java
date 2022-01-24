package com.example.covdefense;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Main extends Application {
  Pane root = new Pane();
  
  public Game game_engine = new Game();
  
  private final Button button_play = new Button();
  private final ImageView image_view_background = new ImageView();
  private final ImageView image_view_explosion = new ImageView();
  
  private Timeline loop;
  
  int tick;
  
  private boolean running = false;
  private boolean bumming = false;
  private final Label label_HP = new Label();

  private final Tower_spawner rezo_spawner = new Tower_spawner(Tower.REZO, 0, root, this);
  private final Tower_spawner drosten_spawner = new Tower_spawner(Tower.DROSTEN, 1, root, this);
  private final Tower_spawner lauterbach_spawner = new Tower_spawner(Tower.LAUTERBACH, 2, root, this);
  private final Tower_spawner spahn_spawner = new Tower_spawner(Tower.SPAHN, 3, root, this);
  private final Tower_spawner merkel_spawner = new Tower_spawner(Tower.MERKEL, 4, root, this);
  
  private final Label label_money = new Label();
  private final Label label_Runde = new Label();
  private final Label label_status_out = new Label();
  
  public void start(Stage primaryStage) { 
    // Were 1184 and 795
    Scene scene = new Scene(root, WindowDimensions.WIDTH, WindowDimensions.HEIGHT);
    // Begin of components
    Image bg = new Image("/bg.png");
    image_view_background.setImage(bg);
    image_view_background.setFitHeight(WindowDimensions.HEIGHT / 1.1357); // Was 700
    image_view_background.setFitWidth(WindowDimensions.WIDTH); // Was 1200
    root.getChildren().add(image_view_background);
    
    button_play.setLayoutX(WindowDimensions.WIDTH / 80.0); // Was 16
    button_play.setLayoutY(WindowDimensions.HEIGHT / 1.0965); // Was 725
    button_play.setPrefHeight(WindowDimensions.HEIGHT / 31.8);
    button_play.setPrefWidth(WindowDimensions.WIDTH / 15.786);
    button_play.setOnAction(
            this::button_play_Action
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
    loop = new Timeline(new KeyFrame(Duration.millis(33), t -> {
    // Tick towers and draw the resulting projectiles
    draw_results(game_engine.tick_towers());

    // Tick projectiles and remove colliding enemies and projectiles
    // aswell as despawning projectiles
    remove_results(game_engine.tick_projectiles());
    label_money.setText("Money: " + game_engine.get_money());

    // Here are the new enemies being spawned
    draw_results(game_engine.spawn_enemies(tick));

    // Here are all enemies being moved
    // This time the results are temporarily saved in variable because
    // they are needed more than once
    ArrayList<ImageView> results = game_engine.tick_enemies();
    // If tick enemies returns something then turn
    // on explosion and update hp afterwards
    if (results.size() > 0) {
      turn_on_explosion();
      label_HP.setText("HP: " + game_engine.get_hp());
    }
    remove_results(results);

    // Game lost
    // This won't be moved into the game class because this (for now)
    // only affects the GUI
    if (game_engine.get_hp() <= 0) {
      Image defeat = new Image("/defeat.png");
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
        Image victory = new Image("/victory.png");
        ImageView victory_view = new ImageView();
        victory_view.setImage(victory);
        victory_view.setX(WindowDimensions.WIDTH / -23.68);
        root.getChildren().add(victory_view);
        loop.stop();
        disable_buttons();
    }

    tick++;
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
    if (!this.bumming) {
      Image explosion_img = new Image("/bum.png");
      image_view_explosion.setImage(explosion_img);
      image_view_explosion.setX(WindowDimensions.WIDTH / 2.368);
      image_view_explosion.setY(WindowDimensions.HEIGHT / 1.445);
      image_view_explosion.setFitHeight(WindowDimensions.HEIGHT / 7.95);
      image_view_explosion.setFitWidth(WindowDimensions.WIDTH / 5.92);
      root.getChildren().add(image_view_explosion);
      bumming = true;
    }
  }
  
  public void turn_off_explosion() {
    if (bumming) {
      bumming = false;
      root.getChildren().remove(image_view_explosion);
    }    
  }
  
  public void draw_results(ArrayList<ImageView> results) {
    for (ImageView result : results) {
      root.getChildren().add(result);
    }
  }
  
  public void remove_results(ArrayList results) {
    for (Object result : results) {
      root.getChildren().remove(result);
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

