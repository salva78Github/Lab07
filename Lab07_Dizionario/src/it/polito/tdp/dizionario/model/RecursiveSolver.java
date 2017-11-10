package it.polito.tdp.dizionario.model;

import java.util.List;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class RecursiveSolver {

	private final UndirectedGraph<String, DefaultEdge> graph;
	private final Set<String> vertici;

	/**
	 * @param graph
	 */
	public RecursiveSolver(UndirectedGraph<String, DefaultEdge> graph) {
		this.graph = graph;
		this.vertici = graph.vertexSet();
	}

	public /*List<String>*/void recursive(List<String> neighbours, List<String> parziale, int level) {

		// A
		
		if (parziale.size() >= neighbours.size()) {
			neighbours.clear();
			neighbours.addAll(parziale);
		}
		

		
		for (String vertice : this.vertici) {
			if (check(parziale, vertice)) {
				// genera nuova soluzione
				parziale.add(vertice);
				/*List<String> tree =*/ recursive(neighbours, parziale, level + 1);
				/*
				if (tree != null) {
					return tree;
				}
				*/

				// D
				// backtracking
				parziale.remove(vertice);
			}

		}

	}

	private boolean check(List<String> parziale, String vertice) {
		if (!parziale.contains(vertice)) {
			for (String v : parziale) {
				if (graph.containsEdge(v, vertice)) {
					return true;
				}
			}
		}

		return false;
	}
}
