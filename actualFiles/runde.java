import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


class Runde {
  
  private int[] timeline;
  
  public Runde (int sekunden) {
    this.timeline = new int[sekunden * 30];
    this.resetTimeline();
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
  
  private void resetTimeline() {
    for (int i = 0; i < timeline.length; i++) {
      timeline[i] = 0;
    } 
  }
  
  public void loadRoundFromFile(String filename) {
    try {
      File Runde = new File("H://GitHub//CovDefense//actualFiles//" + filename);
      Scanner sc = new Scanner(Runde);
      
      this.timeline = new int[Integer.parseInt(sc.nextLine())];
      this.resetTimeline();
      
      while (sc.hasNextLine()) { 
        String text = sc.nextLine();
        String[] split = text.split(",");
        
        this.setEnemyOnFrame(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
      }  
      
      sc.close();
      
    } catch(FileNotFoundException e) {
        System.out.println("Datei nicht da!");
        e.printStackTrace();        
    }
  }
}
