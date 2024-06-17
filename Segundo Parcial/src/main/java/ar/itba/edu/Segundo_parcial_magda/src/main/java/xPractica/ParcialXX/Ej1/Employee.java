package xPractica.ParcialXX.Ej1;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private int recordNum;
    private String name;
    private int individualScore;

    private List<Employee> subordinates;

    Employee(int recNum, String name) {
        this.recordNum = recNum;
        this.name = name;
        this.individualScore = -1;
        subordinates = new ArrayList<>();
    }

    void addSubordinate(Employee emp) {
        subordinates.add(emp);
    }

    boolean isLeaf() {
        return subordinates.isEmpty();
    }

    int getRecordNum() {
        return this.recordNum;
    }

    List<Employee> getSubordinates() {
        return this.subordinates;
    }

    void setScore(int score) {
        this.individualScore = score;
    }

    String getName() {
        return this.name;
    }

    Employee findEmployee(Employee emp, int recNum) {
        if (emp.getRecordNum() == recNum) {
            return emp;
        }
        for (Employee e : emp.getSubordinates()) {
            Employee found = findEmployee(e, recNum);
            if (found != null)
                return found;
        }
        return null;
    }

    double getCollectiveScore(){
        if (isLeaf()) {
            return individualScore;
        }
        double sum = 0;
        for (Employee e : subordinates) {
            sum += e.getCollectiveScore();
        }
        return (sum / subordinates.size())*0.5 + individualScore * 0.5;
    }
}

