package util;

import java.security.Key;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class Cryptage {

    /**
     * Chaine � crypter
     */
    private String chaineIn;

    /**
     * Algorythme utilis�
     */
    private String algo;

    /**
     * Cl� utilis�e
     */
    private byte[] cle;

    /**
     * Fichier de configuration o� les donn�es sont stock�es
     */
    private static String config = "resources/cleDeChiffrement";

    /**
     * Initialisation de l'objet.
     * 
     * @param chaine
     *            Chaine de caractere a chiffrer
     * @param cle
     *            Cle de chiffrement
     */

    public Cryptage(String chaine) {
	// TODO Auto-generated constructor stub
	this.chaineIn = chaine;

	ResourceBundle resource = ResourceBundle.getBundle(config);
	this.cle = resource.getString("cleDeChiffrement").getBytes();
	this.algo = resource.getString("algorithme");
    }

    /**
     * Chriffrage d'une chaine de caractere
     * 
     * @return chaine chiffr�e
     * @throws Exception
     */
    public String chiffrer() throws Exception {
	Key cle = generationCle();
	Cipher c = Cipher.getInstance(algo);
	c.init(Cipher.ENCRYPT_MODE, cle);
	byte[] encVal = c.doFinal(this.chaineIn.getBytes());
	return new BASE64Encoder().encode(encVal);
    }

    /**
     * G�n�re la cl�
     * 
     * @return cl�
     * @throws Exception
     */
    private Key generationCle() throws Exception {
	return new SecretKeySpec(cle, algo);
    }

}
