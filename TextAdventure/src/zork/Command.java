package zork;

import java.util.ArrayList;

public class Command {
  private ArrayList <String> commandWords;
  private String commandWord;
  private String secondWord;
  // test

  /**
   * Create a command object. First and second word must be supplied, but either
   * one (or both) can be null. The command word should be null to indicate that
   * this was a command that is not recognised by this game.
   */


  public Command (String word1, String word2){
  commandWords = new ArrayList<String>(); //new array list to hold words
  //adding all the words, so 2
  commandWords.add(word1);
  commandWords.add(word2);
  commandWord = word1;
  secondWord = word2;
}

  /**
   * Return the command word (the first word) of this command. If the command was
   * not understood, the result is null.
   */
  public String getCommandWord() {
    if (commandWord == null) {
      return "";
    }
    return commandWord;
  }

  /**
   * Return the second word of this command. Returns null if there was no second
   * word.
   */
  public String getSecondWord(){
    return secondWord;
  }

  /**
   * Return true if this command was not understood.
   */
  public boolean isUnknown() {
    return (commandWord == null);
  }

  /**
   * Return true if the command has a second word.
   */
  public boolean hasSecondWord() {
    String secondWord = commandWords.get(1);
    if (secondWord != null){
      return true;
    } else {
    return false;
    }
  }
}
