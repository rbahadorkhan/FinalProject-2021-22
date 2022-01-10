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

    words = inputLine.split(" ");

    String word1 = words[0];

    if (words.length > 1){

      String word2;
      if(isDirection(words) > -1){
        word2 = words[isDirection(words)];
      }
      else if(isItem(words) > -1){
        word2 = words[isItem(words)];
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
    ArrayList<String> directions = new ArrayList<String>(Arrays.asList("north", "east", "south", "west", "northeast", "northwest", "southeast", "southwest", "n", "e", "s", "w", "ne", "nw", "se", "sw"));

    
    for (int i = 0; i < input.length; i++) {
      if(directions.contains(input[i])){
        return i;
    }
  }
    return -1;
  }

  public int isItem(String[] input){
    ArrayList<String> items = new ArrayList<String>(Arrays.asList("grenade", "operator", "spectre", "odin", "vandal"));

    
    for (int i = 0; i < input.length; i++) {
      if(items.contains(input[i])){
        return i;
    }
  }
    return -1;
  }
  //see if any of the words in the arraylist are a direction or item or command and delete the rest of it...

}
