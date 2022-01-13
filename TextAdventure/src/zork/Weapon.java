package zork;

public class Weapon extends Item {
    private int damage;
    private int accuracy;
    private int numBullets;

    public Weapon(int itemWeight, int damage, int accuracy, int numBullets, String name, String startingRoom, boolean isWeapon, boolean isHealing, boolean isSpike) { 
        super(itemWeight, name, startingRoom, isWeapon, isHealing, isSpike);
        this.damage = damage;
        this.accuracy = accuracy;
        this.numBullets = numBullets;
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
