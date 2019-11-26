package fr.insee.bootcampjs.telephoneback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TelephoneBackApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TelephoneBackApplication.class);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// Indique à SpringBoot le nom de l'application ce qui permet de charger le bon
		// fichier de properties.
		// Par défaut la valeur est "application".
		System.setProperty("spring.config.name", "telephone-back");
		// Mapper config
		System.setProperty("spring.jackson.serialization.FAIL_ON_EMPTY_BEANS", "false");
		// Indique à SpringBoot le nom d'un autre fichier à prendre en compte pour les
		// properties.
		// Il s'agit du fichier que le CEI va déposer pour les properties de production.
		System.setProperty("spring.config.location", "file:///${catalina.base}/webapps/telephone-back.properties");
		return application.sources(TelephoneBackApplication.class);
	}
	
}
