package com.contador_wide.ui;

import java.io.File;

import com.contador_wide.core.Status;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class StartView extends VerticalLayout implements View {

	private Navigator navigator = null;
	
	private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	
	private HorizontalLayout fixLayout = null;
	private HorizontalLayout centerLayout = null;
	
	private GridLayout gridLayout = null;
	
	private Button buttonPuesto = null;
	private Button buttonPantalla = null;
	
	private Label label = null;
	
	public StartView(final Navigator navigator) {

    	this.navigator = navigator;
    	setSpacing(true);
    	setMargin(true);
    	
		Status.getInstance().checkSessionsStatus();
		
		setWidth(100,Sizeable.Unit.PERCENTAGE);
		setHeight(100,Sizeable.Unit.PERCENTAGE);
		
		addComponent(getGridLayout());
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
	        
	        gridLayout.addComponent(getCenterLayout(), 1, 1);
	        gridLayout.addComponent(getFixLayout(), 2, 0);
	        gridLayout.addComponent(getLabel(), 0, 0);
	        gridLayout.setComponentAlignment(getFixLayout(), Alignment.TOP_RIGHT);
	        gridLayout.setComponentAlignment(getLabel(), Alignment.TOP_LEFT);
		}
		return gridLayout;
	}
	
	private HorizontalLayout getCenterLayout() {
		if(centerLayout == null) {
			centerLayout = new HorizontalLayout();
			centerLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			centerLayout.setHeight(100,Sizeable.Unit.PERCENTAGE);
			centerLayout.addComponents(getButtonPantalla(), getButtonPuesto());
			centerLayout.setComponentAlignment(getButtonPantalla(), Alignment.MIDDLE_CENTER);
			centerLayout.setComponentAlignment(getButtonPuesto(), Alignment.MIDDLE_CENTER);
		}
		return centerLayout;
	}
	
	private Button getButtonPantalla() {
		if(buttonPantalla == null) {
			buttonPantalla = new Button("<a style='text-decoration:none;color:black;font-size:30px'>Pantalla</a>");
			buttonPantalla.setHtmlContentAllowed(true);
			buttonPantalla.setWidth(150,Sizeable.Unit.PIXELS);
			buttonPantalla.setHeight(150,Sizeable.Unit.PIXELS);
			buttonPantalla.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo("pantalla");
				}
			});
		}
		return buttonPantalla;
	}
	
	private Button getButtonPuesto() {
		if(buttonPuesto == null) {
			buttonPuesto = new Button("<a style='text-decoration:none;color:black;font-size:30px'>Puesto</a>");
			buttonPuesto.setHtmlContentAllowed(true);
			buttonPuesto.setWidth(150,Sizeable.Unit.PIXELS);
			buttonPuesto.setHeight(150,Sizeable.Unit.PIXELS);
			buttonPuesto.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo("puesto");
				}
			});
		}
		return buttonPuesto;
	}
	
	private Label getLabel() {
		if(label == null) {
			label = new Label("Contador Wide 1.0");
			label.setStyleName("h2");
		}
		return label;
	}
}
