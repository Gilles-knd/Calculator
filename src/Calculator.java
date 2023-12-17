import java.util.Scanner;

public class Calculator {
    private static boolean isValidInput(String input) {
        // Vérifier si l'entrée est un seul caractère et si c'est un chiffre, un opérateur ou '='
        if (input.length() == 1) {
            return input.matches("\\d") || isOperator(input) || input.equals("=");
        }

        // Dans le cas où l'entrée peut être un nombre de plusieurs chiffres
        return input.matches("-?\\d+") || isOperator(input); // Regex pour les nombres entiers, incluant les nombres négatifs
    }
    private static boolean isOperator(String input) {
        return input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/");
    }


    private static int performOperation(int a, int b, String operation) {
        switch (operation) {
            case "+":
                Addition add = new Addition();
                return add.calculate(a, b);
            case "-":
                Substraction sub = new Substraction();
                return sub.calculate(a, b);
            case "*":
                Multiplication mul = new Multiplication();
                return mul.calculate(a, b);
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Division par zéro");
                }
                Division div = new Division();
                return div.calculate(a, b);
            default:
                throw new IllegalArgumentException("Opération non reconnue");
        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int currentResult = 0;
        StringBuilder currentInput = new StringBuilder();
        String lastOperation = null;
        boolean firstNumberEntered = false;

        while (true) {
            String display = (firstNumberEntered ? currentResult + " " : "") +
                    (lastOperation != null ? lastOperation + " " : "") +
                    currentInput;

            System.out.print("Veuillez saisir un caractère [" + display + "] : ");
            String input = scanner.nextLine();

            try {
                if (input.equals("=")) {
                    if (lastOperation != null && !currentInput.isEmpty()) {
                        currentResult = performOperation(currentResult, Integer.parseInt(currentInput.toString()), lastOperation);
                    } else if (!firstNumberEntered && !currentInput.isEmpty()) {
                        currentResult = Integer.parseInt(currentInput.toString());
                    }
                    System.out.println(display + " = " + currentResult);
                    break;
                } else if (isValidInput(input)) {
                    if (isOperator(input)) {
                        if (!firstNumberEntered) {
                            currentResult = Integer.parseInt(currentInput.toString());
                            firstNumberEntered = true;
                        } else if (!currentInput.isEmpty()) {
                            currentResult = performOperation(currentResult, Integer.parseInt(currentInput.toString()), lastOperation);
                        }
                        lastOperation = input;
                        currentInput = new StringBuilder();
                    } else {
                        currentInput.append(input);
                    }
                } else {
                    System.out.println("L'opération n'est pas possible.");
                }
            } catch (ArithmeticException e) {
                System.out.println("Erreur: " + e.getMessage());
                // Réinitialiser le dernier numéro et opérateur pour permettre à l'utilisateur de continuer correctement
                currentInput = new StringBuilder();
                lastOperation = null;
            } catch (NumberFormatException e) {
                System.out.println("Erreur: Format de nombre invalide.");
            }
        }
    }


}