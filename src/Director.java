public class Director extends Employee{
    public Director(int name, boolean status) {
        super(name, status);
    }

    public Director() {
    }

    @Override
    public void handle(Call call) {
        System.out.println("Director will handle call with id: " + call.getId());
        super.handle(call);
    }

    @Override
    public String toString() {
        if (this.isFree()) {
            return "Director " + this.getName() + " is free.";
        }
        return "Director " + this.getName() + " is not free.";
    }
}
