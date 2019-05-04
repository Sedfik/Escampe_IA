package graphes;

import modeles.Etat;

/*
 * Noeud qui stocke en plus le coût du meilleur chemin connu depuis
 * l'état initial jusqu'à l'état du noeud et la valeur de la fonction
 * heuristique f = g + h
 *
 * @author pc
 */
public class NoeudGF extends Noeud {

    // Attributs
    /** cout du meilleur chemin connu depuis la racine jusqu'à ce noeud */
    private float cout;
    /** valeur de la fonction heuristique (f=cout+h) pour ce noeud */
    private float f;

    // Constructeurs
    
//- Pas de constructeur pas défaut... trop dangeureux car la
// valeur f serait fausse.

    public NoeudGF(Etat e, Noeud n, float cout, float f) {
        super(e, n);
        this.cout = cout;
        this.f = f;

    }

    // Accesseurs / Modifieurs
    public float getCout() {
        return cout;
    }

    public void setCout(float cout) {
        this.cout = cout;

    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;

    }
}
