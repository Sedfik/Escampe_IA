package escampe;

import java.util.Collection;

import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Probleme;
import problemes.tortues.EtatTortue;

public class ProblemeEscampe extends Probleme{

	public ProblemeEscampe(Etat eInit, String nom) {
		super(eInit, nom);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isTerminal(Etat e) {
		if (!(e instanceof EtatEscampe)) {
            return false;
        }
		EtatEscampe ee = (EtatEscampe) e;
		return true;
	}

	@Override
	public Collection<Etat> successeurs(Etat arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
