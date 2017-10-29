package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static List<String> getAllSimilarWords(List<String> vertexes, String word) {
		List<String> similarWords = new ArrayList<String>();
		int count;
		for (String v : vertexes) {
			if (!v.equals(word)) {
				count = 0;
				for (int i = 0; i < word.length(); i++) {
					if (v.charAt(i) != word.charAt(i)) {
						count++;
					}
				}
				if (count == 1) {
					similarWords.add(v);
				}
			}

		}

		return similarWords;
	}

}
