// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант F: Проверить, является ли формула ДНФ
// Выполнена студентами группы 921701 Чурай Е.С. Марков Д.О.
//http://mini-soft.ru/document/diskretnaya-matematika-3
package sample.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BetterView implements ViewPrinter {
    private final ExpressionRowPanel expressionRowPanel;
    private final FindButton findButton;
    private final BorderPane pane;
    private final Text output = new Text();
    private final Text result = new Text();

    private static final BetterView instance = new BetterView();
    public static BetterView getInstance() {
        return instance;
    }

    private BetterView() {
        expressionRowPanel = new ExpressionRowPanel();
        findButton = new FindButton(expressionRowPanel);
        pane = new BorderPane();
        configGridPane();
    }

    private void configGridPane() {
        pane.setTop(expressionRowPanel.getExpressionRowTextField());
        pane.setLeft(output);
        pane.setRight(result);
        pane.setBackground(getBackground());
        output.setStyle("-fx-fill: white; -fx-font-size: 20px;");
        result.setStyle("-fx-fill: white; -fx-font-size: 20px;");
        pane.setBottom(findButton.getStackPane());
    }

    private Background getBackground() {
        return new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public BorderPane getPane() {
        return pane;
    }

    @Override
    public void print(String str) {
        if (output.getText().equals("")) {
            output.setText(" Ход решения:");
        }
        output.setText(output.getText() + "\n " + str);
    }

    @Override
    public void sayResult(String res) {
        result.setText("Результат:\n" + res + " ");
    }

    @Override
    public void reset() {
        output.setText("");
        result.setText("");
    }
}
