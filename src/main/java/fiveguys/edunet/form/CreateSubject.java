package fiveguys.edunet.form;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreateSubject {
    private String detail;
    private String subjectname;
    private int attend;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer price;
    private LocalDate deadDay;
    private LocalDate endDay;
    private LocalDate startDay;
    private MultipartFile thumbnail;
    private MultipartFile poster;
    private String theme;
}
