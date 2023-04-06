package com.contador_wide.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.contador_wide.ContadorUI;
import com.contador_wide.core.Llamada;
import com.contador_wide.core.Puesto;
import com.contador_wide.core.Status;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import elemental.json.JsonArray;

public class PuestoView extends VerticalLayout implements View {

	private Navigator navigator = null;
	private Properties properties = new Properties();
	
	private VerticalLayout verticalLayout = null;
	private VerticalLayout appVerticalLayout = null;
	
	private HorizontalLayout fixLayout = null;
	
	private GridLayout gridLayout = null;
	
	private CustomLayout setPuestoCustomLayout = null;
	private CustomLayout puestoCustomLayout = null;
	
	
	
	private TextField textField = null;
	
	private Button button = null;
	
	private Button buttonSiguiente = null;
	private Button buttonAtendido = null;
	private Button buttonCampana = null;
	private Button buttonEstablecer = null;
	private Button buttonPublicidad = null;
	
	private Label labelPuesto = null;
	private Label labelNumero = null;
	
	private String nombrePuesto = "";
	
	private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

	private int numero = 0;
	
	public PuestoView(final Navigator navigator) {    
    	this.navigator = navigator;
    	
    	setWidth(100, Sizeable.Unit.PERCENTAGE);
		setHeight(100, Sizeable.Unit.PERCENTAGE);
    	
		setSpacing(true);
    	setMargin(true);
		
        addComponent(getGridLayout());
        setComponentAlignment(getGridLayout(), Alignment.MIDDLE_CENTER);
                
    	JavaScript.getCurrent().addFunction("aboutToClose", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				// TODO Auto-generated method stub
				Status.getInstance().removePuesto(nombrePuesto);
			}
        });
    	
    	Page.getCurrent().getJavaScript().execute("window.onbeforeunload = function (e) { var e = e || window.event; aboutToClose(); return; };");

	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private HorizontalLayout getFixLayout() {
		if(fixLayout == null) {
			Image fix = new Image(null,new FileResource(new File(basepath+File.separator+"fix.jpg")));
			fix.setWidth(100, Sizeable.Unit.PERCENTAGE);
			fix.setHeight(100, Sizeable.Unit.PERCENTAGE);
			
			fixLayout = new HorizontalLayout();
			
			fixLayout.addComponent(fix);
			fixLayout.setComponentAlignment(fix, Alignment.MIDDLE_CENTER);
			fixLayout.setWidth(110,Sizeable.Unit.PIXELS);
			fixLayout.setHeight(60,Sizeable.Unit.PIXELS);
		}
		return fixLayout;
	}
	
	private GridLayout getGridLayout() {
		if(gridLayout == null) {
			gridLayout = new GridLayout();
	        gridLayout.addStyleName("outlined");
	        gridLayout.setSizeFull();

	        gridLayout.removeAllComponents();

	        gridLayout.setRows(3);
	        gridLayout.setColumns(3);
	        
	        gridLayout.addComponent(getSetPuestoCustomLayout(), 1, 1);
	        gridLayout.addComponent(getFixLayout(), 2, 2);
	        gridLayout.setComponentAlignment(getFixLayout(), Alignment.BOTTOM_RIGHT);
		}
		return gridLayout;
	}
	
	
	private CustomLayout getSetPuestoCustomLayout() {
		if(setPuestoCustomLayout == null) {
			String setPuestofilePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"set_puesto_template.xml";
		       
	        try {
	        	setPuestoCustomLayout = new CustomLayout(new FileInputStream(new File(setPuestofilePath)));
	        	setPuestoCustomLayout.addComponent(getTextField(), "puesto");

		        final Label message = new Label("Contador Wide 1.0");        
		        setPuestoCustomLayout.addComponent(message, "message");

		        
		        setPuestoCustomLayout.addComponent(getButton(), "okbutton");

	        } catch (IOException ex) {
	            Logger.getLogger(ContadorUI.class.getName()).log(Level.SEVERE, null, ex);
	        }	        
		}
		return setPuestoCustomLayout;
	}
	
	private CustomLayout getPuestoCustomLayout() {
		if(puestoCustomLayout == null) {
			String puestofilePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"puesto_template.xml";
		       
	        try {
	        	puestoCustomLayout = new CustomLayout(new FileInputStream(new File(puestofilePath)));
	        	puestoCustomLayout.addComponent(getButtonSiguiente(), "next");
	        	puestoCustomLayout.addComponent(getButtonAtendido(), "atendido");
	        	puestoCustomLayout.addComponent(getButtonCampana(), "bell");
	        	puestoCustomLayout.addComponent(getButtonEstablecer(), "setNumber");
	        	puestoCustomLayout.addComponent(getButtonPublicidad(), "setPublicidad");
	        	puestoCustomLayout.addComponent(getLabelPuesto(), "puesto");
	        	puestoCustomLayout.addComponent(getLabelNumero(), "number");

		        final Label message = new Label("Contador Wide 1.0");        
		        puestoCustomLayout.addComponent(message, "message");

	        } catch (IOException ex) {
	            Logger.getLogger(ContadorUI.class.getName()).log(Level.SEVERE, null, ex);
	        }	        
		}
		return puestoCustomLayout;
	}

	private VerticalLayout getAppVerticalLayout() {
		if(appVerticalLayout == null) {
			appVerticalLayout = new VerticalLayout();
			appVerticalLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			appVerticalLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
			appVerticalLayout.addComponents(getButtonSiguiente(),getButtonCampana(),getButtonEstablecer());
		}
		return appVerticalLayout;
	}
	
	private Button getButton() {
		if(button == null) {
			button = new Button("Ingresar");
			button.setWidth(150,Sizeable.Unit.PIXELS);
			button.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			if(getTextField().getValue() != null && !getTextField().getValue().equals("")) {				
	    				nombrePuesto = getTextField().getValue();
	    				if(Status.getInstance().existe(nombrePuesto)) {
	    					Notification.show("Ya existe un puesto con el nombre: "+nombrePuesto, Notification.Type.TRAY_NOTIFICATION);
	    				} else {
	    					VaadinSession currentSession = VaadinSession.getCurrent();
	    					
	    					Puesto puesto = new Puesto();
	    					puesto.nombre = nombrePuesto;
	    					puesto.session = currentSession;
	    					
	    					Status.getInstance().addPuesto(puesto);
	    					getGridLayout().removeComponent(getSetPuestoCustomLayout());
	    					getGridLayout().addComponent(getPuestoCustomLayout(),1,1);
		    				getLabelPuesto().setValue(nombrePuesto);
	    				}
	    			} else {
	    				Notification.show("Debe ingresar un nombre/numero.", Notification.Type.TRAY_NOTIFICATION);
	    			}
	    		}
	    	});
		}
		return button;
	}
	
	private Button getButtonSiguiente() {
		if(buttonSiguiente == null) {
			buttonSiguiente = new Button("Siguiente");
			buttonSiguiente.setWidth(200,Sizeable.Unit.PIXELS);
			buttonSiguiente.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			int num = Status.getInstance().getNext();
	    			
	    			numero = num;
	    			
	    			getLabelNumero().setValue(""+num);
	    			
	    			Llamada llamada = new Llamada();
	    			llamada.nombrePuesto = nombrePuesto;
	    			llamada.numero = num;
	    			
	    			Status.getInstance().addLlamada(llamada);
	    			Status.getInstance().playBell = true;
	    		}
	    	});
		}
		return buttonSiguiente;
	}
	
	private Button getButtonAtendido() {
		if(buttonAtendido == null) {
			buttonAtendido = new Button("Atendido");
			buttonAtendido.setWidth(200,Sizeable.Unit.PIXELS);
			buttonAtendido.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			Status.getInstance().setAtendido(numero);
	    			Status.getInstance().playBell = true;
	    		}
	    	});
		}
		return buttonAtendido;
	}
	
	private Button getButtonCampana() {
		if(buttonCampana == null) {
			buttonCampana = new Button("Campana");
			buttonCampana.setWidth(200,Sizeable.Unit.PIXELS);
			buttonCampana.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			Status.getInstance().playBell = true;
	    		}
	    	});
		}
		return buttonCampana;
	}
	
	private Button getButtonEstablecer() {
		if(buttonEstablecer == null) {
			buttonEstablecer = new Button("Establecer Número");
			buttonEstablecer.setWidth(200,Sizeable.Unit.PIXELS);
			buttonEstablecer.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			getUI().addWindow(new EstablecerNumeroWindow());
	    		}
	    	});
		}
		return buttonEstablecer;
	}
	
	private Button getButtonPublicidad() {
		if(buttonPublicidad == null) {
			buttonPublicidad = new Button("Establecer Publicidad");
			buttonPublicidad.setWidth(200,Sizeable.Unit.PIXELS);
			buttonPublicidad.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			getUI().addWindow(new EstablecerPublicidadWindow());
	    		}
	    	});
		}
		return buttonPublicidad;
	}
	
	private TextField getTextField() {
		if(textField == null) {
			textField = new TextField();
			textField.setRequired(true);
			textField.setWidth(150,Sizeable.Unit.PIXELS);
		}
		return textField;
	}
	
	private Label getLabelPuesto() {
		if(labelPuesto == null) {
			labelPuesto = new Label("");
			labelPuesto.setStyleName("h1");
			labelPuesto.setWidth(100, Sizeable.Unit.PERCENTAGE);
		}
		return labelPuesto;
	}
	
	private Label getLabelNumero() {
		if(labelNumero == null) {
			labelNumero = new Label("");
			labelNumero.setStyleName("h1");
			labelNumero.setWidth(100, Sizeable.Unit.PERCENTAGE);
		}
		return labelNumero;
	}
}
