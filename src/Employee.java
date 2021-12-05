/**
 * Employee is an abstract class. There are 3 types of employees: respondent, manager and director.
 * While in this application we could work with enum "type", while extending, all three roles will have different responsibilities,
 * so decision was made for extended classes.
 * Design pattern "factory method" was used to simplify finding type of employee.
 */
public abstract class Employee {
    private int name;
    private boolean isFree;

    public Employee() {
    }

    public Employee(int name, boolean status) {
        this.name = name;
        this.isFree = status;
    }

    public int getName() {
        return name;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void setFree(boolean free) {
        this.isFree = free;
    }

    public void handle(Call call) {
        CallHandler callHandler = new CallHandler(this, call);
        Thread thread = new Thread(callHandler);
        thread.start();
    }
}
