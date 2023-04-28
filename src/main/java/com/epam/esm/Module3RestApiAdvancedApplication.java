package com.epam.esm;

import com.epam.esm.model.import1000.Import1000;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.FileNotFoundException;

@SpringBootApplication
public class Module3RestApiAdvancedApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(Module3RestApiAdvancedApplication.class, args);
//		ApplicationContext applicationContext = SpringApplication.run(Module3RestApiAdvancedApplication.class, args);
//		Import1000 import1000 = applicationContext.getBean(Import1000.class);
//		import1000.parseFileWithNames();
//		import1000.parseFileWithTags();
//		import1000.createCertificatesWithTags();
	}

}
