package fiveguys.edunet.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "student_id")
    private Student student;

    @Column(columnDefinition = "LONGTEXT")
    private String detail;

    private String subjectname;
    private String attend;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer price;
    private LocalDate deadDay;
    private LocalDate endDay;
    private LocalDate startDay;    
    
}
