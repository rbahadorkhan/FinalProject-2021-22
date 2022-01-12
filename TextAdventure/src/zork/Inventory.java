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
  
  public void addItem (Item item) {
    if (item.getWeight() + currentWeight <= maxWeight) {
     items.add(item);
     return;
    } 
    System.out.println("You don't have room in your inventory for this item");
     
  }


  
  public void dropItem (String item){
    if (inInventory(item)) {
      for (int i = 0; i < items.size(); i++) { 
        Item rem = items.get(i);
          if (rem.getName().equals(item)){
            items.remove(i);
            System.out.println("You dropped " + item + ".");
            return;
          }
      }
    }
    return; 
  }
  
  public void printItems() {
    for (Item s: items){
      System.out.print(s);
    }
    System.out.println();    
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
  
    public ArrayList<Item> getInventory(){
      return items;
    }
  }