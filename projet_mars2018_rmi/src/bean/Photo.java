package bean;

import java.sql.Blob;

import annotation.Regexp;

public class Photo {
    @Regexp(expr = ".{2,50}", value = "Il faut entre 2 et 50 lettres pour le nom")
    private String nom;

    private Blob image;
}
