package graphes;

import modeles.Etat;

/**
 * Cette classe permet de représenter le "graphe" de recherche exploré par
 * un algorithme de recherche dans un espace d'états, sous une forme arborescente.
 * Elle est "minimale" au sens où chaque noeud contient les information minimales,
 * à savoir :
 *   - l'état associé à un noeud
 *   - un noeud parent (idéalement, vers le "meilleur" parent connu, si cela a
 *     du sens pour l'algorithme de recherche considéré.
 *      (null si l'état correspond à un l'état initial du problème).
 *
 * Cette classe pourra être étendue si nécessaire, pour ajouter les informations
 * utiles en fonctin des besoins de chaque algorithme de recherche.
 *
 * @author Philippe Chatalic
 * 
 */
public class Noeud {

    // ------------------- Attributs -------------------
    /**
     * L'état associé au noeud
     */
    private Etat etat;
    /**
     * le noeud correspondant à l'état à partir duquel l'état de ce noeud
     * a été produit lors de l'exploration du graphe de recherche
     */
    private Noeud pere;

    // ------------------- Constructeurs -------------------
    public Noeud() {
        etat = null;
        pere = null;
    }

    /**
     * Construit un noeud a partir
     */
    public Noeud(Etat e, Noeud n) {
        etat = e;
        pere = n;
    }

    // ------------------- Accesseurs -------------------
    /**
     * @return l'état associé au noeud
     */
    public Etat getEtat() {
        return this.etat;
    }

    /**
     * @return le parent de ce noeud dans le graphe de recherche
     */
    public Noeud getPere() {
        return this.pere;
    }


    // ------------------- Modifieurs -------------------

    public void setPere(Noeud n) {
        pere = n;
    }

    public void setEtat(Etat e) {
        etat = e;
    }

    // ------------------- Autres méthodes  -------------------


 
    /**
     * @return vrai si le noeud n correspond au même état que le noeud courant
     */
    public boolean memeEtat(Noeud n) {
        return etat.equals(n.getEtat());
    }
    
    /**
     * @return vrai si le noeud n correspond au même état que le noeud courant
     */
    public boolean memeEtat(Etat e) {
        return etat.equals(e);
    }


    public String toString() {
        String ret = "Noeud : etat = " + etat;
        return ret;
    }



}
