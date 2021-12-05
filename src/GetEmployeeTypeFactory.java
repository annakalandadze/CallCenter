public class GetEmployeeTypeFactory {

    public GetEmployeeTypeFactory() {
    }

    /**
     * @param employeeType string containing type of employeee
     * @return employee type
     */
    public Employee getEmployee(String employeeType){
        if(employeeType == null){
            return null;
        }
        if(employeeType.equalsIgnoreCase("RESPONDENT")) {
            return new Respondent();
        }
        else if(employeeType.equalsIgnoreCase("MANAGER")){
            return new Manager();
        }
        else if(employeeType.equalsIgnoreCase("DIRECTOR")) {
            return new Director();
        }
        return null;
    }
}
