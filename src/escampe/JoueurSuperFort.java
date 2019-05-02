package escampe;

public class JoueurSuperFort implements IJoueur{

	private String player;
	private static EscampeBoard board;
	
	
	@Override
	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		board = new EscampeBoard();
		//board.setFromFile("\\src\\data\\plateau1.txt");
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
		String w = "B2/A1/B1/C2/E2/F2";
		String b = "C6/A6/B5/D5/E6/F5";
		if((board.getWhite()[0] == null) || (board.getBlack()[0] == null)){
			return ((player == "blanc") ?  w : b);
		}
		else {
			String[] moves = board.possibleMoves(player);
			return moves[0];
		}
	}

	@Override
	public void declareLeVainqueur(int colour) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub
		if(player == "blanc") {
			board.play(coup, "noir");
			board.print_black();
			board.print_white();
		}
		else {
			board.play(coup, "blanc");
			board.print_black();
			board.print_white();
		}
	}

	@Override
	public String binoName() {
		// TODO Auto-generated method stub
		if(player == "blanc")
			return ("kobayashi");
		else
			return ("ramos");
	}

}
