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
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.example.ds_project.XMLGraph.*;


public class GraphEditorHandlers {

    static class MostInfluencerHandler extends GraphEditor implements EventHandler{
        Label label=new Label();

        @Override
        public void handle(Event event) {
            try {
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            XMLGraph.getInfluencer();
                label.setText("The Most infuencer user has ID "+pq.peek().getValue()+" and has "+ pq.peek().getKey()+" followers" );

        }
            catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
             label.setFont((Font.font("Arial" , FontWeight.BOLD , FontPosture.ITALIC,18)));
            Stage stage = new Stage();
            stage.setTitle("Most Influencer");

            VBox vbox = new VBox(label);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 500, 250);
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
            Label label = new Label("The Most active user has ID "+activ+" and follows "+activfollow+ " user(s)");
            label.setFont((Font.font("Arial" , FontWeight.BOLD , FontPosture.ITALIC,18)));
            VBox vbox = new VBox(label);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 480, 200);
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
            if (XMLTree.getRoot() != null && UserID1!="" && UserID1!=""){
                XMLGraph.constructGraph(xmlTree.getRoot());
                XMLGraph.MergeSortAll();
                try {
                    Integer.parseInt(UserID1);
                    Integer.parseInt(UserID2);
                    mutualfriend(Integer.parseInt(UserID1),Integer.parseInt(UserID2));
                    Stage stage = new Stage();
                    stage.setTitle("Mutual Followers");
                    if (mutualFriends.size() == 0) {
                        label = new Label("Has no mutual friend");
                    } else label = new Label("Mutual Followers ID(s): " + mutualFriends.toString());
                    label.setFont((Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 18)));
                    VBox vbox = new VBox(label);
                    vbox.setSpacing(20);
                    vbox.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(vbox, 400, 400);
                    stage.setScene(scene);
                    if (XMLTree.getRoot() != null && UserID1 != "" && UserID1 != "")
                        stage.show();
                    mutualFriends.clear();
                }
                catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter a valid User ID");
                    alert.showAndWait();
                }
                catch (Exception exception){
                    if(Integer.parseInt(UserID1) > xmlGraphNodes.size() || Integer.parseInt(UserID2) > xmlGraphNodes.size() ){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("User not found");
                        alert.setHeaderText("We have only "+  (xmlGraphNodes.size()-1) +" user(s)");
                        alert.showAndWait();
                    }
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if(XMLTree.getRoot() != null) {
                    alert.setTitle("Error");
                    alert.setHeaderText("You should select two users");
                }
                else{
                    alert.setTitle("Error");
                    alert.setHeaderText("You should select xml file");
                }
                alert.showAndWait();
            }
    }
    }
    static class SuggestFollowersHandler extends GraphEditor implements EventHandler{
        Label label=new Label();
        @Override
        public void handle(Event event) {
            try{
            XMLGraph.constructGraph(xmlTree.getRoot());
            XMLGraph.MergeSortAll();
            getMostActive();
            suggestFollowers();
                 }

            catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }
            label = new Label(sug);
            Stage stage = new Stage();
            label.setFont((Font.font("Arial" , FontWeight.BOLD , FontPosture.ITALIC,13)));

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
            }
            if (posts.size()==0){
                POST += "\t \t \t \t \t Not found";
            }
          }
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

            searchpost = null;
            posts.clear();
            POST=" ";

        }
    }
}
