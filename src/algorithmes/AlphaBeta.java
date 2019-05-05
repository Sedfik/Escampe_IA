/**
 * 
 */

package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;
import jeux.dominos.PlateauDominos;

public class AlphaBeta implements AlgoRechercheArbre {

    /** La profondeur de recherche par défaut
     */
    private final static int PROFMAXDEFAUT = 5;

   
    // -------------------------------------------
    // Attributs
    // -------------------------------------------
 
    /**  La profondeur de recherche utilisée pour l'algorithme
     */
    private int profMax = PROFMAXDEFAUT;

     /**  L'heuristique utilisée par l'algorithme
      */
    private Heuristique h;

    /** Le joueur Min
     *  (l'adversaire) */
    private Joueur joueurMin;

    /** Le joueur Max
     * (celui dont l'algorithme de recherche adopte le point de vue) */
    private Joueur joueurMax;

    /**  Le nombre de noeuds développé par l'algorithme
     * (intéressant pour se faire une idée du nombre de noeuds développés) */
    private int nbnoeuds;

    /** Le nombre de feuilles évaluées par l'algorithme
     */
    private int nbfeuilles;


  // -------------------------------------------
  // Constructeurs
  // -------------------------------------------
    public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
//		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
    }

   // -------------------------------------------
  // Méthodes de l'interface AlgoJeu
  // -------------------------------------------

   public CoupJeu meilleurCoup(PlateauJeu p) {
	   
	   nbnoeuds=0;
	   
	   nbfeuilles=0;
	   
	   ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMax);
	   
	   CoupJeu firstCoup = coupsPossibles.get(0);
	   
	   nbnoeuds++;
	   
	   PlateauDominos firstP = (PlateauDominos) p.copy();
	   
	   firstP.joue(joueurMax,firstCoup);
	   
	   int alpha =  minMaxAlphaBeta(firstP, this.h, this.profMax-1, -1000000, 1000000);
	   
	   CoupJeu mCoup = firstCoup;
	   
	   for (int i=1; i<coupsPossibles.size();i++){
		   
		   nbnoeuds++;
		   
		   CoupJeu nextCoup = coupsPossibles.get(i);
		   
		   PlateauDominos nextP = (PlateauDominos) p.copy();
		   
		   nextP.joue(joueurMax,nextCoup);
		   	
		   int newAlpha = minMaxAlphaBeta(nextP, this.h, profMax-1, alpha, 1000000);
		   
		   if (newAlpha>alpha){
			   
			   mCoup = nextCoup;
			   
			   alpha = newAlpha;
		   }
	   }
	   System.out.println("Nombre de feuilles développés par la recherche : "+nbfeuilles);
	   
	   System.out.println("Nombre de noeuds développés par la recherche : "+nbnoeuds);
	   
	   return mCoup;
    }
     
   
  // -------------------------------------------
  // Méthodes publiques
  // -------------------------------------------
    public String toString() {
        return "MiniMax(ProfMax="+profMax+")";
    }



  // -------------------------------------------
  // Méthodes internes
  // -------------------------------------------

    
    private int minMaxAlphaBeta (PlateauJeu p, Heuristique h, int profondeur, int alpha, int beta){
    	
    	if ((profondeur <= 0) || (p.finDePartie())) {	// Si profondeur atteinte
    		
    		if (p.finDePartie()){
    			
    			nbnoeuds--;
    		}
    		
    		nbfeuilles++;
    		
    		return this.h.eval(p, this.joueurMax);	
    	
    	}
    	else { // Profondeur > 0
    	
    		for (CoupJeu c : p.coupsPossibles(this.joueurMax)) { // Pour chaques coups possibles
    			
    			nbnoeuds++;
    			
    	    	PlateauJeu pCopy = p.copy();
    	    	
    			pCopy.joue(this.joueurMax, c);	// On joue le coup
    			
    			beta = Math.min(beta, maxMinAlphaBeta(pCopy,h, profondeur - 1, alpha, beta));
    			
    			if (alpha>=beta) {
    				
    				return alpha;
    				
    			}
    			
			}
    		
    	}
    	
    	return beta;
    }
    
    private int maxMinAlphaBeta (PlateauJeu p, Heuristique h,int profondeur, int alpha, int beta){
    	
    	if ((profondeur <= 0) || (p.finDePartie())) {	// Si profondeur atteinte
    		
    		if (p.finDePartie()){
    			
    			nbnoeuds--;
    		}
    		
    		nbfeuilles++;
    		
    		return this.h.eval(p, this.joueurMin);	
    	}
    	else { // Profondeur > 0
    		
    		for (CoupJeu c : p.coupsPossibles(this.joueurMin)) { // Pour chaques coups possibles
    		  	
    			nbnoeuds++;
    			
    			PlateauJeu pCopy = p.copy();
    			
    			pCopy.joue(this.joueurMin, c);
    			
    			alpha = Math.max(alpha, minMaxAlphaBeta(pCopy,h, profondeur - 1,alpha,beta));
    			
    			if (alpha>=beta){
    				
    				return beta;
    			}
    			
			}
    		
    	}
    	
    	return alpha;
    }
    
}
