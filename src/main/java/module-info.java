module com.pwr.dpp.backlog.dpp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    opens com.pwr.dpp.backlog.dpp to javafx.fxml;
    exports com.pwr.dpp.backlog.dpp.business.orm to com.fasterxml.jackson.databind;
    exports com.pwr.dpp.backlog.dpp;
}