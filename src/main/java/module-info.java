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

    exports com.fatidecoraciones.interfaz.controllers.principal to javafx.fxml;
    exports com.fatidecoraciones.interfaz;
    exports com.fatidecoraciones.interfaz.controllers.marcas to javafx.fxml;

    opens com.fatidecoraciones.interfaz to javafx.fxml;
    opens com.fatidecoraciones.interfaz.models to com.fasterxml.jackson.databind, javafx.base;
    opens com.fatidecoraciones.interfaz.controllers.productos to javafx.fxml;
    opens com.fatidecoraciones.interfaz.controllers.principal to javafx.fxml;
    opens com.fatidecoraciones.interfaz.controllers.marcas to javafx.fxml;
}