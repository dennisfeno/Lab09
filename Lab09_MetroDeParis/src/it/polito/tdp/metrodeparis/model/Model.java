package it.polito.tdp.metrodeparis.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import it.polito.tdp.metrodeparis.dao.CoppiaFermata;
import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {

	private WeightedGraph<Fermata, DefaultWeightedEdge> graph  ;
	private List<Fermata> fermate ; 
	private long time = 0 ; 

	public Model(){
		this.getGrafo();
	}
	
	public List<Fermata> getFermate() {
		
		if(this.fermate == null){ 
			MetroDAO dao = new MetroDAO();
			this.fermate = dao.getAllFermate() ; 
		}
		return fermate ; 

	}
	
	public WeightedGraph<Fermata,DefaultWeightedEdge> getGrafo() {
		if(this.graph==null){
			this.creaGrafo();
		}
		return this.graph ;
	}
	
	public void creaGrafo() {
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class) ;
		
		MetroDAO dao = new MetroDAO() ;
		
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, this.getFermate()) ;
		
		// aggiungi gli edges di ciascun collegamento da una fermata all'altra
		for(CoppiaFermata cf : dao.listCoppieFermate()) {
						
				DefaultWeightedEdge d = new DefaultWeightedEdge();
			
				d = graph.addEdge(cf.getPartenza(), cf.getArrivo()) ;
				
				if(d!=null){
				graph.setEdgeWeight(d, cf.calcolaTempo() );
//				System.out.println(d + "time" + cf.calcolaTempo());
				}
		}
		
		//aggiugni gli edges di ciascun collegamento nella stessa fermata. 
		
		for(Fermata i : this.fermate){
			for(Fermata j : this.fermate){
				if(i.getNome().compareTo(j.getNome())==0 && !j.equals(i)){ // se sono la stessa fermata, ma non lo stesso vertice..
					
					DefaultWeightedEdge d = new DefaultWeightedEdge();
					
					d = graph.addEdge(i, j);
					
					if(d!=null){
						graph.setEdgeWeight(d, dao.getTempoPassaggioLinea(j) );
					}
				}
			}	
		}
		
	}

	public List<Fermata> getPercorso(Fermata f1, Fermata f2) {

		DijkstraShortestPath<Fermata,DefaultWeightedEdge> dij = new DijkstraShortestPath<Fermata,DefaultWeightedEdge>(this.getGrafo(),f1,f2); 
		
		time = 0 ; 
		List<Fermata> path = new ArrayList<Fermata>();
		
		Fermata previous = f1 ;
		path.add(previous) ;
		
		if ( dij.getPathEdgeList() == null) {
			return null ; 
		}
//		System.out.println("passo di qui");
		for (DefaultWeightedEdge d : dij.getPathEdgeList()){
			
			previous = Graphs.getOppositeVertex(this.getGrafo(), d, previous) ;
			path.add(previous) ;
			time += (this.getGrafo().getEdgeWeight(d) +30 );
			
		}
		if(path.size()>1)
			time-=30 ;		
		
		//System.out.println(time );
		return path ;
		
	}
	
	public String getTime(){
		Date data = new Date(this.time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return sdf.format(data);
	}
	
	
}
