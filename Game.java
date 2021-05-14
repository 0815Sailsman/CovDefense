class Game{
  private int round;
  private int targetround;
  private int difficulty;
  private int hp;
  private int easy;
  private int medium;
  private int hard;
  private int realistic;
  
  public boolean didplayerlose(int hp){
    if(hp <= 0){
      return true;
    } else {
      return false;
    }
  }
  
  public boolean didplayerwin(int round, boolean didplayerlose){
    if(didplayerlose == false && round >= targetround){
      return true;
    } else {
      return false;
    } 
  }
  
  public int settargetround(int difficulty, int easy, int medium, int hard, int realistic){
    switch (difficulty) {
      case  0: 
        return easy;
      case  1: 
        return medium;
      case  2:
        return hard;
      case  3:
        return realistic;  
      default: 
        break;
    } // end of switch
    return -1;
  }
  
  public int gettargetround(int targetround){
    return targetround;
  }
  
  public int getdifficulty(int difficulty){
    return difficulty;
  }
  
  public int getround(){
    return round;
  }
  
  public int gethp(){
    return hp;
  }
}
