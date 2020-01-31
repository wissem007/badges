package tn.com.digivoip.comman.image;

import java.io.Serializable;

public class RehaussementImage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private int fortesDensites;
	private int hautesLumieres;
	private int intensite;
	private int contraste;
	private int decalageNoir;
	private int decalageBlanc;
	
	public RehaussementImage() {
		super();
	}
	
	public RehaussementImage(int fortesDensites, int hautesLumieres, int intensite, int contraste, int decalageNoir, int decalageBlanc) {
		super();
		this.fortesDensites = fortesDensites;
		this.hautesLumieres = hautesLumieres;
		this.intensite = intensite;
		this.contraste = contraste;
		this.decalageNoir = decalageNoir;
		this.decalageBlanc = decalageBlanc;
	}
	
	public int getFortesDensites() {
		return fortesDensites;
	}
	
	public void setFortesDensites(int fortesDensites) {
		this.fortesDensites = fortesDensites;
	}
	
	public int getHautesLumieres() {
		return hautesLumieres;
	}
	
	public void setHautesLumieres(int hautesLumieres) {
		this.hautesLumieres = hautesLumieres;
	}
	
	public int getIntensite() {
		return intensite;
	}
	
	public void setIntensite(int intensite) {
		this.intensite = intensite;
	}
	
	public int getContraste() {
		return contraste;
	}
	
	public void setContraste(int contraste) {
		this.contraste = contraste;
	}
	
	public int getDecalageNoir() {
		return decalageNoir;
	}
	
	public void setDecalageNoir(int decalageNoir) {
		this.decalageNoir = decalageNoir;
	}
	
	public int getDecalageBlanc() {
		return decalageBlanc;
	}
	
	public void setDecalageBlanc(int decalageBlanc) {
		this.decalageBlanc = decalageBlanc;
	}
}
