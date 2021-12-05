public class Respondent extends Employee {
    public Respondent(int name, boolean status) {
        super(name, status);
    }

    public Respondent() {}

    @Override
    public String toString() {
        if (this.isFree()) {
            return "Respondent " + this.getName() + " is free.";
        }
        return "Respondent " + this.getName() + " is not free.";
    }
}
