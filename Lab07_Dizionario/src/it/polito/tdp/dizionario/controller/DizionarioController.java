package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.exception.DizionarioException;
import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextArea txtResult;
	@FXML
	private TextField inputNumeroLettere;
	@FXML
	private TextField inputParola;
	@FXML
	private Button btnGeneraGrafo;
	@FXML
	private Button btnTrovaVicini;
	@FXML
	private Button btnTrovaGradoMax;
	@FXML
	private Button btnTrovaTuttiVicini;

	private Model model;

	@FXML
	void doReset(ActionEvent event) {
		txtResult.setText("Reset!");
	}

	@FXML
	void doGeneraGrafo(ActionEvent event) {

		try {
			String numeroLettereToString = inputNumeroLettere.getText();
			System.out.println("<doGeneraGrafo> numeroLettereToString: " + numeroLettereToString);
			if (numeroLettereToString == null || "".equals(numeroLettereToString.trim())) {
				txtResult.setText("Inserire un numero di lettere.");
			} else {

				int numeroLettere = 0;
				try {
					numeroLettere = Integer.valueOf(numeroLettereToString);
					List<String> verticiGrafo = this.model.createGraph(numeroLettere);

					if (verticiGrafo != null) {
						txtResult.setText("Trovate " + verticiGrafo.size() + " parole di lunghezza " + numeroLettere);
					} else {
						txtResult.setText("Trovate 0 parole di lunghezza: " + numeroLettere);
					}

					inputNumeroLettere.setDisable(true);
					btnGeneraGrafo.setDisable(true);
					inputParola.setDisable(false);
					btnTrovaVicini.setDisable(false);
					btnTrovaGradoMax.setDisable(false);

					// txtResult.appendText(grafo.toString());

				} catch (NumberFormatException nfe) {
					txtResult.setText("Inserire un numero di lettere valido.");
				}

			}

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaGradoMax(ActionEvent event) {

		try {
			String gradoMax = this.model.findMaxDegree();
			System.out.println("<doTrovaGradoMax> gradoMax: " + gradoMax);
			txtResult.setText(gradoMax);
		} catch (DizionarioException de) {
			txtResult.setText(de.getMessage());
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaVicini(ActionEvent event) {

		try {
			String parola = this.inputParola.getText();
			System.out.println("<doTrovaVicini> parola: " + parola);
			if (parola == null || "".equals(parola.trim())) {
				txtResult.setText("Inserire una parola.");
			} else {
				List<String> vicini = this.model.displayNeighbours(parola);
				if (vicini != null) {
					txtResult.setText(vicini.toString());
				} else {
					txtResult.setText("Non è stato trovato nessun risultato");
				}

			}

		} catch (DizionarioException de) {
			txtResult.setText(de.getMessage());
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaTuttiVicini(ActionEvent event) {
		try {
			String parola = this.inputParola.getText();
			System.out.println("<doTrovaTuttiVicini> parola: " + parola);
			if (parola == null || "".equals(parola.trim())) {
				txtResult.setText("Inserire una parola.");
			} else {
				List<String> vicini = this.model.displayAllNeighboursIterativeVersion(parola);
				if (vicini != null) {
					txtResult.setText(vicini.toString());
				} else {
					txtResult.setText("Non è stato trovato nessun risultato");
				}

			}

		} catch (DizionarioException de) {
			txtResult.setText(de.getMessage());
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}

	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaTuttiVicini != null : "fx:id=\"btnTrovaTuttiVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";

		inputParola.setDisable(true);
		btnTrovaVicini.setDisable(true);
		btnTrovaGradoMax.setDisable(true);

	}

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model = model;
	}
}