package zork;
//done
/**
 * Exit
 */
public class Exit extends OpenableObject {
  private String direction;
  private String adjacentRoom;

  public Exit(String direction, String adjacentRoom, boolean isLocked, String keyId) {
    super(isLocked, keyId);
    this.direction = direction;
    this.adjacentRoom = adjacentRoom;
  }

  public Exit(String direction, String adjacentRoom, boolean isLocked, String keyId, Boolean isOpen) {
    super(isLocked, keyId, isOpen);
    this.direction = direction;
    this.adjacentRoom = adjacentRoom;
  }
// 
  public Exit(String direction, String adjacentRoom) {
    this.direction = direction;
    this.adjacentRoom = adjacentRoom;
  }

  //gives and shows the directions of the exits
  public String getDirection() {
    return direction;
  }

  //sets the direction of the exits
  public void setDirection(String direction) {
    this.direction = direction;
  }

  //gives and shows us the room next to the room you are in
  public String getAdjacentRoom() {
    return adjacentRoom;
  }

 //sets a room next to the room you are in
  public void setAdjacentRoom(String adjacentRoom) {
    this.adjacentRoom = adjacentRoom;
  }

}