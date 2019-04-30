package escampe_jeu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class MainTest {
	
    public static void main(String[] args) {
        // Nouveau plateau
        EscampeBoard plateau = new EscampeBoard();

        System.out.println(plateau);

        //Définition du chemin actuel
        String projectDir = Paths.get(".").toAbsolutePath().normalize().toString();

        // Test setFromFile
        plateau.setFromFile(projectDir + "\\src\\data\\plateau1.txt");

        System.out.println(plateau);

        // Test saveToFile
        plateau.saveToFile(projectDir + "\\src\\data\\sauvegarde.txt");

        // Test isValideMove
        System.out.println("isValideMove: "+plateau.isValidMove("A1-C2", plateau.jBlanc.toString()));

        //On cherche un move
        plateau.findMoves("C2", "blanc");

        // On cherche tous les moves
        plateau.possiblesMoves("blanc");

        // On joue un move
        plateau.play("A1-A2","blanc");
        System.out.println(plateau);

        // On cherche tous les moves
        plateau.possiblesMoves("noir");

        // On joue un move
        plateau.play("B5-A3","blanc");
        System.out.println(plateau);




        // On regarde si on a gagné
        System.out.println("Fin de partie: "+plateau.gameOver());
    }
}