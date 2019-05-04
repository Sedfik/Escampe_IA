package algorithmes;
/*
 * AEtoile.java
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import graphes.Noeud;
import modeles.Etat;
import modeles.Heuristique;
import modeles.ProblemeACout;
import modeles.Probleme;
import modeles.Solution;

/**
 * La classe qui implémente l'algo A*
 * 
 * A completer !
 */
public class AEtoile implements AlgorithmeHeuristiqueRechercheEE {

    private Heuristique h;
    private int noeudsDeveloppe = 0;


    /* Constructeur de base */
    public AEtoile(Heuristique h) {
        this.h = h;
    }

    /* Lance l'exploration sur un problème */
    public Solution chercheSolution(Probleme p) {
    	ProblemeACout p_cout = (ProblemeACout) p;
    	boolean present = false;
    	Collection<Noeud> deja_dev = new HashSet<Noeud>();
    	ArrayList<Noeud> frontiere = new ArrayList<Noeud>();
    	Noeud sinit = new Noeud(p_cout.getEtatInitial(),new Noeud());
    	frontiere.add(sinit);
    	HashMap<Noeud,Float> g = new HashMap<Noeud,Float>();
    	HashMap<Noeud,Float> f = new HashMap<Noeud,Float>();
    	g.put(sinit, (float)0);
    	f.put(sinit, this.h.eval(sinit.getEtat()));
    	this.noeudsDeveloppe++;
    	while(!(frontiere).isEmpty()){
    		//initialisation du noeud
    		Noeud n_fmin = frontiere.get(0);
    		int indice_n = 0;
    		for (int i=0; i<frontiere.size();i++){
    			if (f.get(frontiere.get(i))<f.get(n_fmin)){
    				n_fmin = frontiere.get(i);
    				indice_n = i;
    			}
    		}
    		//Si terminal
    		if (p_cout.isTerminal(n_fmin.getEtat())){
    			//this.noeudsDeveloppe-=100;
    			Solution s = new Solution(n_fmin.getEtat());
    			while (n_fmin.getPere().getEtat()!=p_cout.getEtatInitial()){
    				s.add(n_fmin.getPere().getEtat());
    				n_fmin=n_fmin.getPere();
    			}
				s.add(n_fmin.getPere().getEtat());
    			System.out.println("Nb de noeuds developpes " + noeudsDeveloppe);
    			return s;
    		}
    		//Sinon
    		else {
    			present = false;
    			frontiere.remove(indice_n);
    			deja_dev.add(n_fmin);
    			for (Etat e : p_cout.successeurs(n_fmin.getEtat())){
    				for (Noeud nd : deja_dev){
    					if (nd.getEtat()==e){
    						present=true;
    					}
    				}
    				for (Noeud nf : frontiere){
    					if (nf.getEtat()==e){
    						present=true;
    					}
    				}
    				if (!present){
    					this.noeudsDeveloppe++;
    					Noeud ns = new Noeud(e,n_fmin);
    					g.put(ns, (g.get(n_fmin)+ p_cout.cout(n_fmin.getEtat(), ns.getEtat() )) );
    					f.put(ns, (g.get(ns)) + (this.h.eval(ns.getEtat())) );
    					frontiere.add(ns);
    				}
    				else {
    					//on recupere le noeud correspondant a l'etat
    					Noeud ns = null;
        				for (Noeud nd : deja_dev){
        					if (nd.getEtat()==e){
        						ns=nd;
        					}
        				}
        				for (Noeud nf : frontiere){
        					if (nf.getEtat()==e){
        						ns=nf;
        					}
        				}
    					if ( g.get(ns)> ( g.get(n_fmin)+p_cout.cout(n_fmin.getEtat(), e )) ){
    						ns.setPere(n_fmin);
        					g.put(ns, (g.get(n_fmin)+ p_cout.cout(n_fmin.getEtat(), ns.getEtat() )) );
        					f.put(ns, (g.get(ns)) + (this.h.eval(ns.getEtat())) );
    					}
    				}
    			}
    		}
    	}
    	System.out.println("ECHEC");
    	Solution sol = null;
        return sol;
    }

    public void setHeuristique(Heuristique h) {
        this.h = h;
    }

    public Heuristique getHeuristique() {
        return h;
    }
}
