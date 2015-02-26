package aeroport;

import java.awt.Color;
import java.awt.Graphics;

import aeroport.Fingers.Estat;

public class Fingers {

	enum Estat {
		OCUPADO, VACIO, RESERVADO
	};

	private Estat estat;
	private Avio avio;
	private int posicioX;
	private int posicioY;
	private String nom;

	public void paint(Graphics g, float factorX, float factorY, int offsetX,
			int offsetY) {

		int iniX, iniY, finX, finY;

		iniX = (int) ((posicioX / factorX) + offsetX);
		iniY = (int) ((posicioY / factorY) + offsetY);
		finX = (int) ((1000 / factorX));
		finY = (int) ((1000 / factorY));

		g.setColor(Color.BLACK);
		g.fillRect(iniX, iniY, finX, finY);
		g.setColor(Color.BLACK);
		g.drawRect(iniX, iniY, finX, finY);

	}

	public Fingers() {
		this.estat = Estat.VACIO;
	}

	public Fingers(String nom, Estat estat, Avio avio, int posicioX,
			int posicioY) {
		this.nom = nom;
		this.estat = estat;
		this.avio = avio;
		this.posicioX = posicioX;
		this.posicioY = posicioY;
		this.estat = Estat.VACIO;
	}

	public void setNom(String nom) {

		this.nom = nom;

	}
	


	public Estat getEstat() {
		return estat;
	}

	public String getNom() {

		return nom;

	}

	public void setAvio(Avio avio) {

		this.avio = avio;

	}

	public Avio getAvio() {

		return avio;

	}

	public void setPosicioX(int x) {

		this.posicioX = x;

	}

	public int getPosicioX() {

		return posicioX;

	}

	public void setPosicioY(int y) {

		this.posicioY = y;

	}

	public int getPosicioY() {

		return posicioY;

	}

	public void setOcupado() {
		this.estat = Estat.OCUPADO;
	}
	
	public boolean getOcupado(){
		return estat == Estat.OCUPADO;
	}

	public void setVacio() {
		this.estat = Estat.VACIO;
	}

	public void setReservado() {
		this.estat = Estat.RESERVADO;
	}
	
	public boolean getReservado(){
		
		return estat == Estat.RESERVADO;
	}

	public boolean getEstaVacio() {

		return estat == Estat.VACIO;

	}
	
	public void impEstat(){
		System.out.println(this.getEstat());
	}


}
