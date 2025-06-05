package fiveguys.edunet.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "student_id")
    private Student student;
    @Column(columnDefinition = "LONGTEXT")
    private String detail;
    @OneToOne
    @JoinColumn(name= "teacher_id")
    private Teacher teacher;
    private String subjectname;
    private String attend;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer price;
    private LocalDate deadDay;
    private LocalDate endDay;
    private LocalDate startDay;    
    
}
