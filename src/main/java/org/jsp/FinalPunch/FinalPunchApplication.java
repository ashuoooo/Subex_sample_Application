package org.jsp.FinalPunch;

import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class FinalPunchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalPunchApplication.class, args);

	}

}
