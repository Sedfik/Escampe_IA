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
	public static char[] alphabet = {'A','B','C','D','E','F'};
	public String[] white = new String[6];
	public String[] black = new String[6];
	public char[][] board = new char[6][6];
	
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
		return true;
	}
	
	public String[] possibleMoves(String player){
		return new String[0];
	}
	
	public void play(String move, String player){
        //TODO Jouer un coup
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
