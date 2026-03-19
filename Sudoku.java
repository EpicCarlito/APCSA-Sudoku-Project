import java.util.*;

public class Sudoku {
  public static ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>();

  public static void main(String[] args) {
    generateBoard();
    printBoard();
  }

  public static void generateBoard() {
    // Prints blank board
    int length = 9;

    for (int rows = 0; rows < length; rows++) {
      // I can't have this out or else they are refer to the same memory
      ArrayList<Integer> newList = new ArrayList<Integer>();

      for (int cols = 0; cols < length; cols++) {
        newList.add(cols, -1);
      }

      board.add(rows, newList);
    }

    // Fill out first row
    for (int cols = 0; cols < length; cols++) {
      ArrayList<Integer> firstRow = board.get(0);
      int randNum = (int) (Math.random() * length);
      int[] coords = new int[] { 0, cols };
      if (!checkNum(coords, randNum)) {
        firstRow.set(cols, randNum);
      } else {
        cols--;
      }
    }
  }

  // Checks if the same number is in range (false is good)
  public static boolean checkNum(int[] pos, int num) {
    // Checks by box
    int length = 3;
    int topRow = pos[0] - pos[0] % length;
    int topCol = pos[1] - pos[1] % length;
    for (int innerRow = topRow; innerRow < topRow + length; innerRow++) {
      ArrayList<Integer> currRow = board.get(innerRow);
      for (int innerCol = topCol; innerCol < topCol + length; innerCol++) {
        if (currRow.get(innerCol) == num)
          return true;
      }
    }

    if (board.get(pos[0]).contains(num))
      return true;
    if (board.get(pos[1]).contains(num))
      return true;

    return false;
  }

  public static void printBoard() {
    int currRow = 0;
    int currCol = 0;

    for (int rows = 0; rows < board.size() + 4; rows++) {
      if (rows % 4 == 0) {
        System.out.println("+-------+-------+-------+");
        continue;
      }

      // Print row with nums
      for (int innerRow = 0; innerRow < 3; innerRow++) {
        System.out.print("| ");
        // Numbers in sections of 3
        String threeCols = "";
        for (int innerCol = 0; innerCol < 3; innerCol++) {
          threeCols += board.get(currRow).get(currCol) + " ";
          currCol++;
        }
        System.out.print(String.format("%3s", threeCols));
      }
      System.out.print("| ");
      System.out.println("");
      currRow++;
      currCol = 0;
    }
  }
}
