package com.example.ds_project;

import javafx.application.Application;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class GUIEditor extends Application {
    private Button saveXml;
    XMLTree xmlTree = new XMLTree();
    static String path;
    File file1;
    private Label xmlLabel;
    private Label selectLabel;
    private Label xmlTypeLabel;
    private Button selectButton;
    TextArea xmlTextArea = new TextArea();
    CheckBox c1;
    private Label guilabel;
    CheckBox c2;
    private FlowPane flowPane;
    private HBox hbox;
    private VBox vBox;
    private FlowPane SaveXMLflowPane;

    String xmlText = " ";
    static String xmlFile = "";

    public TextArea getXmlTextarea() {
        return xmlTextArea;
    }

    public void setXmlTextArea(TextArea xmlTextArea) {
        this.xmlTextArea = xmlTextArea;
    }
    public XMLTree getRoot() {
        return xmlTree;
    }

    @Override
    public void start(Stage stage) throws IOException {
        saveXml = new Button("saveXml");
        saveXml.setPrefHeight(30);
        saveXml.setPrefWidth(90);
        xmlTypeLabel= new Label(" Type XML Text");
        xmlTypeLabel.setFont(Font.font("Arial" , FontWeight.NORMAL , FontPosture.ITALIC, 20));
        xmlTextArea= new TextArea();
        xmlTextArea.setPrefHeight(100);
        selectLabel = new Label(" SelectFile");
        selectLabel.setFont(Font.font("Arial" , FontWeight.NORMAL , FontPosture.ITALIC,20));
        guilabel = new Label("GUI Editor");
        guilabel.setTextFill(Color.DARKBLUE);
        guilabel.setFont(Font.font("Arial" , FontWeight.BOLD , FontPosture.ITALIC, 28));
        flowPane = new FlowPane(guilabel);
        flowPane.setAlignment(Pos.CENTER);
        SaveXMLflowPane = new FlowPane( saveXml);
        SaveXMLflowPane.setAlignment(Pos.CENTER);
        c1 = new CheckBox("XML Editor");
        c2 = new CheckBox("Graph Editor");
        c1.setStyle("-fx-border-color: lightblue; "
                + "-fx-padding: 0.166667em 0.166667em 0.25em 0.25em;"
                + "-fx-font-size: 20;"
                + "-fx-border-insets: -5; "
                + "-fx-border-radius: 5;"
                + "-fx-border-style: dotted;"
                + "-fx-border-width: 2;");
        c2.setStyle("-fx-border-color: lightblue; "
                + "-fx-padding: 0.166667em 0.166667em 0.25em 0.25em;"
                + "-fx-font-size: 20;"
                + "-fx-border-insets: -5; "
                + "-fx-border-radius: 5;"
                + "-fx-border-style: dotted;"
                + "-fx-border-width: 2;");

        selectButton = new Button("Browse");
        selectButton.setPrefHeight(20);
        selectButton.setPrefWidth(90);
        hbox = new HBox(selectLabel,selectButton);
        hbox.setSpacing(15);
        vBox = new VBox(flowPane,hbox,xmlTypeLabel,xmlTextArea,SaveXMLflowPane,c1,c2);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 16;");
      //  selectButton.setOnAction(new XmlEditor(new ButtonHandlers.selectFileHandler()));
        c1.setOnAction(new XMLEditor());
        c2.setOnAction(new GraphEditor());
        selectButton.setOnAction(new selectFileHandler());
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
        Scene scene = new Scene(vBox,550, 400);
        stage.setScene(scene);
        stage.show();
    }

    static class selectFileHandler extends GUIEditor implements EventHandler {


        @Override
        public void handle(Event event) {

            Stage stage = new Stage();
            stage.setTitle("FileChooser");
            FileChooser filechooser = new FileChooser();
            Label label = new Label("no files selected");
            Button button = new Button("Select file");
            EventHandler<ActionEvent> event1 =
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            xmlTree = null;

                            file1 = filechooser.showOpenDialog(stage);

                            if (file1 != null) {

                                label.setText(file1.getAbsolutePath()
                                        + " selected");
                                path = file1.getAbsolutePath();
                                try {
                                    xmlTree = new XMLTree(file1);
                                    BufferedReader br;
                                    try {
                                        br = new BufferedReader(new FileReader(file1));
                                    } catch (FileNotFoundException e1) {
                                        throw new RuntimeException(e1);
                                    }

                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Error");
                                alert.setHeaderText("You should select xml file");
                                alert.showAndWait();
                            }
                            String line = "";
                            BufferedReader br = null;
                            try {
                                if (path != null) {
                                    br = new BufferedReader(new FileReader(path));
                                }
                                else {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Select XML file first");
                                    alert.showAndWait();
                                }
                            } catch (FileNotFoundException error) {
                                throw new RuntimeException(error);
                            }
                            while (true) {
                                try {
                                    if (!((line = br.readLine()) != null)) break;
                                } catch (IOException error) {
                                    throw new RuntimeException(error);
                                }
                                xmlFile += line;
                            }

                        }
                    };
            button.setOnAction(event1);
            VBox vbox = new VBox(label, button);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 200, 150);
            stage.setScene(scene);
            stage.show();
        }
    }
    public static void main(String[] args) {
        launch();
    }


}