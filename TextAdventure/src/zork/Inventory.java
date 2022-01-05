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
  
  public boolean addItem (Item item) {
    if (item.getWeight() + currentWeight <= maxWeight) {
    return items.add(item);
    } 
    System.out.println("You don't have room in your inventory for this item");
    return false; 
  }


  //work on dropItem
  public boolean dropItem (String item){
    if (inInventory(item)) {
      for (int i = 0; i < items.size(); i++) {
        Item rem = items.get(i);
          if (rem.getName().equals(item)){
            items.remove(i);
            System.out.println("You dropped " + item + ".");
            return true;
          }
      }
    }
    return false; 
  }
  
  //work on this
  public boolean inInventory (String item){
    for (Item i : items) {
      if (i.getName().equals(item)){
        return true;
      }
    }
      return false;
    }
  }