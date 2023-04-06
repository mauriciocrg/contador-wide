package com.contador_wide.core;

import java.util.Date;

public class Llamada {
	
	public static final int ESTADO_LLAMADO = 0;
	public static final int ESTADO_ATENDIDO = 1;
	
	public int numero = 0;
	public String nombrePuesto = "";
	public Date date = new Date();
	public int estado = 0;
}
