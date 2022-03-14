module com.pwr.dpp.backlog.dpp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.pwr.dpp.backlog.dpp to javafx.fxml;
    opens com.pwr.dpp.backlog.dpp.controllers;

    exports com.pwr.dpp.backlog.dpp;
    exports com.pwr.dpp.backlog.dpp.business;
}