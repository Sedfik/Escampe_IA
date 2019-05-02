package escampe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EscampeBoard {
	
	//Attributs
	public final static char[] alphabet = {'A','B','C','D','E','F'};
	
		
	//liserePlateau[0][0] depend du sens qu'on a recopie le tableau
    public final static int[][] liserePlateau =
        {
            {1,2,2,3,1,2},
            {3,1,3,1,3,2},
            {2,3,1,2,1,3},
            {2,1,3,2,3,1},
            {1,3,1,3,1,2},
            {3,2,2,1,3,2}
        };
    //Lettres en j
    //Chiffre en i

    
	private String[] white = new String[6];
	private String[] black = new String[6];
	private int last_lisere = 0;
	private String bord_noir;
	
	public EscampeBoard (String[] w, String[] b){
		this.white = w;
		this.black = b;
	}
	
    public EscampeBoard() {
        // TODO Auto-generated constructor stub
    }
	
	//Methodes demandees
	public void setFromFile(String fileName){
        Path path = Paths.get(fileName);

        int pionNoir = 0;
        int pionBlanc = 0;

        int iLine = 0;
        try {
            List<String> lines = Files.readAllLines(path);

            for (String line:lines) { // Pour chaque lignes
                if(line.charAt(0) != '%'){
                    String[] splitedLine = line.split("\\s+");
                    char[] valueLine = splitedLine[1].toCharArray();

                    for(int j = 0; j < 6; j++){
                        switch (valueLine[j]){
                            case 'B': white[0] = ""+alphabet[j]+""+(iLine+1);
                                break;
                            case 'b': white[++pionBlanc] = ""+alphabet[j]+""+(iLine+1);
                            break;
                            case 'N': black[0] = ""+alphabet[j]+""+(iLine+1);
                                break;
                            case 'n': black[++pionNoir] = ""+alphabet[j]+""+(iLine+1); 
                                break;
                            default: break;
                        }
                        
                    }
                    iLine++;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void saveToFile(String fileName){
		String sauvegarde = "%\tABCDEF\n";
        char[][] board = lists_to_board();
        for(int i = 0; i < 6; i++){
            sauvegarde += "0"+ (i+1) +"\t";
            for(int j = 0; j < 6; j++){
                sauvegarde += board[i][j];
            }
			sauvegarde += "\t0" + (i+1) + "\n";
		}
		sauvegarde += "%\tABCDEF\n";

		try {

			FileWriter sauv = new FileWriter(fileName);
			sauv.write(sauvegarde);
			sauv.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public boolean isValidMove(String move, String player){
		
		/////////////////////////////// Pas forcement besoin d'enlever board des attributs (on s'en sert tout le temps)
		/////////////////////////////// Sens des liseres pas important?
		
		
		//Pour le placement en DEBUT de partie
        if(move.length() > 5){
        	String[] pions = move.split("/");
        	//Si le joueur est noir, il commence et choisit son bord
        	if (player == "noir") {
        		//Il peut choisir soit le bord haut ou bas
        		int i = get_i_from_string(pions[0]);
        		int j = get_j_from_string(pions[0]);
        		//On regarde si le joueur noir a decider de poser ses pions en haut ou en bas
        		if (i<=1) {
        			bord_noir = "haut";
        		}
        		else {
        			bord_noir = "bas";
        		}
        		//variable locale qui va servir a regarder si les pions se recouvrent
        		ArrayList<String> presence = new ArrayList<>();
        		//On verifie que tous les pions sont du meme cote et qu'ils ne sortent pas du plateau et qu'ils ne se recouvrent pas
    			for(String p : pions) {
    				//Si deux piece se recouvrent, renvoyer faux
    				if (presence.contains(p)){
    					return false;
    				}
    				presence.add(p);
    				i = get_i_from_string(p);
    				j = get_j_from_string(p);
    				if ( (bord_noir=="haut")&&((i<0)||(i>1)||(j<0)||(j>5)) ) {
    					return false;		
    				}
    				else if ( (bord_noir=="bas")&&((i<4)||(i>5)||(j<0)||(j>5)) ) {
    					return false;
    				}
    			}
        	}
        	//Si le joueur est blanc
        	else {
        		//variable locale qui va servir a regarder si les pions se recouvrent
        		ArrayList<String> presence = new ArrayList<>();
        		//On verifie que tous les pions sont du meme cote et qu'ils ne sortent pas du plateau et qu'ils ne se recouvrent pas
    			for(String p : pions) {
    				//Si deux piece se recouvrent, renvoyer faux
    				if (presence.contains(p)){
    					return false;
    				}
    				presence.add(p);
    				int i = get_i_from_string(p);
    				int j = get_j_from_string(p);
    				if ( (bord_noir=="bas")&&((i<0)||(i>1)||(j<0)||(j>5)) ) {
    					return false;		
    				}
    				else if ( (bord_noir=="haut")&&((i<4)||(i>5)||(j<0)||(j>5)) ) {
    					return false;
    				}
    			}        		
        	}        	
        }
		
        //Pour le deplacement en MILIEU de partie
        else {  
        	String[] possible_moves = possibleMoves(player);
        	for(int i=0; i<possible_moves.length;i++) {
        		if (possible_moves[i]==move) {
        			return true;
        		}
        	}
        	return false;
        	
        	/**
        	//On split le move
    		String[] change = move.split("-");
            String start = change[0];
            String end = change[1];
            
            //On recupere les indices correspondants a la case de depart et d'arrivee
            int start_i = get_i_from_string(start);
            int start_j = get_j_from_string(start);
            int end_i = get_i_from_string(end);
            int end_j = get_j_from_string(end);

            return true;
            **/
        }
        return false;
	}

    
	public String[] possibleMoves(String player){
		//Liste des differents coups possibles
		ArrayList<String> possible_moves = new ArrayList<>();
		//Les pions qu'on va regarder 
		String[] pions;
		if (player=="blanc") {
			pions = white;
		}
		else {
			pions = black;
		}

		//On recupere les pions deplacables
		ArrayList<String> pions_deplacables = new ArrayList<>();
		//Si le lisere est egal a 0, i.e. si on est en debut de partie, alors le joueur peut deplacer n'importe quel pion
		if (last_lisere==0) {
			for (int i=0; i<6; i++) {
				//On met dans une liste la position des pions
				pions_deplacables.add(pions[i]);
			}
		}
		else {
			for (int i=0; i<6; i++) {
	            int pion_i = get_i_from_string(pions[i]);
	            int pion_j = get_j_from_string(pions[i]);
	        	//On verifie le respect du lisere
                if (liserePlateau[pion_i][pion_j]==last_lisere){
    				pions_deplacables.add(pions[i]);
                }
			}
		}
		
		//Pour chaque pions deplacables, on regarde ses differentes cases atteignables
		for (String p : pions_deplacables) {
			//On met dans une HashMap la position du pion ainsi que une direction "nul" qui represente la direction de la ou on vient dans l'exploration des cases (donc nul au depart)
			HashMap<String,String> pion = new HashMap<>();
			pion.put(p, "nul");
			ArrayList<String> cases_atteignables = explore_adjacents_rec (pion, player, 0);
			//On traduit tout ca sous forme de coups potentiels
			for (String c : cases_atteignables) {
				String move = p+"-"+c;
				possible_moves.add(move);
			}
		}		
		
		//On convertit l'array en un tableau
		String[] possible_moves_tab = new String[possible_moves.size()];
		for (int i=0; i<possible_moves.size();i++) {
			possible_moves_tab[i]=possible_moves.get(i);
		}
		return possible_moves_tab;
	}
	
	public ArrayList<String> explore_adjacents_rec (HashMap<String,String> cases, String player, int n){
		//Si on a atteint le nombre de mouvements, on renvoie la liste des positions des cases atteignables
		if (n==last_lisere) {
			ArrayList<String> cases_atteignables = new ArrayList<>();
			for (String c: cases.keySet()) {
				cases_atteignables.add(c);
			}
			return cases_atteignables;
		}
		else {
			//Sinon, on explore une case plus loin
			return explore_adjacents_rec( explore_adjacents(cases, player), player, n++); 
		}
	}
	
	public HashMap<String,String> explore_adjacents (HashMap<String,String> cases, String player) {		
		//Tableau des differentes directions
		String[] directions = {"haut","bas","droite","gauche"};
		//Hashmap qui sera retourne
		HashMap<String,String> res = new HashMap<>();
		//On parcourt les cases a la frontiere
		for (String c : cases.keySet()) {
			int start_i = get_i_from_string(c);
			int start_j = get_j_from_string(c);
			for (String d : directions) {
				//Pour chaque case, on va explorer les cases adjacentes sauf celle de laquelle on vient
				if (d!=cases.get(c)) {
					if (d=="haut") {
						//Si on ne sort pas du tableau
						if (start_i-1>=0) {
							//Si la case n'est pas occupee, alors on l'ajoute dans le resultat
							if (!is_occupied(start_i-1,start_j,player)){
								String indice = String.valueOf(start_i-1 +1);
								String alpha = String.valueOf(alphabet[start_j]);
								String new_case = alpha+indice;
								res.put(new_case,"bas");
							}	
						}
					}
					if (d=="bas") {
						if (start_i+1<=5) {
							if (!is_occupied(start_i+1,start_j,player)){
								String indice = String.valueOf(start_i+1 +1);
								String alpha = String.valueOf(alphabet[start_j]);
								String new_case = alpha+indice;
								res.put(new_case,"haut");
							}	
						}
					}				
					if (d=="droite") {
						if (start_j+1<=5) {
							if (!is_occupied(start_i,start_j+1,player)){
								String indice = String.valueOf(start_i +1);
								String alpha = String.valueOf(alphabet[start_j+1]);
								String new_case = alpha+indice;
								res.put(new_case,"gauche");
							}	
						}
					}
					if (d=="gauche") {
						if (start_j-1>=0) {
							if (!is_occupied(start_i-1,start_j-1,player)){
								String indice = String.valueOf(start_i +1);
								String alpha = String.valueOf(alphabet[start_j-1]);
								String new_case = alpha+indice;
								res.put(new_case,"droite");
							}	
						}
					}
				}
			}
		}
		return res;
	}
	
	public boolean is_occupied (int i, int j, String player) {
		char[][] board = lists_to_board();
		if (player=="noir") {
			if ( (board[i][j]=='-')||(board[i][j]=='B') ) {
				return false;
			}
		}
		if (player=="blanc") {
			if ( (board[i][j]=='-')||(board[i][j]=='N') ) {
				return false;
			}
		}
		return true;
	}
	
	public void play(String move, String player){
		//pour le placement en debut de partie
		char[][] board = lists_to_board();
        if(move.length() > 5){ 
            String[] pions = move.split("/");

            if(player == "blanc"){
                for(int i = 0; i < 6; i++) {
                    white[i] = pions[i];
                }
            }
            else {
                for(int i = 0; i < 6; i++){
                    black[i] = pions[i];
                }
            }
        }
        //pour un deplacement normal
        else{
            String[] change = move.split("-");
            String start = change[0];
            String end = change[1];
            int end_i = get_i_from_string(end);
            int end_j = get_j_from_string(end);
            if(player == "blanc"){
            	//On regarde si la licorne adverse est sur la case d'arrivee
            	if (board[end_i][end_j]=='N') {
            		//Dans ce cas, elle est morte
            		black[0]="ZZ";
            	}
                int pion = 0;
                while(white[pion] != start){
                    pion++;
                }
                white[pion] = end;
            }
            else {
            	//On regarde si la licorne adverse est sur la case d'arrivee
            	if (board[end_i][end_j]=='B') {
            		//Dans ce cas, elle est morte
            		white[0]="ZZ";
            	}
                int pion = 0;
                while(black[pion] != start){
                    pion++;
                }
                black[pion] = end;
            }
            //On met a jour last_lisere
            last_lisere = liserePlateau[end_i][end_j];
        }
	}
	
	public boolean gameOver(){
		//On regarde si l'une des deux positions des licornes est �gale � ZZ
		return ( (white[0]=="ZZ")||(black[0]=="ZZ") );
	}
	
	public char[][] lists_to_board(){
		//Initialisation
		char[][] board = new char[6][6];
		for(int i=0; i<6; i++){
			for(int j=0; j<6; j++){
				board[i][j]='-';
			}
		}
		//Positionnement des licornes
		board[get_i_from_string(this.white[0])][get_j_from_string(this.white[0])] = 'B';
		board[get_i_from_string(this.black[0])][get_j_from_string(this.black[0])] = 'N';
		
		//Affectation des pions blancs
		for(int i=1; i<6; i++){
			board[get_i_from_string(this.white[i])][get_j_from_string(this.white[i])] = 'b';
			board[get_i_from_string(this.black[i])][get_j_from_string(this.black[i])] = 'n';
		}
		return board;
	}
	
	int get_j_from_string(String s){
		char j = s.charAt(0);
		for (int w=0; w<6;w++){
			if (alphabet[w]==j){
				return w;
			}
		}
		//si la lettre n'est pas entre A et F
		return -1;
	}
	
	int get_i_from_string(String s){
		char i = s.charAt(1);
		return (Character.getNumericValue(i)-1);
	}
	
	
	public static void main (String[] args){
		//Tests
	
		EscampeBoard eb = new EscampeBoard();
	    //Definition du chemin actuel
	    String projectDir = Paths.get(".").toAbsolutePath().normalize().toString();
	 
	    // Test setFromFile
	    eb.setFromFile(projectDir + "\\src\\data\\plateau1.txt");	    
	    
	    // Test saveToFile
	    eb.saveToFile(projectDir + "\\src\\data\\sauvegarde.txt");
	 
	    // Test isValideMove
        
	    // On cherche tous les moves
        String[] pm = eb.possibleMoves("blanc");
        for(String s : pm) {
            System.out.println(s);
        }
	}
}
