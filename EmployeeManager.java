/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author belal khaled
 */
public class EmployeeManager {
    private List<Employee> employees;
    private List<Department> departments;
    private FileHandler fileHandler;

    public EmployeeManager(List<Employee> employees, List<Department> departments, FileHandler fileHandler) {
        this.employees = employees;
        this.departments = departments;
        this.fileHandler = fileHandler;
    }
    public boolean authenticate(String username, String password){
        return username.equals("admin")&&password.equals("admin123");

}
    public boolean addEmployee(Employee e){boolean id_taken=false;
    if(e==null)
        return false;
        for(int i=0;i<employees.size();i++)
        {
            if((employees.get(i).getId())==e.getId())
            {id_taken=true;
            }
            }
        
    if(e!=null && !id_taken &&e.getDepartment()!=null && e.getAge()>0 && !e.getFullName().isBlank())
    {employees.add(e);
    fileHandler.saveEmployees(employees);
    return true;
   }
    return false;
    
    }
   public boolean updateEmployee(int originalId,Employee updated)
   {int i,j;
       boolean found=false;
   if(updated!=null &&updated.getDepartment()!=null && updated.getAge()>0 && !updated.getFullName().isBlank())
   {
   for(i=0;i<employees.size();i++){
   if(employees.get(i).getId()==originalId)
   {found=true;
       break;}
   
   }
   if(found==false) return false;
   if(updated.getId()== employees.get(i).getId() )
   {employees.set(i, updated);
   fileHandler.saveEmployees(employees);
   return true;
   }
   else{for(j=0;j<employees.size();j++)
   {
       if(employees.get(j).getId() == updated.getId())
           
   return false;
   }
   employees.set(i,updated);
   fileHandler.saveEmployees(employees);
   return true;
   
   }
   }
   return false;}
   public boolean deleteEmployee(int id)
   {int i;
       boolean found=false;
       for(i=0;i<employees.size();i++)
       {if(employees.get(i).getId()==id)
       {found=true;
           break;}
           }
       if(found){
           employees.remove(i);
   fileHandler.saveEmployees(employees);
   return true;
       }
   return false;}

public List<Employee> getAllEmployees(){
List<Employee> e=new ArrayList<>(employees);//creating a copy so that the gui cant change the main list "encapsulation"
return e;
}
public Employee searchById(int id)
{int i;
for(i=0;i<employees.size();i++)
if(employees.get(i).getId()== id)
    return employees.get(i);
return null;
}
public List<Employee> searchByName(String name)        
{int i;List<Employee> e = new ArrayList<>();boolean found=false;
    if(name != null && !name.isBlank())
{
    
    for(i=0;i<employees.size();i++)
{if (employees.get(i).getFullName().toLowerCase().contains(name.toLowerCase())){
e.add(employees.get(i));
found=true;
}
}

}if(found)
    return e;
else
return null;
}
public List<Employee> searchByDepartment(String name)
{int i;List<Employee> e = new ArrayList<>();boolean found=false;
 if(name != null && !name.isBlank())
{
    
    for(i=0;i<employees.size();i++)
    {if(employees.get(i).getDepartment().getDepartmentName().equalsIgnoreCase(name))
    {e.add(employees.get(i));
    found=true;}
    
    } 

}
if(found)
    return e;
return null;
}

public boolean isEmployeeIdUnique(int id)
{int i;
for(i=0;i<employees.size();i++)
{if( employees.get(i).getId()==id){
return false;
}


}return true;
}

    public List<Department> getAllDepartments() {
        List<Department> d=new ArrayList<>(departments);
        return d;
    }
    
public boolean isDepartmentIdUnique(int id)
{ int i;
for(i=0;i<departments.size();i++)
{if(departments.get(i).getDepartmentId()==id)
return false;
}
 return true;


}
public boolean isDepartmentNameUnique(String name)
{ int i;
for(i=0;i<departments.size();i++)
{if(name.equalsIgnoreCase(departments.get(i).getDepartmentName()))
return false;
}
 return true;


}     
        
public boolean addDepartment(Department d)
{
    if (d == null || d.getDepartmentName() == null || d.getDepartmentName().isBlank())
        return false;
    if (!isDepartmentNameUnique(d.getDepartmentName()) || !isDepartmentIdUnique(d.getDepartmentId()))
        return false;
    departments.add(d);
    fileHandler.saveDepartments(departments);
    return true;
}
public boolean deleteDepartment(Department d)
{
    if (d == null)
        return false;
    int i, index = -1;
    for (i = 0; i < departments.size(); i++)
    {
        if (departments.get(i).getDepartmentId() == d.getDepartmentId())
        {
            index = i;
            break;
        }
    }
    if (index == -1)
        return false;
    for (i = 0; i < employees.size(); i++)
    {
        if (employees.get(i).getDepartment().getDepartmentId() == d.getDepartmentId())
            return false;
    }
    departments.remove(index);
    fileHandler.saveDepartments(departments);
    return true;
}
        }
