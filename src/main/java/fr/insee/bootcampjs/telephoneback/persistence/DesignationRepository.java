package fr.insee.bootcampjs.telephoneback.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.insee.bootcampjs.telephoneback.model.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

	public List <Designation >findAllByOrderByIdAsc();
	
	@Query("select d from Designation d  where d.designation = 'EN ATTENTE' order by d.phoneNumber")
	public List <Designation> findEnAttente();
	
	@Query("select d from Designation d  where d.designation <> '' and d.phoneNumber <> '' and d.redList = false order by d.designation")
	public List<Designation> toExport();

}
