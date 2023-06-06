package sample;


import java.util.ArrayList;

public class Print extends Statement implements Runnable {

    private static ArrayList<String> outPuts = new ArrayList<>();//to save result of Print statements

    public Print() {
    }


    public static ArrayList<String> getOutPuts() {
        return outPuts;
    }

    @Override
    public ArrayList<Variable> detector(String line, ArrayList<Variable> variables) throws InvalidValueException {
        line = line.replaceAll("\\s+", "");
        StringBuilder find = new StringBuilder(line);
        Variable index = index(find.substring(5), variables);
        if (index != null) {
            print(index);
        } else {
            print(find.substring(5));
        }
        return variables;
    }

    public int print(Variable variable) {
        String output = variable.getName() + ":" + variable.getInitialize();
        outPuts.add(output);
        return output.length();
    }

    public int print(String string) {
        outPuts.add(string);
        return string.length();
    }
}