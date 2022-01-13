package zork;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
  private CommandWords commands; // holds all valid command words
  private Scanner in;

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

    words = inputLine.split(" ");

    String word1 = words[0];

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

    
      if (commands.isCommand(word1))
        return new Command(word1, word2);
      else
        return new Command(null, word2);
  } else{
    if(commands.isCommand(word1)){
      return new Command(word1, null);
    }
  }
  return null;
}

  /**
   * Print out a list of valid command words.
   */
  public void showCommands() {
    commands.showAll();
  }

  public int isDirection(String[] input){
    ArrayList<String> directions = new ArrayList<String>(Arrays.asList("north", "east", "south", "west", "northeast", "northwest", "southeast", "southwest"));

    
    for (int i = 0; i < input.length; i++) {
      if(directions.contains(input[i])){
        return i;
    }
  }
    return -1;
  }

  public int isItem(String[] input){
    ArrayList<String> items = new ArrayList<String>(Arrays.asList("grenade", "operator", "spectre", "odin", "vandal", "health"));

    
    for (int i = 0; i < input.length; i++) {
      if(items.contains(input[i])){
        return i;
    }
  }
    return -1;
  }
  //see if any of the words in the arraylist are a direction or item or command and delete the rest of it...

  public int isTeleportRoom(String[] input){
    ArrayList<String> teleportRooms = new ArrayList<String>(Arrays.asList("fountain", "hookah", "mid", "garage"));

    for (int i = 0; i < input.length; i++) {
      if(teleportRooms.contains(input[i])){
        return i;
    }
  }
    return -1;
  }

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
