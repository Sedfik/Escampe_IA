/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles;

/**
 *
 * @author pc
 */
public abstract class ProblemeACout extends Probleme {

    // Constructeur
    public ProblemeACout(Etat eInit, String nom) {
        super(eInit, nom);
    }

    /**
     * @param e1
     * @param e2
     * @return cout de l'opérateur permettant de passer de e1 à e2
     */
    public abstract float cout(Etat e1, Etat e2);
}
