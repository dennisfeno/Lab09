package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLng;

public class Fermata {

	private int idFermata;
	private String nome;
	private LatLng coords;
	private int idLinea ;
	private String nomeLinea ;

	public Fermata(int idFermata, String nome, LatLng coords, int idLinea, String nomeLinea) {
		super();
		this.idFermata = idFermata;
		this.nome = nome;
		this.coords = coords;
		this.idLinea = idLinea;
		this.nomeLinea = nomeLinea;
	}
	public int getIdFermata() {
		return idFermata;
	}
	public void setIdFermata(int idFermata) {
		this.idFermata = idFermata;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LatLng getCoords() {
		return coords;
	}
	public void setCoords(LatLng coords) {
		this.coords = coords;
	}
	public int getIdLinea() {
		return idLinea;
	}
	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}
	public String getNomeLinea() {
		return nomeLinea;
	}
	public void setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFermata;
		result = prime * result + idLinea;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermata other = (Fermata) obj;
		if (idFermata != other.idFermata)
			return false;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return nome+", linea "+nomeLinea;
	}
}
