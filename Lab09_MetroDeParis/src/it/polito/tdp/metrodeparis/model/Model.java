package it.polito.tdp.metrodeparis.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
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
	
	private WeightedGraph<Fermata,DefaultWeightedEdge> getGrafo() {
		if(this.graph==null){
			this.creaGrafo();
		}
		return this.graph ;
	}
	
	public void creaGrafo() {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class) ;
		
		MetroDAO dao = new MetroDAO() ;
		
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, this.getFermate()) ;
			
		for(CoppiaFermata cf : dao.listCoppieFermate()) {
						
				DefaultWeightedEdge d = new DefaultWeightedEdge();
			
				d = graph.addEdge(cf.getPartenza(), cf.getArrivo()) ;
				
				if(d!=null){
				graph.setEdgeWeight(d, cf.calcolaTempo() );
//				System.out.println(d + "time" + cf.calcolaTempo());
				}
			
		}
	}

	public List<Fermata> getPercorso(Fermata f1, Fermata f2) {

		DijkstraShortestPath<Fermata,DefaultWeightedEdge> dij = new DijkstraShortestPath<Fermata,DefaultWeightedEdge>(this.getGrafo(),f1,f2); 
		
		time = 0 ; 
		List<Fermata> path = new ArrayList<Fermata>();
		
		Fermata previous = f1 ;
		path.add(previous) ;

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
