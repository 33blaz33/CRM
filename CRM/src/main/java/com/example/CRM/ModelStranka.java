package com.example.CRM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ModelStranka {
	private Integer strankaID;
	private String ime;
	private String priimek;
	private String email;
	private String telefon;			//v tipu string zaradi raznih oznak, npr. +386
	
	private static Integer lastID;	//da vemo od katerega ID, povečujemo
	private static final HashMap<Integer, ModelStranka> vseStranke = new HashMap<>();
	
	//konstruktor brez ID-ja, ker se avtomatsko povečuje
	public ModelStranka(String ime, String priimek, String email, String telefon) {
		this.strankaID 	= lastID++;
		this.ime 	 	= ime;
		this.priimek 	= priimek;
		this.email 	 	= email;
		this.telefon 	= telefon;
	}
	
	//konstrukotr z ID-jem zaradi pravilnega delovanja testnih vrednosti
	public ModelStranka(Integer strankaID, String ime, String priimek, String email, String telefon) {
		this.strankaID 	= strankaID;
		this.ime 	 	= ime;
		this.priimek 	= priimek;
		this.email 	 	= email;
		this.telefon 	= telefon;
	}
	
	//simuliram podatke
	public static HashMap<Integer, ModelStranka> fillData(){
		vseStranke.put(0, new ModelStranka(0, "Rok", "Rupnik", "rr@a.a", "031 314 051"));
		vseStranke.put(1, new ModelStranka(1, "Jože", "Novak", "jn@a.a", "031 314 456"));
		vseStranke.put(2, new ModelStranka(2, "Marko", "Stare", "ms@a.a", "031 334 051"));
		vseStranke.put(3, new ModelStranka(3, "Rene", "Mihelič", "rm@a.a", "+386 41 214 051"));
		
		lastID = vseStranke.size();		//da vemo od katerega IDja naprej povečevati
		
		return vseStranke;
	}

	//iskalnik strank po imenu ali priimku
	public static synchronized List<ModelStranka> findAll(String stringFilter) {
		ArrayList<ModelStranka> arrayList = new ArrayList<>();
		for (ModelStranka stranka : vseStranke.values()) {
			boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
					|| stranka.toString().toLowerCase().contains(stringFilter.toLowerCase());
			if (passesFilter)
				arrayList.add(stranka);
		}
		
		Collections.sort(arrayList, new Comparator<ModelStranka>() {
			@Override
			public int compare(ModelStranka o1, ModelStranka o2) {
				return (int) (o2.getStrankaID() - o1.getStrankaID());
			}
		});
		return arrayList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (this.strankaID == null)
			return false;

		if (obj instanceof ModelStranka && obj.getClass().equals(getClass()))
			return this.strankaID.equals(((ModelStranka) obj).strankaID);

		return false;
	}
	
	//izbriši stranko
	public void delete(){
		vseStranke.remove(this.strankaID);
	}
	
	//posodobi obstoječo stranko
	public void update(){
		vseStranke.replace(this.strankaID, new ModelStranka(ime, priimek, email, telefon));
	}
	
	//dodaj novo stranko
	public void insert(){
		vseStranke.put(ModelStranka.lastID, new ModelStranka(ModelStranka.lastID, ime, priimek, email, telefon));
	}
	
	
	//geterji in seterji
	public Integer getStrankaID() {
		return strankaID;
	}
	
	public void setStrankaID(Integer StrankaID) {
		this.strankaID = StrankaID;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPriimek() {
		return priimek;
	}
	
	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	//če želiš iskanje po še drugem atributu, ga dodaj tukaj
	@Override
	public String toString(){
		return this.ime + " " + this.priimek;
	}
}