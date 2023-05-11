import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class sample2 {

    static Employee generateEmployee() {
        String[] names = new String[] { "Анатолий", "Глеб", "Клим", "Мартин", "Лазарь", "Владлен", "Клим", "Панкратий",
                "Рубен", "Герман" };
        String[] surnames = new String[] { "Григорьев", "Фокин", "Шестаков", "Хохлов", "Шубин", "Бирюков", "Копылов",
                "Горбунов", "Лыткин", "Соколов" };

        Random random = new Random();
        int index = random.nextInt(1, 3);
        int age = random.nextInt(21, 45);
        int workerSalary = random.nextInt(50, 100) * 1000;
        int freelancerSalary = random.nextInt(1, 5) * 1000;
        int freelancerOrders = random.nextInt(4, 9);

        if (index % 2 == 0) {
            return new Freelancer(names[random.nextInt(names.length)], surnames[random.nextInt(surnames.length)], age,
                    freelancerSalary, freelancerOrders);
        }
        return new Worker(names[random.nextInt(names.length)], surnames[random.nextInt(surnames.length)], age,
                workerSalary);
    }

    public static void main(String[] args) {

        Employee[] employees = new Employee[10];
        for (int i = 0; i < employees.length; i++)
            employees[i] = generateEmployee();

        for (Employee employee : employees) {
            System.out.println(employee);
        }

        Arrays.sort(employees, new SalaryComparator());

        System.out.printf("\n*** Отсортированный по з/п массив сотрудников ***\n\n");

        for (Employee employee : employees) {
            System.out.println(employee);
        }

        Arrays.sort(employees, new AgeComporator());

        System.out.printf("\n*** Отсортированный по возрасту массив сотрудников ***\n\n");

        for (Employee employee : employees) {
            System.out.println(employee);
        }

        Arrays.sort(employees, new SurnameComparator());

        System.out.printf("\n*** Отсортированный по фамилии массив сотрудников ***\n\n");

        for (Employee employee : employees) {
            System.out.println(employee);
        }

    }

}

class AgeComporator implements Comparator<Employee>{

    @Override
    public int compare(Employee o1, Employee o2){
        return Integer.compare(o1.age, o2.age);
    }
}

class SalaryComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {

        return Double.compare(o2.calculateSalary(), o1.calculateSalary());
    }
}

class NameComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        int res = o1.name.compareTo(o2.name);
        if (res == 0) {
            res = Double.compare(o2.calculateSalary(), o1.calculateSalary());
        }
        return res;
    }
}

class SurnameComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        int res = o1.surName.compareTo(o2.surName);
        if (res == 0) {
            res = Double.compare(o2.calculateSalary(), o1.calculateSalary());
        }
        return res;
    }
}

abstract class Employee implements Comparable<Employee> {

    protected String name;
    protected String surName;
    protected int age;
    protected double salary;

    public Employee(String name, String surName, int age, double salary) {

        this.name = name;
        this.surName = surName;
        this.age = age;
        this.salary = salary;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {
        return String.format(
            "Сотрудник: %s %s; Возраст: %d; Ср. з/п: %.2f",
            name, surName, age, salary);
    }

    @Override
    public int compareTo(Employee o) {
        return Double.compare(o.calculateSalary(), calculateSalary());
    }
}

class Worker extends Employee {

    public Worker(String name, String surName, int age, double salary) {
        super(name, surName, age, salary);
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s; Возраст: %d; Рабочий; Ср. з/п (фикс): %.2f (руб.)",
                name, surName, age, salary);
    }
}

class Freelancer extends Employee {

    protected int orders;

    public Freelancer(String name, String surName, int age, double salary, int orders) {
        super(name, surName, age, salary);
        this.orders = orders;

    }

    @Override
    public double calculateSalary() {
        return orders * salary;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s Возраст: %d; Фрилансер; З.п. (кол-во зак-ов в мес. (%d шт.) * ставку за зак.): %.2f (руб.)",
                name, surName, age, orders, salary * orders);
    }
}