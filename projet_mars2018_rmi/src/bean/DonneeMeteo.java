package bean;

import java.io.Serializable;

import annotation.Regexp;

public class DonneeMeteo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // pourcentage
    @Regexp(expr = "(^[0-9][0-9]?([,.][0-9]{1,3})?$|^100$)", value = "Le pourcentage de pluie doit �tre entre 0 et 100 avec maximum deux chiffres apres la virgule")
    private double pluie;

    @Regexp(expr = "^(?:360|(3[0-5][0-9]|[12][0-9][0-9]|[1-9]?[0-9])([,.][0-9]{1,3})?$)$", value = "La direction du vent doit �tre comprise entre 0 et 360")
    private double directionVent;

    @Regexp(expr = "^(?:500|([1234][0-9][0-9]|[1-9]?[0-9])([,.][0-9]{1,3})?)$", value = "La vitesse du vent doit �tre comprise entre 0 et 500")
    private double vitesseVent;

    private Soleil soleil;

    @Regexp(expr = "^-?(?:50|[1234][0-9]|[0-9])$", value = "La temperature doit �tre comprise entre -50 et 50")
    private int temperature;

    @Override
    public boolean equals(Object o) {
	if (o == null)
	    return false;
	if (!(o instanceof DonneeMeteo))
	    return false;
	DonneeMeteo donnee = (DonneeMeteo) o;
	if (donnee == this)
	    return true;

	if (this.directionVent == donnee.directionVent && this.pluie == donnee.pluie
		&& this.soleil.equals(donnee.soleil) && this.temperature == donnee.temperature
		&& this.vitesseVent == donnee.vitesseVent) {
	    return true;
	}
	return false;

    }

    public double getPluie() {
	return pluie;
    }

    public void setPluie(double pluie) {
	this.pluie = pluie;
    }

    public double getDirectionVent() {
	return directionVent;
    }

    public void setDirectionVent(double directionVent) {
	this.directionVent = directionVent;
    }

    public double getVitesseVent() {
	return vitesseVent;
    }

    public void setVitesseVent(double vitesseVent) {
	this.vitesseVent = vitesseVent;
    }

    public Soleil getSoleil() {
	return soleil;
    }

    public void setSoleil(Soleil soleil) {
	this.soleil = soleil;
    }

    public int getTemperature() {
	return temperature;
    }

    public void setTemperature(int temperature) {
	this.temperature = temperature;
    }
}
