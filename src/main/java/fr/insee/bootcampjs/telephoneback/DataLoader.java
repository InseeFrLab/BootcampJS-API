package fr.insee.bootcampjs.telephoneback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import edu.emory.mathcs.backport.java.util.Arrays;
import fr.insee.bootcampjs.telephoneback.model.Designation;
import fr.insee.bootcampjs.telephoneback.persistence.DesignationRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	DesignationRepository designationRepo;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {

		// TODO à replacer dans du service ailleurs

		String emplacementCSV = "telephone-init-data.csv";
		File fichierCSV = new ClassPathResource(emplacementCSV).getFile();
		System.out.println("fichier csv existe " + fichierCSV.getAbsolutePath());
		System.out.println("fichier csv existe " + fichierCSV.exists());
		List<String> listeCSV = new ArrayList<>();

		// CHARGER LE FICHIER DANS UNE LISTE
		try (Stream<String> stream = Files.lines(fichierCSV.toPath())) {

			listeCSV = stream.skip(1).collect(Collectors.toList());

		} catch (IOException ioE) {
			// Au cas où ça plante on a une roue de secours
			chargementDeSecours();
		}

		// convertir la liste en liste de designations à persister
		List<Designation> listeDesignations = new ArrayList<>();

		for (String str : listeCSV) {
			String[] split = str.split(";", -1);
			listeDesignations.add(convertirEnDesignation((String[]) Arrays.copyOfRange(split, 0, 9)));
		}
		for (Designation designation : listeDesignations) {
			designation = designationRepo.saveAndFlush(designation);
		}

	}

	private Designation convertirEnDesignation(String[] split) {
		
		String num_ligne = split[0].trim();
		String bureau = split[1].trim();
		String designation = split[2].trim();
		String liste_rouge = split[3].trim();
		String attribution = split[4].trim();
		String service = split[5].trim();
		String division = split[6].trim();
		String observation = split[7].trim();

//		return designationBean.phoneNumber(num_ligne).office(bureau).designations(designation).redList(vrai(liste_rouge))
//				.attribution(attribution).service(service).division(division).observation(observation).build();
		Designation des = new Designation(num_ligne, bureau, designation, vrai(liste_rouge), attribution, service, division, observation);
		return des;
	}

	private boolean vrai(String str) {
		if (str == "VRAI") {
			return true;
		} else {
			return false;
		}
	}

	private void chargementDeSecours() {
		long id = 1;
		Designation des = new Designation(id, "8734", "Turban");
		des = designationRepo.saveAndFlush(des);
	}
}
