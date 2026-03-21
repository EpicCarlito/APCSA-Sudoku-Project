import java.util.*;

public class Sudoku {
  public static Scanner sc = new Scanner(System.in);
  public static int innerLength, length;
  public static ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
  public static final String redColor = "\u001B[31m";
  public static final String blueColor = "\u001B[34m";
  public static final String defaultColor = "\u001B[0m";

  public Sudoku(int size) {
    innerLength = size;
    length = (int) (innerLength * innerLength);
  }

  public static void main(String[] args) {
    generateBoard();

    System.out.println("Welcome to Sudoku!");
    char mode = 'A';
    while (mode != 'E' && mode != 'M' && mode != 'H') {
      System.out.println("What mode do you want to play? ('E' -> Easy, 'M' -> Medium, 'H' -> Hard)");
      mode = sc.next().toUpperCase().charAt(0);
      switch (mode) {
        case 'E':
          setDifficulty(2, 3);
          break;
        case 'M':
          setDifficulty(3, 4);
          break;
        case 'H':
          setDifficulty(5, 7);
          break;
        default:
          System.out.println("Please enter 'E', 'M', 'H' for your diffculty!");
          break;
      }
    }

    long start = System.currentTimeMillis();
    while (!isBoardComplete()) {
      printBoard();

      int row = getValues("Enter a row between 1 to " + length + ": ", false);
      int col = getValues("Enter a col between 1 to " + length + ": ", false);

      if (getNum(row, col) != 0) {
        System.out.println("‼️ That position is already filled!");
        continue;
      }

      int num = getValues("Enter a number between 1 to " + length + ": ", true);

      if (checkNum(new int[] { row, col }, num)) {
        System.out.println("‼️ That position is invalid!");
        continue;
      }

      board.get(row).set(col, blueColor + String.valueOf(num) + defaultColor);
      System.out.println("✅ Placed your number!");
    }
    printBoard();

    System.out.println("🎉 You solved the sudoku!");
    long end = System.currentTimeMillis();
    long elaspedSeconds = (end - start) / 1000;

    System.out.println("Time taken: " + (elaspedSeconds / 60) + "m " + (elaspedSeconds % 60) + "s");
  }

  public static int getValues(String msg, boolean ifAdjust) {
    int curr = -1;
    while (true) {
      System.out.print(msg);
      try {
        curr = sc.nextInt();
      } catch (Exception e) {
        System.out.println("Use numbers!");
        sc.nextLine();
        continue;
      }

      if (length >= curr && curr > 0) {
        return ifAdjust ? curr : curr - 1;
      }
      System.out.println("Please enter a valid number between 1 to " + length);
    }
  }

  // I have to get rid of the color strings
  public static int cleanNum(String num) {
    return Integer.parseInt(num.replace(redColor, "").replace(blueColor, "").replace(defaultColor, ""));
  }

  public static int getNum(int row, int col) {
    return cleanNum(board.get(row).get(col));
  }

  // Removes numbers based on a range
  public static void setDifficulty(int min, int max) {
    for (int row = 0; row < length; row++) {
      int removal = (int)(Math.random() * (max - min)) + min;
      int i = 0;
      while (removal > i) {
        int randCol = (int) (Math.random() * length);
        if (getNum(row, randCol) != 0) {
          board.get(row).set(randCol, redColor + "0" + defaultColor);
          i++;
        }
      }
    }
  }

  public static void generateBoard() {
    // Prints blank board
    for (int rows = 0; rows < length; rows++) {
      // I can't have this out or else they are refer to the same memory
      ArrayList<String> newList = new ArrayList<String>();

      for (int cols = 0; cols < length; cols++) {
        newList.add(cols, "0");
      }

      board.add(rows, newList);
    }

    setBoard(0, 0);
  }

  public static void setBoard(int row, int col) {
    if (isBoardComplete())
      return;

    int nextRow = (col == length - 1) ? row + 1 : row;
    int nextCol = (col == length - 1) ? 0 : col + 1;

    ArrayList<Integer> randNums = new ArrayList<Integer>();
    while (randNums.size() < length) {
      int randNum = (int) (Math.random() * length) + 1;
      if (!randNums.contains(randNum)) {
        randNums.add(randNum);
      }
    }

    for (int num : randNums) {
      int[] coords = new int[] { row, col };

      if (!checkNum(coords, num)) {
        board.get(row).set(col, num + "");

        setBoard(nextRow, nextCol);

        if (isBoardComplete())
          return;

        board.get(row).set(col, "0");
      }
    }

  }

  public static boolean isBoardComplete() {
    for (ArrayList<String> rows : board) {
      for (String col : rows) {
        if (cleanNum(col) == 0) {
          return false;
        }
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
      ArrayList<String> currRow = board.get(innerRow);
      for (int innerCol = topCol; innerCol < topCol + innerLength; innerCol++) {
        if (cleanNum(currRow.get(innerCol)) == num)
          return true;
      }
    }

    // row
    ArrayList<String> currRow = board.get(pos[0]);
    for (String col : currRow) {
      if (cleanNum(col) == num)
        return true;
    }
    // col
    for (int innerRow = 0; innerRow < board.size(); innerRow++) {
      if (getNum(innerRow, pos[1]) == num)
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
          String curr = board.get(currRow).get(currCol);
          threeCols += curr + " ";

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
