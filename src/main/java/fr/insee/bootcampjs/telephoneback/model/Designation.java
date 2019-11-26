package fr.insee.bootcampjs.telephoneback.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table (name = "designation")
@JsonRootName (value = "designation")
public class Designation {

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE , generator = "gen_designation")
	@SequenceGenerator (name = "gen_designation" , allocationSize = 1 , sequenceName = "seq_designation")
	@Column (name = "id" , updatable = false , nullable = false)
	private Long id;
	
	public Designation() {
		super();
	}

	public Designation(String phoneNumber, String office, String designation, Boolean redList,
			String attribution, String service, String division, String observation) {
		super();
		this.phoneNumber = phoneNumber;
		this.office = office;
		this.designation = designation;
		this.redList = redList;
		this.attribution = attribution;
		this.service = service;
		this.division = division;
		this.observation = observation;
	}

	public Designation(long id, String phoneNumber, String designation) {
		super();
		this.phoneNumber = phoneNumber;
		this.id = id;
		this.designation = designation;
	}

	@Column
	private String phoneNumber;
	
	@Column
	private String office;
	
	@Column
	private String designation;
	
	@Column
	private Boolean redList;
	
	@Column
	private String attribution;
	
	@Column
	private String service;
		
	@Column
	private String division;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Boolean getRedList() {
		return redList;
	}

	public void setRedList(Boolean redList) {
		this.redList = redList;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Column
	private String observation;


}

