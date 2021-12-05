import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static GetEmployeeTypeFactory factory = new GetEmployeeTypeFactory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<? extends Employee> respondents = fillList(scanner, "respondent");
        List<? extends Employee> managers = fillList(scanner, "manager");
        List<? extends Employee> directors = fillList(scanner, "director");
        if (respondents.size() == 0 && managers.size() == 0 && directors.size() == 0) {
            System.out.println("Sorry, there are no available employees, call later please!");
            scanner.close();
            return;
        }

        List<Call> callsResp = new ArrayList<>();
        List<Call> callsManager = new ArrayList<>();
        List<Call> callsDirector = new ArrayList<>();

        System.out.println("Press C to start or X to end.");
        String next = scanner.next();
        while (!next.equals("X")) {
            System.out.println("Enter call duration (in seconds): ");
            int duration = scanner.nextInt();
            Call call = new Call(duration);
            System.out.println("Your call id is " + call.getId() + ". It will be processed soon!");
            if (call.getRank() == 0) callsResp.add(call);
            else if (call.getRank() == 1) callsManager.add(call);
            else callsDirector.add(call);
            handle(callsResp, respondents, callsManager, callsDirector, 0);
            handle(callsManager, managers, callsDirector, callsResp, 1);
            handle(callsDirector, directors, callsResp, callsManager, 2);
            System.out.println("Press C to call or X if you want to end.");
            next = scanner.next();
        }
        scanner.close();
        System.out.println("Thank you for using the program!");
    }

    public static List<? extends Employee> fillList(Scanner scanner, String role) {
        System.out.println("Please enter number of free " + role + "s: ");
        List<Employee> list = new ArrayList<>();
        int free = scanner.nextInt();
        for (int i = 0; i < free; i++) {
            Employee employee = factory.getEmployee(role);
            employee.setFree(true);
            employee.setName(i);
            list.add(employee);
        }
        return list;
    }

    public static void handle(List<Call> calls, List<? extends Employee> employees, List<Call> redirected, List<Call> others, int rank) {
        if (calls.size() > 0) {
            Call first = calls.remove(0);
            String line = "";
            if (rank == 0) {
                line = "Line respondent.";
            }
            else if (rank == 1) {
                line = "Line manager.";
            }
            else {
                line = "Line director.";
            }
            System.out.println(line + " Your call id is " + first.getId());
            boolean accepted = false;
            for (Employee employee: employees) {
                if (employee.isFree()) {
                    System.out.println("You are 0 in line");
                    accepted = true;
                    employee.setFree(false);
                    CallHandler callHandler = new CallHandler(employee, first);
                    Thread t = new Thread(callHandler);
                    t.start();
                    System.out.println(first.getId() + ": " + employee.getClass().getSimpleName() + " with id " + employee.getName() + " is handling the call");
                    break;
                }
            }
            if (!accepted) {
                redirectEmployees(first, calls, redirected, others, employees, rank);
            }
        }
    }

    public static void redirectEmployees(Call first, List<Call> calls, List<Call> redirected, List<Call> others, List<? extends Employee> employees, int rank) {
        if (rank == 0) {
            redirected.add(first);
            System.out.println("You are " + redirected.size() + " in line");
            System.out.println(first.getId() + ": " + "All our respondents are busy, you will be redirected to a manager.");
        }
        else if (rank == 1) {
            redirected.add(first);
            System.out.println("You are " + redirected.size() + " in line");
            System.out.println(first.getId() + ": " + "All our managers are busy, you will be redirected to a director.");
        }
        else {
            redirectDirector(first, calls, redirected, others, employees);
        }
    }

    public static void redirectDirector(Call first, List<Call> ownCalls, List<Call> respCalls, List<Call> manCalls, List<? extends Employee> employees) {
        if (first.getRank() == 0) {
            System.out.println("You are " + respCalls.size() + " in line");
            System.out.println(first.getId() + ": " + "All directors are busy, you are redirected back to a respondent.");
            ownCalls.remove(first);
            respCalls.add((int) 0, first);
        }
        else if (first.getRank() == 1 || employees.size() <= 0) {
            System.out.println("You are " + manCalls.size() + " in line");
            System.out.println(first.getId() + ": " + "All directors are busy, you are redirected back to a manager.");
            ownCalls.remove(first);
            manCalls.add((int) 0, first);
        }
        else {
            System.out.println("You are " + ownCalls.size() + " in line");
            System.out.println(first.getId() + ": " + "Sorry, you will have to wait a bit longer!");
        }
    }
}