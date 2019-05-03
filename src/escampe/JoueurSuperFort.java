package escampe;

import iia.espacesEtats.algorithmes.AEtoile;
import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Heuristique;

public class JoueurSuperFort implements IJoueur{

	private String player;
	private static EscampeBoard board;
	private static AEtoile algo = new AEtoile(
		new Heuristique() {
		
			@Override
			public float eval(Etat e) {
				// TODO Auto-generated method stub
				return 0;
			}
		}
	);
	
	@Override
	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		board = new EscampeBoard();
		//board.setFromFile("\\src\\data\\plateau1.txt");
		player = (mycolour == -1) ? "blanc" : "noir";
	}

	@Override
	public int getNumJoueur() {
		// TODO Auto-generated method stub
		return player == "blanc" ? -1 : 1;
	}

	@Override
	public String choixMouvement() {
		// TODO Auto-generated method stub
		/*if(board.gameOver())
			return "xxxxx";*/
		String w = "B2/A1/B1/C2/E2/F2";
		String b = "C6/A6/B5/D5/E6/F5";
		if((board.getWhite()[0] == null) || (board.getBlack()[0] == null)){
			return ((player == "blanc") ?  w : b);
		}
		else {
			//Si le coup choisi est fatal, on renvoit xxxxx pour signaler la fin de partie
			String[] moves = board.possibleMoves(player);
			if (player == "blanc") {
				if (moves[0].split("-")[1].contentEquals(board.getBlack()[0])) {
					return "xxxxx";
				}
			}
			else {
				if (moves[0].split("-")[1].contentEquals(board.getWhite()[0])) {
					return "xxxxx";
				}
			}
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
