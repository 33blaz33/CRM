package com.example.CRM;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SuppressWarnings("serial")
@Theme("mytheme")
public class MyUI extends UI {
	private static Grid grid = new Grid();							//seznam strank
	private static TextField filterText= new TextField();			//tb za iskanje po imenu
	private Button btnNew 		= new Button("Dodaj");				//gumb za dodajanje stranke 
	private Button btnEdit 		= new Button("Uredi");				//gumb za urejanje stranke
	private Button btnDelete 	= new Button("Izbriši");			//gumb za izbris stranke
	private Button btnClearFilter = new Button(FontAwesome.TIMES);	//počisti filter
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	VerticalLayout layout	 = new VerticalLayout();
    	HorizontalLayout buttons = new HorizontalLayout();			//layout za gumbe
    	CssLayout filtering 	 = new CssLayout();		 			//naredi iskanje in gumb v isti vrstici
    	buttons.addComponents(btnNew, btnEdit, btnDelete);			//dodaj gumbe v layout
    	filtering.addComponents(filterText, btnClearFilter);
    	layout.addComponents(filtering, grid, buttons);
    	
    	ModelStranka.fillData();									//simuliram podatke strank
    	
    	//iskanje strank
    	filterText.setInputPrompt("Išči po imenu ali priimku");		
    	filterText.addTextChangeListener(e -> {						//iskanje se izvrši ob prenehanju pisanja
			grid.setContainerDataSource(new BeanItemContainer<>(ModelStranka.class,
					ModelStranka.findAll(e.getText())));
		});
    	
    	btnClearFilter.addClickListener(e -> {						//počisti iskanje
			filterText.clear();
			updateList();											//ne pozabi osvežiti podatkov
		});
    	
    	//seznam strank
    	grid.setColumns("ime", "priimek", "telefon", "email");
    	grid.setSelectionMode(SelectionMode.SINGLE);
    	
    	//dodajanje nove stranke
    	btnNew.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
		        UI.getCurrent().addWindow(new FormDodajStranko()); 
			}
		});
    	
    	//urejanje stranke
    	btnEdit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
	    		Object selection= grid.getSelectedRow();
	    		if(selection != null){
	    			UI.getCurrent().addWindow(new FormDodajStranko((ModelStranka)selection));
	    		}
			}
		});

    	//malo je buggy, če dvokliknemo vrstico bi jo molari urejati
    	/*grid.addListener(new ItemClickListener() {
			
			public void itemClick(ItemClickEvent event) {
				if(event.getButton() == ItemClickEvent.BUTTON_RIGHT){
					System.out.println("desniklik");
		    		Object selection= grid.getSelectedRow();
		    		if(selection != null){
		    			UI.getCurrent().addWindow(new FormDodajStranko((ModelStranka)selection));
		    		}
				}
				
			}
		});*/

    	//izbriši izbrano stranko
    	btnDelete.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Object selection= grid.getSelectedRow();
	    		if(selection != null){
	    			ModelStranka mStranka = (ModelStranka)selection;
	    			
	    			try{
	    				mStranka.delete();
	    				System.out.println(mStranka.toString() + " " + mStranka.getStrankaID());
	    				updateList();
	    			}
	    			catch (Exception e) {
						System.out.println(e);
					}
	    		}
			}
		});
    	
    	
    	updateList();												//napolnim seznam strank
    	
    	
    	//polepšave
    	btnDelete.setStyleName(ValoTheme.BUTTON_DANGER);
    	btnNew.setStyleName(ValoTheme.BUTTON_PRIMARY);
    	btnEdit.setStyleName(ValoTheme.BUTTON_PRIMARY);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filterText.setWidth("250px");
    	grid.setWidth("600px");
		
    	//prikaz gradnikov    					
    	layout.setMargin(true);
   		layout.setSpacing(true);
   		buttons.setSpacing(true);
   		setContent(layout);
    }
    
    //posodobi list
    public static void updateList() {
   		grid.setContainerDataSource(new BeanItemContainer<>(ModelStranka.class, ModelStranka.findAll(filterText.getValue())));
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
