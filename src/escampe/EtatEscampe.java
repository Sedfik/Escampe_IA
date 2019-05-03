package escampe;

import iia.espacesEtats.modeles.Etat;

public class EtatEscampe implements Etat{

	private EscampeBoard escampeBoard;
	private String nomJoueur;
	
	public EtatEscampe(EscampeBoard escampeBoard, String nomJoueur) {
		super();
		this.escampeBoard = escampeBoard;
		this.nomJoueur = nomJoueur;
	}

	public EscampeBoard getEscampeBoard() {
		return escampeBoard;
	}

	public String getNomJoueur() {
		return nomJoueur;
	}

	public EtatEscampe(EtatEscampe ee) {
		this.escampeBoard = ee.escampeBoard;
		this.nomJoueur = ee.getNomJoueur();
	}
	
	
}
