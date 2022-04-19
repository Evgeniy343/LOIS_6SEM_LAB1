// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант F: Проверить, является ли формула ДНФ
// Выполнена студентами группы 921701 Чурай Е.С. Марков Д.О.
//http://mini-soft.ru/document/diskretnaya-matematika-3
package sample;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.view.BetterView;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BetterView form = BetterView.getInstance();
        primaryStage.setScene(new Scene(form.getPane(), 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
