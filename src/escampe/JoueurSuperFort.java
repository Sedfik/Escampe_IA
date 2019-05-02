package escampe;

public class JoueurSuperFort implements IJoueur{

	private String player;
	private EscampeBoard board;
	
	
	@Override
	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		board = new EscampeBoard();
		
		if(mycolour == -1)
			player = "blanc";
		else
			player = "noir";
	
	}

	@Override
	public int getNumJoueur() {
		// TODO Auto-generated method stub
		if (player == "blanc")
			return -1;
		else
			return 1;
	}

	@Override
	public String choixMouvement() {
		// TODO Auto-generated method stub
		String[] moves = board.possibleMoves(player);
		return moves[0];
	}

	@Override
	public void declareLeVainqueur(int colour) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub
		if(player == "blanc")
			board.play(coup, "noir");
		else
			board.play(coup, "blanc");
	}

	@Override
	public String binoName() {
		// TODO Auto-generated method stub
		return "Ramoyashi";
	}

}
