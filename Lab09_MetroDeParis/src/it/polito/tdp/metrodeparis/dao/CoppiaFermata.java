package it.polito.tdp.metrodeparis.dao;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.model.Fermata;

public class CoppiaFermata {

	private Fermata partenza ;
	private Fermata arrivo ;
	private double velocitaLinea ; 
	
	public CoppiaFermata(Fermata fp, Fermata fa, double velLinea){
		
		this.partenza = fp ; 
		this.arrivo = fa ; 
		this.velocitaLinea = velLinea ;
		
	}

	public Fermata getPartenza() {
		return partenza;
	}

	public void setPartenza(Fermata partenza) {
		this.partenza = partenza;
	}

	public Fermata getArrivo() {
		return arrivo;
	}

	public void setArrivo(Fermata arrivo) {
		this.arrivo = arrivo;
	}

	public double getVelocitaLinea() {
		return velocitaLinea;
	}

	public void setVelocitaLinea(double velocitaLinea) {
		this.velocitaLinea = velocitaLinea;
	}

	public double calcolaTempo() {

//		System.out.print(partenza.getCoords() + "arrivo" + arrivo.getCoords());
		
		double distanza = LatLngTool.distance(partenza.getCoords(), arrivo.getCoords(), LengthUnit.KILOMETER) ;
		double hours = distanza / this.velocitaLinea ; 
		//System.out.println("dist"+distanza + "hours " + hours);
		return hours * (3600) ;
		
	}

	@Override
	public String toString() {
		return "CoppiaFermata [partenza=" + partenza + ", arrivo=" + arrivo + ", velocitaLinea=" + velocitaLinea + "]";
	}
	
	
	
}
