package  algorithmes;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;

public interface AlgoRechercheArbre {

    /** Renvoie le meilleur
     * @param p
     * @return
     */
	public CoupJeu meilleurCoup(PlateauJeu p);

}
 