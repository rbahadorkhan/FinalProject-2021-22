package zork;

import java.util.ArrayList;


public class Inventory {
  
  private ArrayList<Item> items;
  private int maxWeight;
  private int currentWeight;
  
    

  public Inventory(int maxWeight) {
    this.items = new ArrayList<Item>();
    this.maxWeight = maxWeight;
    this.currentWeight = 0;
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public int getCurrentWeight() {
    return currentWeight;
  }
  
  public boolean addItem (Command com, Item item) {
    if (!com.hasSecondWord()){
      System.out.println("What do you want to take?");

    } else if (com.getSecondWord().toString().equals(item.getName())) { //add weight part
      return items.add(item);

    } else if (com.getSecondWord().toString() != item.getName()){
      System.out.println("This item doesn't exist");

    } else {
      System.out.println("There is no space in your inventory for this item, drop it or something else.");
    }
    return true; 
    //work on it after commands

  }

  public boolean addItem(Item item) {
    if (item.getWeight() + currentWeight <= maxWeight)
      return items.add(item);
    else {
      System.out.println("There is no room to add the item.");
      return false;
    }
  }

  public boolean dropItem (Command com){
    if (com.getSecondWord().size() > 0) {
      for (int i = items.size(); i >= 0; i--) {
        String name = com.getSecondWord().toString();
          if (items.get(i).getName().equals(name)) {
            items.remove(i); 
            return true; 
          } else if (items.get(i).getName() != name) {
            System.out.println("You don't have this.");
          }
      } 
      } else {
        System.out.println("What do you want to drop?");
    }
    return false; //just until i finish it
  }

}
