package zork;

public class Item extends PickableObject {
  private int weight;
  private String name;
  private boolean isPickable;

  public Item(int weight, String name, boolean isPickable) {
    this.weight = weight;
    this.name = name;
    this.isPickable = isPickable;
  }

  public void open() {
    if (!isPickable)
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

  public boolean isPickable() {
    return isPickable;
  }

  public void setPickable(boolean isPickable) {
    this.isPickable = isPickable;
  }

}
