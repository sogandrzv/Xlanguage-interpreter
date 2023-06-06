package sample;

import java.util.ArrayList;

public class Statement implements Runnable {
    private Variable first;
    private Variable second;

    public double calculate(Variable first, Variable second) {// a method to calculate the result of a statement
        return 0;
    }


    public static Variable index(String check, ArrayList<Variable> variables) throws InvalidValueException {//Find and return the needed variable in ArrayList
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).getName().equals(check)) {
                return variables.get(i);
            }
        }
        String pattern = "[0-9-.-]{1,}";
        if (check.matches(pattern)) {
            Variable variable = new Variable();
            variable.setInitialize(check);
            if (check.contains(".")) {
                variable.setSort("float");
            } else {
                variable.setSort("int");
            }
            return variable;
        }
        return null;
    }

    @Override
    public ArrayList<Variable> detector(String line, ArrayList<Variable> variables) throws InvalidValueException {
        return null;
    }
}