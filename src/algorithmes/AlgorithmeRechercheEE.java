package algorithmes;

import modeles.Probleme;
import modeles.Solution;

/**
 *  Interface minimale d'un algorithme de résolution de problème
 *  de recherche dans un graphe d'états
 *  
 * @author Philippe Chatalic
 */

public interface AlgorithmeRechercheEE {

    /**
     * Recherche un chemin solution pour un problème formulé en terme de
     * recherche dans un espace d'états
     *
     * @param p le problème à résoudre
     * @return une solution du problème
     */
    public Solution chercheSolution(Probleme p);
}
