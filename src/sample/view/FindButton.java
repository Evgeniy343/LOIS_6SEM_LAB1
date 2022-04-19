// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант F: Проверить, является ли формула ДНФ
// Выполнена студентами группы 921701 Чурай Е.С. Марков Д.О.
//http://mini-soft.ru/document/diskretnaya-matematika-3
package sample.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import sample.parser.ExpressionParser;
import sample.parser.exception.*;

public class FindButton {
    private static final String FIND = "Определить";

    private TextField expRowTextField;
    private final StackPane stackPane;

    public FindButton(ExpressionRowPanel expressionRowPanel) {
        expRowTextField = expressionRowPanel.getExpressionRowTextField();
        stackPane = new StackPane();
        configStackPane();
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    private void configStackPane() {
        Button find = new Button(FIND);

        find.setOnAction(findEventHandler);
        find.setStyle("-fx-pref-width: 150; " +
                      "-fx-pref-height: 50; " +
                      "-fx-font-size: 20;" +
                      "-fx-background-color: rgb(100,100,100);" +
                      "-fx-text-fill: white");

        stackPane.getChildren().add(find);
    }

    private final EventHandler<ActionEvent> findEventHandler = e -> {
        ViewPrinter.getInstance().reset();
        try {
            if (!new ExpressionParser(expRowTextField.getText()).isDNF()) {
                ViewPrinter.getInstance().sayResult("НЕ является ДНФ");
            } else {
                ViewPrinter.getInstance().sayResult("является ДНФ");
            }
        } catch (Exception exception) {
            ViewPrinter.getInstance().sayResult(exception.getMessage());
        }
    };
}
