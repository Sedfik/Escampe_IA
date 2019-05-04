package escampe;

import java.util.Collection;
import java.util.LinkedList;

import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Probleme;
import iia.espacesEtats.modeles.ProblemeACout;
import problemes.tsp.EtatTSP;
/**
 * 	┏━━╮┏┓┏┓╭━┓┏━┓
	┃┏╮┃┃┃┃┃┃╭┛┃┗┓
	┃┃┃╰┛┃┃┃┃╰┓┃┗┓
	┗┛╰━━┛┗┛╰━┛┗━┛﻿
	@author ramos*/
public class ProblemeEscampe extends ProblemeACout{
	
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
		System.err.println("isTerm?");
		return eb.gameOver();
	}

	@Override
	public Collection<Etat> successeurs(Etat e) {
		LinkedList<Etat> toRet = new LinkedList<Etat>();
        if ((e instanceof EtatEscampe) && (!this.isTerminal(e))) {
            EtatEscampe eb = (EtatEscampe) e;
            EtatEscampe newEb = null;
            // Pour chaque coups possible on simule le mouvement dans un nouvel etat
            String[] pm = eb.getEscampeBoard().possibleMoves(eb.getNomJoueur());
            for(String m : pm) {
            	//TODO : Le plateau principal est modifié par ses fils
            	eb.getEscampeBoard().print_white();
            	newEb = new EtatEscampe(eb);
            	
            	newEb.getEscampeBoard().play(m, newEb.getNomJoueur());
            	
            	System.err.println(eb.getEscampeBoard().getWhite());
            	
            	eb.getEscampeBoard().print_white();
            	
            	newEb.getEscampeBoard().print_white();
            	
            	toRet.add(newEb);
            	newEb = null;
            }
        }
        return toRet;
	}

	@Override
	public float cout(Etat e1, Etat e2) {
		// TODO Auto-generated method stub
		return ((EscampeBoard) e1).cout(e2);
	}

}
