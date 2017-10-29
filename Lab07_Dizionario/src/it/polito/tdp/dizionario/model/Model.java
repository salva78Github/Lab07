package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

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
		
		for(String word : graph.vertexSet()){
			// Alternativa 1: uso il Database -- LENTO!
			//List<String> similarWords = dao.getAllSimilarWords(word);

			// Alternativa 2: uso il mio algoritmo in Java
			List<String> similarWords = Utils.getAllSimilarWords(vertexes, word);
			//System.out.println("<createGraph> similarWords size: " + similarWords.size());

			
			for(String sw : similarWords){
				if(!word.equals(sw)){
					graph.addEdge(word, sw);
				}	
			}
			
		}
		
		System.out.println("<createGraph> grafo: " + graph);

		
		return vertexes;
		
	}

	public List<String> displayNeighbours(String parolaInserita) throws DizionarioException{
		System.out.println("<displayNeighbours> parolaInserita: " + parolaInserita);

		if(graph.containsVertex(parolaInserita)){
			List<String> neighbours = Graphs.neighborListOf(graph, parolaInserita);
			System.out.println("<displayNeighbours> neighbours size: " + neighbours.size());
			return neighbours;
		}
		else{
			throw new InvalidVertexException("La parola " + parolaInserita + " non appartiene al grafo");
		}

	}

	public String findMaxDegree() throws DizionarioException {
		System.out.println("<findMaxDegree>");
		int maxDegree = 0;
		String vertexWithMaxDegree=null;
		
		for(String vertex : graph.vertexSet()){
			int degree = graph.degreeOf(vertex);
			if(degree>maxDegree){
				maxDegree=degree;
				vertexWithMaxDegree=vertex;
			}
		}
		System.out.println("<findMaxDegree> maxDegree: " + maxDegree);
		
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("Max degree: %d from vertex: %s\n", maxDegree, vertexWithMaxDegree));

		List<String> neighbours = displayNeighbours(vertexWithMaxDegree);
		
		for(String n : neighbours){
			sb.append(n).append("\n");
		}
		
		
		return sb.toString();
	}
}
