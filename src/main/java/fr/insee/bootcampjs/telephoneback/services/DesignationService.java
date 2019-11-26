package fr.insee.bootcampjs.telephoneback.services;

import org.springframework.beans.factory.annotation.Autowired;

import fr.insee.bootcampjs.telephoneback.persistence.DesignationRepository;

public class DesignationService {


	@Autowired
	DesignationRepository designationRepo;
	
//	public Designation enregistrer(DesignationDto designationDto) {
//
//		Designation designation = designationRepo.getOne(designationDto.getIdUe());
//
//		InfoSuiviGestion isg = InfoSuiviGestion.builder()
//				.dateInfo(up.getDateUpload())
//				.statut(infoDto.getStatut())
//				.uniteEnquete(ue)
//				.upload(up)
//				.build();
//		return infoRepo.saveAndFlush(isg);
//	}

}
	
	
