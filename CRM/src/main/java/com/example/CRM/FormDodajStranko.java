package com.example.CRM;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class FormDodajStranko extends Window {
	private Button btnSave 	= new Button("Shrani");
	private Button btnClose = new Button("Prekliči");
	private TextField textFName	= new TextField();
	private TextField textLName = new TextField();
	private TextField textPhone	= new TextField();
	private TextField textEmail	= new TextField();
	
	//konstrukotr, ki se pokliče ob dodajanju nove stranke
	public FormDodajStranko() {
		super("Dodaj stranko"); // Set window caption
	    center();
	    init();
	    
	    //ob pritiskku na gumb save dodaj novo stranko
	    btnSave.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(!validate())
					return;
				
				ModelStranka modelStranka = new ModelStranka(
														textFName.getValue(),
														textLName.getValue(), 
														textEmail.getValue(),
														textPhone.getValue());
				
				//poizkusi dodati novo stranko
				try{
					modelStranka.insert();
					MyUI.updateList();								//žal moram narediti tako, ker nisam našel kode ONRESOULT OK
				}
				catch (Exception e) {
					System.out.println(e);
				}

				close();	
			}
		});
	}
	
	//konstruktor, ki se pokliče ob urejanju
	public FormDodajStranko(ModelStranka modelStranka) {
		super("Uredi stranko"); // Set window caption
	    center();
		init();
		
		//zapolni vrednosti celic iz grida
		textFName.setValue(modelStranka.getIme());
		textLName.setValue(modelStranka.getPriimek());
		textEmail.setValue(modelStranka.getEmail());
		textPhone.setValue(modelStranka.getTelefon());
		
		//ob prisisku na gumb save posodobi stranko
		btnSave.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(!validate())
					return;
				
				modelStranka.setIme(textFName.getValue());
				modelStranka.setPriimek(textLName.getValue());
				modelStranka.setEmail(textEmail.getValue());
				modelStranka.setTelefon(textPhone.getValue());
				System.out.println("FDS urejam stranko: " + modelStranka.toString() + " " + modelStranka.getStrankaID());
				
				//poizkusi posodobiti
				try{
					modelStranka.update();
				}
				catch (Exception e) {
					System.out.println(e);
				}
				
				close();
			}
		});
		
	}

	
	//inicializacija gradnikov
	private void init(){
		// postavi gradnike
	    VerticalLayout content 		= new VerticalLayout();
	    HorizontalLayout buttons	= new HorizontalLayout();
	    buttons.addComponents(btnSave, btnClose);
	    content.addComponents(textFName, textLName, textPhone, textEmail, buttons);
	    
	    
	    //določi imena polj
	    textFName.setInputPrompt("Ime");
	    textLName.setInputPrompt("Priimek");
	    textPhone.setInputPrompt("Telefon");
	    textEmail.setInputPrompt("Email");
	    
	    
	    //gumb za preklic dodajanja nove stranke
	    btnClose.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				close(); 
			}
		});
	    
	    
	    //obarvaj gumbe
	    btnClose.setStyleName(ValoTheme.BUTTON_DANGER);
	    btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
	    
	    //postavi gradnike
	    content.setComponentAlignment(buttons, Alignment.BOTTOM_CENTER);
	    buttons.setSpacing(true);
	    content.setSpacing(true);
	    content.setMargin(true);
	    setContent(content);
		
	}
	
	//dodaj validacijo polj, polja ne smejo biti prazna
	private boolean validate(){
		boolean isOk = false;
		
		//obarvaj polje rdeče, če ni bilo izpolnjeno
		textFName.addValidator(new StringLengthValidator("Pustili ste prazno polje", 1, 30, true));
		textLName.addValidator(new StringLengthValidator("Pustili ste prazno polje", 1, 30, true));
		textPhone.addValidator(new StringLengthValidator("Pustili ste prazno polje", 1, 30, true));
		textEmail.addValidator(new StringLengthValidator("Pustili ste prazno polje", 1, 30, true));
		
		//če so vsa polja polna potem je bila validacija uspešna
		if(textFName.getValue().length() > 0 &&
			textLName.getValue().length() > 0 &&
			textPhone.getValue().length() > 0 &&
			textEmail.getValue().length() > 0)
			isOk = true;
		
		return isOk;
			
	}
}
