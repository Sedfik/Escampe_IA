package escampe_jeu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EscampeBoard {
	
	//Attributs
	public final static char[] alphabet = {'A','B','C','D','E','F'};
	
	
	////////////////////////////// On est sur de prendre le lisere dans ce sens ? ca se trouve, si on fait liserePlateau[0][0] sur celui du prof, ca donnera pas la meme
	
    public final static int[][] liserePlateau =
        {		
        		{1,2,2,3,1,2},
                {3,1,3,1,3,2},
                {2,3,1,2,1,3},
                {2,1,3,2,3,1},
                {1,3,1,3,1,2},
                {3,2,2,1,3,2}
        };
	private String[] white = new String[6];
	private String[] black = new String[6];
	private char[][] board = new char[6][6];
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
		
		////////////////////////////// NON TRAITE POUR LE CAS DU MOVE C6/A6/B5/D5/.... 
		
		
		//recuperer le lisere du dernier joueur
		//regarder si le lisere de la case start correspond bien au lisere du dernier joueur
		
		//TRAITE :
		//on regarde bien qu'il y a un pion sur la case depart
		//ne pas sortir du tableau
		//regarder si ya qq (si c'est une licorne ennemie, on a le droit)
		//regarder le liseré
		//regarder si la case est bien possible (faire le bail avec les valeurs absolues)
		
		//interdit de passer par une case deja occupee
		//interdit de passer deux fois par une meme case
		
		
		//On split le move
		String[] change = move.split("-");
        String start = change[0];
        String end = change[1];
        
        //On recupere les indices correspondants a la case de depart et d'arrivee
        int start_i = get_i_from_string(start);
        int start_j = get_j_from_string(start);
        int end_i = get_i_from_string(end);
        int end_j = get_j_from_string(end);
        
        //On verifie que le joueur a bien un pion sur la case depart
        ////////////////////On pourra le mettre dans une fonction a part
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
        ////////////////////On pourra le mettre dans une fonction a part (je crois que ya une autre fonction qui utilise ce procede)
        boolean v2 = true;
        for(int i=1; i<6; i++) {
        	//////////////////////////// Si le pion a le droit de revenir sur sa case depart, il faut modifier un bail
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
        else // Liseré == 1 || 2
            return (nbMouvement == start_lis);
        
        /** Ca voulait dire quoi ca ?
        if( (caseDebut.toString() != ""+Character.toLowerCase(nomJoueur)
        || caseDebut.toString() != ""+Character.toUpperCase(nomJoueur)) && (caseArrive.toString() != "-")){
         	return false;
        }
		**/ 
	}

    
	public String[] possibleMoves(String player){
		///////////////////////////////Allocation dynamique, je passe par un array
		ArrayList<String> possible_moves_array = new ArrayList<>();
		String[] pions;
		if (player=="blanc") {
			pions = white;
		}
		else {
			pions = black;
		}
		for(String s: pions) {
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
		
		String[] possible_moves = new String[possible_moves_array.size()];
		for (int i=0; i<possible_moves_array.size();i++) {
			possible_moves[i]=possible_moves_array.get(i);
		}
		return possible_moves;
	}
	
	public void play(String move, String player){
		//////////////////////////////Il n 'y a pas le cas ou on mange la licorne
		//pour le placement en debut de partie
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

            if(player == "blanc"){
                int pion = 0;
                while(white[pion] != start){
                    pion++;
                }
                white[pion] = end;
            }
            else {
                int pion = 0;
                while(black[pion] != start){
                    pion++;
                }
                black[pion] = end;
            }
        }
	}
	
	public boolean gameOver(){
		//On regarde si l'une des deux positions des licornes est égale à ZZ
		return ( (white[0]=="ZZ")|(black[0]=="ZZ") );
	}
	
	public void lists_to_board(){
		//Initialisation
		for(int i=0; i<6; i++){
			for(int j=0; j<6; j++){
				board[i][j]='r';
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
