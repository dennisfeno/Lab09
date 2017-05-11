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

		final String sql = "SELECT f1.id_fermata AS idf1, f1.nome AS nf1, f1.coordx AS cx1, f1.coordy AS cy1, "
				+ "f2.id_fermata AS idf2, f2.nome AS nf2, f2.coordx AS cx2, f2.coordy AS cy2,"
				+ "linea.id_linea AS idl, linea.nome AS nl FROM "
				+ "fermata AS f1, fermata AS f2,linea,connessione "
				+ "WHERE id_stazP=f1.id_fermata AND id_stazA=f2.id_fermata AND connessione.id_linea=linea.id_linea ORDER BY idl ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("idf1"),rs.getString("nf1"), new LatLng(rs.getDouble("cx1"), rs.getDouble("cy1")),rs.getInt("idl"),rs.getString("nl"));
				if(!fermate.contains(f))
					fermate.add(f);
				Fermata g = new Fermata(rs.getInt("idf2"),rs.getString("nf2"), new LatLng(rs.getDouble("cx2"), rs.getDouble("cy2")),rs.getInt("idl"),rs.getString("nl"));
				if(!fermate.contains(g))
					fermate.add(g) ;
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

		final String sql = "SELECT id_StazP AS sp, id_StazA AS sa, A.nome AS pn, "
				+ "B.nome AS an, A.coordx AS px, A.coordy AS py, "
				+ "B.coordx AS ax, B.coordy AS ay, linea.id_linea AS idl, "
				+ "linea.nome AS nl, linea.velocita AS v "
				+ "FROM connessione, fermata AS A, fermata AS B, linea "
				+ "WHERE id_stazP=A.id_fermata AND id_stazA=B.id_fermata "
				+ "AND connessione.id_linea=linea.id_linea ORDER BY sa ASC";
		
		List<CoppiaFermata> coppiaFermata = new ArrayList<CoppiaFermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				CoppiaFermata f = new CoppiaFermata(
						new Fermata(rs.getInt("sp"),rs.getString("pn") ,new LatLng(rs.getDouble("px"), rs.getDouble("py")),rs.getInt("idl"),rs.getString("nl")), 
						new Fermata(rs.getInt("sa"),rs.getString("an") ,new LatLng(rs.getDouble("ax"), rs.getDouble("ay")),rs.getInt("idl"),rs.getString("nl")), 
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

	public double getTempoPassaggioLinea(Fermata j) {

		final String sql = "SELECT intervallo FROM linea WHERE id_linea=?";
		
		double tempo = 0 ; 
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, j.getIdLinea());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				tempo = rs.getDouble("intervallo");
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return tempo * 60 ; // in secondi.. 
	}
}
