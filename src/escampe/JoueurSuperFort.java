package escampe;

public class JoueurSuperFort implements IJoueur{

	private int colour;
	
	@Override
	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		colour = mycolour;
		
	}

	@Override
	public int getNumJoueur() {
		// TODO Auto-generated method stub
		return colour;
	}

	@Override
	public String choixMouvement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void declareLeVainqueur(int colour) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String binoName() {
		// TODO Auto-generated method stub
		return null;
	}

}
