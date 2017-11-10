package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.dizionario.db.WordDAO;
import it.polito.tdp.dizionario.exception.DizionarioException;
import it.polito.tdp.dizionario.exception.InvalidVertexException;

public class Model {
	private static WordDAO dao = new WordDAO();
	UndirectedGraph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

	public List<String> createGraph(int numeroLettere) {

		System.out.println("<createGraph> numeroLettere: " + numeroLettere);

		graph = new SimpleGraph<>(DefaultEdge.class);

		List<String> vertexes = dao.getAllWordsFixedLength(numeroLettere);
		Graphs.addAllVertices(graph, vertexes);

		for (String word : graph.vertexSet()) {
			// Alternativa 1: uso il Database -- LENTO!
			// List<String> similarWords = dao.getAllSimilarWords(word);

			// Alternativa 2: uso il mio algoritmo in Java List<String>
			List<String> similarWords = Utils.getAllSimilarWords(vertexes, word);
			System.out.println("<createGraph> similarWords size: " + similarWords.size());

			for (String sw : similarWords) {
				if (!word.equals(sw)) {
					graph.addEdge(word, sw);
				}
			}

		}

		/*
		 * List<String> vertexes = new ArrayList<>(); vertexes.add("casa");
		 * vertexes.add("cara"); vertexes.add("case"); vertexes.add("casa");
		 * vertexes.add("caro"); vertexes.add("care"); vertexes.add("cure");
		 * vertexes.add("fila"); vertexes.add("file"); vertexes.add("pile");
		 * Graphs.addAllVertices(graph, vertexes); graph.addEdge("casa",
		 * "cara"); graph.addEdge("cara", "caro"); graph.addEdge("casa",
		 * "case"); graph.addEdge("cara", "care"); graph.addEdge("case",
		 * "care"); graph.addEdge("caro", "care"); graph.addEdge("care",
		 * "cure"); graph.addEdge("fila", "file"); graph.addEdge("file",
		 * "pile");
		 */
		System.out.println("<createGraph> grafo: " + graph);

		return vertexes;

	}

	public List<String> displayNeighbours(String parolaInserita) throws DizionarioException {
		System.out.println("<displayNeighbours> parolaInserita: " + parolaInserita);

		if (graph.containsVertex(parolaInserita)) {
			List<String> neighbours = Graphs.neighborListOf(graph, parolaInserita);
			System.out.println("<displayNeighbours> neighbours size: " + neighbours.size());
			return neighbours;
		} else {
			throw new InvalidVertexException("La parola " + parolaInserita + " non appartiene al grafo");
		}

	}

	/*
	 * VERSIONE LIBRERIA JGRAPHT
	 */
	public List<String> displayAllNeighbours(String vertex) throws DizionarioException {
		System.out.println("<displayAllNeighbours> vertex: " + vertex);

		if (graph.containsVertex(vertex)) {

			GraphIterator<String, DefaultEdge> dfv = new DepthFirstIterator<String, DefaultEdge>(graph, vertex);
			List<String> neighbours = new ArrayList<>();
			
			while (dfv.hasNext()) {
				neighbours.add(dfv.next());
			}

			neighbours.remove(vertex);

			System.out.println("<displayAllNeighbours> neighbours size: " + neighbours.size());
			return neighbours;
		} else {
			throw new InvalidVertexException("La parola " + vertex + " non appartiene al grafo");

		}

	}

	/*
	 * VERSIONE RICORSIVA
	 */
	public List<String> displayAllNeighboursRecursiveVersion(String vertex) throws DizionarioException {
		System.out.println("<displayAllNeighboursRecursiveVersion> vertex: " + vertex);

		if (graph.containsVertex(vertex)) {

			List<String> parziale = new ArrayList<>();
			List<String> neighbours = new ArrayList<>();
			parziale.add(vertex);

			RecursiveSolver rs = new RecursiveSolver(this.graph);
			rs.recursive(neighbours, parziale, 1);

			neighbours.remove(vertex);
			System.out.println("<displayAllNeighboursRecursiveVersion> neighbours size: " + neighbours.size());
			return neighbours;
		} else {
			throw new InvalidVertexException("La parola " + vertex + " non appartiene al grafo");

		}

	}

	/**
	 * La versione iterativa utilizza due liste, quella dei nodi Visitati e
	 * quella dei nodi daVisitare. Si inizia inserendo la parola scelta nella
	 * lista daVisitare. L’algoritmo continua fino a quando la lista dei nodi
	 * daVisitare non si svuota. Ad ogni passo si estrae un nodo dalla lista
	 * daVisitare e si inseriscono tutti i nodi vicini a quello estratto (a meno
	 * di quelli già visitati) nella lista daVisitare. Infine, il nodo estratto
	 * viene inserito nella lista dei Visitati.
	 * 
	 * @param vertex
	 * @return
	 * @throws DizionarioException
	 */
	/*
	 * VERSIONE ITERATIVA
	 */

	public List<String> displayAllNeighboursIterativeVersion(String vertex) throws DizionarioException {
		System.out.println("<displayAllNeighboursIterativeVersion> vertex: " + vertex);

		if (graph.containsVertex(vertex)) {

			List<String> visitati = new ArrayList<>();
			List<String> neighbours = new ArrayList<>();
			List<String> daVisitare = new ArrayList<>();
			daVisitare.add(vertex);

			while (daVisitare.size() > 0) {

				String nodo = daVisitare.get(0);
				daVisitare.remove(nodo);
				neighbours = Graphs.neighborListOf(graph, nodo);
				for (String n : neighbours) {
					if (!visitati.contains(n) && !daVisitare.contains(n)) {
						daVisitare.add(n);
					}
				}
				visitati.add(nodo);
			}
			visitati.remove(vertex);
			System.out.println("<displayAllNeighboursIterativeVersion> neighbours size: " + visitati.size());
			return visitati;

		} else {
			throw new InvalidVertexException("La parola " + vertex + " non appartiene al grafo");

		}

	}

	public String findMaxDegree() throws DizionarioException {
		System.out.println("<findMaxDegree>");
		int maxDegree = 0;
		String vertexWithMaxDegree = null;

		for (String vertex : graph.vertexSet()) {
			int degree = graph.degreeOf(vertex);
			if (degree > maxDegree) {
				maxDegree = degree;
				vertexWithMaxDegree = vertex;
			}
		}
		System.out.println("<findMaxDegree> maxDegree: " + maxDegree);

		StringBuffer sb = new StringBuffer();
		sb.append(String.format("Max degree: %d from vertex: %s\n", maxDegree, vertexWithMaxDegree));

		List<String> neighbours = displayNeighbours(vertexWithMaxDegree);

		for (String n : neighbours) {
			sb.append(n).append("\n");
		}

		return sb.toString();
	}

}
