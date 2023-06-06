package sample;

public class Variable {
    private String name;
    private String sort;//float or int type
    private String initialize;//value of variable

    public Variable(String name, String sort, String initialize) throws InvalidValueException {
        setName(name);
        setSort(sort);
        setInitialize(initialize);
    }

    public Variable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String pattern = "^[a-zA-Z_$][a-zA-Z_$0-9]*$";//check if the name sync with rules of java
        if (name.matches(pattern)) {
            this.name = name;
        } else {
            Controller.warning("Invalid variable");
        }

    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        if (sort.equals("int") || sort.equals("float")) {
            this.sort = sort;
        }
    }

    public String getInitialize() {
        return initialize;
    }

    public void setInitialize(String initialize) throws InvalidValueException {//check if the initialize value sync with float/int type
        String pattern = "[0-9-.-]{1,}";
        if (initialize.matches(pattern)) {
            this.initialize = initialize;
        } else {
            throw new InvalidValueException();
        }
    }
}
