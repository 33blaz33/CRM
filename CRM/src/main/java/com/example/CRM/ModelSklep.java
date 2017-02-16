package com.example.CRM;

import java.util.HashMap;

public class ModelSklep {
	private Integer sklepID;
	private String vrstaSklepa;
	
	private static Integer lastID;	//da vemo od katerega ID, povečujemo
	private static final HashMap<Integer, ModelSklep> vsiSklepi = new HashMap<>();
	
	//konstrukor, ki z ID-jem zaradni pravilnega vnosa testnih podatkov
	public ModelSklep(Integer sklepID, String vrstaSklepa) {
		this.sklepID     = sklepID;
		this.vrstaSklepa = vrstaSklepa;
	}
	
	//konstrukor, ki brez IDja, ker se sam povečuje
	public ModelSklep(String vrstaSklepa) {
		this.sklepID     = lastID++;
		this.vrstaSklepa = vrstaSklepa;
	}
	
	
	//simuliram podatke
	public static HashMap<Integer, ModelSklep> fillData(){
		vsiSklepi.put(0, new ModelSklep(0, "Določitev naslednjega sestanka"));
		vsiSklepi.put(1, new ModelSklep(1, "Pogodba"));
		vsiSklepi.put(2, new ModelSklep(2, "Račun"));
		
		lastID = vsiSklepi.size();		//da vemo od katerega IDja naprej povečevati
		
		return vsiSklepi;
	}
	
	
	public Integer getSklepID() {
		return sklepID;
	}
	
	public void setSklepID(Integer sklepID) {
		this.sklepID = sklepID;
	}
	
	public String getVrstaSklepa() {
		return vrstaSklepa;
	}
	
	public void setVrstaSklepa(String vrstaSklepa) {
		this.vrstaSklepa = vrstaSklepa;
	}
	
}
