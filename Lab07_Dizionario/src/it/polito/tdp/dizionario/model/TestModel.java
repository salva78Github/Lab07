package it.polito.tdp.dizionario.model;

import java.util.List;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.exception.DizionarioException;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println(
				String.format("**Grafo creato** - Trovate #%d parole di lunghezza 4\n", model.createGraph(4).size()));

		List<String> vicini;
		List<String> viciniTrovatiAmpiezza;
		try {
			vicini = model.displayNeighbours("casa");
			System.out.println("Vicini di casa: " + vicini);

			System.out.println();

			System.out.println("Cerco il vertice con grado massimo...");
			System.out.println(model.findMaxDegree());

			
			viciniTrovatiAmpiezza = model.displayAllNeighbours("casa");
			System.out.println("Vicini di casa trovati in ampiezza: " + viciniTrovatiAmpiezza);
			viciniTrovatiAmpiezza.clear();
			viciniTrovatiAmpiezza = model.displayAllNeighbours("fila");
			System.out.println("Vicini di fila trovati in ampiezza: " + viciniTrovatiAmpiezza);

			viciniTrovatiAmpiezza.clear();
			viciniTrovatiAmpiezza = model.displayAllNeighboursRecursiveVersion("casa");
			System.out.println("Vicini di casa trovati in ampiezza: " + viciniTrovatiAmpiezza);
			viciniTrovatiAmpiezza.clear();
			viciniTrovatiAmpiezza = model.displayAllNeighboursRecursiveVersion("fila");
			System.out.println("Vicini di fila trovati in ampiezza: " + viciniTrovatiAmpiezza);
			
			viciniTrovatiAmpiezza.clear();
			viciniTrovatiAmpiezza = model.displayAllNeighboursIterativeVersion("casa");
			System.out.println("Vicini di casa trovati in ampiezza: " + viciniTrovatiAmpiezza);
			viciniTrovatiAmpiezza.clear();
			viciniTrovatiAmpiezza = model.displayAllNeighboursIterativeVersion("fila");
			System.out.println("Vicini di fila trovati in ampiezza: " + viciniTrovatiAmpiezza);
			
			
			
			
		} catch (DizionarioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errore: " + e.getMessage());

		}

	}

}
