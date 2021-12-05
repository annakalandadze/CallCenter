import java.time.LocalDateTime;

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
            call.setStartTime(LocalDateTime.now());
            employee.setFree(false);
            employee.setCall(call);
            Thread.sleep(this.call.getDuration() * 1000L);
            this.employee.setFree(true);
            employee.setCall(null);
            System.out.println(employee.getClass().getSimpleName() + " finished processing call with id: " + call.getId());
            CallCenter.pollQueue(this.employee);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
