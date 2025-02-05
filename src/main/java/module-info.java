module main {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;


    opens main to javafx.fxml;
    exports main;
    exports servicii;
    exports modele;
    exports enums;
    exports controllers;
    exports dto;
    opens controllers to javafx.fxml;
    opens modele to org.junit.platform.commons;
    opens servicii to org.junit.platform.commons;
    opens enums to org.junit.platform.commons;
    opens dto to javafx.base;
}
