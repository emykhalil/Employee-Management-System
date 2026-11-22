/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

/**
 *
 * @author Lenovo
 */


public class PartTimeEmployee extends Employee {

    private double hourlyRate;
    private double hoursWorked;

    public PartTimeEmployee(int id, String fullName, int age,
                            Department department, String position,
                            double hourlyRate, double hoursWorked) {

        super(id, fullName, age, department, position);

        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    
    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    
    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}