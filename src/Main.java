import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private final static GetEmployeeTypeFactory factory = new GetEmployeeTypeFactory();
    static Scanner scanner = new Scanner(System.in);
    static List<? extends Employee> respondents = fillList(scanner, "respondent");
    static List<? extends Employee> managers = fillList(scanner, "manager");
    static List<? extends Employee> directors = fillList(scanner, "director");

    static List<Call> callsResp = new ArrayList<>();
    static List<Call> callsManager = new ArrayList<>();
    static List<Call> callsDirector = new ArrayList<>();

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

    public static void main(String[] args) {
        if (respondents.size() == 0 && managers.size() == 0 && directors.size() == 0) {
            System.out.println("Sorry, there are no available employees, call later please!");
            scanner.close();
            return;
        }


        System.out.println("Press C to start or X to end.");
        String next = scanner.next();

        while (!next.equals("X")) {
            System.out.println("Enter call duration (in seconds): ");
            int duration = scanner.nextInt();
            Call call = new Call(duration);
            System.out.println("Your call id is " + call.getId() + ", rank is " +call.getRank() +  ". It will be processed soon!");

            handleCall(call);

            System.out.println("Press C to call or X if you want to end.");
            next = scanner.next();
        }
        scanner.close();
        System.out.println("Thank you for using the program!");
    }

    public static void handleCall(Call call) {
        switch (call.getRank()) {
            case 0 : respondentHandle(call); break;
            case 1 : managerHandle(call); break;
            case 2 : directorHandle(call); break;
        }
    }

    private static void directorHandle(Call call) {
        for (Employee employee : directors) {
            if (employee.isFree()) {
                employee.handle(call);
                return;
            }
        }
        switch (call.getRank()) {
            case 0 : callsResp.add(call); break;
            case 1 : callsManager.add(call); break;
            case 2 : callsDirector.add(call); break;
        }
    }

    private static void managerHandle(Call call) {
        for (Employee employee : managers) {
            if (employee.isFree()) {
                employee.handle(call);
                return;
            }
        }
        directorHandle(call);

    }

    private static void respondentHandle(Call call) {
        for (Employee employee : respondents) {
            if (employee.isFree()) {
                employee.handle(call);
                return;
            }
        }
        managerHandle(call);
    }

    public static void pollQueue(Employee employee) {
        System.out.println("\tPolling queue... ");
        String className = employee.getClass().getSimpleName().toLowerCase(Locale.ROOT);

        switch (className) {
            case "respondent" : if (!callsResp.isEmpty()) handleCall(callsResp.remove(0));
                                else System.out.println("\tRespondent queue is empty"); break;
            case "manager" :    if (!callsManager.isEmpty()) handleCall(callsManager.remove(0));
                                else System.out.println("\tManager queue is empty"); break;
            case "director" :   if (!callsDirector.isEmpty()) handleCall(callsDirector.remove(0));
                                else System.out.println("\tDirector queue is empty"); break;
            default:
                System.out.println("\tAll queues are empty");
                break;
        }
    }

}