package fiveguys.edunet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fiveguys.edunet.domain.Student;
import fiveguys.edunet.repositrory.StudentRepository;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class EdunetApplication implements CommandLineRunner{
	private final StudentRepository studentRepository;
	public static void main(String[] args) {
		SpringApplication.run(EdunetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		studentRepository.save(Student.builder()
		.username("pss")
		.password("pss")
		.email("pss")
		.studentname("pss")
		.phonenumber("pss").build());
		
	}

}
