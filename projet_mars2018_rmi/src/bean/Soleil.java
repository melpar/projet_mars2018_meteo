package bean;

public enum Soleil {
	// Objets directement construits
	ECLAIRCIES("Eclaircies"), AVERSE("Averses"), AVERSE_ORAGEUSE("Averses orageuses"), ENSOLEILLE("Ensoleillé"), NEIGE(
			"Neige"), NUAGEUX("Nuageux");
	private String name = "";

	// Constructeur
	Soleil(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
