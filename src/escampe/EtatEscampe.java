package escampe;

import iia.espacesEtats.modeles.Etat;

/** Un etat est represente par un Joueur de l'interface IJoueur
 * autorisant l'acces au plateau et a la couleur du joueur*/
public class EtatEscampe implements Etat{
	IJoueur joueur;
	
	public EtatEscampe(IJoueur joueur) {
		// TODO Auto-generated constructor stub
		this.joueur = joueur;
	}
}
