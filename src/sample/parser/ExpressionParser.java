// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант F: Проверить, является ли формула ДНФ
// Выполнена студентами группы 921701 Чурай Е.С. Марков Д.О.
//http://mini-soft.ru/document/diskretnaya-matematika-3
package sample.parser;

import sample.parser.exception.BracketNumberException;
import sample.parser.exception.ExpressionException;
import sample.parser.exception.NegativeExpressionException;
import sample.parser.exception.SymbolNotCorrectException;
import sample.parser.validation.Validator;
import sample.view.BetterView;
import sample.view.ViewPrinter;

public class ExpressionParser {

    private String expression;
    private boolean isDNF = true;


    private static final char OPENED = '(';
    private static final char CLOSED = ')';
    private static final char SLASH = '/'; // AND /\
    private static final char BACKSLASH = '\\'; // OR \/
    private static final char NOT = '!';
    private static final String INVERSION = "In";
    private static final String TERMINAL = "T";
    private final static String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";



    public ExpressionParser(String expression) {
        this.expression = expression;
    }

    public boolean isDNF() throws BracketNumberException, SymbolNotCorrectException, NegativeExpressionException, ExpressionException {
        if (!Validator.isBracketsCountCorrect(expression)) {
            isDNF = false;
        }
        if (!Validator.isSymbolsCorrect(expression)) {
            isDNF = false;
        }

        if(!Validator.isNegativeExpression(expression)){
            isDNF = false;
        }

        if(!Validator.isExpression(expression)){
            isDNF = false;
        }

        if (expression.contains("->") || expression.contains("~"))
            isDNF = false;

        if(isTerminal()){
            return true;
        }

        if(!allInBrackets()){
            isDNF = false;
        }

        if (isDNF) {
            replaceInversion();
            if(isConjunctOfInversionAndTerminal()){
                return true;
            }
            if (expression.contains("!") || !replaceConjunct()) {
                isDNF = false;
            } else if (!expression.contains("/\\")) {
                isDNF = replaceDisjunction();
            } else isDNF = false;
        }
        return isDNF;
    }

    private void replaceInversion() {
        int openedBracket = 0;
        int closedBracket = 0;
        String subInversion;
        for (int i = 0; i < expression.length(); i++) {
            Character character = expression.charAt(i);
            if (character.compareTo(NOT) == 0) {
                openedBracket = i - 1;
                closedBracket = expression.indexOf(CLOSED, i) + 1;
                subInversion = expression.substring(openedBracket, closedBracket);
                if (Validator.isBracketsCountCorrect(subInversion)) {
                    expression = expression.replace(subInversion, "In");
                    BetterView.getInstance().print(expression);
                    i = -1;
                }
            }
        }
    }

    private boolean replaceConjunct() {
        int openedBracket = 0;
        int closedBracket;
        boolean isCorrectConj = true;

        for (int i = 0; i < expression.length(); i++) {
            Character character = expression.charAt(i);

            if (character.compareTo(OPENED) == 0) {
                openedBracket = i;
            } else if (character.compareTo(SLASH) == 0
                    && expression.charAt(i + 1) == BACKSLASH
                    && expression.charAt(i + 2) != OPENED) {
                closedBracket = expression.indexOf(CLOSED, i) + 1;
                String simpleConjunct = expression.substring(openedBracket, closedBracket);
                if (isBinary(simpleConjunct, SLASH)) {
                    expression = expression.replace(simpleConjunct, "Co");
                    BetterView.getInstance().print(expression);
                    i = -1;
                } else {
                    isCorrectConj = false;
                    break;
                }
            }
        }
        return isCorrectConj;
    }

    private boolean replaceDisjunction() {

        int openedBracket = 0;
        int closedBracket;
        boolean isDNF = true;

        for (int i = 0; i < expression.length(); i++) {
            Character character = expression.charAt(i);
            if (character.compareTo(OPENED) == 0) {
                openedBracket = i;
            } else if (character.compareTo(BACKSLASH) == 0
                    && expression.charAt(i + 1) == SLASH
                    && expression.charAt(i + 2) != OPENED) {
                closedBracket = expression.indexOf(CLOSED, i) + 1;
                String simpleDisjunction = expression.substring(openedBracket, closedBracket);
                if (isBinary(simpleDisjunction, BACKSLASH)) {
                    expression = expression.replace(simpleDisjunction, "Dn");
                    BetterView.getInstance().print(expression);
                    i = -1;
                } else {
                    isDNF = false;
                    break;
                }
            }
        }
        if (isDNF)
            return expression.contains("Dn");
        else return false;
    }

    private boolean isBinary(String substring, char operat) {
        for (int i = 1; i < substring.length() - 1; i++) {
            Character character = substring.charAt(i);
            int isSlash = character.compareTo(operat);
            int index = substring.indexOf(operat, i + 1);
            if (isSlash == 0 && index > -1) {
                break;
            }
        }
        return true;
    }

    private boolean isTerminal() throws ExpressionException {
        int countOfSymbols = 0;
        int countOfNegative = 0;
        int countOfOpenedBrackets = 0;
        if (expression.indexOf(BACKSLASH) == -1 || expression.indexOf(SLASH) == -1) {
            char[] charArray = expression.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char character = charArray[i];
                if ("(".indexOf(character) != -1) {
                    String substring = expression.substring(i, expression.indexOf(")", i) + 1);
                    if (!substring.contains("!")) {
                        countOfOpenedBrackets++;
                    }
                }
                if (SYMBOLS.indexOf(character) != -1) {
                    countOfSymbols++;
                }
                if ("!".indexOf(character) != -1) {
                    countOfNegative++;
                }
            }
            if(countOfOpenedBrackets > 0){
                throw new ExpressionException("ошибка выражения");
            }
            return countOfSymbols > 0 && countOfNegative < 2;
        }
        return false;
    }

    private boolean isConjunctOfInversionAndTerminal(){
        int countOfSlash = 0;
        int countOfBackSlash = 0;
        replaceTerminal();
        for (char character : expression.toCharArray()) {
            if(character == SLASH){
                countOfSlash++;
            }
            if(character == BACKSLASH){
                countOfBackSlash++;
            }
        }
        if(countOfSlash == 1 && countOfBackSlash == 1){
            return expression.contains(TERMINAL + SLASH + BACKSLASH + INVERSION)
                    || expression.contains(TERMINAL + SLASH + BACKSLASH + TERMINAL)
                    || expression.contains(INVERSION + SLASH + BACKSLASH + TERMINAL)
                    || expression.contains(INVERSION + SLASH + BACKSLASH + INVERSION);
        }
        return false;
    }

    private boolean allInBrackets(){
        char[] characters = expression.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            if(characters[i] == '!'){
                if(characters[i + 1] == '('){
                    return false;
                }
            }
        }
        return true;
    }

    private void replaceTerminal(){
        char[] characters = expression.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            if(i == characters.length - 1){
                break;
            }
            if (SYMBOLS.indexOf(characters[i]) != -1 && characters[i + 1] != 'n'){
                characters[i] = 'T';
            }
        }
        expression = new String(characters);
        ViewPrinter.getInstance().print(expression);
    }
}
