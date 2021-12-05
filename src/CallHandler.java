public class CallHandler implements Runnable {
    private final Employee employee;
    private final Call call;

    public CallHandler(Employee employee, Call call) {
        this.employee = employee;
        this.call = call;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Call getCall() {
        return call;
    }

    /**
     * Each call will be executed certain amount of time. For this time the employee will be blocked to answer other calls.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(this.call.getDuration() * 1000L);
            this.employee.setFree(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
