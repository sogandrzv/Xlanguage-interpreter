package sample;

import java.util.ArrayList;

public class For {
    private int repetition; //Number of FOR loops
    private ArrayList<Variable> variables;
    private int currentRepetition; //Number of applied FOR loops
    private ArrayList<String> saveOrders = new ArrayList<>(); //Saving statements in arrayList

    public ArrayList<String> getSaveOrders() {
        return saveOrders;
    }

    public void setSaveOrders(ArrayList<String> saveOrders) {
        this.saveOrders = saveOrders;
    }


    public For(ArrayList<String> strings, int repetition, ArrayList<Variable> variables) throws InvalidValueException {
        setSaveOrders(strings);
        setRepetition(repetition);
        this.variables = variables;
        this.variables = forLoop();
    }


    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public int getCurrentRepetition() {
        return currentRepetition;
    }

    public void setCurrentRepetition(int currentRepetition) {
        this.currentRepetition = currentRepetition;
    }

    public ArrayList<Variable> forLoop() throws InvalidValueException { //a recursion method that apply statements in For loops
        for (int j = 0; j < repetition; j++) {
            for (int i = 1; i < saveOrders.size(); i++) {
                calculator(i); //Calculate statements in For loop
                if (saveOrders.get(i).contains("for") && !saveOrders.get(i).contains("endfor")) {
                    StringBuilder stringBuilder = new StringBuilder(this.saveOrders.get(i));
                    int set = (Integer.valueOf(stringBuilder.substring(3)));
                    ArrayList<String> saveOrders = new ArrayList<>();
                    saveOrders.add(this.saveOrders.get(i));
                    int forNumber = 1, endForNumber = 0;
                    while (forNumber != endForNumber) {
                        i++;
                        saveOrders.add(this.saveOrders.get(i));
                        if (this.saveOrders.get(i).contains("for") && !this.saveOrders.get(i).contains("endfor"))
                            forNumber++;
                        else if (this.saveOrders.get(i).contains("endfor"))
                            endForNumber++;
                    }
                    For forLoop = new For(saveOrders, set, variables);
                }
            }
        }
        return variables;
    }


    public void calculator(int i) throws InvalidValueException {
        if (saveOrders.get(i).contains("+")) {
            Addition addition = new Addition();
            variables = addition.detector(saveOrders.get(i), variables);
        } else if (saveOrders.get(i).contains("/")) {
            Division division = new Division();
            variables = division.detector(saveOrders.get(i), variables);
        } else if (saveOrders.get(i).contains("*")) {
            Multiplication multiplication = new Multiplication();
            variables = multiplication.detector(saveOrders.get(i), variables);
        } else if (saveOrders.get(i).contains("-")) {
            Subtraction subtraction = new Subtraction();
            variables = subtraction.detector(saveOrders.get(i), variables);
        } else if (saveOrders.get(i).contains("print")) {
            Print print = new Print();
            print.detector(saveOrders.get(i), variables);
        }
    }
}