package zork;

public class Weapon extends Item {
    private int damage;
    private int accuracy;
    private int numBullets;

    //weapon constructor 
    public Weapon(int itemWeight, int damage, int accuracy, int numBullets, String name, String startingRoom, boolean isWeapon, boolean isHealing, boolean isSpike, String desciption) { 
        super(itemWeight, name, startingRoom, isWeapon, isHealing, isSpike, desciption);
        this.damage = damage;
        this.accuracy = accuracy;
        this.numBullets = numBullets;
      }

      //returns the number of bullets
      private int getNumBullets() {
        return numBullets;
      }
    
      //returns the damage per bullet
      private int getDamage() {
        return damage;
      }
    
      //returns the accuracy of boots
      private int getAccuracy(){
        return accuracy;
      }
    
      //calculates the totaldamage
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
