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

        setHourlyRate(hourlyRate);
        setHoursWorked(hoursWorked);
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (!Double.isFinite(hourlyRate) || hourlyRate < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative or invalid.");
        }
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        if (!Double.isFinite(hoursWorked) || hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative or invalid.");
        }
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}
