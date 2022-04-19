// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант F: Проверить, является ли формула ДНФ
// Выполнена студентами группы 921701 Чурай Е.С. Марков Д.О.
//http://mini-soft.ru/document/diskretnaya-matematika-3
package sample.parser.validation;

import sample.parser.exception.ExpressionException;
import sample.parser.exception.NegativeExpressionException;
import sample.parser.exception.SymbolNotCorrectException;

public class Validator {

    private final static String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01~\\/()!->";
    private final static String OPERATIONS = "~\\/!->";
    private final static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public static boolean isSymbolsCorrect(String expression) throws SymbolNotCorrectException {
        for (Character character : expression.toCharArray()) {
            if (!SYMBOLS.contains(Character.toString(character))) {
                throw new SymbolNotCorrectException("Некорректные символы", Character.toString(character));
            }
        }
        if (expression.contains("!!")) {
            throw new SymbolNotCorrectException("неверное выражение", "!!");
        }
        return true;
    }

    public static boolean isBracketsCountCorrect(String expression) {
        int openedBracket = 0;
        int closedBracket = 0;
        for (char symbol : expression.toCharArray()) {
            if (symbol == '(') {
                openedBracket++;
                continue;
            }
            if (symbol == ')') {
                closedBracket++;
            }
        }
        return openedBracket == closedBracket;
    }

    public static boolean isNegativeExpression(String expression) throws NegativeExpressionException {
        if (expression.contains("(!)")) {
            throw new NegativeExpressionException("(!)","(!)");
        }
        return !expression.contains("!(!");
    }

    public static boolean isExpression(String expression) throws ExpressionException {
        if (Validator.isTerminal(expression)) {
            return true;
        }
        return expression.indexOf("(") == 0 && expression.lastIndexOf(")") == expression.length() - 1;
    }

    public static boolean isTerminal(String expression) throws ExpressionException {
        int countOfSymbols = 0;
        int countOfBrackets = 0;
        if (expression.contains("(")) {
            for (char character : expression.toCharArray()) {
                if (character == '(' || character == ')') {
                    countOfBrackets++;
                }
                if (CHARACTERS.indexOf(character) != -1) {
                    countOfSymbols++;
                }
            }
        }

        if (countOfSymbols == expression.length() - countOfBrackets) {
            throw new ExpressionException("ошибка грамматики");
        }
        return !haveOperations(expression);
    }

    private static boolean haveOperations(String expression) {
        for (char character : expression.toCharArray()) {
            if (OPERATIONS.indexOf(character) != -1) {
                return true;
            }
        }
        return false;
    }

}
