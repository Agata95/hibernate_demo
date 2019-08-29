package hibernate_demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // to jest encja bazodanowa
public class Student implements IBaseEntity {
    @Id
    // dodatkowa tabela "hibernate_sequence" - sztuczne identyfikatory
    // identity - generuj identyfikator z bazy, pobierz go i przypisz do pola
    // sequence - pobierz identyfikator, przypisz go do pola, zapisz obiekt
    // table    -
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
    private Double average; // nullable - nie ma "not null"
    private int age; // "not null"
    private boolean alive; // "not null"

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "student")
//    fetch eager pobiera całą relację (join) z innymi tabelami,
//    lazy pobiera tylko info z jednej tabelki
    private List<Grade> gradeList;

    public Student(String name, Double average, int age, boolean alive) {
        this.name = name;
        this.average = average;
        this.age = age;
        this.alive = alive;
    }
}
