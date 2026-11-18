/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
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

        setId(id);
        setFullName(fullName);
        setAge(age);
        setDepartment(department);
        setPosition(position);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive.");
        }
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }
        this.fullName = fullName.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be greater than zero.");
        }
        this.age = age;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department must be selected.");
        }
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Position cannot be empty.");
        }
        this.position = position.trim();
    }

    public abstract double calculateSalary();
}