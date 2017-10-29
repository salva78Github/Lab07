package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

	/*
	 * Ritorna tutte le parole di una data lunghezza che differiscono per un
	 * solo carattere
	 */
	public List<String> getAllSimilarWords(String parola) {
		System.out.println("<getAllSimilarWords> parola: " + parola);
		List<String> similarWords = new ArrayList<String>();

		for (int i = 0; i < parola.length(); i++) {
			StringBuilder sb = new StringBuilder(parola);
			sb.setCharAt(i, '_');
			System.out.println("<getAllSimilarWords> parola con carattere speciale: " + sb.toString());
			similarWords.addAll(getSimilarWords(sb.toString()));
		}

		return similarWords;
	}

	private List<String> getSimilarWords(String parola) {
		System.out.println("<getSimilarWords> parola: " + parola);
		List<String> similarWords = new ArrayList<String>();

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT nome FROM parola WHERE nome LIKE ?";

		try {
			c = DBConnect.getInstance().getConnection();
			ps = c.prepareStatement(query);
			ps.setString(1, parola);

			rs = ps.executeQuery();

			while (rs.next()) {
				String sw = rs.getString("nome");
				System.out.println("<getSimilarWords> similiar word: " + sw);
				similarWords.add(sw);
			}

			return similarWords;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		} finally {
			DBConnect.getInstance().closeResources(c, ps, rs);
		}

	}

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int numeroLettere) {

		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		PreparedStatement st = null;
		ResultSet res = null;
		try {

			st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);
			res = st.executeQuery();

			List<String> parole = new ArrayList<String>();

			while (res.next())
				parole.add(res.getString("nome"));

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		} finally {
			DBConnect.getInstance().closeResources(conn, st, res);
		}
	}

}
