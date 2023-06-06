package sample;

import java.util.ArrayList;

public class Multiplication extends Statement implements Runnable {
    public double calculate(Variable first, Variable second) {
        if (first.getSort().equals("int") && second.getSort().equals("int")) {
            int x = Integer.parseInt(first.getInitialize());
            int y = Integer.parseInt(second.getInitialize());
            return x * y;
        } else if (first.getSort().equals("float") && second.getSort().equals("float")) {
            double x = Double.parseDouble(first.getInitialize());
            double y = Double.parseDouble(second.getInitialize());
            return x * y;
        }
        if (first.getSort().equals("float") && second.getSort().equals("int")) {
            double x = Double.parseDouble(first.getInitialize());
            int y = Integer.parseInt(second.getInitialize());
            return x * y;
        } else if (first.getSort().equals("int") && second.getSort().equals("float")) {
            int x = Integer.parseInt(first.getInitialize());
            double y = Double.parseDouble(second.getInitialize());
            return x * y;
        }
        return 0;
    }

    @Override
    public ArrayList<Variable> detector(String line, ArrayList<Variable> variables) throws InvalidValueException {
        line = line.replaceAll("\\s+", "");
        StringBuilder find = new StringBuilder(line);
        int equal = find.indexOf("=");
        int sum = find.indexOf("*");
        double result = calculate((index(find.substring(equal + 1, sum), variables)),
                (index(find.substring(sum + 1), variables)));
        String sort = (index(find.substring(0, equal), variables)).getSort();

        if (sort.equals("int")) {
            int result1 = (int) result;
            (index(find.substring(0, equal), variables)).setInitialize(String.valueOf(result1));
        } else {
            (index(find.substring(0, equal), variables)).setInitialize(String.valueOf(result));
        }
        return variables;
    }
}
