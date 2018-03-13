package bean;

import java.io.Serializable;

public enum Soleil implements Serializable {
    // Objets directement construits
    ECLAIRCIES("Eclaircies", 1), AVERSES("Averses", 2), AVERSE_ORAGEUSE("Averses orageuses",
	    3), ENSOLEILLE("Ensoleillé", 4), NEIGE("Neige", 5), NUAGEUX("Nuageux", 6);
    private String name = "";
    private int id;

    // Constructeur
    Soleil(String name, int id) {
	this.name = name;
	this.id = id;
    }

    public static Soleil getById(int id) {
	for (Soleil sol : Soleil.values()) {
	    if (sol.id == id) {
		return sol;
	    }
	}
	return null;
    }

    public String getName() {
	return name;
    }

    public int getId() {
	return id;
    }
}
