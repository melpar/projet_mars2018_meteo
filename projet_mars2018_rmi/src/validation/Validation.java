package validation;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import annotation.Regexp;

public class Validation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Indique si une erreur a �t� detect�e
     */
    boolean valide = true;

    /**
     * Contiendra les valeurs saisies dans les champs. ex : ("auteur",
     * "lauteursaisi")
     */
    Hashtable<String, String> valeurs = new Hashtable<String, String>();

    /**
     * Contiendra les erreurs �ventuelles
     */
    Hashtable<String, String> erreurs = new Hashtable<String, String>();

    /**
     * Permet de tester une valeur
     * 
     * @param c
     *            class ou le champs est contenu
     * @param param
     *            nom du champs
     * @param val
     *            valeur du champs
     * @return vrai si pas d'erreur, faux sinon
     */
    public boolean regexp(Class c, String param, String val) {
	boolean res = false;

	try {
	    System.out.println("validation du champ " + param);
	    System.out.println("valeur saisie : " + val);

	    Field f = c.getDeclaredField(param);
	    Regexp ann = f.getAnnotation(Regexp.class);
	    System.out.println("exp reg = " + ann.expr());
	    System.out.println("mess err = " + ann.value());

	    Pattern pattern = Pattern.compile(ann.expr());
	    Matcher matcher = pattern.matcher(val);
	    if (!matcher.matches() || val == "") {
		// valeur incorrecte
		System.out.println("Erreur d�tect�e : " + ann.value());
		erreurs.put(param, ann.value());
		valide = false;
	    } else {
		System.out.println("Pas d 'erreur");
	    }

	    valeurs.put(param, val);

	} catch (Exception e) {
	    valide = false;
	    System.out.println("Erreur Validation.regexp " + e.getMessage());
	}

	return res;
    }

    public boolean isValide() {
	return valide;
    }

    public void setValide(boolean valide) {
	this.valide = valide;
    }

    public Hashtable<String, String> getValeurs() {
	return valeurs;
    }

    public void setValeurs(Hashtable<String, String> valeurs) {
	this.valeurs = valeurs;
    }

    public Hashtable<String, String> getErreurs() {
	return erreurs;
    }

    public void setErreurs(Hashtable<String, String> erreurs) {
	this.erreurs = erreurs;
    }

    public void addValue(String key, String value) {
	this.valeurs.put(key, value);
    }

    public void addErreur(String key, String value) {
	this.erreurs.put(key, value);
    }
}
