public class Respondent extends Employee {

    public Respondent(int name, boolean status) {
        super(name, status);
    }

    @Override
    public void handle(Call call) {
        System.out.println("Respondent will handle call with id: " + call.getId());
        super.handle(call);
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
