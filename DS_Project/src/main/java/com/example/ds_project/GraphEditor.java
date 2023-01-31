package com.example.ds_project;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GraphEditor extends GUIEditor implements EventHandler {

    private Button saveXml;
    private Button mostInfluencerButton;
    private Button activeUserButton;
    private Button mutualUsersButton;
    private Button suggestFollowersButton;
    private Button searchButton;

    private Label graphLabel;
    private Label selectLabel;
    private Label graphTypeLabel;
    TextArea graphTextArea = new TextArea();
    private FlowPane flowPane;
    private FlowPane SaveGraphflowPane;

    private VBox vBoxButtons;
    private VBox vBoxLabel;
    private HBox hBox;
    private VBox vBox;
    static TextField textField = new TextField();
    static TextField usertext3 = new TextField();
    static TextField usertext1 = new TextField();
    static TextField usertext2 = new TextField();


    public TextArea getXmlTextarea() {
        return graphTextArea;
    }

    public void setgraphTextArea(TextArea graphTextArea) {
        this.graphTextArea = graphTextArea;
    }

    public XMLTree getRoot() {
        return xmlTree;
    }


    @Override
    public void handle(Event event) {
        Stage stage = new Stage();
        graphLabel = new Label("Graph Editor");
        graphLabel.setTextFill(Color.DARKBLUE);
        graphLabel.setFont(Font.font("Arial" , FontWeight.BOLD , FontPosture.ITALIC, 28));
        mostInfluencerButton = new Button("Most Influencer User");
        activeUserButton = new Button("Active User");
        mutualUsersButton = new Button("Mutual Followers");
        suggestFollowersButton = new Button("Suggest Followers");
        searchButton = new Button("Search");
        mostInfluencerButton.setPrefHeight(30);
        mostInfluencerButton.setPrefWidth(130);
        activeUserButton.setPrefHeight(30);
        activeUserButton.setPrefWidth(120);
        mutualUsersButton.setPrefHeight(30);
        mutualUsersButton.setPrefWidth(120);
        suggestFollowersButton.setPrefHeight(30);
        suggestFollowersButton.setPrefWidth(130);

        flowPane = new FlowPane(graphLabel);
        flowPane.setAlignment(Pos.CENTER);


        textField.setPromptText("Enter word");

        Label searchlabel = new Label("Enter word to search");
        searchlabel.setFont(Font.font("Cambria" , FontWeight.SEMI_BOLD, FontPosture.REGULAR,15));
        Label influencerlabel = new Label("Find Most Influencer User");
        influencerlabel.setFont(Font.font("Cambria" , FontWeight.SEMI_BOLD, FontPosture.REGULAR,15));
        Label activeuserlabel = new Label("Find Most Active User");
        activeuserlabel.setFont(Font.font("Cambria" , FontWeight.SEMI_BOLD, FontPosture.REGULAR,15));
        Label mutuallabel = new Label("Find mutual followers");
        mutuallabel.setFont(Font.font("Cambria" , FontWeight.SEMI_BOLD, FontPosture.REGULAR,15));
        Label suggestlabel = new Label("Suggest Followers");
        suggestlabel.setFont(Font.font("Cambria" , FontWeight.SEMI_BOLD, FontPosture.REGULAR,15));

        HBox spin = new HBox(new Label("User1"),usertext1,new Label("User2"),usertext2);
        spin.setSpacing(10);
        vBoxLabel = new VBox(influencerlabel,activeuserlabel,mutuallabel);
        vBoxLabel.setSpacing(30);
        vBoxButtons = new VBox(mostInfluencerButton,activeUserButton,mutualUsersButton);
        vBoxButtons.setAlignment(Pos.CENTER);
        vBoxButtons.requestLayout();
        vBoxButtons.setSpacing(20);
        hBox = new HBox(vBoxLabel,vBoxButtons);
        hBox.setSpacing(15);
        //suggestFollowersButton.setAlignment(Pos.CENTER);
        HBox hbox2 = new HBox(suggestlabel,suggestFollowersButton);
        hbox2.setSpacing(60);
        FlowPane flow = new FlowPane(searchlabel,textField,searchButton);
        flow.setHgap(10);

        HBox spin2 = new HBox(new Label("User ID"),usertext3);
        spin2.setSpacing(10);
        vBox = new VBox(flowPane,flow,hBox,spin,hbox2,spin2);
        vBox.setFillWidth(true);
        vBox.setStyle("-fx-padding: 16;");
        vBox.setSpacing(20);

        searchButton.setOnAction(new GraphHandlers.SearchHandler());
        mostInfluencerButton.setOnAction(new GraphHandlers.MostInfluencerHandler());
        activeUserButton.setOnAction(new GraphHandlers.ActiveUserHandler());
        mutualUsersButton.setOnAction(new GraphHandlers.MutualFollowersHandler());
        suggestFollowersButton.setOnAction(new GraphHandlers.SuggestFollowersHandler());


        Scene scene = new Scene(vBox, 550, 400);
        stage.setTitle("GraphEditor");
        stage.setScene(scene);
        stage.show();


    }
}