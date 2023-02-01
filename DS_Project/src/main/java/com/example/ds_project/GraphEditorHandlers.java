package com.example.ds_project;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.example.ds_project.XMLGraph.*;


public class GraphEditorHandlers {

    // static XMLGraph xmlGraph = new XMLGraph();
    static class MostInfluencerHandler extends GraphEditor implements EventHandler{
        Label label=new Label();

        @Override
        public void handle(Event event) {
            try {
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            XMLGraph.getInfluencer();
                label.setText("The Infuencer has ID "+pq.peek().getValue()+" and has "+ pq.peek().getKey()+" followers" );

        }
            catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }

            Stage stage = new Stage();
            stage.setTitle("Most Influencer");

            VBox vbox = new VBox(label);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 400, 400);
            stage.setScene(scene);
            if (XMLTree.getRoot() != null) stage.show();
        }
    }

    static class ActiveUserHandler extends GraphEditor implements EventHandler{
        @Override
        public void handle(Event event) {
            try {
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            getMostActive(); }
            catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }

            Stage stage = new Stage();
            stage.setTitle("Active User");
            Label label = new Label("The Most active user has ID "+activ+"and follows "+activfollow+ " user(s)");
            VBox vbox = new VBox(label);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 400, 400);
            stage.setScene(scene);
            if (XMLTree.getRoot() != null) stage.show();
        }
    }

    static class MutualFollowersHandler extends GraphEditor implements EventHandler{
        String UserID1 = "";
        String UserID2 = "";
        Label label;
        @Override
        public void handle(Event event) {
            UserID1 = usertext1.getText();
            UserID2 = usertext2.getText();
            try {

            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            mutualfriend(Integer.parseInt(UserID1),Integer.parseInt(UserID2)); }
            catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
            Stage stage = new Stage();
            stage.setTitle("Mutual Followers");

            label = new Label("Mutual Followers ID(s):"+mutualFriends.toString());
            VBox vbox = new VBox(label);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 400, 400);
            stage.setScene(scene);
            if (XMLTree.getRoot() != null) stage.show();
        }
    }
    static class SuggestFollowersHandler extends GraphEditor implements EventHandler{
        Label label=new Label();
        @Override
        public void handle(Event event) {
            try{
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            XMLGraph.suggestFollowers();
                 }

            catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
            label = new Label(sug);
            Stage stage = new Stage();
            stage.setTitle("Suggestions");
            VBox vbox = new VBox(label);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 400, 400);
            stage.setScene(scene);
            if (XMLTree.getRoot() != null) stage.show();
        }
    }
    static class SearchHandler extends GraphEditor implements EventHandler{
        Label label;
        PostDatabase searchpost;

        String POST = "";
        ArrayList<Post> posts;

        @Override
        public void handle(Event event) {
            Stage stage = new Stage();
            stage.setTitle("Posts");
          try {
            searchpost = new PostDatabase(xmlTree.getRoot());
            posts = searchpost.search(textField.getText());
            for(Post post : posts) {
                POST += "User ID: "+ post.getUserID()+"\n"+"Topic(s): "+ post.getTopics() +"\n"+ "Post: " + post.getBody()+"\n\n";
            } }
          catch (Exception exception) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Error");
              alert.setHeaderText("You should select xml file");
              alert.showAndWait();
          }
            label = new Label(POST);
            label.setFont(new Font("Arial", 13));
            label.setWrapText(true);
            label.setMaxWidth(450);
            ScrollPane scrollPane= new ScrollPane(label);
            scrollPane.setMinHeight(280);
            VBox vbox = new VBox(scrollPane);
            vbox.setSpacing(20);
            vbox.setStyle("-fx-padding: 16;");
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 400, 400);
            stage.setScene(scene);
            if (XMLTree.getRoot() != null) stage.show();
        }
    }
}
