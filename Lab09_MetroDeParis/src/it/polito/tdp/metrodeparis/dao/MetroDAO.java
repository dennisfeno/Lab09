package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Fermata;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<CoppiaFermata> listCoppieFermate() {

		final String sql = "SELECT id_StazP AS sp, id_StazA AS sa, A.nome AS pn, B.nome AS an, A.coordx AS px, A.coordy AS py, B.coordx AS ax, B.coordy AS ay, linea.velocita AS v FROM connessione, fermata AS A, fermata AS B, linea WHERE id_stazP=A.id_fermata AND id_stazA=B.id_fermata AND connessione.id_linea=linea.id_linea ORDER BY sa ASC";
		
		List<CoppiaFermata> coppiaFermata = new ArrayList<CoppiaFermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				CoppiaFermata f = new CoppiaFermata(
						new Fermata(rs.getInt("sp"),rs.getString("pn") ,new LatLng(rs.getDouble("px"), rs.getDouble("py"))), 
						new Fermata(rs.getInt("sa"),rs.getString("an") ,new LatLng(rs.getDouble("ax"), rs.getDouble("ay"))), 
						rs.getDouble("v")
						);
				
				coppiaFermata.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return coppiaFermata;
	}
}
