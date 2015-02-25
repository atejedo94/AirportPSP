package aeroport;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Fingers {

	enum Estat{ocupat,buit,reservat};
	private Estat estat;
	private Avio avio;
	private int posicioX;
	private int posicioY;
	private String nom;
	
	
	public void paint(Graphics g, float factorX, float factorY, int offsetX, int offsetY) {
        
		int iniX, iniY, finX, finY;
        
		iniX = (int)(( posicioX/ factorX) + offsetX);//5000
        iniY = (int)(( posicioY / factorY) + offsetY);//8000
        finX = (int)((1000 / factorX));
        finY = (int)((1000 / factorY));             
                
        
        g.setColor(Color.BLACK);
        g.fillRect(iniX, iniY, finX, finY);
        g.setColor(Color.BLACK);
        g.drawRect(iniX, iniY, finX, finY);
        
    }//paint
	
	public Fingers(){
		this.estat=Estat.buit;
	}//constructor vasio
	
	public Fingers(String nom,Estat estat,Avio avio,int posicioX,int posicioY){
		this.nom=nom;
		this.estat=estat;
		this.avio=avio;
		this.posicioX=posicioX;
		this.posicioY=posicioY;
	}//constructor

	public void setNom(String nom){
		
		this.nom=nom;
		
	}//set Avio
	
	public String getNom(){
		
		return nom;
		
	}//set Avio
	
	public void setAvio(Avio avio){
		
		this.avio=avio;
		
	}//set Avio
	
	public Avio getAvio(){
		
		return avio;
		
	}//set Avio
	
	public void setPosicioX(int x){
		
		this.posicioX=x;
		
	}//set pos x
	
	public int getPosicioX(){
		
		return posicioX;
		
	}//get pos x
	
	public void setPosicioY(int y){
		
		this.posicioY=y;
		
	}//set pos y
	
	public int getPosicioY(){
		
		return posicioY;
		
	}//get pos y
	
	public void setOcupat(){
		this.estat=Estat.ocupat;
	}//setOcupat
	
	public void setBuit(){
		this.estat=Estat.buit;
		this.avio=null;
	}//setBuit
	
	public void setReservat(){
		this.estat=Estat.reservat;
	}//setReservat
	
	public boolean EstaBuit(){
		
		if(estat==Estat.buit){
			return true;
		}//if
		
		else{ return false;}//else
		
	}//getEstat
	
	
}
