public class Director extends Employee{
    public Director(int name, boolean status) {
        super(name, status);
    }

    public Director() {
    }

    @Override
    public String toString() {
        if (this.isFree()) {
            return "Director " + this.getName() + " is free.";
        }
        return "Director " + this.getName() + " is not free.";
    }
}
