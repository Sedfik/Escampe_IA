package escampe;

import iia.espacesEtats.algorithmes.AEtoile;
import iia.espacesEtats.algorithmes.RechercheEnLargeur;
import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Heuristique;
import iia.espacesEtats.modeles.Probleme;
import iia.espacesEtats.modeles.Solution;

public class JoueurSuperFort implements IJoueur{

	public String player;
	private EscampeBoard board; //TODO : Peut-il etre static avec l'algo IA?
	
	
	private static Heuristique h1 = new Heuristique() {
		
		@Override
		public float eval(Etat e) {
			// TODO Auto-generated method stub
			if (e instanceof EtatEscampe) {
				EtatEscampe eb = (EtatEscampe) e;
				int evalutation = 0; 
				for(String p : eb.getEscampeBoard().getWhite()) {
					evalutation += eb.getEscampeBoard().get_i_from_string(p) + eb.getEscampeBoard().get_j_from_string(p);
				}
				for(String p : eb.getEscampeBoard().getBlack()) {
					evalutation -= eb.getEscampeBoard().get_i_from_string(p) + eb.getEscampeBoard().get_j_from_string(p);
				}
                return evalutation;
            } else {
                throw new Error("Cette heursitique ne peut s'appliquer que sur des EtatEscampe");
            }
		}
	};
	private static Heuristique h2 = new Heuristique() {
		
		@Override
		public float eval(Etat e) {
			if (e instanceof EtatEscampe) {
				EtatEscampe eb = (EtatEscampe) e;
				int ret = 0;
				int iOrigin,jOrigin;
				String[] pions;

				if(eb.getNomJoueur() == "blanc") {
					pions = eb.getEscampeBoard().getWhite();
					
					}
				else {
					pions = eb.getEscampeBoard().getBlack();
				}
				iOrigin = eb.getEscampeBoard().get_i_from_string(pions[0]);
				jOrigin = eb.getEscampeBoard().get_j_from_string(pions[0]);
				
				// On regarde la distance de chaques pions de la licorne
				for(int i = 1; i < 6; i++) {
					int iDistance = Math.abs(iOrigin - eb.getEscampeBoard().get_i_from_string(pions[i]));
					int jDistance = Math.abs(jOrigin - eb.getEscampeBoard().get_j_from_string(pions[i]));
					// Plus ils sont eloigne, plus la licorde est en danger
					ret += (iDistance + jDistance);
				}
				return ret;
            } else {
                throw new Error("Cette heursitique ne peut s'appliquer que sur des EtatEscampe");
            }
		}
	};
	
	private Heuristique h3 = new Heuristique() {
		
		@Override
		public float eval(Etat arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	private static AEtoile algo = new AEtoile(h1);
	
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
		
		
		if(board.gameOver()) {
			return "xxxxx";
		}
			
		String w = "B2/A1/B1/C2/E2/F2";
		String b = "C6/A6/B5/D5/E6/F5";
		
		String coupJoue;
		if((board.getWhite()[0] == null) || (board.getBlack()[0] == null)){
			coupJoue = ((player == "blanc") ?  w : b);
		}
		else {
			
			Etat initial = new EtatEscampe(board, player);
			Probleme pb = new ProblemeEscampe(initial, "Pb escampe");
			
			Solution sol = algo.chercheSolution(pb);
			
			if (sol != null) {
				System.out.println("Solution trouvée : ");
				sol.affiche();
			}
			else
				System.out.println("Echec !");
			
			
			
			
			//// Mais du coup le coup n'est pas joué non ?
			
			//Si le coup choisi est fatal, on renvoit xxxxx pour signaler la fin de partie
			String[] moves = board.possibleMoves(player);
			if (player == "blanc") {
				if (moves[0].split("-")[1].contentEquals(board.getBlack()[0])) {
					coupJoue = "xxxxx";
				}
			}
			else {
				if (moves[0].split("-")[1].contentEquals(board.getWhite()[0])) {
					coupJoue = "xxxxx";
				}
			}
			coupJoue = moves[0];
		}
		board.play(coupJoue,player);
		return coupJoue;
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
