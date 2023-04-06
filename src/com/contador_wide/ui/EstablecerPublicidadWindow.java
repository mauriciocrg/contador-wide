package com.contador_wide.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.contador_wide.core.Status;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EstablecerPublicidadWindow extends Window {

	private VerticalLayout contentLayout = null;
	private VerticalLayout centerLayout = null;
	
	private GridLayout footerLayout = null;
	private Button okButton = null;
	private Button cancelButton = null;
	private Label label = null;
	
	private Upload fotoUpload = null;
	
	private Panel imagePanel = null;
	
	private VerticalLayout imgLayout = new VerticalLayout();

	private ImageUploader imageUploader = new ImageUploader(imgLayout,"images");
	
	public EstablecerPublicidadWindow() {
		setResizable(false);
		setModal(true);
		
		setWidth(500,Sizeable.Unit.PIXELS);
		setHeight(550,Sizeable.Unit.PIXELS);
		setContent(getContentLayout());
	}
	
	private VerticalLayout getContentLayout() {
		if(contentLayout == null) {
			contentLayout = new VerticalLayout();
			contentLayout.setMargin(true);
			contentLayout.setSpacing(true);
			contentLayout.addComponents(getLabel(),getCenterLayout(),getFooterLayout());
			
			contentLayout.setExpandRatio(getCenterLayout(), 0.8f);
			contentLayout.setExpandRatio(getLabel(), 0.1f);
			contentLayout.setExpandRatio(getFooterLayout(), 0.1f);
			
			contentLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			contentLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		}
		return contentLayout;
	}
	
	private VerticalLayout getCenterLayout() {
		if(centerLayout == null) {
			centerLayout = new VerticalLayout();
			centerLayout.setSpacing(true);
			centerLayout.addComponents(getImagePanel(),getFotoUpload());
			centerLayout.setExpandRatio(getImagePanel(), 1);
			centerLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			centerLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		}
		return centerLayout;
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
		}
		return footerLayout;
	}
	
	private Panel getImagePanel() {
		if(imagePanel == null) {
			imagePanel = new Panel("Imagen");
			imagePanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			imagePanel.setHeight(100, Sizeable.Unit.PERCENTAGE);
			
			File publicidadImageFile = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"images"+File.separator+"publicidad.img");
			
			if(publicidadImageFile.exists()) {
				
				Image publicidadImage = new Image(null,new FileResource(publicidadImageFile));
				
				imgLayout.removeAllComponents();
		    	
				publicidadImage .setWidth(80, Sizeable.Unit.PERCENTAGE);
				publicidadImage .setHeight(80, Sizeable.Unit.PERCENTAGE);		
				
				
		    	imgLayout.addComponent(publicidadImage);
		    	imgLayout.setComponentAlignment(publicidadImage, Alignment.MIDDLE_CENTER);
		    	imgLayout.setSizeFull();
			}
			
			imagePanel.setContent(imgLayout);
		}
		return imagePanel;
	}
	
	private Button getOkButton() {
		if(okButton == null) {
			okButton = new Button("Ok");
			okButton.setWidth(150,Sizeable.Unit.PIXELS);
			okButton.addClickListener(new Button.ClickListener() {
	    		public void buttonClick(ClickEvent event) {
	    			if(imageUploader.file != null) {
	    				imageUploader.file.renameTo(new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"images"+File.separator+"publicidad.img"));
	    				Status.getInstance().setPublicidad = true;
	    			}
	    			close();
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
	
	private Label getLabel() {
		if(label == null) {
			label = new Label("Seleccione una imagen.");
			label.setStyleName("h2");
		} 
		return label;
	}
	
	private Upload getFotoUpload() {
		if(fotoUpload == null) {
			fotoUpload = new Upload(null, imageUploader);
			fotoUpload.addSucceededListener(new SucceededListener() {
				@Override
				public void uploadSucceeded(SucceededEvent event) {
					// TODO Auto-generated method stub
					final Image image = new Image(null, new StreamResource(new StreamSource() {
			    		@Override
			    		public InputStream getStream() {
			    			try {
								return  new FileInputStream(imageUploader.file.getAbsoluteFile());
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return null;
			    		}	
					},imageUploader.file.getAbsolutePath()));
			    	
			    	imgLayout.removeAllComponents();
			    	
			    	image.setWidth(80, Sizeable.Unit.PERCENTAGE);
					image.setHeight(80, Sizeable.Unit.PERCENTAGE);		
					
					
			    	imgLayout.addComponent(image);
			    	imgLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
			    	imgLayout.setSizeFull();
				}
				
			});
			fotoUpload.setWidth(100, Sizeable.Unit.PERCENTAGE);
		}
		return fotoUpload;
	}
}
