package com.example.covdefense;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


class Tower_spawner {
  
  private final Tower_parameters params;
  private Button button;
  private Label label;
  private final int position;
  private final Pane root;
  private final Main main;
  
  // There are 5 Buttons, position specifies which of these buttons this id
  public Tower_spawner(int tower_id, int position, Pane root, Main main) {
    this.root = root;
    this.position = position;
    this.main = main;
    this.params = Tower.load_tower_parameters(Tower.id_to_path(tower_id));
    this.button = this.construct_button();
    this.label = this.construct_label();
  }

  private Button construct_button() {
    button = new Button();
    button.setLayoutX((WindowDimensions.WIDTH / 5.638) + (WindowDimensions.WIDTH / 13.929) * this.position);
    button.setLayoutY(WindowDimensions.HEIGHT / 1.0966);
    button.setPrefHeight(WindowDimensions.HEIGHT / 31.8);
    button.setPrefWidth(WindowDimensions.WIDTH / 15.786);
    button.setOnAction(
      (event) -> button_Action(this.params, this.main)
    );
    Image img = new Image(this.params.sprite_path);
    ImageView view = new ImageView(img);
    view.setFitHeight((WindowDimensions.HEIGHT / 15.9));
    view.setFitWidth((WindowDimensions.WIDTH / 23.68));
    button.setGraphic(view);
    return button;
  }
  
  // Position specifies which
  private Label construct_label() {
    label = new Label();
    label.setLayoutX((WindowDimensions.WIDTH / 5.148) + this.position * (WindowDimensions.WIDTH / 13.929));
    label.setLayoutY(WindowDimensions.HEIGHT / 1.128);
    label.setPrefHeight(WindowDimensions.HEIGHT / 39.75);
    label.setPrefWidth(WindowDimensions.WIDTH / 15.18);
    label.setText(this.params.cost + "$");
    return label;
  }


  public void button_Action(Tower_parameters params, Main main) {
    this.root.setOnMouseClicked(event -> main.place_tower(main.game_engine.place_tower_logic(event, params)));
  }

  public Button get_button() {
    return this.button;
  }

  public Label get_label() {
    return this.label;
  }


}

