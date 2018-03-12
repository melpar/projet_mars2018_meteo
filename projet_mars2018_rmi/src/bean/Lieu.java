package bean;

import java.io.Serializable;

import annotation.Regexp;

public class Lieu implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Regexp(expr = ".{2,}", value = "Il faut au moins 2 lettres pour le pays")
    private String pays;

    @Regexp(expr = ".{2,}", value = "Il faut au moins 2 lettres pour le departement")
    private String departement;

    @Regexp(expr = ".{2,}", value = "Il faut au moins 2 lettres pour la ville")
    private String ville;

    public String getPays() {
	return pays;
    }

    public void setPays(String pays) {
	this.pays = pays;
    }

    public String getDepartement() {
	return departement;
    }

    public void setDepartement(String departement) {
	this.departement = departement;
    }

    public String getVille() {
	return ville;
    }

    public void setVille(String ville) {
	this.ville = ville;
    }
}
