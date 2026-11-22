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
    public boolean setHourlyRate(double hourlyRate) {
        if (!Double.isFinite(hourlyRate) || hourlyRate < 0) {
            return false;
        }
        this.hourlyRate = hourlyRate;
        return true;
    }

    public boolean setHoursWorked(double hoursWorked) {
        if (!Double.isFinite(hoursWorked) || hoursWorked < 0) {
            return false;
        }
        this.hoursWorked = hoursWorked;
        return true;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}