package bean;

import java.io.Serializable;

import annotation.Regexp;

public class Lieu implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int id;

    @Regexp(expr = ".{2,}", value = "Il faut au moins 2 lettres pour le pays")
    private String pays;

    @Regexp(expr = ".{2,}", value = "Il faut au moins 2 lettres pour le departement")
    private String departement;

    @Regexp(expr = ".{2,}", value = "Il faut au moins 2 lettres pour la ville")
    private String ville;

    @Override
    public boolean equals(Object o) {
	if (o == null)
	    return false;
	if (!(o instanceof Lieu))
	    return false;
	Lieu lieu = (Lieu) o;
	if (lieu == this)
	    return true;

	if (this.departement.equals(lieu.departement) && this.ville.equals(lieu.ville) && this.pays.equals(lieu.pays)) {
	    return true;
	}
	return false;

    }

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

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

}
