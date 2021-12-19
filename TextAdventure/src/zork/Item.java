package zork;

public class Item extends OpenableObject {
  private int weight;
  private String name;
  private boolean isOpenable;
  private int damage;
  private int accuracy;
  private int numBullets;

  public Item(int weight, int damage, String name, boolean isOpenable) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
    this.damage = damage;
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


  private int getNumBullets() {
    return numBullets;
  }  

  private int getDamage() {
    return damage;
  }

  private int getAccuracy(){
    return accuracy;
  }

  public int damageDealt(){
    int totalDamage = 0;
    for (int i = 0; i < this.getNumBullets(); i++) {
      int accuracy = (int)(Math.random()*100 + 1);
        if(accuracy < this.getAccuracy()){
          totalDamage += this.getDamage();
        }
    }
    return totalDamage;
  }
}


