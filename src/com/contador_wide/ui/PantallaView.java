package com.contador_wide.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.contador_wide.core.Llamada;
import com.contador_wide.core.Status;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Audio;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class PantallaView extends VerticalLayout implements View {

	private Navigator navigator = null;
	
	private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	private int count = 0;
	
	private HorizontalLayout horizontalTopLayout = new HorizontalLayout();
	private GridLayout horizontalBottomLayout = new GridLayout();
	private HorizontalLayout publicidadLayout = new HorizontalLayout();
	private VerticalLayout tableLayout = new VerticalLayout();
	
	private Label timeLabel = new Label();
	
	//private final Table table = new Table();
	
	private Audio bell = new Audio();
    private final Resource audioResource = new FileResource(new File(basepath+File.separator+"Doorbell.mp3"));
    
    private SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public PantallaView(final Navigator navigator) {    
    	this.navigator = navigator;
    	setSpacing(true);
    	setMargin(true);
    	setWidth(100, Sizeable.Unit.PERCENTAGE);
    	
    	bell.setSource(audioResource);
        bell.setHtmlContentAllowed(true);
        bell.setWidth(190,Sizeable.Unit.PIXELS);
		bell.setHeight(30,Sizeable.Unit.PIXELS);
        
    	
		horizontalTopLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
		
		
		horizontalBottomLayout.addStyleName("outlined");
        horizontalBottomLayout.removeAllComponents();

        horizontalBottomLayout.setRows(1);
        horizontalBottomLayout.setColumns(3);
        
		horizontalBottomLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
		
		
		
		Image fix = new Image(null,new FileResource(new File(basepath+File.separator+"fix.jpg")));
		fix.setWidth(100, Sizeable.Unit.PERCENTAGE);
		fix.setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		HorizontalLayout fixLayout = new HorizontalLayout();
		
		fixLayout.addComponent(fix);
		fixLayout.setComponentAlignment(fix, Alignment.MIDDLE_CENTER);
		fixLayout.setWidth(110,Sizeable.Unit.PIXELS);
		fixLayout.setHeight(60,Sizeable.Unit.PIXELS);

		
		horizontalTopLayout.setHeight(60,Sizeable.Unit.PIXELS);
		horizontalTopLayout.addComponent(fixLayout);
		horizontalTopLayout.setComponentAlignment(fixLayout, Alignment.TOP_LEFT);
		
		timeLabel.setCaptionAsHtml(true);
		timeLabel.setWidth(400, Sizeable.Unit.PIXELS);
		timeLabel.setHeight(50, Sizeable.Unit.PIXELS);
		horizontalTopLayout.addComponent(timeLabel);
		horizontalTopLayout.setComponentAlignment(timeLabel, Alignment.TOP_RIGHT);
		
		setPublicidad();
		
		
		
		horizontalBottomLayout.addComponent(bell,0,0);
		horizontalBottomLayout.setComponentAlignment(bell, Alignment.MIDDLE_LEFT);
		
		horizontalBottomLayout.addComponent(publicidadLayout,2,0);
		horizontalBottomLayout.setComponentAlignment(publicidadLayout, Alignment.MIDDLE_RIGHT);
		
		init();
		
    	Thread thread = new Thread() {
    		public void run() {
    			while(true) {
    				if(Status.getInstance().playBell) {
	    				Status.getInstance().playBell = false;
	    				bell.play();
	    			} else {
	    				refreshTableLayout();		
	    				try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(Status.getInstance().setPublicidad) {
	    					Status.getInstance().setPublicidad = false;
		    				setPublicidad();
	    				}
	    			}
				}
    		}
    	};
    	thread.start();
	}
	
	private void init() {
		
		setWidth(100, Sizeable.Unit.PERCENTAGE);
		setHeight(100, Sizeable.Unit.PERCENTAGE);

		addComponent(horizontalTopLayout);
		setComponentAlignment(horizontalTopLayout, Alignment.TOP_CENTER);
		setExpandRatio(horizontalTopLayout, 0);
		timeLabel.setCaptionAsHtml(true);
		timeLabel.setCaption("<center><a style='text-decoration:none;color:black;font-size:50px'>"+simpleDateTimeFormat.format(new Date()) +"</a></center>");
		
		tableLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
		tableLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		addComponent(tableLayout);
		setComponentAlignment(tableLayout, Alignment.MIDDLE_CENTER);
		setExpandRatio(tableLayout, 1);
		
		addComponent(horizontalBottomLayout);
		setComponentAlignment(horizontalBottomLayout, Alignment.BOTTOM_CENTER);
		setExpandRatio(horizontalBottomLayout, 0);
		
	}
	
	private void refreshTableLayout() {
		timeLabel.setCaption("<center><a style='text-decoration:none;color:black;font-size:50px'>"+simpleDateTimeFormat.format(new Date()) +"</a></center>");
		tableLayout.removeAllComponents();
		HorizontalLayout cabezalLayout = getCabezal();
		tableLayout.addComponent(cabezalLayout);
		tableLayout.setExpandRatio(cabezalLayout, 0.25f);
		List <Llamada> llamadas = Status.getInstance().getLlamadas();
		for(int i = 0; i < 4; i++) {
			Llamada llamada = (llamadas.size() > i) ? llamadas.get(i):null;
			HorizontalLayout rowLayout = getRow(llamada);
			tableLayout.addComponent(rowLayout);
			tableLayout.setExpandRatio(rowLayout, 0.25f);
		}
	}
	
	
	private HorizontalLayout getCabezal() {
		HorizontalLayout cabezalLayout = new HorizontalLayout();
		cabezalLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
		cabezalLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		Label labelNumero = new Label("<center><strong><a style='display:block;width:97%;height:120px;text-decoration:none;color:white;background:blue;font-size:90px;font-weight:bold;'>Número</a><strong></center>", ContentMode.HTML);
		
		Label labelPuesto = new Label("<center><strong><a style='display:block;width:97%;height:120px;text-decoration:none;color:white;background:blue;font-size:90px;font-weight:bold;'>Puesto</a></strong></center>", ContentMode.HTML);
		
		Label labelEstado = new Label("<center><strong><a style='display:block;width:97%;height:120px;text-decoration:none;color:white;background:blue;font-size:90px;font-weight:bold;'>Estado</a></strong></center>", ContentMode.HTML);
		
		cabezalLayout.addComponents(labelNumero,labelPuesto,labelEstado);
		cabezalLayout.setExpandRatio(labelNumero, 0.3f);
		cabezalLayout.setExpandRatio(labelPuesto, 0.35f);
		cabezalLayout.setExpandRatio(labelEstado, 0.35f);
		
		return cabezalLayout;
	} 
	
	private HorizontalLayout getRow(Llamada llamada) {
		HorizontalLayout rowLayout = new HorizontalLayout();
		rowLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
		rowLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		Label labelNumero = new Label("<center><strong><a style='display:block;width:97%;height:120px;text-decoration:none;color:black;background:grey;font-size:90px;font-weight:bold;'>"+(llamada!=null?llamada.numero:"-")+"</a><strong></center>", ContentMode.HTML);
		
		Label labelPuesto = new Label("<center><strong><a style='display:block;width:97%;height:120px;text-decoration:none;color:black;background:grey;font-size:90px;font-weight:bold;'>"+(llamada!=null?llamada.nombrePuesto:"-")+"</a></strong></center>", ContentMode.HTML);
		
		Label labelEstado = new Label("<center><strong><a style='display:block;width:97%;height:120px;text-decoration:none;color:black;background:grey;font-size:90px;font-weight:bold;'>"+(llamada!=null?(llamada.estado==0?"Llamado":"Atendido"):"-")+"</a></strong></center>", ContentMode.HTML);
		
		rowLayout.addComponents(labelNumero,labelPuesto,labelEstado);
		rowLayout.setExpandRatio(labelNumero, 0.3f);
		rowLayout.setExpandRatio(labelPuesto, 0.35f);
		rowLayout.setExpandRatio(labelEstado, 0.35f);
		
		return rowLayout;
	}
	
	private void setPublicidad() {
		File publicidadFile = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"images"+File.separator+"publicidad.img");
		
		if(publicidadFile.exists()) {
			Image publicidad = new Image(null,new FileResource(publicidadFile));
			publicidad.setWidth(100, Sizeable.Unit.PERCENTAGE);
			publicidad.setHeight(100, Sizeable.Unit.PERCENTAGE);
			
			publicidadLayout.removeAllComponents();
			publicidadLayout.addComponent(publicidad);
			publicidadLayout.setComponentAlignment(publicidad, Alignment.MIDDLE_CENTER);
			
			publicidadLayout.setWidth(110,Sizeable.Unit.PIXELS);
			publicidadLayout.setHeight(60,Sizeable.Unit.PIXELS); 
			publicidadLayout.setSpacing(false);
			publicidadLayout.setMargin(false);
		}
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub		
	}
}
