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
	ArrayList<Carrer> ArrayListFinger1 = new ArrayList<Carrer>();
	
	ArrayList<Carrer> ArrayDespegue = new ArrayList<Carrer>();
	ArrayList<Fingers> arrfinger;
	Mapa map;

	
	
	
	public Controlador(ArrayList<Carrer> ArrayListCarrers, Mapa map){
		this.ArrayListCarrers=ArrayListCarrers;
		this.map = map;
		
		crearRuta();
	}//controlador
	
	private void crearRuta() {
		
//   0 	this.carrers.add(new HCarrer("H1",this.cmCarrerWidth,this.cmCarrerMark, 25000, 50,1000,Direction.FORWARD));
//   1 	this.carrers.add(new HCarrer("H2",this.cmCarrerWidth,this.cmCarrerMark, 22000, 1500,7200,Direction.FORWARD));
//   2 	this.carrers.add(new VCarrer("V1",this.cmCarrerWidth,this.cmCarrerMark, 7000, 1000, 1000,Direction.FORWARD));
//   3	this.carrers.add(new VCarrer("V2",this.cmCarrerWidth,this.cmCarrerMark, 7000, 23500, 1000,Direction.BACKWARD));
//   4 	this.carrers.add(new VCarrer("F1",this.cmCarrerWidth,this.cmCarrerMark,2000,2700,7200,Direction.FORWARD));
//   5	this.carrers.add(new VCarrer("F2",this.cmCarrerWidth,this.cmCarrerMark,2000,5400,7200,Direction.FORWARD));


		ArrayListRuta.add(ArrayListCarrers.get(2));
		ArrayListRuta.add(ArrayListCarrers.get(1));

		
		//New added to go finger:
		ArrayListRuta.add(ArrayListCarrers.get(4));
		
		
		// Despegue de aviones:
		rutaDespegue();

		
	}
	
	private void rutaDespegue(){
		ArrayDespegue.add(ArrayListCarrers.get(1));
		ArrayDespegue.add(ArrayListCarrers.get(3));
		ArrayDespegue.add(ArrayListCarrers.get(0));
		
	}
	
	private void crearRuta1() {
		
	//   0 	this.carrers.add(new HCarrer("H1",this.cmCarrerWidth,this.cmCarrerMark, 25000, 50,1000,Direction.FORWARD));
	//   1 	this.carrers.add(new HCarrer("H2",this.cmCarrerWidth,this.cmCarrerMark, 22000, 1500,7200,Direction.FORWARD));
	//   2 	this.carrers.add(new VCarrer("V1",this.cmCarrerWidth,this.cmCarrerMark, 7000, 1000, 1000,Direction.FORWARD));
	//   3	this.carrers.add(new VCarrer("V2",this.cmCarrerWidth,this.cmCarrerMark, 7000, 23500, 1000,Direction.BACKWARD));
	//   4 	this.carrers.add(new VCarrer("F1",this.cmCarrerWidth,this.cmCarrerMark,2000,2700,7200,Direction.FORWARD));
//     	 5	this.carrers.add(new VCarrer("F2",this.cmCarrerWidth,this.cmCarrerMark,2000,5400,7200,Direction.FORWARD));


		ArrayListFinger1.add(ArrayListCarrers.get(2));
		ArrayListFinger1.add(ArrayListCarrers.get(1));

		
			//New added to go finger:
		ArrayListFinger1.add(ArrayListCarrers.get(5));
			
			
			// Despegue de aviones:
			
		rutaDespegue();
			
		}

	public ArrayList<Avio> getAvio(){
		
		return ArrayListAvions;
		
	};
	
	public void addAvio(String idavio, Carrer way){
		
		Avio avio = new Avio(idavio,way,800, ArrayListRuta, Avio.EstatAvio.LANDING, ArrayDespegue, this, ArrayListFinger1);		
		ArrayListAvions.add(avio);
		avio.start();
		
	}//add avio
//	

	
	
	@Override
	public void run() {
		
		int s=0;
		
		while(true){
			try{
				if (fingerLibre()) {
					addAvio("A1"+s, ArrayListCarrers.get(0));
					s++;
					
					Thread.sleep(3000);

				}
				else{
					System.out.println("NO Empty finger");

				}
			}catch (InterruptedException e) {
				e.printStackTrace();
		}
		}

		
		
//		for(int i=0;i<2;i++){	
//			try {				
//				if (fingerIsEmpty()) {
//	
//					addAvio("A"+i,ArrayListCarrers.get(0));
//				}
//
//
//				
//				
//				Thread.sleep(3000);
//
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		}
		

	}
	
	
	public synchronized void enter(Avio avio){
		
		while(!fingerLibre()){
			try{

				wait();
				System.out.println("Avion aterrizando");
			}catch (InterruptedException e) {
            }
		}
		aparcarAvionEnfinger(avio);
	}
	
	

    private boolean fingerLibre() {
        for (Fingers finger : map.fingers) {
            if (!finger.getOcupado()) {
                return true;
            }
        }
        return false;
    }

    public void aparcarAvionEnfinger(Avio avion) {
        
       Fingers f=  buscarFingerVacio();
       f.setAvio(avion);
       f.setOcupado();
//       f.setEstat(Fingers.Estat.ocupat);       
      
        }
    

    public synchronized void salir(Avio avio) { 
        Fingers f = getFingerAvion(avio);
        f.setAvio(null);
//        f.setEstat(Finger.Estat.VACIO);
        f.setVacio();
        imprimirEstado();
        notifyAll();
    }
    public Fingers buscarFingerVacio() {
        for (Fingers finger : map.fingers) {
            if (!finger.getOcupado()) {
                return finger;
            }
        }
        return null;
    }
    public Fingers getFingerAvion(Avio avion){
        for (Fingers finger : map.fingers) {
            if(finger.getAvio()==avion){
                return finger;
            }
        }return null;
    }
	
    
    public void imprimirEstado() {
        for (Fingers finger : map.fingers) {
            finger.impEstat();
        }
        System.out.println("-------");
    }
//	public boolean isFirstFingerEmpty(){
//		
//		if (map.fingers.get(0).getEstat() == Fingers.Estat.buit) {
//			return true;
//		}
//		
//		return false;
//
//	}
	
//	public boolean isFirstFingerEmpty1(){
//		
//		if (map.fingers.get(1).getEstat() == Fingers.Estat.buit) {
//			return true;
//		}
//		
//		return false;
//
//	}
	
	
//	public void setFirstFingerFull(){
//		
//		arrfinger.get(0).setOcupat();
//		
//	}
	
	
	
	public void deleteAvion(Avio a){
		
		ArrayListAvions.remove(a);
		
	}

	public void paintAvions(Graphics g, float factorX, float factorY,
			int offsetX, int offsetY) {
		Iterator<Avio> itr = ArrayListAvions.iterator();
		
			while (itr.hasNext()) {
	            itr.next().paint(g, factorX, factorY, offsetX, offsetY);
	        }
       
		
	}


}
