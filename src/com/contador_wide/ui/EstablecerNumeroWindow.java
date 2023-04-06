package com.contador_wide.ui;

import com.contador_wide.core.Status;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class EstablecerNumeroWindow extends Window {

	private FormLayout formLayout = null;
	private VerticalLayout contentLayout = null;
	private GridLayout footerLayout = null;
	private Button okButton = null;
	private Button cancelButton = null;
	private TextField valueField = null;
	private Label label = null;
	
	public EstablecerNumeroWindow() {
		setResizable(false);
		setModal(true);
		setContent(getContentLayout());
		setWidth(400,Sizeable.Unit.PIXELS);
		setHeight(180,Sizeable.Unit.PIXELS);
	}
	
	private FormLayout getFormLayout() {
		if(formLayout == null) {
			formLayout = new FormLayout();
			formLayout.setMargin(false);
			formLayout.setSpacing(false);
			formLayout.addComponent(getValueField());
			formLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			formLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		}
		return formLayout;
	}
	
	private VerticalLayout getContentLayout() {
		if(contentLayout == null) {
			contentLayout = new VerticalLayout();
			//contentLayout.setSpacing(true);
			contentLayout.setMargin(true);
			contentLayout.addComponents(getLabel(),getFormLayout(),getFooterLayout());
			//contentLayout.setComponentAlignment(getFooterLayout(), Alignment.BOTTOM_CENTER);
			//contentLayout.setComponentAlignment(getFormLayout(), Alignment.MIDDLE_CENTER);
			//contentLayout.setComponentAlignment(getLabel(), Alignment.TOP_CENTER);
			//contentLayout.setExpandRatio(getLabel(), 0.4f);
			//contentLayout.setExpandRatio(getFormLayout(), 0.3f);
			//contentLayout.setExpandRatio(getFooterLayout(), 0.3f);
			contentLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			contentLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		}
		return contentLayout;
	}
	
	private GridLayout getFooterLayout() {
		if(footerLayout == null) {
			
			footerLayout = new GridLayout();
			footerLayout.addStyleName("outlined");
			footerLayout.setSizeFull();

			footerLayout.removeAllComponents();

			footerLayout.setRows(3);
			footerLayout.setColumns(3);
	        
			footerLayout.addComponent(getOkButton(), 0, 1);
			footerLayout.addComponent(getCancelButton(), 2, 1);
	
			footerLayout.setComponentAlignment(getOkButton(),Alignment.MIDDLE_CENTER);
			footerLayout.setComponentAlignment(getCancelButton(),Alignment.MIDDLE_CENTER);
			
			footerLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			footerLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
			/*
			
			footerLayout = new HorizontalLayout();
			footerLayout.setSpacing(true);
			footerLayout.setMargin(true);
			footerLayout.setSizeFull();
			footerLayout.addComponents(getOkButton(), getCancelButton());
			footerLayout.setComponentAlignment(getOkButton(),Alignment.MIDDLE_CENTER);
			footerLayout.setComponentAlignment(getCancelButton(),Alignment.MIDDLE_CENTER);*/
			//footerLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			//footerLayout.setHeight(10, Sizeable.Unit.PERCENTAGE);
		}
		return footerLayout;
	}
	
	private Button getOkButton() {
		if(okButton == null) {
			okButton = new Button("Ok");
			okButton.setWidth(150,Sizeable.Unit.PIXELS);
			okButton.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			String value = getValueField().getValue();
	    			if(value != null && !value.equals("")) {
	    				try{
			    			int num = new Integer(value);
			    			if(num < 1 && num > Integer.MAX_VALUE) {
			    				Notification.show("El Valor debe ser un entero positivo en el rango de 1 y "+Integer.MAX_VALUE, Notification.Type.TRAY_NOTIFICATION);
			    			} else {
			    				Status.getInstance().setNumber(num);
			    				close();
			    			}
		    			} catch(Exception e) {
		    				Notification.show("El Valor debe ser un entero positivo en el rango de 1 y "+Integer.MAX_VALUE, Notification.Type.TRAY_NOTIFICATION);
		    			}
		    		} else {
		    			Notification.show("El Valor no puede ser vacio.", Notification.Type.TRAY_NOTIFICATION);
		    		}
	    		}
	    	});
		}
		return okButton;
	}
	
	private Button getCancelButton() {
		if(cancelButton == null) {
			cancelButton = new Button("Cancel");
			cancelButton.setWidth(150,Sizeable.Unit.PIXELS);
			cancelButton.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			close();
	    		}
	    	});
		}
		return cancelButton;
	}
	
	private TextField getValueField() {
		if(valueField == null) {
			valueField = new TextField("Numero:");
			valueField.setMaxLength((""+Integer.MAX_VALUE).length());
			valueField.setWidth(100,Sizeable.Unit.PERCENTAGE);
		}
		return valueField;
	}
	
	private Label getLabel() {
		if(label == null) {
			label = new Label("Ingrese el nuevo Numero");
			label.setStyleName("h2");
		} 
		return label;
	}
}
