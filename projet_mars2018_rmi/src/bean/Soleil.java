package bean;

public enum Soleil {
    // Objets directement construits
    ECLAIRCIES("Eclaircies", 1), AVERSE("Averses", 2), AVERSE_ORAGEUSE("Averses orageuses", 3), ENSOLEILLE("Ensoleillé",
	    4), NEIGE("Neige", 5), NUAGEUX("Nuageux", 6);
    private String name = "";
    private int id;

    // Constructeur
    Soleil(String name, int id) {
	this.name = name;
	this.id = id;
    }

    public String toString() {
	return name;
    }

    public int getId() {
	return id;
    }
}
