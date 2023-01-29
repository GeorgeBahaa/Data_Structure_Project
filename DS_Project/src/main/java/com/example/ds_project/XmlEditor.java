package com.example.ds_project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

public class XmlEditor extends Application {
    File file1;
    XMLTree xmlTree = new XMLTree();
    private Button selectButton;
    private Button validateButton;
    private Button convertJsonButton;
    private Button compressButton ;
    private Button decompressButton;
    private Button prettifyButton;
    private Button saveXml;
    private Label xmlLabel;
    private Label selectLabel;
    private Label xmlTypeLabel;
    TextArea xmlTextArea = new TextArea();
    private FlowPane flowPane;
    private FlowPane SaveXMLflowPane;

    private HBox hBoxButtons;
    private HBox hBox;
    private VBox vBox;
    String xmlText = " ";
    static String xmlFile = "";

    @Override
    public void start(Stage stage) throws IOException {
        xmlLabel = new Label("XML Editor");
        xmlLabel.setTextFill(Color.DARKBLUE);
        xmlLabel.setFont(Font.font("Arial" , FontWeight.BOLD , FontPosture.ITALIC, 28));
        xmlTypeLabel= new Label(" Type XML Text");
        xmlTypeLabel.setFont(Font.font("Arial" , FontWeight.NORMAL , FontPosture.ITALIC, 20));
        selectLabel = new Label(" SelectFile");
        selectLabel.setFont(Font.font("Arial" , FontWeight.NORMAL , FontPosture.ITALIC,20));

        selectButton = new Button("Browse");
        validateButton = new Button("validate");
        convertJsonButton = new Button("convertJson");
        compressButton = new Button("compress");
        decompressButton = new Button(" decompress");
        prettifyButton = new Button("prettify");
        saveXml = new Button("saveXml");
        selectButton.setPrefHeight(20);
        selectButton.setPrefWidth(90);
        saveXml.setPrefHeight(30);
        saveXml.setPrefWidth(90);
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


        xmlTextArea= new TextArea();
        xmlTextArea.setPrefHeight(100);


        flowPane = new FlowPane( xmlLabel);
        flowPane.setAlignment(Pos.CENTER);
        SaveXMLflowPane = new FlowPane( saveXml);
        SaveXMLflowPane.setAlignment(Pos.CENTER);
        hBox = new HBox(selectLabel, selectButton);
        hBox.setSpacing(15);
        hBoxButtons = new HBox(validateButton ,convertJsonButton ,compressButton ,decompressButton ,prettifyButton);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(10);
        vBox = new VBox(flowPane,hBox,xmlTypeLabel, xmlTextArea,SaveXMLflowPane, hBoxButtons);
        vBox.setSpacing(20);

        convertJsonButton.setOnAction(new ButtonHandlers.JsonConverterHandler());
        selectButton.setOnAction(new ButtonHandlers.selectFileHandler());
        compressButton.setOnAction(new ButtonHandlers.CompressHandler());
        decompressButton.setOnAction(new ButtonHandlers.DecompressHandler());
        prettifyButton.setOnAction(new ButtonHandlers.PrettifyHandler());
        validateButton.setOnAction(new ButtonHandlers.ValidateHandler());
        saveXml.setOnAction(action -> {
            xmlText = xmlTextArea.getText();
            xmlFile = String.valueOf(xmlText);
            try {
                xmlTree = null;
                xmlTree = new XMLTree(new StringReader(xmlText));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(vBox ,550, 400);
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