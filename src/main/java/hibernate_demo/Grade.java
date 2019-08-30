package hibernate_demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Grade implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp // ustawi LocalDateTime.now(); czas dodaje się samemu
    private LocalDateTime dateAdded;

    @Enumerated(value = EnumType.STRING)
    private GradeSubject subject;

    private double value;

    // "nullable = false" == "not null"

    @ToString.Exclude
    @ManyToOne()
    private Student student;

    public Grade(GradeSubject subject, double value) {
        this.subject = subject;
        this.value = value;
    }
}


