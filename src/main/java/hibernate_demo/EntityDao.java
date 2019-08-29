package hibernate_demo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityDao {

    //    Metoda: jeśli obiekt zawiera identyfikator to go modyfikujemy, jeśli nie ma, to go tworzy
    public <T extends IBaseEntity> void saveOrUpdate(T entity) {
//        <T extends IBaseEntity> denifiuje typ generyczny / deklaracja typu generycznego
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(entity);

            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public <T extends IBaseEntity> List<T> getAll(Class<T> classT) {
//        classT - klasa tego co przyjmuje parametr jest to szblon

        List<T> list = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {

//            Narzędzia do kreowania zapytania, do tworzenia query i budowania klauzuli 'where'
            CriteriaBuilder cb = session.getCriteriaBuilder();

//            Obiekt reprezentujący zapytanie o <typ generyczny>
            CriteriaQuery<T> criteriaQuery = cb.createQuery(classT);

//            Reprezentuje tabelę 'Student' i towrzymy tą instancję żeby powiedzieć
//            do jakiej tabeli chcemy wykonać zapytanie.
//            root - korzeń, czyli zapytanie z tabeli wyjściowej,bazowej
            Root<T> rootTable = criteriaQuery.from(classT);

//            Wykonanie select'a z tabeli
            criteriaQuery.select(rootTable);

//            Wywołujemy zapytanie, wyniki zbieramy do listy
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException e) {
            e.fillInStackTrace();
        }
        return list;
    }

    public <T extends IBaseEntity> Optional<T> getById(Class<T> classT, Long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {

            T entity = session.get(classT, id);

//            może być null, zatem ofNullable
            return Optional.ofNullable(entity);
        }
    }

    public <T extends IBaseEntity> void delete(Class<T> classT, Long id) {
//        baza danych ma wyszukać po id i potem usunąć
        Optional<T> optionalEntity = getById(classT, id);

        if (optionalEntity.isPresent()) {
            delete(optionalEntity.get());
        } else {
            System.err.println("Nie udało się odnaleźć instancji.");
        }
    }

    //    przeciążenie metody, po to aby usunąć studenta,
//    jest to dodatkowa funkcjonalność (można usunąć nie tylko po id)
    public <T extends IBaseEntity> void delete(T entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
//            wprowadzamy zmiany na bazie i dlatego ona oczekuje od nas transakcji
            Transaction transaction = session.beginTransaction();

            session.delete(entity);

            transaction.commit();
        }
    }

}
