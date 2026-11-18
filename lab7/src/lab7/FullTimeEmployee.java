/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

/**
 *
 * @author Lenovo
 */
public class FullTimeEmployee extends Employee {

    private double basicSalary;
    private double bonus;

    public FullTimeEmployee(int id, String fullName, int age,
                            Department department, String position,
                            double basicSalary, double bonus) {

        super(id, fullName, age, department, position);

        setBasicSalary(basicSalary);
        setBonus(bonus);
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        if (!Double.isFinite(basicSalary) || basicSalary < 0) {
            throw new IllegalArgumentException("Basic salary cannot be negative or invalid.");
        }
        this.basicSalary = basicSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        if (!Double.isFinite(bonus) || bonus < 0) {
            throw new IllegalArgumentException(
                "Bonus cannot be negative or invalid.");}
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return basicSalary + bonus;
    }
}