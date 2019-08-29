package hibernate_demo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityDao dao = new EntityDao();


        String komenda;
        do {
            komenda = scanner.nextLine();

            if (komenda.equalsIgnoreCase("dodajS")) {
                dodajStudenta(dao);
            } else if (komenda.equalsIgnoreCase("dodajG")) {
                dodajGrade(dao);
            } else if (komenda.equalsIgnoreCase("listujS")) {

                System.out.println();
                dao.getAll(Student.class).forEach(System.out::println);
            } else if (komenda.equalsIgnoreCase("listujG")) {

                System.out.println();
                dao.getAll(Grade.class).forEach(System.out::println);
            }

        } while (!komenda.equalsIgnoreCase("quit"));

    }

    private static void dodajStudenta(EntityDao dao) {
        Student student = new Student();
        System.out.println("Podaj imie:");
        student.setName(scanner.nextLine());
        System.out.println("Podaj wiek:");
        student.setAge(Integer.parseInt(scanner.nextLine()));
        System.out.println("Podaj srednia:");
        student.setAverage(Double.valueOf(scanner.nextLine()));
        System.out.println("Podaj czy zyje:");
        student.setAlive(Boolean.parseBoolean(scanner.nextLine()));
        dao.saveOrUpdate(student);
    }

    private static void dodajGrade(EntityDao dao) {
        Grade grade = new Grade();
        System.out.println("Podaj przedmiot:");
        grade.setSubject(GradeSubject.valueOf(scanner.nextLine()));
        System.out.println("Podaj ocene:");
        grade.setValue(Double.parseDouble(scanner.nextLine()));
        dao.saveOrUpdate(grade);
    }
}
