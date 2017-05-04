package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {

	private static boolean DEBUG = false;

	private UndirectedGraph<String, DefaultEdge> graph;
	private int numeroLettere = 0;
	private WordDAO wordDAO;

	public Model() {
		wordDAO = new WordDAO();
	}

	public List<String> createGraph(int numeroLettere) {

		graph = new SimpleGraph<>(DefaultEdge.class);
		this.numeroLettere = numeroLettere;

		List<String> parole = wordDAO.getAllWordsFixedLength(numeroLettere);

		// Aggiungo tutti i vertici del grafo
		for (String parola : parole)
			graph.addVertex(parola);

		// Per ogni parola aggiungo un arco di collegamento con le parole
		// che differiscono per una sola lettera (stessa lunghezza)
		for (String parola : parole) {

			// Alternativa 1: uso il Database -- LENTO!
			// List<String> paroleSimili = wordDAO.getAllSimilarWords(parola, numeroLettere);

			// Alternativa 2: uso il mio algoritmo in Java
			List<String> paroleSimili = Utils.getAllSimilarWords(parole, parola, numeroLettere);

			if (DEBUG) {
				System.out.println("parola: " + parola);
				System.out.println(paroleSimili);
			}

			// Creo l'arco
			for (String parolaSimile : paroleSimili) {
				graph.addEdge(parola, parolaSimile);
			}
		}

		// Ritorno la lista dei vertici
		return parole;
	}

	public List<String> displayNeighbours(String parolaInserita) {

		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		List<String> parole = new ArrayList<String>();

		// Ottengo la lista dei vicini di un vertice
		parole.addAll(Graphs.neighborListOf(graph, parolaInserita));

		if (DEBUG) {
			System.out.println(graph.degreeOf(parolaInserita));
			System.out.println(parole);
		}

		// Ritorno la lista dei vicini
		return parole;
	}

	public String findMaxDegree() {

		int max = 0;
		String temp = null;

		for (String vertex : graph.vertexSet()) {
			if (graph.degreeOf(vertex) > max) {
				max = graph.degreeOf(vertex);
				temp = vertex;
			}
		}

		if (max != 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Max degree: %d from vertex: %s\n", max, temp));

			for (String v : Graphs.neighborListOf(graph, temp))
				sb.append(v + "\n");

			return sb.toString();
		}
		return "Non trovato.";
	}

}
