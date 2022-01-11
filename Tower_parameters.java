class Tower_parameters {
  
  public int cost;
  public String sprite_path;
  public double attack_range;
  public int attack_damage;
  public int attack_speed;
  public double projectile_speed;
  public int type_id;
  
  public Tower_parameters() {
    this.cost = -1;
    this.sprite_path = "";
    this.attack_range = -1.0;
    this.attack_damage = -1;
    this.attack_speed = -1;
    this.projectile_speed = -1.0;
    this.type_id = -1;
  }
  
  public void add(String file_reading) {
    if (this.cost == -1) {
      this.cost = Integer.valueOf(file_reading);
    }
    else if (this.sprite_path == "") {
      this.sprite_path = file_reading;
    }
    else if (this.attack_range == -1.0) {
      this.attack_range = Double.valueOf(file_reading);
    }
    else if (this.attack_damage == -1) {
      this.attack_damage = Integer.valueOf(file_reading);
    }
    else if (this.attack_speed == -1) {
      this.attack_speed = Integer.valueOf(file_reading);
    }
    else if (this.projectile_speed == -1.0) {
      this.projectile_speed = Double.valueOf(file_reading);
    }
    else if (this.type_id == -1) {
      this.type_id = Integer.valueOf(file_reading);
    }
  }
  
}

