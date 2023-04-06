package com.contador_wide;

import javax.servlet.annotation.WebServlet;


import com.contador_wide.ui.PantallaView;
import com.contador_wide.ui.PuestoView;
import com.contador_wide.ui.StartView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("contador_wide")
public class ContadorUI extends UI {

	private Navigator navigator;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ContadorUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		navigator = new Navigator(this, this);
	    // Create and register the views
	    navigator.addView("pantalla", new PantallaView(navigator));
	    navigator.addView("puesto", new PuestoView(navigator));
	    navigator.addView("start", new StartView(navigator));
	    
	    navigator.navigateTo("start");
	    
        setPollInterval(500);
	}

}