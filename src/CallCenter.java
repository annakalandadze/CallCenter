import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CallCenter {
    private final static GetEmployeeTypeFactory factory = new GetEmployeeTypeFactory();
    private static Scanner scanner = new Scanner(System.in);
    private static List<? extends Employee> respondents = fillList(scanner, "respondent");
    private static List<? extends Employee> managers = fillList(scanner, "manager");
    private static List<? extends Employee> directors = fillList(scanner, "director");

    private static List<Call> callsResp = new ArrayList<>();
    private static List<Call> callsManager = new ArrayList<>();
    private static List<Call> callsDirector = new ArrayList<>();

    private static List<? extends Employee> fillList(Scanner scanner, String role) {
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

        System.out.println("Press C to start, S to view statistics or X to end.");
        String next = scanner.next();

        while (!next.equals("X")) {
            if (next.equals("C")) {
                System.out.println("Enter call duration (in seconds): ");
                int duration = scanner.nextInt();
                Call call = new Call(duration);
                System.out.println("Your call id is " + call.getId() + ", rank is " + call.getRank() + ". It will be processed soon!");

                handleCall(call);
            } else if (next.equals("S")) {
                viewStatistics();
            } else {
                System.out.println("Unknown command, please enter again.");
            }
            System.out.println("Press C to call, S to view statistics or X if you want to end.");
            next = scanner.next();
        }
        scanner.close();
        System.out.println("Thank you for using the program!");
    }

    private static void viewStatistics() {
        statistics("Respondent", respondents);
        statistics("Manager", managers);
        statistics("Director", directors);
        queueStatistics();
    }

    private static void statistics(String type, List<? extends Employee> list) {
        System.out.println(type + "s: ");
        if (list.isEmpty()) {
            System.out.println("\tThere are no " + type.toLowerCase(Locale.ROOT) + "s at work at the moment.");
        }
        for(Employee employee: list) {
            if (employee.isFree()) {
                System.out.println("\t" + type + " with id " + employee.getName() + " is free." );
            }
        }
        for(Employee employee: list) {
            if (!employee.isFree()) {
                Duration between = Duration.between(LocalDateTime.now(), employee.getCall().getStartTime().plusSeconds(employee.getCall().getDuration()));
                System.out.println("\t"+ type + " with id " + employee.getName() + " is busy. Finishes in: " + between.getSeconds() + " seconds." );
            }
        }
    }

    private static void queueStatistics() {
        System.out.println("Respondents queue is: \n \t size = " + callsResp.size());;
        for (Call call: callsResp) {
            System.out.println("\t" + call);
        }
        System.out.println("Managers queue is: \n \t size = " + callsManager.size());
        for (Call call: callsManager) {
            System.out.println("\t" + call);
        }
        System.out.println("Directors queue is: \n \t size = " + callsDirector.size());
        for (Call call: callsDirector) {
            System.out.println("\t" + call);
        }
    }

    private static void handleCall(Call call) {
        switch (call.getRank()) {
            case 0 -> respondentHandle(call);
            case 1 -> managerHandle(call);
            case 2 -> directorHandle(call);
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
            case 0 : if (!respondents.isEmpty()) callsResp.add(call);
                    else if (!managers.isEmpty()) callsManager.add(call);
                    else callsDirector.add(call); break;
            case 1 : if (!managers.isEmpty()) callsManager.add(call);
                    else if (!directors.isEmpty()) callsDirector.add(call);
                    else callsResp.add(call); break;
            case 2 : if (!directors.isEmpty()) callsDirector.add(call);
                     else if (!managers.isEmpty()) callsManager.add(call);
                     else callsResp.add(call); break;
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