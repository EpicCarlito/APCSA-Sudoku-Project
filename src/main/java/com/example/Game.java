package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Game {
    @FXML
    private VBox root;
    public GridPane grid;
    public int innerLength = 3, length = (int) (innerLength * innerLength);
    public TextField[][] cells = new TextField[length][length];
    public Sudoku gameLogic;

    @FXML
    public void initialize(Sudoku gameLogic) {
        long start = System.currentTimeMillis();
        this.gameLogic = gameLogic;
        int currRow = 0;

        for (int row = 0; row < length + 4; row++) {
            if (row % 4 == 0) {
                Rectangle horizontalSep = new Rectangle((length * 50) + (2 * 8) + (6 * 2), 4); // trust my width
                                                                                               // calculation 🥲
                horizontalSep.setFill(Color.BLACK);
                GridPane.setColumnSpan(horizontalSep, length + 2); // grid systems suck
                grid.add(horizontalSep, 0, row);
                continue;
            }

            Rectangle verticalSep1 = new Rectangle(4, 50);
            Rectangle verticalSep2 = new Rectangle(4, 50);
            verticalSep1.setFill(Color.BLACK);
            verticalSep2.setFill(Color.BLACK);
            grid.add(verticalSep1, 3, row);
            grid.add(verticalSep2, 7, row);

            int currCol = 0;
            for (int gridCol = 0; gridCol < length + 2; gridCol++) {
                if (gridCol == 3 || gridCol == 7)
                    continue;

                TextField cell = new TextField();
                cell.setPrefWidth(50);
                cell.setPrefHeight(50);
                cell.setAlignment(javafx.geometry.Pos.CENTER); // javafx really be complicating it

                int currVal = Sudoku.cleanNum(gameLogic.getBoard()[currRow][currCol]);
                if (currVal != 0) {
                    cell.setText(String.valueOf(currVal));
                    cell.setEditable(false);
                } else {
                    final int CR = currRow;
                    final int CC = currCol;

                    // ptsd from tic tac toe
                    cell.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("[1-9]?")) {
                            cell.setText(oldValue);
                            return;
                        }

                        int num = Integer.parseInt(newValue);

                        if (Sudoku.cleanNum(gameLogic.getFinalBoard()[CR][CC]) != num) {
                            cell.setStyle("-fx-text-fill: red;");
                            return;
                        }

                        String[][] newBoard = gameLogic.getBoard();
                        newBoard[CR][CC] = String.valueOf(num);
                        gameLogic.setBoard(newBoard);
                        cell.setEditable(false);
                        cell.setStyle("-fx-text-fill: blue;");

                        if (gameLogic.boardCompleted()) {
                            long end = System.currentTimeMillis();
                            long elaspedSeconds = (end - start) / 1000;
                            Text winMessage = new Text("🎉 Sudoku Complete!");
                            Text timeMessage = new Text("Time taken: " + (elaspedSeconds / 60) + "m " + (elaspedSeconds % 60) + "s");
                            root.getChildren().add(winMessage);
                            root.getChildren().add(timeMessage);
                        }
                    });
                }

                cells[currRow][currCol] = cell;
                grid.add(cell, gridCol, row);
                currCol++;
            }

            currRow++;
        }
    }
}
