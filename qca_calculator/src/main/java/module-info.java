module cs4013_project {
    requires javafx.controls;
    requires javafx.fxml;

    opens cs4013_project to javafx.fxml;
    exports cs4013_project;
}
