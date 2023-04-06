package com.contador_wide.core;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.VaadinSession;

public class Status {

	private static int currentNumber = 0;
	
	private static List<Puesto> puestos = new ArrayList<Puesto>();
	
	private static List<Llamada> statusList = new ArrayList<Llamada>();
	
	private static Status status = null;
	
	private Status() {}
	
	public boolean playBell = false;
	
	public boolean setPublicidad = false;
	
	public int getNext() {
		currentNumber ++;
		return currentNumber;
	}
	
	public void setNumber(int number) {
		currentNumber = number -1;
	}
	
	public boolean existe(String nombre) {
		for(Puesto puesto: puestos) {
			if(puesto.nombre.equals(nombre)) return true;
		}
		return false;
	}
	
	public void setAtendido(int numero) {
		for(Llamada llamada: statusList) {
			if(llamada.numero == numero) llamada.estado = llamada.ESTADO_ATENDIDO;
		}
	}
	
	public void checkSessionsStatus() {
		for(Puesto puesto: puestos) {
			if(puesto.session.getState() == VaadinSession.State.CLOSED ||
			   puesto.session.getState() == VaadinSession.State.CLOSING) 
				puestos.remove(puesto);
		}
	}
	
	public void removePuesto(String nombrePuesto) {
		puestos.remove(nombrePuesto);
	}
	
	public void addPuesto(Puesto puesto) {
		puestos.add(puesto);
	}
	
	public void addLlamada(Llamada llamada) {
		statusList.add(0,llamada);
	}
	
	public List<Llamada> getLlamadas() {
		return statusList;
	}
	
	public static Status getInstance() {
		if(status == null) {
			status = new Status();
		}
		return status;
	}
}


