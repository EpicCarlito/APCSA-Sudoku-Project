import java.util.*;

public class Sudoku {
  public static ArrayList<ArrayList<Integer>> numArr = new ArrayList<ArrayList<Integer>>();

  public static void main(String[] args) {
    generateBoard();
    System.out.println(numArr);
    printBoard();
  }

  public static void generateBoard() {
    int length = 9;
    for (int i = 0; i < length; i++) {
      ArrayList<Integer> newList = new ArrayList<Integer>();
      for (int j = 0; j < length; j++) {
        newList.add(j, j);
      }
      numArr.add(i, newList);
    }
  }

  public static void printBoard() {
    for (int i = 0; i < numArr.size() + 4; i++) {
      if (i % 4 == 0) {
        System.out.println("+-------+-------+-------+");
        continue;
      }
      int currRow = 0;
      int currCol = 0;

      // Print row with nums
      for (int j = 0; j < 3; j++) {
        System.out.print("| ");
        // Numbers in sections of 3
        for (int k = 0; k < 3; k++) {
          System.out.print(numArr.get(currRow).get(currCol) + " ");
          currCol++;
        }
      }
      System.out.print("| ");
      System.out.println("");
      currRow++;
    }
  }
}
