package sample;


import java.util.ArrayList;

public interface Runnable {
   ArrayList<Variable> detector(String line, ArrayList<Variable> variables) throws InvalidValueException;
   //detect variables and statements in different lines of code =}
}
