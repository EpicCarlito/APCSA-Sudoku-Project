package com.example;

import java.util.*;

public class Sudoku {
  public static int innerLength, length;
  public static String[][] board;

  public static Scanner sc = new Scanner(System.in);
  public static final String redColor = "\u001B[31m";
  public static final String blueColor = "\u001B[34m";
  public static final String defaultColor = "\u001B[0m";

  public Sudoku(int smLength) {
    innerLength = smLength;
    length = innerLength * innerLength;
    board = new String[length][length];
    genBoard(0, 0);
  }

  public static void main(String[] args) {
    innerLength = 3;
    length = innerLength * innerLength;
    board = new String[length][length];
    genBoard(0, 0);

    System.out.println("Welcome to Sudoku!");

    char mode = 'A';
    while (mode != 'E' && mode != 'M' && mode != 'H') {
      System.out.println("What mode do you want to play? ('E' -> Easy, 'M' -> Medium, 'H' -> Hard)");
      mode = sc.next().toUpperCase().charAt(0);
      switch (mode) {
        case 'E':
          setDifficulty(1, 1);
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

      if (checkNum(num, row, col)) {
        System.out.println("‼️ That position is invalid!");
        continue;
      }

      board[row][col] = blueColor + String.valueOf(num) + defaultColor;
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
    if (num == null)
      return 0;
    return Integer.parseInt(num.replace(redColor, "").replace(blueColor, "").replace(defaultColor, ""));
  }

  public static int getNum(int row, int col) {
    if (board[row][col] == null)
      return 0;
    return cleanNum(board[row][col]);
  }

  // Removes numbers based on a range
  public static void setDifficulty(int min, int max) {
    for (int row = 0; row < length; row++) {
      int removal = (int) (Math.random() * (max - min)) + min;
      int i = 0;
      while (removal > i) {
        int randCol = (int) (Math.random() * length);
        if (getNum(row, randCol) != 0) {
          board[row][randCol] = redColor + "0" + defaultColor;
          i++;
        }
      }
    }
  }

  public void setDiff(int min, int max) {
    for (int row = 0; row < length; row++) {
      int removal = (int) (Math.random() * (max - min)) + min;
      int i = 0;
      while (removal > i) {
        int randCol = (int) (Math.random() * length);
        if (getNum(row, randCol) != 0) {
          board[row][randCol] = redColor + "0" + defaultColor;
          i++;
        }
      }
    }
  }

  public static void genBoard(int row, int col) {
    if (isBoardComplete())
      return;

    int nextRow = (col == length - 1) ? row + 1 : row;
    int nextCol = (col == length - 1) ? 0 : col + 1;

    int[] randNums = new int[length];
    int i = 0;
    while (i < length) {
      int randNum = (int) (Math.random() * length) + 1;
      if (!Arrays.asList(randNums).contains(randNum)) {
        randNums[i] = randNum;
        i++;
      }
    }

    for (int num : randNums) {
      if (!checkNum(num, row, col)) {
        board[row][col] = num + "";

        genBoard(nextRow, nextCol);

        if (isBoardComplete())
          return;

        board[row][col] = "0";
      }
    }
  }

  public static boolean isBoardComplete() {
    for (String[] rows : board) {
      for (String col : rows) {
        if (cleanNum(col) == 0) {
          return false;
        }
      }
    }

    return true;
  }

  public boolean boardCompleted() {
    for (String[] rows : board) {
      for (String col : rows) {
        if (cleanNum(col) == 0) {
          return false;
        }
      }
    }

    return true;
  }

  // Checks if the same number is in range (false is good)
  public static boolean checkNum(int num, int row, int col) {
    // Checks by box
    int topRow = row - row % innerLength;
    int topCol = col - col % innerLength;
    for (int innerRow = topRow; innerRow < topRow + innerLength; innerRow++) {
      String[] currRow = board[innerRow];
      for (int innerCol = topCol; innerCol < topCol + innerLength; innerCol++) {
        if (cleanNum(currRow[innerCol]) == num)
          return true;
      }
    }

    // row
    String[] currRow = board[row];
    for (String currCol : currRow) {
      if (cleanNum(currCol) == num)
        return true;
    }

    // col
    for (int innerRow = 0; innerRow < length; innerRow++) {
      if (getNum(innerRow, col) == num)
        return true;
    }

    return false;
  }

  public static void printBoard() {
    int currRow = 0;
    int currCol = 0;

    for (int rows = 0; rows < length + 4; rows++) {
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
          String curr = board[currRow][currCol];
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

  public void setBoard(String[][] newBoard) {
    board = newBoard;
  }

  public String[][] getBoard() {
    return board;
  }
}
