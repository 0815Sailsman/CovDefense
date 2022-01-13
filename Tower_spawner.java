import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageView.*;


class Tower_spawner {
  
  private Tower_parameters params;
  private Button button;
  private Label label;
  private int position;
  private Pane root;
  private Main main;
  
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
    button.setLayoutX(210  + 85 * this.position);
    button.setLayoutY(725);
    button.setPrefHeight(25);
    button.setPrefWidth(75);
    button.setOnAction(
      (event) -> {button_Action(event, this.params, this.main);} 
    );
    Image img = new Image(this.params.sprite_path);
    ImageView view = new ImageView(img);
    view.setFitHeight(50);
    view.setFitWidth(50);
    button.setGraphic(view);
    return button;
  }
  
  // Poistion specifies which 
  private Label construct_label() {
    label = new Label();
    label.setLayoutX(230 + this.position * 85);
    label.setLayoutY(705);
    label.setPrefHeight(20);
    label.setPrefWidth(78);
    label.setText(this.params.cost + "$");
    return label;
  }


  public void button_Action(Event evt, Tower_parameters params, Main main) {
    this.root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        main.place_tower(main.game_engine.place_tower_logic(event, params));
      }
    });
  }
  
  // Getter und Setter
  public Tower_parameters get_params() {
    return this.params;
  }
  
  public Button get_button() {
    return this.button;
  }

  public Label get_label() {
    return this.label;
  }
  
  public int get_position() {
    return this.position;
  }


}

