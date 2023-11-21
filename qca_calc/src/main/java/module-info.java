module cs4013.poject {
    requires javafx.controls;
    requires javafx.fxml;

    opens cs4013.poject to javafx.fxml;
    exports cs4013.poject;
}
