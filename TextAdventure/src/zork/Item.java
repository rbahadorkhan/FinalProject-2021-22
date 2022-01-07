package zork;
//done
//combine items and make a class called weapons and have true or false for each item
      //in the item class have it check if it is an item or not

public class Item extends OpenableObject {
  private int weight;
  private String name;
  private boolean isOpenable;
  private boolean isWeapon;
  //private boolean isWeapon;

  public Item(int weight, String name, boolean isOpenable) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
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

  public void setOpenable(boolean isOpenable) {
    this.isOpenable = isOpenable;
  }

  public int damageDealt(Item gun){
    return 0; //work on tomorrow
    //so return the total damage by calculating damage per bullet, % chance and how many bullets;
  }  

  public boolean isWeapon() {
    return isWeapon;
  //}
}
