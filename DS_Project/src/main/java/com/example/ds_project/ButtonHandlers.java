package com.example.ds_project;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ButtonHandlers {

    static class selectFileHandler extends XmlEditor implements EventHandler {


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
                            file1 = filechooser.showOpenDialog(stage);

                            if (file1 != null) {

                                label.setText(file1.getAbsolutePath()
                                        + " selected");
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



    static class JsonConverterHandler extends XmlEditor implements EventHandler {

        Label label = new Label();

        @Override
        public void handle(Event event) {
            Stage stage = new Stage();
            stage.setTitle("Json Output");
            EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    FileChooser fileChooser = new FileChooser();

                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Json files (*.json)", "*.json");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showSaveDialog(stage);

                    if (file != null) {
                        saveTextToFile(xmlTree.getObjectJson(), file);
                    }
                }
            };
            try {
                xmlTree.printJson(xmlTree.getRoot());
                label = new Label(xmlTree.getObjectJson());
                Button button = new Button("save file");
                button.setOnAction(e);
                button.setPrefWidth(80);
                button.setPrefHeight(60);
                button.setOnAction(e);
                ScrollPane scrollPane = new ScrollPane(label);
                VBox vbox = new VBox(10, scrollPane, new Label("Choose Path to save File"), button);
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                Scene scene = new Scene(vbox, 600, 600);
                stage.setScene(scene);
                stage.show();
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }

        }

        private void saveTextToFile(String content, File file) {
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(content);
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(XmlEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    static class PrettifyHandler extends XmlEditor implements EventHandler {

        Label label = new Label();

        @Override
        public void handle(Event event) {
            Stage stage = new Stage();

            EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    FileChooser fileChooser = new FileChooser();

                    //Set extension filter for text files
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                    fileChooser.getExtensionFilters().add(extFilter);

                    //Show save file dialog
                    File file = fileChooser.showSaveDialog(stage);

                    if (file != null) {
                        saveTextToFile(xmlTree.getObjectXMLPretify(), file);
                    }
                }
            };

            stage.setTitle("XML Prettify");
            try {
                xmlTree.XMLPrettify();
                label = new Label(xmlTree.getObjectXMLPretify());
                Button button = new Button("save file");
                button.setOnAction(e);
                button.setPrefWidth(80);
                button.setPrefHeight(60);
                button.setOnAction(e);
                ScrollPane scrollPane = new ScrollPane(label);
                VBox vbox = new VBox(10, scrollPane, new Label("Choose Path to save File"), button);
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                Scene scene = new Scene(vbox, 600, 600);
                stage.setScene(scene);
                stage.show();
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You should select xml file");
                alert.showAndWait();
            }


        }
    }

        private static void saveTextToFile(String content, File file) {
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(content);
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(XmlEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        static class CompressHandler extends XmlEditor implements EventHandler {


            @Override
            public void handle(Event event) {

            }
        }

            static class DecompressHandler extends XmlEditor implements EventHandler {


                @Override
                public void handle(Event event) {

                }
            }

            static class ValidateHandler extends XmlEditor implements EventHandler {
                @Override
                public void handle(Event event) {
                    Stage stage = new Stage();
                    stage.setTitle("Error");

                    String error = " ";
                    if (XMLTree.getRoot() == null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("You should select xml file");
                        alert.showAndWait();

                    } else if (xmlTree.getErrorLines().size() == 0) {
                        error += "No Errors Found";
                    } else for (int i = 0; i < xmlTree.getErrorLines().size(); i++) {
                        error += xmlTree.getErrorMessages().get(i) + " in line " + xmlTree.getErrorLines().get(i) + "\n";
                    }
                    Label label = new Label(error);
                    VBox vbox = new VBox(label);
                    vbox.setSpacing(20);
                    vbox.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(vbox, 400, 400);
                    stage.setScene(scene);
                    if (XMLTree.getRoot() != null) stage.show();
                    error = null;
                }
            }

            static class SaveXmlHandler extends XmlEditor implements EventHandler {
                @Override
                public void handle(Event event) {
                    System.out.println(xmlTextField.getText());
                }
            }




}
