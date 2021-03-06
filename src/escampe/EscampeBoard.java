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

import modeles.Etat;


public class EscampeBoard implements Etat {
	
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

    
	private String[] white;
	private String[] black;
	private int last_lisere;
	private String bord_noir;
	
	public String[] getWhite() {
		return this.white;
	}
	
	public String[] getBlack() {
		return this.black;
	}
	
	public int getLastLisere() {
		return this.last_lisere;
	}
	
	public int getLisereAt(String c) {
		int i = get_i_from_string(c);
		int j = get_j_from_string(c);
		return liserePlateau[i][j];
	}
	
	public EscampeBoard (String[] w, String[] b, int last_lisere){
		this.white = new String[6];
		this.black = new String[6];
		this.white = w;
		this.black = b;
		this.last_lisere = last_lisere;
	}
	
    public EscampeBoard() {
        // TODO Auto-generated constructor stub
    	this.white = new String[6];
    	this.black = new String[6];
    	last_lisere = 0;
    }
	

    
	//Methodes demandees
	public void setFromFile(String fileName){
		String projectDir = Paths.get(".").toAbsolutePath().normalize().toString();
		String filePath = projectDir + fileName;
		Path path = Paths.get(filePath);
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
		String projectDir = Paths.get(".").toAbsolutePath().normalize().toString();
		String filePath = projectDir + fileName;
		
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

			FileWriter sauv = new FileWriter(filePath);
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
        	if (player.contains("noir")) {
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
    				if ( (bord_noir.contains(("haut")))&&((i<0)||(i>1)||(j<0)||(j>5)) ) {
    					return false;		
    				}
    				else if ( (bord_noir.contains("bas"))&&((i<4)||(i>5)||(j<0)||(j>5)) ) {
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

	 // TODO : Il faut gérer le cas où le joueur ne peut pas jouer
	public String[] possibleMoves(String player){
		//Les pions qu'on va regarder 
		String[] pions;
		if (player.contains("blanc")) {
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
		//ArrayList des differents coups possibles qu'on va remplir par la suite
		ArrayList<String> possible_moves = new ArrayList<>();
		//Pour chaque pions deplacables, on regarde ses differentes cases atteignables
		for (String p : pions_deplacables) {
			
			
			//System.out.println("NEW PION "+p);
			
			
			//On met dans une ArrayList la position du pion ainsi que une direction "nul" qui represente la direction de la ou on vient dans l'exploration des cases (donc nul au depart)
			ArrayList<String> pion = new ArrayList<>();
			//On distingue les licornes aux paladins
			if (p.contains(pions[0])) {
				pion.add(p+"/l/nul");
			}
			else {
				pion.add(p+"/p/nul");
			}
			ArrayList<String> cases_atteignables = explore_adjacents_rec (pion, player, 0, liserePlateau[get_i_from_string(p)][get_j_from_string(p)]);
			//On recupere les coups possibles
			for (String c : cases_atteignables) {
				String move = p+"-"+c.split("/")[0];
				//On ajoute pas les mouvements deja presents
				if (!possible_moves.contains(move)) {
					possible_moves.add(move);
				}
			}
		}		
		
		//On convertit l'array en un tableau
		String[] possible_moves_tab = new String[possible_moves.size()];
		for (int i=0; i<possible_moves.size();i++) {
			possible_moves_tab[i]=possible_moves.get(i);
		}
		
		
		//System.out.println("Possible moves number :" + possible_moves_tab.length);
		return possible_moves_tab;
		
		
	}
	
	public ArrayList<String> explore_adjacents_rec (ArrayList<String> cases, String player, int n, int lisere){
		//Si on a atteint le nombre de mouvements, on renvoie la liste des positions des cases atteignables
		if (n==lisere) {
			ArrayList<String> cases_atteignables = new ArrayList<>();
			for (String c: cases) {
				cases_atteignables.add(c.split("/")[0]);
			}
			//System.out.println("finish");
			return cases_atteignables;
		}
		else {
			//Sinon, on explore une case plus loin
			return explore_adjacents_rec( explore_adjacents(cases, player), player, n+1, lisere); 
		}
	}
	
	public ArrayList<String> explore_adjacents (ArrayList<String> cases, String player) {		
		//Tableau des differentes directions
		String[] directions = {"haut","bas","droite","gauche"};
		//ArrayList qui sera retourne
		ArrayList<String> res = new ArrayList<>();
		//On parcourt les cases a la frontiere
		for (String c : cases) {
			//On decompose chaque case de la liste en ses differentes composantes (position,type du pion,direction par laquelle il vient)
			String[] composantes = c.split("/");
			String pos = composantes[0];
			String p_type = composantes[1];
			String come_from = composantes[2];
			int start_i = get_i_from_string(pos);
			int start_j = get_j_from_string(pos);
			for (String d : directions) {
				//Pour chaque case, on va explorer les cases adjacentes sauf celle de laquelle on vient
				if (d!=come_from) {
					if (d.contains("haut")) {
						//Si on ne sort pas du tableau
						if (start_i-1>=0) {
							//Si la case n'est pas occupee, alors on l'ajoute dans le resultat
							if (!is_occupied(start_i-1,start_j,p_type,player)){
								String indice = String.valueOf(start_i-1 +1);//+1 car c'est un indice
								String alpha = String.valueOf(alphabet[start_j]);
								String new_case = alpha+indice;
								res.add(new_case+"/"+p_type+"/bas");
							}	
						}
					}
					if (d.contains("bas")) {
						if (start_i+1<=5) {
							if (!is_occupied(start_i+1,start_j,p_type,player)){
								String indice = String.valueOf(start_i+1 +1);
								String alpha = String.valueOf(alphabet[start_j]);
								String new_case = alpha+indice;
								res.add(new_case+"/"+p_type+"/haut");
							}	
						}
					}				
					if (d.contains("droite")) {
						if (start_j+1<=5) {
							if (!is_occupied(start_i,start_j+1,p_type,player)){
								String indice = String.valueOf(start_i +1);
								String alpha = String.valueOf(alphabet[start_j+1]);
								String new_case = alpha+indice;
								res.add(new_case+"/"+p_type+"/gauche");
							}	
						}
					}
					if (d.contains("gauche")) {
						if (start_j-1>=0) {
							if (!is_occupied(start_i,start_j-1,p_type,player)){
								String indice = String.valueOf(start_i +1);
								String alpha = String.valueOf(alphabet[start_j-1]);
								String new_case = alpha+indice;
								res.add(new_case+"/"+p_type+"/droite");
							}	
						}
					}
				}
			}
		}
		return res;
	}
	
	
	//Fonction qui regarde si la case est occupee en fonction d'un type de pion et d'un joueur
	public boolean is_occupied (int i, int j, String p_type, String player) {
		char[][] board = lists_to_board();
		if (player.contains("noir")) {
			if ( (board[i][j]=='-')) {
				return false;
			}
			//Si c'est un paladin, il peut prendre la licorne adverse
			if (p_type.contains("p")&&(board[i][j]=='B')) {
				return false;
			}
		}
		if (player.contains("blanc")) {
			if ( (board[i][j]=='-')) {
				return false;
			}
			if (p_type.contains("p")&&(board[i][j]=='N')) {
				return false;
			}
		}
		return true;
	}
	

	public void play(String move, String player){
		//pour le placement en debut de partie
        if(move.length() > 5){ 
            String[] pions = move.split("/");
            
            if(player == "blanc"){
                    white = pions;
            }
            else {
                    black = pions;
            }
        }
        //pour un deplacement normal
        else{
    		char[][] board = lists_to_board();
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
                while(!white[pion].contentEquals(start)){
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
                while(!black[pion].contentEquals(start)){
                	pion++;               
                }
                black[pion] = end;
            }
            //On met a jour last_lisere
            last_lisere = liserePlateau[end_i][end_j];
        }
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
	
	
	public boolean gameOver(){
		//On regarde si l'une des deux positions des licornes est �gale � ZZ
		return ( (white[0]=="ZZ")||(black[0]=="ZZ") );
	}
	
	public static int get_j_from_string(String s){
		char j = s.charAt(0);
		for (int w=0; w<6;w++){
			if (alphabet[w]==j){
				return w;
			}
		}
		//si la lettre n'est pas entre A et F
		return -1;
	}
	
	public static int get_i_from_string(String s){
		char i = s.charAt(1);
		return (Character.getNumericValue(i)-1);
	}
	
	public void print_white () {
		for (int i=0; i<white.length; i++) {
			System.out.print(white[i]+";");
		}
		System.out.println("");
	}
	
	public void print_black () {
		for (int i=0; i<black.length; i++) {
			System.out.print(black[i]+";");
		}
		System.out.println("");
	}
	
	
	public static void main (String[] args){
		
		String s = "";
		String[] ss = s.split("/");
		
		System.err.println(ss.length );
		/*//Tests
	
		EscampeBoard eb = new EscampeBoard();
	    //Definition du chemin actuel
	    String projectDir = Paths.get(".").toAbsolutePath().normalize().toString();
	 
	    // Test setFromFile
	    eb.setFromFile("\\src\\data\\plateau1.txt");	    
	    
	    // Test saveToFile
	    eb.saveToFile("\\src\\data\\sauvegarde.txt");
	    
	    // Test isValideMove

        System.out.println(eb.isValidMove("A1-A2", "blanc"));
	    // On cherche tous les moves
        String[] pm = eb.possibleMoves("blanc");

        for(String s : pm) {
            System.out.println(s);
        }*/
	}
	
	
	/***********************************************************************************Fonctions necessaires pour la recherche de chemin **********************************************************************/
	
	
	//Fonction qui servira pour enumerer les successeurs lors de la recherche de chemin
		public void simulate_play (String move, String[] w, String[] b, String player, int lastLisere) {
			//pour le placement en debut de partie
	        if(move.length() > 5){ 
	            String[] pions = move.split("/");
	            
	            if(player.contentEquals("blanc")){
	                    w = pions;
	            }
	            else {
	                    b = pions;
	            }
	        }
	        //pour un deplacement normal
	        else{
	    		char[][] board = given_lists_to_board(w,b);
	            String[] change = move.split("-");
	            String start = change[0];
	            String end = change[1];
	            int end_i = get_i_from_string(end);
	            int end_j = get_j_from_string(end);
	            if(player.contentEquals("blanc")){
	            	//On regarde si la licorne adverse est sur la case d'arrivee
	            	if (board[end_i][end_j]=='N') {
	            		//Dans ce cas, elle est morte
	            		b[0]="ZZ";
	            	}
	                int pion = 0;
	                while(!w[pion].equals(start)){
	                    pion++;
	                }
	                w[pion] = end;
	            }
	            else {
	            	//On regarde si la licorne adverse est sur la case d'arrivee
	            	if (board[end_i][end_j]=='B') {
	            		//Dans ce cas, elle est morte
	            		w[0]="ZZ";
	            	}
	                int pion = 0;
	                while(!b[pion].contentEquals(start)){
	                	pion++;               
	                }
	                b[pion] = end;
	            }
	        }
		}
		
		
		//fonction qui renvoie un plateau 6*6 en fonction d'un etat de jeu passé en parametres
		public char[][] given_lists_to_board(String[] w, String[] b){
			//Initialisation
			char[][] board = new char[6][6];
			for(int i=0; i<6; i++){
				for(int j=0; j<6; j++){
					board[i][j]='-';
				}
			}
			//Positionnement des licornes
			board[get_i_from_string(w[0])][get_j_from_string(w[0])] = 'B';
			board[get_i_from_string(b[0])][get_j_from_string(b[0])] = 'N';
			
			//Affectation des pions blancs
			for(int i=1; i<6; i++){
				board[get_i_from_string(w[i])][get_j_from_string(w[i])] = 'b';
				board[get_i_from_string(b[i])][get_j_from_string(b[i])] = 'n';
			}
			return board;
		}
		
}
