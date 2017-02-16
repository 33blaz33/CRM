package com.example.CRM;

import java.util.HashMap;

public class ModelSestanek {
	private Integer sestanekID;
	private Integer sklepID;
	private Integer strankaID;
	private String lokacija;
	private String ura;
	private String datumPricetka;			//ne pozabi String kasneje spremeniti v date
	private String datumZakljucka;
	
	private static Integer lastID;			//da vemo od katerega ID, povečujemo
	private static final HashMap<Integer, ModelSestanek> vsiSestanki = new HashMap<>();
	
	//konstrukor, za pravilno dodajanje testnih podatkov
	public ModelSestanek(Integer sestanekID, Integer sklepID, Integer strankaID,
						String lokacija, String ura, String datumPricetka, String datumZakljucka){
		this.sestanekID = sestanekID;
		this.sklepID 	= sklepID;
		this.strankaID	= strankaID;
		this.lokacija	= lokacija;
		this.ura		= ura;
		this.datumPricetka 	= datumPricetka;
		this.datumZakljucka = datumZakljucka;
	}
	
	//konstrukor, ki samodejno povečuje ID
	public ModelSestanek(Integer sklepID, Integer strankaID,
						String lokacija, String ura, String datumPricetka, String datumZakljucka){
		this.sestanekID = lastID++;
		this.sklepID 	= sklepID;
		this.strankaID	= strankaID;
		this.lokacija	= lokacija;
		this.ura		= ura;
		this.datumPricetka 	= datumPricetka;
		this.datumZakljucka = datumZakljucka;
	}
	
	//simuliram podatke
	public static HashMap<Integer, ModelSestanek> fillData(){
		vsiSestanki.put(0, new ModelSestanek(0, 1, 1, "Trnovo" , "16:00", "" , ""));
		vsiSestanki.put(1, new ModelSestanek(1, 2, 2, "Trnovo" , "16:00", "" , ""));
		vsiSestanki.put(2, new ModelSestanek(2, 2, 2, "Moste" , "16:00", "" , ""));
		vsiSestanki.put(3, new ModelSestanek(3, 3, 3, "Trnovo" , "16:00", "" , ""));
		vsiSestanki.put(4, new ModelSestanek(4, 3, 4, "Trnovo" , "16:00", "" , ""));
		
		lastID = vsiSestanki.size();		//da vemo od katerega IDja naprej povečevati
		
		return vsiSestanki;
	}
	
	
	public Integer getSestanekID() {
		return sestanekID;
	}
	
	public void setSestanekID(Integer sestanekID) {
		this.sestanekID = sestanekID;
	}
	
	public Integer getSklepID() {
		return sklepID;
	}
	
	public void setSklepID(Integer sklepID) {
		this.sklepID = sklepID;
	}
	
	public Integer getStrankaID() {
		return strankaID;
	}
	
	public void setStrankaID(Integer strankaID) {
		this.strankaID = strankaID;
	}
	
	public String getLokacija() {
		return lokacija;
	}
	
	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}
	
	public String getUra() {
		return ura;
	}
	
	public void setUra(String ura) {
		this.ura = ura;
	}
	
	public String getDatumPricetka() {
		return datumPricetka;
	}
	
	public void setDatumPricetka(String datumPricetka) {
		this.datumPricetka = datumPricetka;
	}
	
	public String getDatumZakljucka() {
		return datumZakljucka;
	}
	
	public void setDatumZakljucka(String datumZakljucka) {
		this.datumZakljucka = datumZakljucka;
	}
}
