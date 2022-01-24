module com.example.covdefense {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.covdefense to javafx.fxml;
    exports com.example.covdefense;
}