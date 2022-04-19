// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант F: Проверить, является ли формула ДНФ
// Выполнена студентами группы 921701 Чурай Е.С. Марков Д.О.
//http://mini-soft.ru/document/diskretnaya-matematika-3
package sample.view;

public interface ViewPrinter {

    void print(String str);

    void sayResult(String result);

    void reset();

    static ViewPrinter getInstance() {
        return BetterView.getInstance();
    }
}
