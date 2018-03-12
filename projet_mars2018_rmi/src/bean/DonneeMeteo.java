package bean;

import annotation.Regexp;

public class DonneeMeteo {
    // pourcentage
    @Regexp(expr = "(^[0-9][0-9]?(,[0-9]{1,3})?$|^100$)", value = "Le pourcentage de pluie doit être entre 0 et 100 avec maximum deux chiffres apres la virgule")
    private double pluie;

    @Regexp(expr = "^(?:360|3[0-5][0-9]|[12][0-9][0-9]|[1-9]?[0-9])?$", value = "La direction du vent doit être comprise entre 0 et 360")
    private double directionVent;

    @Regexp(expr = "^(?:500|[1234][0-9][0-9]|[1-9]?[0-9])?$", value = "La vitesse du vent doit être comprise entre 0 et 500")
    private double vitesseVent;

    private Soleil soleil;

    @Regexp(expr = "^-?(?:50|[1234][0-9]|[0-9])?$", value = "La temperature doit être comprise entre -50 et 50")
    private int temperature;
}
