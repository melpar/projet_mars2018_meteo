package bean;

import java.sql.Blob;

import annotation.Regexp;

public class Photo {
    @Regexp(expr = ".{2,50}", value = "Il faut entre 2 et 50 lettres pour le nom")
    private String nom;

    private Blob image;

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public Blob getImage() {
	return image;
    }

    public void setImage(Blob image) {
	this.image = image;
    }
}
