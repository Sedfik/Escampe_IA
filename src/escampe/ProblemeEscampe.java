package escampe;

import java.util.Collection;
import java.util.LinkedList;

import modeles.Etat;
import modeles.Probleme;
import modeles.ProblemeACout;
import sun.awt.SunHints.Value;
//import problemes.tsp.EtatTSP;

public class ProblemeEscampe extends ProblemeACout{
	
	public ProblemeEscampe(Etat eInit, String nom) {
		super(eInit, nom);
		// TODO Auto-generated constructor stub
	}
	
	
	//////////////////////////////////////////////////////////////////////A MODIFIER
	@Override
	public boolean isTerminal(Etat e) {
		if (!(e instanceof EtatEscampe)) {
            return false;
        }
		EtatEscampe ee = (EtatEscampe) e;
		System.err.println("isTerm?");
		///////////////////////////////////////////////TODO : Pas besoin de copier ?
		EscampeBoard eb = new EscampeBoard(ee.getWhite(),ee.getBlack(),ee.getLastLisere());
		return eb.gameOver();
	}

	@Override
	public Collection<Etat> successeurs(Etat e) {
		LinkedList<Etat> toRet = new LinkedList<Etat>();
        if ((e instanceof EtatEscampe) && (!this.isTerminal(e))) {
            EtatEscampe etat = (EtatEscampe) e;
            
            //EtatEscampe newEb = null;
            // Pour chaque coups possible on simule le mouvement dans un nouvel etat
            //String[] pm = eb.getEscampeBoard().possibleMoves(eb.getNomJoueur());
            
            //On fait des copies pour eviter les effets de bords
            EscampeBoard eb = new EscampeBoard(etat.getWhite().clone(),etat.getBlack().clone(),new Integer(etat.getLastLisere()));
            
            for(String m : eb.possibleMoves(etat.getPlayer())) {
            	System.out.println("movement : "+m);
            	eb.print_black();
            	eb.print_white();
            	System.out.println(etat.getPlayer());
            	//On fait des copies pour eviter les effets de bords
            	String[] white = etat.getWhite().clone();
            	String[] black = etat.getBlack().clone();
            	String player = new String(etat.getPlayer());
            	int lastLisere = new Integer(etat.getLastLisere());
            	//On recupere l'etat resultant d'un coup en modifiant directement les variables d'etat et en simulant un coup
            	eb.simulate_play(m, white, black, player, lastLisere);
            	//TODO : LastLisere n'est pas modifie a l'issue de l'appel alors que normalement ca devrait ptn de sa mere
            	System.out.println(lastLisere);
            	toRet.add(new EtatEscampe(white,black,player,lastLisere));
            }
        }
        return toRet;
	}

	///////////////////////////////////////////////////////////////A MODIFIER
	@Override
	public float cout(Etat e1, Etat e2) {
		////////////////////////////////// TODO Auto-generated method stub
		if ((e1 instanceof EtatEscampe)&&(e2 instanceof EtatEscampe) ){
			float c = ((EtatEscampe)e1).cout((EtatEscampe)e2);
			return ( ((EtatEscampe)e1).cout((EtatEscampe)e2) ) ;
		}
		return 0;
	}

}
