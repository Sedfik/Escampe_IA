package escampe;

import java.util.Collection;
import java.util.LinkedList;

import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Probleme;

public class ProblemeEscampe extends Probleme{
	
	
		
	public ProblemeEscampe(Etat eInit, String nom) {
		super(eInit, nom);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isTerminal(Etat e) {
		if (!(e instanceof EscampeBoard)) {
            return false;
        }
		EscampeBoard eb = (EscampeBoard) e;
		return eb.gameOver();
	}

	@Override
	public Collection<Etat> successeurs(Etat e) {
		LinkedList<Etat> toRet = new LinkedList<Etat>();
        if ((e instanceof EscampeBoard) && (!this.isTerminal(e))) {
            EscampeBoard eb = (EscampeBoard) e;
            EscampeBoard newEb = null;
            // Pour chaque coups possible on simule le mouvement dans un nouvel etat
            String[] pm = eb.possibleMoves(getNom());
            for(String m : pm) {
            	newEb = new EscampeBoard();
            	newEb.play(m, getNom());
            	toRet.add(newEb);
            }
        }
        return toRet;
	}
}
