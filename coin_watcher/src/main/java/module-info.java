module com.crypto {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.graphics;

    // Libs
    requires com.google.gson;
    requires com.google.guice;

    exports com.crypto.app;
    exports com.crypto.data;
    exports com.crypto.domain;
    exports com.crypto.ui;
    exports com.crypto.utils;

    opens com.crypto.app to javafx.graphics;
}