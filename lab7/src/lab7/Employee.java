/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

/**
 *
 * @author Lenovo
 */
public abstract class Employee {

    private int id;
    private String fullName;
    private int age;
    private Department department;
    private String position;

    public Employee(int id, String fullName, int age,
                    Department department, String position) {

        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.department = department;
        this.position = position;
    }
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public Department getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }
    public boolean setId(int id) {
        if (id <= 0) {
            return false;
        }
        this.id = id;
        return true;
    }

    public boolean setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        this.fullName = fullName.trim();
        return true;
    }

    public boolean setAge(int age) {
        if (age <= 0) {
            return false;
        }
        this.age = age;
        return true;
    }

    public boolean setDepartment(Department department) {
        if (department == null) {
            return false;
        }

        this.department = department;
        return true;
    }

    public boolean setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            return false;
        }
        this.position = position.trim();
        return true;
    }

    public abstract double calculateSalary();
}
