// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант F: Проверить, является ли формула ДНФ
// Выполнена студентами группы 921701 Чурай Е.С. Марков Д.О.
//http://mini-soft.ru/document/diskretnaya-matematika-3
package sample.view;

import javafx.scene.control.TextField;

public class ExpressionRowPanel {
    private final TextField expressionRowTextField;

    public ExpressionRowPanel() {
        expressionRowTextField = new TextField();
        configureExpressionRowTextField();
    }

    public TextField getExpressionRowTextField() {
        return expressionRowTextField;
    }

    private void configureExpressionRowTextField() {
        expressionRowTextField.setEditable(true);
        expressionRowTextField.setFocusTraversable(true);
        expressionRowTextField.setStyle("-fx-background-color: rgb(100,100,100); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 22px;");
        expressionRowTextField.setPrefHeight(70);
        expressionRowTextField.setMinWidth(600);


        expressionRowTextField.textProperty().addListener(e -> {
            expressionRowTextField.positionCaret(expressionRowTextField.getText().length());
        });
    }
}
