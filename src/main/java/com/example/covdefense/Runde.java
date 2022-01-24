package com.example.covdefense;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


class Runde {
  
  private int[] timeline;
  
  public Runde (int seconds) {
    this.timeline = new int[seconds * 30];
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
  
  public int length() {
    return this.timeline.length;
  }
  
  private void resetTimeline() {
    Arrays.fill(timeline, 0);
  }
  
  public boolean loadRoundFromFile(String filename) {
    try {
      File Runde = new File(filename);
      Scanner sc = new Scanner(Runde);
      
      this.timeline = new int[Integer.parseInt(sc.nextLine())];
      this.resetTimeline();
      
      while (sc.hasNextLine()) { 
        String text = sc.nextLine();
        String[] split = text.split(",");
        
        this.setEnemyOnFrame(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
      }  
      
      sc.close();
      return true;
    } catch(FileNotFoundException e) {
        return false;       
    }
  }
}
