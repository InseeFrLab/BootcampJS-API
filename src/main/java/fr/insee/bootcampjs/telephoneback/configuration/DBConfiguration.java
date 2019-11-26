package fr.insee.bootcampjs.telephoneback.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
@SuppressWarnings("unused")
public class DBConfiguration {

	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	@Profile("dev")
	@Bean
	public String devDatabaseConnection() {
		return "Connexion à la BDD de DEV";
	}
	@Profile("test")
	@Bean
	public String testDatabaseConnection() {
		return "Connexion à la BDD de TEST";
	}
	@Profile("prod")
	@Bean
	public String prodDatabaseConnection() {
		return "Connexion à la BDD de PROD";
	}
	
	
}
