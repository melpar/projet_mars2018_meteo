package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ArchiveMeteo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Lieu lieu;
    private Date date;
    private DonneeMeteo donnee;
    private List<Photo> photos;

    public Lieu getLieu() {
	return lieu;
    }

    public void setLieu(Lieu lieu) {
	this.lieu = lieu;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public DonneeMeteo getDonnee() {
	return donnee;
    }

    public void setDonnee(DonneeMeteo donnee) {
	this.donnee = donnee;
    }

    public List<Photo> getPhotos() {
	return photos;
    }

    public void setPhotos(List<Photo> photos) {
	this.photos = photos;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }
}
