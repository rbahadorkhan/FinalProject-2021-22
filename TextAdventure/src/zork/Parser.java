package zork;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
  private CommandWords commands; // holds all valid command words
  private Scanner in;

  //creates new parser
  public Parser() {
    commands = new CommandWords();
    in = new Scanner(System.in);
  }

  public Command getCommand() throws java.io.IOException {
    String inputLine = "";
    String[] words;

    System.out.print("> "); // print prompt

    inputLine = in.nextLine();
    inputLine.toLowerCase();

    //splits the words and adds them to the array words
    words = inputLine.split(" ");

    String word1 = words[0];

    //checks to see if there is a command word in words. Sets it to word 1
    for (String s : words) {
      if(commands.isCommand(s)){
        word1 = s;
        break;
      }
    }

    /*if there is more than 1 word it checks to see if any of the other words is a direction, item, something you can display
     * or teleport room and makes that the second word
     */ 

    if (words.length > 1){

      String word2;
      if(isDirection(words) > -1){
        word2 = words[isDirection(words)];
      } 
      else if(isItem(words) > -1){
        word2 = words[isItem(words)];
        if(word2.equals("health") || word2.equals("potion"))
          word2 = "Health Potion";
      }
      else if(isTeleportRoom(words) > -1){
        word2 = words[isTeleportRoom(words)];
      }
      else if(isDisplayable(words)> -1){
        word2 = words[isDisplayable(words)];
      }
      else{
        word2 = null;
      }

    //creates and returns the commands. 
      if (commands.isCommand(word1))
        return new Command(word1, word2);
      else
        return new Command(null, word2);
  } else{
    if(commands.isCommand(word1)){
      return new Command(word1, null);
    }
  }
  return new Command(null, null);
}

  /**
   * Print out a list of valid command words.
   */
  public void showCommands() {
    commands.showAll();
  }

  //checks to see if there is a direction word in the array
  public int isDirection(String[] input){
    ArrayList<String> directions = new ArrayList<String>(Arrays.asList("north", "east", "south", "west", "northeast", "northwest", "southeast", "southwest"));

    
    for (int i = 0; i < input.length; i++) {
      if(directions.contains(input[i])){
        return i;
    }
  }
    return -1;
  }

 //checks to see if there is a item word in the array
  public int isItem(String[] input){
    ArrayList<String> items = new ArrayList<String>(Arrays.asList("grenade", "operator", "spectre", "odin", "vandal", "health", "pistol", "explosive", "fireball", "marshal", "pistol", "phantom", "ares"));

    
    for (int i = 0; i < input.length; i++) {
      if(items.contains(input[i])){
        return i;
    }
  }
    return -1;
  }
  //see if any of the words in the arraylist are a direction or item or command and delete the rest of it...


  //checks to see if there is a teleport room word in the array
  public int isTeleportRoom(String[] input){
    ArrayList<String> teleportRooms = new ArrayList<String>(Arrays.asList("fountain", "hookah", "mid", "garage"));

    for (int i = 0; i < input.length; i++) {
      if(teleportRooms.contains(input[i])){
        return i;
    }
  }
    return -1;
  }

  //checks to see if there is a display word in the array
  public int isDisplayable(String[] input){
    ArrayList<String> teleportRooms = new ArrayList<String>(Arrays.asList("kills", "health", "inventory"));

    for (int i = 0; i < input.length; i++) {
      if(teleportRooms.contains(input[i])){
        return i;
    }
  }
    return -1;
  }

}
