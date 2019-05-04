/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithmes;

import modeles.Heuristique;

/**
 *
 * @author pc
 */
public interface AlgorithmeHeuristiqueRechercheEE extends AlgorithmeRechercheEE {

    public void setHeuristique(Heuristique h);
    
    public Heuristique getHeuristique();

}
