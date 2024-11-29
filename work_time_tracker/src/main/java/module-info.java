module com.javafx {
    requires javafx.graphics;
    requires javafx.controls;

    opens com.javafx to javafx.graphics;
}
