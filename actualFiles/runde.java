class Runde {
  
  private int[] timeline;
  
  public Runde (int sekunden) {
    this.timeline = new int[sekunden * 30];
    for (int i = 0; i < timeline.length; i++) {
      timeline[i] = 0;
    }
  }
  
  public void setEnemyOnFrame(int frame, int enemyID) {
    this.timeline[frame] = enemyID;
  }
  
  public int getEnemyOnFrame(int frame) {
    try {
      return this.timeline[frame];
    } catch(Exception e) {
      return -1;
    }
  }
  
  public int getRoundLength() {
    return this.timeline.length;
  }
}
