
# APCSA Sudoku Project
Student Name: **James Cocone**

This program generates a valid 9x9 Sudoku board and can print it in the console. Based on the criteria that each number in the board is unique based on its position on the row, column, and box it’s in. You can play a game of Sudoku based on your chosen difficulty of easy, medium, or hard. And if you decide to use the jar version, you can play the GUI version using JavaFX. At the end, it gives you the elapsed time it took to complete the Sudoku.

# Installation
For the console version, open this repository in your IDE (I used Visual Studio Code) and make sure you have a valid Java SDK. In your terminal at the root of the project, run:
```
cd src/main/java/com/example
java Sudoku.java
```

For the JavaFX version, obtain the Sudoku.jar file from the releases of this repository. Run ``java -jar Sudoku.jar`` based on the directory in which the file is located. Make sure you have a version of the Java SDK with JavaFX or are using the JavaFX library to open it. I recommend the [Liberica Full JDK](https://bell-sw.com/pages/downloads/#jdk-26)!

# Algorithm Design
Starting from 0,0 (the top left of the board), it starts generating numbers from left to right, top to bottom. As it goes to each position, I loop through an integer array of valid numbers that contains numbers between 1 and 9 in a random order. I stored it this way so Math.random() doesn’t generate the same number when it came to checking if the number it generated was a value in the position on the board. If all the numbers in that array were invalid for the board, I had to implement recursion to return the function to the last position it was in. It would keep going forward on the board until no numbers were able to be valid in the current position on the board, meaning it had to go back. Until the last position on the board, bottom right, was complete, it would stop as the board would be complete.
# Board Generation
Loops through rows and columns to print the board. I needed to store currRow and currCol separately because additional rows and columns were needed for the outline. At each row divisible by 4, it prints the horizontal divider. If it isn’t divisible by 4, it loops through the next three rows relative to currRow, prints the horizontal divider, then loops through the next three columns relative to currCol until the rows are complete. Once completed, another horizontal divider is added at the end, the currCol is set to 0, and the currRow is added by one. This process continues until rows are less than the length + 4 (13 in this case).

# Files
JavaFX
[App.java](src/main/java/com/example/App.java)
[Game.java](src/main/java/com/example/Game.java)
[Menu.java](src/main/java/com/example/Menu.java)

Console Sudoku / Game Logic for JavaFX
[Sudoku.java](src/main/java/com/example/Sudoku.java)

# Preview
<img src="https://raw.githubusercontent.com/EpicCarlito/APCSA-Sudoku-Project/master/SudokuConsole.png"/>
<img src="https://raw.githubusercontent.com/EpicCarlito/APCSA-Sudoku-Project/master/SudokuJavaFX.png"/>