package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileParser {
	/**
	 * liste des ligne du fichier
	 */
	List<String>	allLine	= new ArrayList<String>();

	/**
	 * récupére chaque ligne du fichier passé en paramétre
	 * 
	 * @param file
	 *            fichier à parser
	 */
	FileParser(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				allLine.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getAllLine() {
		return allLine;
	}

	/**
	 * @return retourne une liste de toute les lignes du fichier découpé sous
	 *         forme de tableau : 1er colone = numéro de la salle d'origine 2eme
	 *         colone = direction 3éme colone = numéro de la salle d'arrivé
	 */
	public List<String[]> parseLines() {
		List<String[]> parsedLines = new ArrayList<>();
		String[] tmpLine;
		for (String line : allLine) {
			tmpLine = line.split(" ");
			parsedLines.add(tmpLine);
		}
		return parsedLines;
	}

}
