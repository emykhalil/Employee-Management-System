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

        this.basicSalary = basicSalary;
        this.bonus = bonus;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getBonus() {
        return bonus;
    }
    
    public boolean setBasicSalary(double basicSalary) {
        if (!Double.isFinite(basicSalary) || basicSalary < 0) {
            return false;
        }
        this.basicSalary = basicSalary;
        return true;
    }

    public boolean setBonus(double bonus) {
        if (!Double.isFinite(bonus) || bonus < 0) {
            return false;
        }
        this.bonus = bonus;
        return true;
    }

    @Override
    public double calculateSalary() {
        return basicSalary + bonus;
    }
}