import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
/**
 *
- @author Emy Khalil
 */
public class FileHandler {
    private final String employeeFilePath;
    private final String departmentFilePath;

    public FileHandler() {
        this.employeeFilePath ="employees.csv";
        this.departmentFilePath ="departments.csv";
    }
    //save employees from list to csv file
    public void saveEmployees(List<Employee> employees) {
       Path path = Path.of(employeeFilePath);
        try {
            if (path.getParent() != null) { // creates any missing parent directories for path without throwing error
                Files.createDirectories(path.getParent());
            }
            //if we have the file 
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                writer.write("type,id,fullName,age,departmentId,departmentName,position,basicSalary,bonus,hourlyRate,hoursWorked");
                writer.newLine(); //write column headers
                
                for (Employee emp : employees) {
                    Department dept = emp.getDepartment();
                    int deptId = 0;
                    String deptName = "";

                    if (dept != null) { //mawgod 
                        deptId = dept.getDepartmentId();
                        deptName = dept.getDepartmentName();
                    }
                    String row;
                    if (emp instanceof FullTimeEmployee ft) {
                        //escapeCsv is a helper function used to protect text values in csv (so it doesnt mix up commas)
                        row = "FULL_TIME" + ","+ ft.getId() + ","+ escapeCsv(ft.getFullName()) + ","+ ft.getAge() + ","
                            + deptId + ","+ escapeCsv(deptName) + ","+ escapeCsv(ft.getPosition()) + ","+ ft.getBasicSalary() + ","
                            + ft.getBonus() + ","+ "0" + ","+ "0";

                    } else if (emp instanceof PartTimeEmployee pt) {
                            row = "PART_TIME" + ","+ pt.getId() + ","+ escapeCsv(pt.getFullName()) + ","+ pt.getAge() + ","+ deptId + ","
                                + escapeCsv(deptName) + ","+ escapeCsv(pt.getPosition()) + ","+ "0" + ","+ "0" + ","+ pt.getHourlyRate() + ","+ pt.getHoursWorked();
                    } else {
                        continue;//if null
                    }

                    writer.write(row);
                    writer.newLine(); //write next
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving employees: " + e.getMessage());
        }
    }

    //load employees from csv file to list and return it 
    public List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>(); //make the list to append to then return at end 
        Path path = Path.of(employeeFilePath);
        if (!Files.exists(path)) {
            System.out.println("No employee file found yet. Starting with an empty list.");
            return employees;
        }
         try (BufferedReader reader = new BufferedReader(new FileReader(employeeFilePath))) {
            String header = reader.readLine(); // Skip the header row
            if (header == null) {
                return employees;
            }
            String row; //actual rows 
            while ((row = reader.readLine()) != null) { //tol mahna mawasalnash lel akher 
                if (row.isBlank()) {
                  continue;
                }
                //awl haga create object of employee then append to list 
                List<String> parsed = parseCsvLine(row);
                String[] parts = parsed.toArray(new String[0]);
                //n3araf kol haga 
                int id = Integer.parseInt(parts[1]);//from string to int
                String fullName = parts[2];
                int age = Integer.parseInt(parts[3]); //from string to int
                int deptId = Integer.parseInt(parts[4]);//from string to int
                String deptName = parts[5];
                String position = parts[6];
                //do dept object to pass to employee constructor
                Department dept = new Department(deptId, deptName);
               if("FULL_TIME".equals(parts[0])){ //checking type 
                   double basicSalary = Double.parseDouble(parts[7]); //basic salary from string to double 
                   double bonus = Double.parseDouble(parts[8]);//bonus from string to double 
                   //append to list the new obj
                   employees.add(new FullTimeEmployee(id, fullName, age, dept, position, basicSalary, bonus));
                }
                else if("PART_TIME".equals(parts[0])){
                    double hourlyRate=Double.parseDouble(parts[9]);
                    double hoursWorked=Double.parseDouble(parts[10]);
                    employees.add(new PartTimeEmployee(id, fullName, age, dept, position,hourlyRate,hoursWorked));
                }
            } 
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
    //save department from list to csv
    public void saveDepartments(List<Department> departments) {
        Path path = Path.of(departmentFilePath);
        try {
            if (path.getParent() != null) { // creates any missing parent directories for path without throwing error
                Files.createDirectories(path.getParent());
            }
            //if we have the file 
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                writer.write("departmentId,departmentName");
                writer.newLine(); //write column headers
                
                for (Department dept : departments) {
                    if (dept != null) { //law mawgod 
                        writer.write(dept.getDepartmentId() + "," + escapeCsv(dept.getDepartmentName()));
                        writer.newLine();
                    }
                }
            }
        }
        catch (IOException e) { //putting the catch outside of both trys will lead here 
                System.err.println("Error saving departments: " + e.getMessage());
        }
    }
    //from csv to list
    public List<Department> loadDepartments() {
        List<Department> departments = new ArrayList<>();
        Path path = Path.of(departmentFilePath);
        if (!Files.exists(path)) {
            System.out.println("No department file found yet. Starting with an empty list.");
            return departments;
        }
        //read from file 
        try (BufferedReader reader = new BufferedReader(new FileReader(departmentFilePath))) {
            String header = reader.readLine(); // Skip the header row
            if (header == null) {
                return departments;
            }
            String row; //actual rows 
            while ((row = reader.readLine()) != null) { //tol mahna mawasalnash lel akher 
                if (row.isBlank()) {
                  continue;
                }
                //awl haga create object of departments then append to list 
                List<String> parsed = parseCsvLine(row);
                String[] parts = parsed.toArray(new String[0]);
                int deptid=Integer.parseInt(parts[0]);
                String deptname=parts[1];
                departments.add(new Department(deptid,deptname));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return departments;
    }
    //helper function 
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        boolean needsQuotes = value.contains(",")
                || value.contains("\"")
                || value.contains("\n")
                || value.contains("\r");

        String escaped = value.replace("\"", "\"\"");

        if (needsQuotes) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }

    //helper function to split csv row while keeping quoted commas intact
    private List<String> parseCsvLine(String row) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < row.length(); i++) {
            char c = row.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < row.length() && row.charAt(i + 1) == '"') {
                        current.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    current.append(c);
                }
            } else {
                if (c == '"') {
                    inQuotes = true;
                } else if (c == ',') {
                    fields.add(current.toString());
                    current.setLength(0);
                } else {
                    current.append(c);
                }
            }
        }
        fields.add(current.toString());
        return fields;
    }
}