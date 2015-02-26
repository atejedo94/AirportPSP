package aeroport;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import aeroport.Avio.Direction;

public class Controlador implements Runnable{
	
	public Avio av;
	final int numMaxAvions=40;
	Carrer c;
	ArrayList<Avio> ArrayListAvions= new ArrayList<Avio>();
	ArrayList<Carrer> ArrayListCarrers=new ArrayList<Carrer>();
	ArrayList<Carrer> ArrayListRuta = new ArrayList<Carrer>();
	ArrayList<Carrer> ArrayDespegue = new ArrayList<Carrer>();
	ArrayList<Fingers> arrfinger;
	Fingers fing;
	Mapa map;

	
	
	
	public Controlador(ArrayList<Carrer> ArrayListCarrers){
		this.ArrayListCarrers=ArrayListCarrers;
		
		crearRuta();
	}//controlador
	
	private void crearRuta() {
		
//   0 	this.carrers.add(new HCarrer("H1",this.cmCarrerWidth,this.cmCarrerMark, 25000, 50,1000,Direction.FORWARD));
//   1 	this.carrers.add(new HCarrer("H2",this.cmCarrerWidth,this.cmCarrerMark, 22000, 1500,7200,Direction.FORWARD));
//   2 	this.carrers.add(new VCarrer("V1",this.cmCarrerWidth,this.cmCarrerMark, 7000, 1000, 1000,Direction.FORWARD));
//   3	this.carrers.add(new VCarrer("V2",this.cmCarrerWidth,this.cmCarrerMark, 7000, 23500, 1000,Direction.BACKWARD));
//   4 	this.carrers.add(new VCarrer("F1",this.cmCarrerWidth,this.cmCarrerMark,2000,2700,7200,Direction.FORWARD));


		ArrayListRuta.add(ArrayListCarrers.get(2));
		ArrayListRuta.add(ArrayListCarrers.get(1));
//		ArrayListRuta.add(ArrayListCarrers.get(3));
//		ArrayListRuta.add(ArrayListCarrers.get(0));
		//New added to go finger:
		ArrayListRuta.add(ArrayListCarrers.get(4));
		
		
		// Despegue de aviones:
		
		ArrayDespegue.add(ArrayListCarrers.get(1));
		ArrayDespegue.add(ArrayListCarrers.get(3));
		ArrayDespegue.add(ArrayListCarrers.get(0));
		
	}

	public ArrayList<Avio> getAvio(){
		
		return ArrayListAvions;
		
	};
	
	public void addAvio(String idavio, Carrer way){
		
		Avio avio = new Avio(idavio,way,800, ArrayListRuta, Avio.EstatAvio.LANDING, ArrayDespegue);		
		ArrayListAvions.add(avio);
		avio.start();
		
	}//add avio
//	

	
	
	@Override
	public void run() {
		
		for(int i=0;i<1;i++){	
			try {				
				
				addAvio("A1",ArrayListCarrers.get(0));
				
				
				Thread.sleep(1000);
//				addAvio("ss",ArrayListCarrers.get(1));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//for		
		

	}

	public void paintAvions(Graphics g, float factorX, float factorY,
			int offsetX, int offsetY) {
		Iterator<Avio> itr = ArrayListAvions.iterator();
		
			while (itr.hasNext()) {
	            itr.next().paint(g, factorX, factorY, offsetX, offsetY);
	        }
       
		
	}
	
	public void impFuckingFingerState(){
		System.out.println(map.fingers.get(0).getEstat());
	}
	
	public void setFirstFingerFull(){
		
		arrfinger.get(0).setOcupat();
		
	}
	
	
	
	public void deleteAvion(Avio a){
		
		ArrayListAvions.remove(a);
		
	}


}
