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
        StudentDao studentDao = new StudentDao();

        String komenda;
        do {
            komenda = scanner.nextLine();

            if (komenda.equalsIgnoreCase("dodajS")) {
                dodajStudenta(dao);
            } else if (komenda.equalsIgnoreCase("dodajG")) {
                dodajGrade(dao);
            } else if (komenda.equalsIgnoreCase("listS")) {

                System.out.println();
                dao.getAll(Student.class).forEach(System.out::println);
            } else if (komenda.equalsIgnoreCase("listG")) {

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
        // Na początek pobieramy studenta. Jeśli uda się go znaleźć, to przechodzimy do oceny
        System.out.println("Podaj id studenta:");
        Long idStudent = Long.valueOf(scanner.nextLine());
        Optional<Student> studentOptional = dao.getById(Student.class, idStudent);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            Grade grade = new Grade();
            grade.setStudent(student);
            System.out.println("Podaj przedmiot:");
            grade.setSubject(GradeSubject.valueOf(scanner.nextLine()));
            System.out.println("Podaj ocene:");
            grade.setValue(Double.parseDouble(scanner.nextLine()));

            dao.saveOrUpdate(grade);

//            w pełni opcjonalne
            student.getGradeList().add(grade);
            dao.saveOrUpdate(student);
        }
    }
}
