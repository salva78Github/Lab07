package it.polito.tdp.dizionario.model;

import java.util.List;

import it.polito.tdp.dizionario.exception.DizionarioException;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		System.out.println(String.format("**Grafo creato** - Trovate #%d parole di lunghezza 4\n",  model.createGraph(4).size()));
		
		List<String> vicini;
		try {
			vicini = model.displayNeighbours("casa");
			System.out.println("Vicini di casa: " + vicini);
		
		System.out.println();
		
		System.out.println("Cerco il vertice con grado massimo...");
		System.out.println(model.findMaxDegree());
		} catch (DizionarioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errore: " + e.getMessage());
			
		}
	}

}
