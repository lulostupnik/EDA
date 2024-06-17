package xPractica.ParcialXX.Ej1;

public class CorporateHierarchy {

    Employee ceo;

    CorporateHierarchy(Employee ceo) {
        this.ceo = ceo;
    }

    void addEmployee(int bossRecNum, Employee subordinate) {
        Employee boss = findEmployee(bossRecNum);
        boss.addSubordinate(subordinate);
    }

    void setEmployeeScore(int recNum, int score) {
        Employee emp = findEmployee(recNum);
        if (emp == null)
            throw new RuntimeException("Practica.ParcialXX.Ej1.Employee not found");
        emp.setScore(score);
    }

    Employee findEmployee(int recNum) {
        return ceo.findEmployee(ceo, recNum);
    }

    double getCollectiveScore(int recNum) {
        return findEmployee(recNum).getCollectiveScore();
    }

    public static void main(String[] args) {
        CorporateHierarchy ch = new CorporateHierarchy(new Employee(103, "Sol Lopez"));

        ch.addEmployee(103, new Employee(254, "Pedro Poy"));
        ch.addEmployee(103, new Employee(375, "Luz Pel"));
        ch.addEmployee(103, new Employee(532, "Miguel Kar"));

        ch.addEmployee(375, new Employee(534, "Perla Fe"));
        ch.addEmployee(375, new Employee(332, "Dardo Tel"));
        ch.addEmployee(375, new Employee(328, "Marta Ger"));

        ch.addEmployee(534, new Employee(753, "Maria Dal"));
        ch.addEmployee(534, new Employee(872, "Juan Der"));

        ch.setEmployeeScore(103, 84);
        ch.setEmployeeScore(254, 65);
        ch.setEmployeeScore(375, 67);
        ch.setEmployeeScore(532, 87);
        ch.setEmployeeScore(534, 92);
        ch.setEmployeeScore(332, 79);
        ch.setEmployeeScore(328, 64);
        ch.setEmployeeScore(753, 76);
        ch.setEmployeeScore(872, 66);

        System.out.println(ch.getCollectiveScore(332));
        System.out.println(ch.getCollectiveScore(753));
        System.out.println(ch.getCollectiveScore(872));
        System.out.println(ch.getCollectiveScore(534));
        System.out.println(ch.getCollectiveScore(375));
    }
}

