public class Manager extends Employee{
    public Manager(int name, boolean status) {
        super(name, status);
    }

    public Manager() {
    }

    @Override
    public String toString() {
        if (this.isFree()) {
            return "Manager " + this.getName() + " is free.";
        }
        return "Manager " + this.getName() + " is not free.";
    }
}
