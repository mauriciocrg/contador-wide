package com.contador_wide.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class ImageUploader implements Receiver, SucceededListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String folder;
	public File file;
	
	
	private VerticalLayout verticalLayout = null;
	
	public ImageUploader(VerticalLayout verticalLayout, String folder) {
		this.folder = folder;
		this.verticalLayout = verticalLayout;
	}
    
    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.
            file = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+folder+File.separator+"tmp.img");
            if(file != null) fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>",
                             e.getMessage(),
                             Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            return null;
        }
        return fos; // Return the output stream to write to
    }

    public void uploadSucceeded(SucceededEvent event) {
        // Show the uploaded file in the image viewer
        //image.setVisible(true);
    	Image image = new Image(null, new StreamResource(new StreamSource() {
    		@Override
    		public InputStream getStream() {
    			try {
					return  new FileInputStream(file.getAbsoluteFile());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
    		}	
		},file.getAbsolutePath()));
    	
    	System.out.println("upload  = "+file.getAbsolutePath());
    	
    	this.verticalLayout.removeAllComponents();
    	this.verticalLayout.addComponent(image);
    	this.verticalLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
    }
}
