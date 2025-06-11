package fiveguys.edunet.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "subject")
    private List<Student> students;

    @Column(columnDefinition = "LONGTEXT")
    private String detail;
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    private String subjectname;
    private int attend;
    private LocalTime startTime;
    private LocalTime endTime;
    private int price;
    private LocalDate deadDay;
    private LocalDate startDay;
    private LocalDate endDay;
    private String thumbnail;
    private String poster;
    private String theme;
}