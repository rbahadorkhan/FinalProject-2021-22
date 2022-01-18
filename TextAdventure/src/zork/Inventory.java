package zork;

import java.util.ArrayList;


public class Inventory {
  
  private ArrayList<Item> items;
  private int maxWeight;
  private int currentWeight;
  
    
//constructor for a new inventory
  public Inventory(int maxWeight) {
    this.items = new ArrayList<Item>();
    this.maxWeight = maxWeight;
    this.currentWeight = 0;
  }

  //returns the maximum weight
  public int getMaxWeight() {
    return maxWeight;
  }

  //returns the amount of weight the player is holding
  public int getCurrentWeight() {
    return currentWeight;
  }
  
  //adds an item to the inventory
  public boolean addItem (Item item) {
    if (item.getWeight() + currentWeight <= maxWeight) {
     items.add(item);
     currentWeight += item.getWeight();
     return true;
    } 
    System.out.println("You don't have room in your inventory for this item.");
     return false;
  }


  //drops an item from the inventory
  public Item dropItem (String item){
    if (inInventory(item)) {
      for (int i = 0; i < items.size(); i++) { 
        Item rem = items.get(i);
          if (rem.getName().equalsIgnoreCase(item)){
            items.remove(i);             
            currentWeight -= rem.getWeight();
            System.out.println("You dropped " + item + ".");
            return rem;
          }
      }
    }
    System.out.println("You do not have that item.");
    return null;
  }
  
  //prints out all the items in the inventory
  public void printItems() {
    String inventory = "";
    for (Item s: items){
      inventory += s.getName() + ", ";
    }
      inventory = inventory.substring(0, inventory.lastIndexOf(",")); //removes the last comma
    System.out.println(inventory);    
  }

  //checks to see if an item is in the inventory
  public boolean inInventory (String item){
    for (Item i : items) {
      if (i.getName().equalsIgnoreCase(item)){
        return true;
      }
    }
      return false;
    }
  
    //returns an the items in the inventory
    public ArrayList<Item> getInventory(){
      return items;
    }

    //returns the remaining weight the player can hold
    public int remainingWeight(){
      return maxWeight-currentWeight;
    }

    //drops all the items in the inventory and returns all the items that were previously there
    public ArrayList<Item> dropAll () {
      ArrayList<Item> newRoomItems = new ArrayList<Item>();
      newRoomItems.addAll(items);
      items.clear();
      return newRoomItems;
    }
}

