package com.example.ds_project;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XMLEditor extends GUIEditor implements EventHandler {


    private Button validateButton;
    private Button convertJsonButton;
    private Button compressButton ;
    private Button decompressButton;
    private Button prettifyButton;

    private Label xmlLabel;

    TextArea xmlTextArea = new TextArea();
    private FlowPane flowPane;
    private VBox vBoxButtons;
    private HBox hBox;
    private VBox vBox;


    @Override
    public void handle(Event event)  {
        Stage stage = new Stage();
        xmlLabel = new Label("XML Editor");
        xmlLabel.setTextFill(Color.DARKBLUE);
        xmlLabel.setFont(Font.font("Arial" , FontWeight.BOLD , FontPosture.ITALIC, 28));

        validateButton = new Button("validate");
        convertJsonButton = new Button("convertJson");
        compressButton = new Button("compress");
        decompressButton = new Button(" decompress");
        prettifyButton = new Button("prettify");
        validateButton.setPrefHeight(30);
        validateButton.setPrefWidth(90);
        convertJsonButton.setPrefHeight(30);
        convertJsonButton.setPrefWidth(90);
        compressButton.setPrefHeight(30);
        compressButton.setPrefWidth(90);
        decompressButton.setPrefHeight(30);
        decompressButton.setPrefWidth(90);
        prettifyButton.setPrefHeight(30);
        prettifyButton.setPrefWidth(90);


        flowPane = new FlowPane( xmlLabel);
        flowPane.setAlignment(Pos.CENTER);

        Label validatelabel = new Label("Validate XML");
        validatelabel.setFont(Font.font("Cambria" , FontWeight.SEMI_BOLD, FontPosture.REGULAR,15));
        Label jsonlabel = new Label("Convert XML to Json");
        jsonlabel.setFont(Font.font( "Cambria", FontWeight.SEMI_BOLD , FontPosture.REGULAR,15));
        Label compresslabel = new Label("Compress XML");
        compresslabel.setFont(Font.font( "Cambria", FontWeight.SEMI_BOLD , FontPosture.REGULAR,15));
        Label decompresslabel = new Label("Decompress File");
        decompresslabel.setFont(Font.font("Cambria" , FontWeight.SEMI_BOLD , FontPosture.REGULAR,15));
        Label prettifyLabel = new Label("Prettify XML");
        prettifyLabel.setFont(Font.font("Cambria" , FontWeight.SEMI_BOLD , FontPosture.REGULAR,15));
        VBox vBoxLabel = new VBox(validatelabel,jsonlabel,compresslabel,decompresslabel,prettifyLabel);
        vBoxLabel.setSpacing(28);
        vBoxButtons = new VBox(validateButton,convertJsonButton,compressButton ,decompressButton ,prettifyButton);
        vBoxButtons.setAlignment(Pos.CENTER);
        vBoxButtons.setSpacing(15);
        hBox = new HBox(vBoxLabel,vBoxButtons);
        hBox.setSpacing(15);
        vBox = new VBox(flowPane,hBox);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 16;");
        convertJsonButton.setOnAction(new ButtonHandlers.JsonConverterHandler());
        compressButton.setOnAction(new ButtonHandlers.CompressHandler());
        decompressButton.setOnAction(new ButtonHandlers.DecompressHandler());
        prettifyButton.setOnAction(new ButtonHandlers.PrettifyHandler());
        validateButton.setOnAction(new ButtonHandlers.ValidateHandler());

        Scene scene = new Scene(vBox, 550, 400);

        stage.setTitle("XMLEditor");
        stage.setScene(scene);
        stage.show();
    }

    public TextArea getXmlTextarea() {
        return xmlTextArea;
    }

    public void setXmlTextArea(TextArea xmlTextArea) {
        this.xmlTextArea = xmlTextArea;
    }

    public XMLTree getRoot() {
        return xmlTree;
    }

    public static void main(String[] args) {
        launch();
    }


}