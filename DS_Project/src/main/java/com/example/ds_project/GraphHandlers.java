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


public class GraphHandlers {

    // static XMLGraph xmlGraph = new XMLGraph();
    static class MostInfluencerHandler extends GraphEditor implements EventHandler{
        Label label;

        @Override
        public void handle(Event event) {
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            Stage stage = new Stage();
            stage.setTitle("Most Influencer");
            if (XMLTree.getRoot() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
            Label label = new Label("The Infuencer has ID "+pq.peek().getValue()+" and has "+ pq.peek().getKey()+" followers" );
            VBox vbox = new VBox(label);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 400, 400);
            stage.setScene(scene);
            if (XMLTree.getRoot() != null) stage.show();
            // getInfluencer();
        }
    }
    static class ActiveUserHandler extends GraphEditor implements EventHandler{
        @Override
        public void handle(Event event) {
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            getMostActive();
            Stage stage = new Stage();
            stage.setTitle("Active User");
            if (XMLTree.getRoot() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
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
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            mutualfriend(Integer.parseInt(UserID1),Integer.parseInt(UserID2));
            Stage stage = new Stage();
            stage.setTitle("Mutual Followers");
            if (XMLTree.getRoot() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
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
        String UserID = "";
        Label label;
        @Override
        public void handle(Event event) {
            UserID = usertext3.getText();
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            suggestFollowers(Integer.parseInt(UserID));
            Stage stage = new Stage();
            stage.setTitle("Suggestions");
            if (XMLTree.getRoot() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
            label = new Label("Suggested to follow ID(s):");
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
            if (XMLTree.getRoot() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
            searchpost = new PostDatabase(xmlTree.getRoot());
            posts = searchpost.search(textField.getText());
            for(Post post : posts) {
                POST += "User ID: "+ post.getUserID()+"\n"+"Topic(s): "+ post.getTopics() +"\n"+ "Post: " + post.getBody()+"\n\n";
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
