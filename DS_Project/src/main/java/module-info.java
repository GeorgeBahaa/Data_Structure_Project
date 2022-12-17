module com.example.ds_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ds_project to javafx.fxml;
    exports com.example.ds_project;
}