module com.example.xmlproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.xmlproject to javafx.fxml;
    exports com.example.xmlproject;
}