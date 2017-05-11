package it.polito.tdp.metrodeparis.dao;

import java.util.Comparator;

import it.polito.tdp.metrodeparis.model.Fermata;

public class FermataComparator implements Comparator<Fermata> {

		@Override
        public int compare(Fermata f1,Fermata f2) {
            if(f1.getNome() != null && f2.getNome() != null && f2.getNome().compareTo(f1.getNome()) != 0) {
                return f1.getNome().compareTo(f2.getNome());
            }
           else {
              return f1.getNomeLinea().compareTo(f2.getNomeLinea());
           }
        }

}
