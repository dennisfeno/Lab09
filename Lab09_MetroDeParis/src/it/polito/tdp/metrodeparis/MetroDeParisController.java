package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.dao.FermataComparator;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {

	private Model model ;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> cmbPartenza;

    @FXML
    private ComboBox<Fermata> cmbArrivo;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCalcolaPercorso(ActionEvent event) {

    	Fermata f1 = cmbPartenza.getValue(),  f2 = cmbArrivo.getValue() ;
    	List<Fermata> path = model.getPercorso(f1,f2);
    	
    	if(path!=null){
    		txtResult.appendText("Percorso:");
    		Fermata previous = new Fermata (0,"",new LatLng(0,0),0,"") ; 

    		for(Fermata f : path){
    			
    			if(f.getIdLinea()==previous.getIdLinea()){
    	    		txtResult.appendText(f.getNome()+" - ");
    			}else{
    				txtResult.appendText("\n\nCambio linea: "+f.getNomeLinea()+"\n"+f.getNome()+" - ");
    			}
    			
    			previous=f; 
    		}
    		
    		txtResult.appendText("\n\nTempo approssimativo: "+model.getTime()+"(togli un'ora)\n_________________\n");
    	}else txtResult.appendText("\nNessun percorso trovato.\n_______________\n");
    	
    	}

    @FXML
    void initialize() {
        assert cmbPartenza != null : "fx:id=\"cmbPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert cmbArrivo != null : "fx:id=\"cmbArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }

	public void setModel(Model model) {

		this.model = model ;
		
		List<Fermata> l = model.getFermate() ;
		
		Collections.sort(l, new FermataComparator() );

            
		cmbPartenza.getItems().addAll(l) ;
		cmbArrivo.getItems().addAll(l) ;
		
		if(cmbPartenza.getItems().size() > 0 && cmbArrivo.getItems().size() > 0) {
	        	cmbPartenza.setValue(cmbPartenza.getItems().get(0)); 
	        	cmbArrivo.setValue(cmbArrivo.getItems().get(1)); 
		}
		
	}
}
