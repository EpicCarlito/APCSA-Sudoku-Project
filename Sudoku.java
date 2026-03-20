import java.util.*;

public class Sudoku {
  public static Scanner sc = new Scanner(System.in);
  public static final int innerLength = 3;
  public static final int length = (int) (innerLength * innerLength);
  public static ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>();

  public static void main(String[] args) {
    generateBoard();

    System.out.println("Welcome to Sudoku!");
    char mode = 'A';
    while (mode != 'E' && mode != 'M' && mode != 'H') {
      System.out.println("What mode do you want to play? ('E' -> Easy, 'M' -> Medium, 'H' -> Hard)");
      mode = sc.next().toUpperCase().charAt(0);
      switch (mode) {
        case 'E':
          setDifficulty(1);
          break;
        case 'M':
          setDifficulty(5);
          break;
        case 'H':
          setDifficulty(7);
          break;
        default:
          System.out.println("Please enter 'E', 'M', 'H' for your diffculty!");
          break;
      }
    }

    long start = System.currentTimeMillis();
    while (!isBoardComplete()) {
      printBoard();

      int row, col, num = 0;
      while (true) {
        System.out.print("Enter a row between 1 to " + length + ": ");
        int curr = sc.nextInt();
        if (length >= curr && curr > 0) {
          row = curr - 1;
          break;
        }
        System.out.println("Please enter a valid row between 1 to " + length);
      }

      while (true) {
        System.out.print("Enter a column between 1 to " + length + ": ");
        int curr = sc.nextInt();
        if (length >= curr && curr > 0) {
          col = curr - 1;
          break;
        }
        System.out.println("Please enter a valid column between 1 to " + length);
      }

      if (board.get(row).get(col) != 0) {
        System.out.println("‼️ That position is already filled!");
        continue;
      }

      while (true) {
        System.out.print("Enter a number between 1 to " + length + ": ");
        int curr = sc.nextInt();
        if (length >= curr && curr > 0) {
          num = curr;
          break;
        }
        System.out.println("Please enter a valid number between 1 to " + length);
      }

      if (checkNum(new int[] { row, col }, num)) {
        System.out.println("‼️ That position is invalid!");
        continue;
      }

      board.get(row).set(col, num);
      System.out.println("✅ Placed your number!");
    }

    System.out.println("🎉 You solved the sudoku!");
    long end = System.currentTimeMillis();
    long elaspedSeconds = (end - start) / 1000;

    System.out.println("Time taken: " + (elaspedSeconds / 60) + "m " + (elaspedSeconds % 60) + "s");
  }

  public static void setDifficulty(int removal) {
    for (int row = 0; row < length; row++) {
      int i = 0;
      while (removal > i) {
        int randCol = (int) (Math.random() * length);
        if (board.get(row).get(randCol) != 0) {
          board.get(row).set(randCol, 0);
          i++;
        }
      }
    }
  }

  public static void generateBoard() {
    // Prints blank board
    for (int rows = 0; rows < length; rows++) {
      // I can't have this out or else they are refer to the same memory
      ArrayList<Integer> newList = new ArrayList<Integer>();

      for (int cols = 0; cols < length; cols++) {
        newList.add(cols, 0);
      }

      board.add(rows, newList);
    }

    setBoard(0, 0);
  }

  public static void setBoard(int row, int col) {
    if (isBoardComplete())
      return;

    int nextRow = (col == 8) ? row + 1 : row;
    int nextCol = (col == 8) ? 0 : col + 1;

    ArrayList<Integer> randNums = new ArrayList<>();
    while (randNums.size() < length) {
      int randNum = (int) (Math.random() * length) + 1;
      if (!randNums.contains(randNum)) {
        randNums.add(randNum);
      }
    }

    for (int num : randNums) {
      int[] coords = new int[] { row, col };

      if (!checkNum(coords, num)) {
        board.get(row).set(col, num);

        setBoard(nextRow, nextCol);

        if (isBoardComplete())
          return;

        board.get(row).set(col, 0);
      }
    }

  }

  public static boolean isBoardComplete() {
    for (ArrayList<Integer> rows : board) {
      for (int cols : rows) {
        if (cols == 0)
          return false;
      }
    }

    return true;
  }

  // Checks if the same number is in range (false is good)
  public static boolean checkNum(int[] pos, int num) {
    // Checks by box
    int topRow = pos[0] - pos[0] % innerLength;
    int topCol = pos[1] - pos[1] % innerLength;
    for (int innerRow = topRow; innerRow < topRow + innerLength; innerRow++) {
      ArrayList<Integer> currRow = board.get(innerRow);
      for (int innerCol = topCol; innerCol < topCol + innerLength; innerCol++) {
        if (currRow.get(innerCol) == num)
          return true;
      }
    }

    // row
    if (board.get(pos[0]).contains(num))
      return true;
    // col
    for (int innerRow = 0; innerRow < board.size(); innerRow++) {
      if (board.get(innerRow).get(pos[1]) == num)
        return true;
    }

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
