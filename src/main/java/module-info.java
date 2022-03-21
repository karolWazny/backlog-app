module com.pwr.dpp.backlog.dpp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.pwr.dpp.backlog.dpp to javafx.fxml;
    exports com.pwr.dpp.backlog.dpp;
}