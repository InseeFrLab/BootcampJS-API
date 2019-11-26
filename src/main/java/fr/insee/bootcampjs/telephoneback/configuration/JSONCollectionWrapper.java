package fr.insee.bootcampjs.telephoneback.configuration;

import java.util.Collection;

public class JSONCollectionWrapper<T> {
	 public Collection<T> getDonnees() {
		return donnees;
	}

	public void setDonnees(Collection<T> donnees) {
		this.donnees = donnees;
	}

	private Collection<T> donnees;

}
