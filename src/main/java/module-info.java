module com.fatidecoraciones.interfaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires com.fasterxml.jackson.databind;

    opens com.fatidecoraciones.interfaz to javafx.fxml;
    opens com.fatidecoraciones.interfaz.models to com.fasterxml.jackson.databind, javafx.base;

    exports com.fatidecoraciones.interfaz;
    opens com.fatidecoraciones.interfaz.controllers.producto to javafx.fxml;
}