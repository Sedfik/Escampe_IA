package escampe_jeu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class EscampeBoard {
	
	//Attributs
	public final static char[] alphabet = {'A','B','C','D','E','F'};
	
		
	//liserePlateau[0][0] depend du sens qu'on a recopie le tableau
    public final static int[][] liserePlateau =
        {
            	{3,1,2,2,3,1},
            	{2,3,1,3,1,2},
            	{2,1,3,1,3,2},
            	{1,3,2,2,1,3},
            	{3,1,3,1,3,1},
            	{2,2,1,3,2,2}
        };
    //Lettres en i
    //Chiffre en j

    
	private String[] white = new String[6];
	private String[] black = new String[6];
	private int last_lisere = 0;
	private String bord_noir;
	
	public EscampeBoard (String[] w, String[] b){
		this.white = w;
		this.black = b;
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
                            case 'B': white[0] = ""+iLine+""+j;
                                break;
                            case 'b': white[++pionBlanc] = ""+iLine+""+j;
                                break;
                            case 'N': black[0] = ""+iLine+""+j;
                                break;
                            case 'n': black[++pionNoir] = ""+iLine+""+j;
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
		String[][] board = new String[6][6];
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
		
		////////////////////////////////Ya certains test qui ne sont pas forcement necessaires ? (test si ca sort du tableau. Si on sait qu'on ne va pas donner des cases qui sortent du tableau, pas besoin de test)
		/////////////////////////////// Le bail qui regarde si la case d'arrivee a un pion, deja fait dans possiblemoves
		
		///////////////////////////////Pas forcement besoin d'enlever board des attributs (on s'en sert tout le temps)
		
		//Pour le placement en debut de partie
        if(move.length() > 5){
        	String[] pions = move.split("/");
        	//Si le joueur est noir, il commence et choisit son bord
        	if (player == "noir") {
        		//Il peut choisir soit le bord haut ou bas
        		int i = get_i_from_string(pions[0]);
        		int j = get_j_from_string(pions[1]);
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
		
        //Pour le deplacement en milieu de partie
        else {        	
        	//On split le move
    		String[] change = move.split("-");
            String start = change[0];
            String end = change[1];
            
            //On recupere les indices correspondants a la case de depart et d'arrivee
            int start_i = get_i_from_string(start);
            int start_j = get_j_from_string(start);
            int end_i = get_i_from_string(end);
            int end_j = get_j_from_string(end);
            
        	//On verifie le respect du lisere
            if (last_lisere!=0) { //last_lisere egal a 0 en debut de partie pour que le premier joueur puisse deplacer le pion de son choix
                if (liserePlateau[start_i][start_j]!=last_lisere){
                	return false;
                }
            }
            
            //On verifie que le joueur a bien un pion sur la case depart
            boolean v = false;
            if(player == "blanc") {
            	for (int i=0; i<6; i++) {
            		if (white[i]==start) {
            			v = true;
            		}
            	}
            }
            else {
            	for (int i=0; i<6; i++) {
            		if (black[i]==start) {
            			v = true;
            		}
            	}
            }
            if (!v){
            	return false;
            }
            
            //On verifie que la case d'arrivee ne sort pas du tableau
            if ((end_i<0)|(end_i>5)|(end_j<0)|(end_j>5)) {
            	return false;
            }
            
            //On regarde s'il existe deja un pion autre que la licorne adverse sur la case d'arrivee
            boolean v2 = true;
            for(int i=1; i<6; i++) {
            	if ((white[i]==end)|(black[i]==end)){
            		v2 = false;
            	}
            }
            //un pion blanc ne peut pas aller sur la case occupee par la licorne amie
            if (player == "blanc") {
            	if (white[0]==end) {
            		v2 = false;
            	}
            }
            else {
            	if (black[0]==end) {
            		v2 = false;
            	}
            }
            //On retourne faux si la case est occupee par un autre pion que la licorne ennemie
            if (!v2) {
            	return false;
            }
            
            //On recupere le lisere de la case depart
            int start_lis = liserePlateau[start_i][start_j];
            //Puis on verifie que le deplacement est bien possible avec ce nb de mouvement
            
            //////////////////////////////////Rajouter : interdit de passer 2 fois par la meme case ? , interdit de passer par une case occupee ? 
            int nbMouvement = Math.abs(start_i - end_i) + Math.abs(start_j-end_j);
            if(start_lis == 3){
                return ( nbMouvement== 1 || nbMouvement == 3);
            }
            else // Lisere == 1 || 2
                return (nbMouvement == start_lis);
        }
        return false;
	}

    
	public String[] possibleMoves(String player){
		ArrayList<String> possible_moves_array = new ArrayList<>();
		
		String[] pions;
		if (player=="blanc") {
			pions = white;
		}
		else {
			pions = black;
		}
		
		
		//On test toutes les combinaisons de coups possibles et on garde celles qui sont valides
		
		for(String s: pions) {
			////////////////////////On peut regarder juste les cases a -3;+3 pour optimiser (sinon on regarde les cases atteignables et on les fait passer par la fonction)
			for (char i : alphabet) {
				for (int j=0; j<6; j++) {
					String end = String.valueOf(i)+String.valueOf(j);
					String move = s+"-"+end;
					if (isValidMove(move, player)) {
						possible_moves_array.add(move);
					}
				}
			}
		}
		
		
		
		
		
		//On regarde les cases atteignables :
		//On recupere le lisere
		//On regarde les pions possibles
		//On regarde les cases atteignables grace a une fonction recursive.
		//on fait passer tout ca par isValidMove
		//Pas besoin du test de lisere dans isValidMove du coup
		
		
		
		//On convertit l'array en un tableau
		String[] possible_moves = new String[possible_moves_array.size()];
		for (int i=0; i<possible_moves_array.size();i++) {
			possible_moves[i]=possible_moves_array.get(i);
		}
		return possible_moves;
	}
	
	public ArrayList<Pair<String,String>> explore_sides (String case_depart, String venu_de, String player) {
		////////////////////////////Il ne faut pas que ca sorte du tableau
		char[][] board = lists_to_board();
		String[] directions = {"haut","bas","droite","gauche"};
		ArrayList<Pair<String,String>> res = new ArrayList<>();
		int start_i = get_i_from_string(case_depart);
		int start_j = get_j_from_string(case_depart);
		for (String d : directions) {
			if (d!=venu_de) {
				if (d=="haut") {
					if (!is_occupied(start_i-1,start_j,player)){
						String alpha = String.valueOf(alphabet[start_i-1]);
						String indice = String.valueOf(start_j);
						String new_case = alpha+indice;
						res.add(new Pair<>(new_case,"bas"));
					}
				}
				if (d=="bas") {
					if (!is_occupied(start_i+1,start_j,player)){
						String alpha = String.valueOf(alphabet[start_i+1]);
						String indice = String.valueOf(start_j);
						String new_case = alpha+indice;
						res.add(new Pair<>(new_case,"haut"));
					}
				}				
				if (d=="droite") {
					if (!is_occupied(start_i,start_j+1,player)){
						String alpha = String.valueOf(alphabet[start_i]);
						String indice = String.valueOf(start_j+1);
						String new_case = alpha+indice;
						res.add(new Pair<>(new_case,"gauche"));
					}
				}
				if (d=="gauche") {
					if (!is_occupied(start_i-1,start_j-1,player)){
						String alpha = String.valueOf(alphabet[start_i]);
						String indice = String.valueOf(start_j-1);
						String new_case = alpha+indice;
						res.add(new Pair<>(new_case,"droite"));
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
		//On regarde si l'une des deux positions des licornes est égale à ZZ
		return ( (white[0]=="ZZ")|(black[0]=="ZZ") );
	}
	
	public char[][] lists_to_board(){
		char[][] board = new char[6][6];
		//Initialisation
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
			board[get_i_from_string(this.white[0])][get_j_from_string(this.white[0])] = 'b';
			board[get_i_from_string(this.black[0])][get_j_from_string(this.black[0])] = 'n';
		}
		return board;
	}
	
	int get_i_from_string(String s){
		char i = s.charAt(0);
		for (int w=0; w<6;w++){
			if (alphabet[w]==i){
				return w;
			}
		}
		//si la lettre n'est pas entre A et F
		return -1;
	}
	
	int get_j_from_string(String s){
		char j = s.charAt(1);
		return Character.getNumericValue(j);
	}
	
	
	public static void main (String[] args){
		//Tests
		/**
		System.out.println(alphabet[0]);
		ArrayList<Str_Int_couples> white = new ArrayList<>();
		ArrayList<Str_Int_couples> black = new ArrayList<>();	
		white.add(new Str_Int_couples("A",1));
		black.add(new Str_Int_couples("B",1));
		EscampeBoard b = new EscampeBoard(white,black);
		b.saveToFile("yessai");
	}
	**/
	
	}
}
