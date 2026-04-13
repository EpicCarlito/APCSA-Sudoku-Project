package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu {
  @FXML
  private void onEasy(ActionEvent event) throws IOException {
    startGame(event, 1, 1);
  }

  @FXML
  private void onMedium(ActionEvent event) throws IOException {
    startGame(event, 3, 4);
  }

  @FXML
  private void onHard(ActionEvent event) throws IOException {
    startGame(event, 5, 7);
  }

  private void startGame(ActionEvent event, int min, int max) throws IOException {
    Sudoku game = new Sudoku(3);
    game.setDiff(min, max);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
    Parent root = loader.load();

    
    Game controller = loader.getController();
    controller.initialize(game);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // bless vs code with these types i have no idea on 😭
    stage.setScene(new Scene(root, 1000, 750));
  }
}