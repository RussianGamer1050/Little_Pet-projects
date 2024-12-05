module com.javafx {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;

    opens com.javafx to javafx.graphics;
}
