package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PrimaryController {
    @FXML
    public GridPane grid;
    public int innerLength = 3, length = (int) (innerLength * innerLength);
    public TextField[][] cells = new TextField[length][length];

    @FXML
    public void initialize() {
        int currRow = 0;

        for (int row = 0; row < length + 4; row++) {
            if (row % 4 == 0) {
                Rectangle horizontalSep = new Rectangle((length * 50) + (2 * 8) + (6 * 2), 4); // trust my width calculation 🥲
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

                cells[currRow][currCol] = cell;
                grid.add(cell, gridCol, row);
                currCol++;
            }

            currRow++;
        }
    }
}
