package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/* public class CommentCellFactory implements Callback<ListView<Comment>>, ListCell<Comment> {
  @Override
  public ListCell<Comment> call(ListView<Comment> param) {
    return new ListCell<>(){
      @Override
      public void updateItem(Comment comment, boolean empty) {
        super.updateItem(comment, empty);
        if (empty || comment == null) {
          setText(null);
          setGraphic(null);
          setStyle("-fx-background-color: transparent");
          setStyle("-fx-background: transparent");
        } else {
          setGraphic(getCommentCell(comment));
          setText(null);
        }
      }
    };
  }
  VBox getCommentCell(Comment comment) {
    VBox commentPane = new VBox();
    commentPane.setId("commentBox");
    return commentPane;
  }
}*/
