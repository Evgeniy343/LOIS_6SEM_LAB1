// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант F: Проверить, является ли формула ДНФ
// Выполнена студентами группы 921701 Чурай Е.С. Марков Д.О.
//http://mini-soft.ru/document/diskretnaya-matematika-3
package sample.parser.exception;

public class NegativeExpressionException extends Exception {
    public NegativeExpressionException(String s, String message) {
        super(message);
    }
}
