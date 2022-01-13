package zork;
import java.util.Scanner; 
//done
//combine items and make a class called weapons and have true or false for each item
      //in the item class have it check if it is an item or not
public class Item extends OpenableObject {
  private int weight;
  private String name;
  private boolean isOpenable;
  private String startingRoom;
  private boolean isWeapon;
  private boolean isHealing;
  
  public Item(int weight, String name, String startingRoom, boolean isWeapon, boolean isHealing, boolean isSpike) {
    this.weight = weight;
    this.name = name;
    //this.isOpenable = isOpenable;
    this.startingRoom = startingRoom;
    this.isWeapon = isWeapon;
    this.isHealing = isHealing;
  }
 
  public Item(){
  }
  public void open() {
    if (!isOpenable)
      System.out.println("The " + name + " cannot be opened.");
  }
  public int getWeight() {
    return weight;
  }
  public void setWeight(int weight) {
    this.weight = weight;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public boolean isOpenable() {
    return isOpenable;
  }
  public void setOpenable(boolean isOpenable) {
    this.isOpenable = isOpenable;
  }

  public String getStartingRoom() {
    return startingRoom;
} 

public boolean isWeapon() {
  return isWeapon;

}

public boolean isHealing() {
  return isHealing;
}

public String Spike(){
  boolean isDefused = false;
  boolean isCorrect = false;
  try (Scanner in = new Scanner(System.in)) {
    if(in.hasNext("diffuse")){
      System.out.println("Congratulations! You found the bomb, but you have to defuse it. Hurry up! Before it explodes and destorys humanity. To diffuse it you need to solve a riddle first:");
      System.out.println("I have cities, but no houses. I have mountains, but no trees. I have water, but no fish. What am I?");
      System.out.println("Enter your answer: ");
    }
  }
  try (Scanner ans = new Scanner(System.in)) {
    if(ans.hasNext("map")){
      isCorrect = true;
      isDefused = true;
      return ("Good job! You diffused the bomb and saved humanity!");
    }else{
      return("The answer you have given is incorrect. Please try again! Hurry up, our lives are at risk here!");
    }
  }
}
}
