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
    static String path;
    static HuffmanNode node = new HuffmanNode();
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
            Label label = new Label();
            String compressed = "";
            @Override
            public void handle(Event event) {
                Stage stage = new Stage();
                stage.setTitle("Compression Output");
                TextField textField = new TextField();
                Button button = new Button("save compressed file");
                String line = "", str = " ";
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
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                while (true) {
                    try {
                        if (!((line = br.readLine()) != null)) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    str += line;
                }

                try {
                    compressed = node.Compress(str);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                label = new Label(compressed);
                label.setWrapText(true);
                label.setMaxWidth(450);
                EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        FileChooser fileChooser = new FileChooser();

                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                        fileChooser.getExtensionFilters().add(extFilter);

                        //Show save file dialog
                        File file = fileChooser.showSaveDialog(stage);
                        try {
                            node.writeBinaryToFile(compressed, file.getPath());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }; button.setOnAction(e);

                ScrollPane scrollPane= new ScrollPane(label);
                scrollPane.setMinHeight(300);
                VBox vbox = new VBox(50,label ,scrollPane ,button);
                vbox.setAlignment(Pos.CENTER);
                Scene scene = new Scene(vbox,500 ,500);
                stage.setScene(scene);
                stage.show();
            }
        }

            static class DecompressHandler extends XmlEditor implements EventHandler {
                Label label;
                Label label1;
                File file;
                String decompressed ="";
                @Override
                public void handle(Event event) {
                    Stage stage = new Stage();
                    stage.setTitle("FileChooser");
                    label = new Label("no files selected");
                    Button button = new Button("Select file to Decompress");
                    Button button1 = new Button("Save xml");
                    FileChooser filechooser = new FileChooser();
                    EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e)
                        {
                            file = filechooser.showOpenDialog(stage);
                            if (file != null) {
                                try {
                                    decompressed = node.Decompression(file.getAbsolutePath(),node.root);
                                    XMLTree xmlTree = new XMLTree(new StringReader(decompressed));
                                    xmlTree.XMLPrettify();
                                    decompressed = xmlTree.getObjectXMLPretify().toString();
                                    label.setText(decompressed);
                                    label.setWrapText(true);
                                    label.setMaxWidth(450);
                                    EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent actionEvent) {
                                            FileChooser fileChooser = new FileChooser();

                                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML","*.xml");
                                            fileChooser.getExtensionFilters().add(extFilter);

                                            //Show save file dialog
                                            File file = fileChooser.showSaveDialog(stage);
                                            if(file != null){
                                                saveTextToFile(decompressed,file);
                                            }
                                        }
                                    }; button1.setOnAction(e2);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                        }
                    }; button.setOnAction(event1);
                    ScrollPane scrollPane= new ScrollPane(label);
                    scrollPane.setMinHeight(300);
                    VBox vbox = new VBox(50,label,scrollPane ,button,button1);
                    vbox.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(vbox,500 ,500);
                    stage.setScene(scene);
                    stage.show();
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
}
