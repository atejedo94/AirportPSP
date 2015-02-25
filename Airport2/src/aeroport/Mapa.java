package aeroport;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import aeroport.Avio.Direction;


public class Mapa extends Canvas implements Runnable {

    private int cityCmWidth;
    private int cityCmHeight;
    private int mapWidth;
    private int mapHeigth;
    private float factorX;
    private float factorY;
    private float zoomLevel;
    private int offsetX;
    private int offsetY;
    private int cmCarrerWidth;
    private int cmCarrerMark;
    private BufferedImage imgMap, imgBg ,imgFing;
    private Controlador controlador;
    private ArrayList<Carrer> carrers;
    private ArrayList<CrossRoad> crossroads;
    private ArrayList<Fingers> fingers;
    private Carrer way;

    public Mapa(int cityCmWidth, int cityCmHeight, int mapPixWidth, int mapPixHeight) {
        this.cityCmWidth = cityCmWidth;
        this.cityCmHeight = cityCmHeight;
        this.mapWidth = mapPixWidth;
        this.mapHeigth = mapPixHeight;

        this.offsetX = 0;
        this.offsetY = 0;
        this.zoomLevel = 2;
        this.setFactorXY();

        this.cmCarrerWidth = 800;
        this.cmCarrerMark = 300; // Longitud marcas viales en cm

        this.carrers = new ArrayList<Carrer>();
        this.crossroads = new ArrayList<CrossRoad>();
        this.fingers = new ArrayList<Fingers>();

        this.loadCarrers();
        this.calculateCrossRoads();
        this.crearFingers();
       

        Dimension d = new Dimension(700, 572);
        this.setPreferredSize(d);

        try {
            this.imgBg = ImageIO.read(new File("bg.jpg"));
        } catch (IOException e) {
            System.out.println("Img Error: not found bg.jpg");
        }
    }

    public ArrayList<Carrer> getCarrers() {
        return this.carrers;
    }

    public void setWidth(int mapWidth) {
        this.mapWidth = mapWidth;
        this.setFactorXY();
    }

    public void setHeig(int mapHeigth) {
        this.mapHeigth = mapHeigth;
        this.setFactorXY();
    }


    public void setFactorXY() {
        this.mapWidth = this.getWidth();
        this.mapHeigth = this.getHeight();

        this.factorX = ((float) this.cityCmWidth / (float) this.mapWidth / this.zoomLevel);
        this.factorY = ((float) this.cityCmHeight / (float) this.mapHeigth / this.zoomLevel);
        this.paintImgMap();
    }

    private boolean addCrossRoad(CrossRoad newCr) {
        Iterator<CrossRoad> itr = this.crossroads.iterator();
        while (itr.hasNext()) {
            if (itr.next().equals(newCr)) {
                return false;  // ====== Crossroad duplicated ================>>
            }
        }

        this.crossroads.add(newCr);
        return true;
    }

    private void calculateCrossRoads() {
        Iterator<Carrer> itrCarrers1;
        Iterator<Carrer> itrCarrers2;
        Carrer auxCarrer1, auxCarrer2;

        itrCarrers1 = this.carrers.iterator();
        while (itrCarrers1.hasNext()) {
            auxCarrer1 = itrCarrers1.next();

            itrCarrers2 = this.carrers.iterator();
            while (itrCarrers2.hasNext()) {
                auxCarrer2 = itrCarrers2.next();
                if (auxCarrer1.carrerIntersection(auxCarrer2)) {
                    this.addCrossRoad(new CrossRoad(auxCarrer1, auxCarrer2));
                }
            }
        }
    }

    private void loadCarrers() {
        //this.carrers.add(new HCarrer("H1", this.cmCarrerWidth, this.cmCarrerMark, 59800, 0, 0));
        //this.carrers.add(new VCarrer("V1", this.cmCarrerWidth, this.cmCarrerMark, 20000, 0, 100));
    	this.carrers.add(new HCarrer("H1",this.cmCarrerWidth,this.cmCarrerMark, 25000, 50,1000,Direction.BACKWARD));
    	this.carrers.add(new HCarrer("H2",this.cmCarrerWidth,this.cmCarrerMark, 22000, 1500,7200,Direction.FORWARD));
    	this.carrers.add(new VCarrer("V1",this.cmCarrerWidth,this.cmCarrerMark, 7000, 1000, 1000,Direction.FORWARD));
    	this.carrers.add(new VCarrer("V2",this.cmCarrerWidth,this.cmCarrerMark, 7000, 23500, 1000,Direction.BACKWARD));
    	
    	// Calle Fingers             cmLong,cmPosIniX,CmPosIniY ,Direction,
    	this.carrers.add(new VCarrer("F1",this.cmCarrerWidth,this.cmCarrerMark,2000,2700,7200,Direction.FORWARD));
        
    }
    
    private void crearFingers(){
    	String nom = "finger";
    	int auxX=8000;
    	int auxY=8000;
    	for(int i=0;i<8;i++){
    		Fingers fing = new Fingers();
    		fing.setNom(nom+i);
    		fing.setPosicioX((auxX*(i+1))/3);
    		fing.setPosicioY(auxY);
    		this.fingers.add(fing);
    	}//for
    	
    }//crear fingers
    

    public void moveRight() {
        this.offsetX += 10;
        this.setFactorXY();
    }

    public void moveLeft() {
        this.offsetX -= 10;
        this.setFactorXY();
    }

    public void moveDown() {
        this.offsetY += 10;
        this.setFactorXY();
    }

    public void moveUp() {
        this.offsetY -= 10;
        this.setFactorXY();
    }

    public synchronized void paint() {
        BufferStrategy bs;
        bs = this.getBufferStrategy();
        if (bs == null) {
            return; 
        }
        
        if ((this.mapWidth < 0) || (this.mapHeigth < 0)) {
           // System.out.println("Map size error: (" + this.mapWidth + "," + this.mapHeigth + ")");
            return; 
        }        
        
        Graphics gg = bs.getDrawGraphics();

        gg.drawImage(this.imgMap, 0, 0, this.mapWidth, this.mapHeigth, null);
        this.paintAvions(gg);
       
        bs.show();

        gg.dispose();
    }

    public void paintBackgroud(Graphics g) {
        g.drawImage(this.imgBg, 0, 0, null);
        
        try{
    		//this.imgFing = ImageIO.read(new File("fing.jpg"));
    	}//try
    	catch(Exception e){
    		System.out.println(e.toString());
    	}//catch
        
    }



    public void paintCrossRoads(Graphics g) {
        Iterator<CrossRoad> itr = this.crossroads.iterator();

        while (itr.hasNext()) {
            itr.next().paint(g, this.factorX, this.factorY, this.offsetX, this.offsetY);
        }
    }
    
    private void paintFingers(Graphics g){
    	Iterator<Fingers> itr = this.fingers.iterator();

        while (itr.hasNext()) {
            itr.next().paint(g, this.factorX, this.factorY, this.offsetX, this.offsetY);
        }

    }//paint fingers
    
    private void paintAvions(Graphics g){
    	
    	this.controlador.paintAvions(g, this.factorX, this.factorY, this.offsetX, this.offsetY);
    	
    }//paintavions
    
    private void paintTerminal(Graphics g, float factorX, float factorY, int offsetX, int offsetY){
    	 int iniX, iniY, finX, finY;
    	 
    	 iniX = (int)((2000 / factorX) + offsetX);
         iniY = (int)((9000 / factorY) + offsetY);
         finX = (int)((21200 / factorX));
         finY = (int)((3000 / factorY));                
                 
         // Paint crossroad
         g.setColor(Color.black);
         g.fillRect(iniX, iniY, finX, finY);
         g.setColor(Color.BLACK);
         g.drawRect(iniX, iniY, finX, finY);
    }//terminal

    public synchronized void paintImgMap() {
        if ((this.mapWidth <= 0) || (this.mapHeigth <= 0)) {
           // System.out.println("Map size error: (" + this.mapWidth + "," + this.mapHeigth + ")");
            return; 
        }

        this.imgMap = new BufferedImage(this.mapWidth, this.mapHeigth, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = this.imgMap.createGraphics();

        this.paintBackgroud(g);
        this.paintCarrers(g);
        this.paintCrossRoads(g);
        this.paintFingers(g);
        this.paintTerminal(g,this.factorX, this.factorY, this.offsetX, this.offsetY);
        g.dispose();
    }

    public void paintCarrers(Graphics g) {
        Iterator<Carrer> itr = this.carrers.iterator();

        while (itr.hasNext()) {
            itr.next().paint(g, this.factorX, this.factorY, this.offsetX, this.offsetY);
        }
    }

    @Override
    public void run() {
        this.createBufferStrategy(2);

        while (!Aeroport.isEnd()) {
            this.paint();

            do {
                try {
                    Thread.sleep(7); // nano -> ms
                } catch (InterruptedException ex) {
                }
            } while (Aeroport.isPaused());
        }
    }

    public void zoomIn() {
        this.zoomIn(0.01f);
    }

    public void zoomIn(float inc) {
        this.zoomLevel += inc;
        this.setFactorXY();

    }

    public void zoomOut() {
        this.zoomOut(0.01f);
        this.setFactorXY();
    }

    public void zoomOut(float inc) {
        this.zoomLevel -= inc;
        this.setFactorXY();
    }

    public void zoomReset() {
        this.zoomLevel = 1;
        this.setFactorXY();
    }

	public void setControlador(Controlador traffic) {
		this.controlador=traffic;
		
	}
}