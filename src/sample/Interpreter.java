package sample;

import java.io.*;
import java.util.ArrayList;

public class Interpreter {
    ArrayList<Variable> variables = new ArrayList<>();
    private File file;

    public Interpreter(File file) throws RedundantVariableException, InvalidValueException {
        this.file = file;
        compile();
    }

    public void compile() throws RedundantVariableException, InvalidValueException { //Interpreting the lines
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                define(line);
                if (line.contains("%%")) {
                    while (true) {
                        String line2 = reader.readLine();
                        if (line2 == null) {
                            break;
                        }
                        calculation(line2);
                        if (line2.contains("for")) {
                            String str = line2.replaceAll(" ", "");
                            StringBuilder stringBuilder = new StringBuilder(str);
                            ArrayList<String> saveOrders = new ArrayList<>();
                            saveOrders.add(str);
                            int forNumber = 1, endForNumber = 0;
                            while (forNumber != endForNumber) {
                                String line3 = reader.readLine();
                                if (line3 == null) {
                                    break;
                                }
                                String order = line3.replaceAll(" ", "");
                                saveOrders.add(order);
                                if (line3.contains("for") && !line3.contains("endfor"))
                                    forNumber++;
                                else if (line3.contains("endfor"))
                                    endForNumber++;
                            }
                            For forLoop = new For(saveOrders, Integer.valueOf(stringBuilder.substring(3)), variables);
                        }
                    }
                }
            }
        } catch (InvalidFileException e) {
            Controller.warning("Invalid type");
        } catch (FileNotFoundException e) {
            Controller.warning("any Type");
        } catch (IOException e) {
            Controller.warning("any Type");
        }
    }

    public void define(String line) throws RedundantVariableException, InvalidValueException {//define different types
        if (line.contains("int")) {
            applyInt(line);
        } else if (line.contains("float")) {
            applyFloat(line);
        } else if (!line.contains("%%")) {
            Controller.warning("Invalid variable");
        }
    }

    public void applyInt(String line) throws RedundantVariableException, InvalidValueException {
        Variable variable;
        StringBuilder checkName = new StringBuilder(line);
        int equal1 = checkName.indexOf("=");
        line = line.replaceAll("\\s+", "");
        StringBuilder find = new StringBuilder(line);
        int equal = find.indexOf("=");
        if (equal != -1) {
            String check = checkName.substring(3, equal1);
            check = check.trim();
            if (Statement.index(check, variables) != null) {
                throw new RedundantVariableException();
            }
            String checkInitialized = find.substring(equal + 1);
            if (checkInitialized.contains(".")) {
                throw new InvalidValueException();
            }
            variable = new Variable(check, "int", find.substring((equal + 1)));
        } else {
            String check = checkName.substring(3);
            check = check.trim();
            variable = new Variable(check, "int", "0");
        }
        variables.add(variable);
    }

    public void applyFloat(String line) throws RedundantVariableException, InvalidValueException {
        Variable variable;
        StringBuilder checkName = new StringBuilder(line);
        int equal1 = checkName.indexOf("=");
        line = line.replaceAll(" ", "");
        StringBuilder find = new StringBuilder(line);
        int equal = find.indexOf("=");
        if (equal != -1) {
            String check = checkName.substring(5, equal1);
            check = check.trim();
            if (Statement.index(check, variables) != null) {
                throw new RedundantVariableException();
            }
            variable = new Variable(check, "float", find.substring((equal + 1)));
        } else {
            String check = checkName.substring(5);
            check = check.trim();
            variable = new Variable(check, "float", "0.0");
        }
        variables.add(variable);
    }

    public void calculation(String line2) throws InvalidValueException {//Calculate the result of different statements
        if (line2.contains("+")) {
            Addition addition = new Addition();
            variables = addition.detector(line2, variables);

        } else if (line2.contains("/")) {
            Division division = new Division();
            variables = division.detector(line2, variables);

        } else if (line2.contains("*")) {
            Multiplication multiplication = new Multiplication();
            variables = multiplication.detector(line2, variables);

        } else if (line2.contains("-")) {
            Subtraction subtraction = new Subtraction();
            variables = subtraction.detector(line2, variables);

        } else if (line2.contains("print")) {
            Print print = new Print();
            print.detector(line2, variables);

        }
    }

}