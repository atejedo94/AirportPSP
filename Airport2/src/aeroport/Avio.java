package aeroport;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Avio extends Thread {

	private CrossRoad cruce = null;
	int position = 0;

	public static enum Orientation {

		NORTH(Direction.BACKWARD), SOUDTH(Direction.FORWARD), WEST(
				Direction.BACKWARD), EAST(Direction.FORWARD);
		private Direction direction;

		private Orientation(Direction direction) {
			this.direction = direction;
		}

		public static Direction getDirection(Orientation orientation) {
			if ((orientation == Orientation.SOUDTH)
					|| (orientation == Orientation.EAST)) {
				return Direction.FORWARD; // =================================>>
			}
			return Direction.BACKWARD;
		}

		public static Orientation getOrientation(Avio avio) {
			return getOrientation(avio.getWay(), avio.getDirection());
		}

		public static Orientation getOrientation(Carrer way, Direction direction) {
			if (way instanceof VCarrer) {
				if (direction == Direction.FORWARD) {
					return Orientation.SOUDTH; // =============================>>
				} else {
					return Orientation.NORTH; // ==============================>>
				}
			}

			if (way instanceof HCarrer) {
				if (direction == Direction.FORWARD) {
					return Orientation.EAST; // ===============================>>
				} else {
					return Orientation.WEST; // ===============================>>
				}
			}

			return Orientation.WEST;
		}

		public static int getDegrees(Orientation orientation) {
			switch (orientation) {
			case NORTH:
				return 0;
			case EAST:
				return 90;
			case SOUDTH:
				return 180;
			case WEST:
				return 270;
			}

			return 0;
		}
	}

	public static enum Direction {

		FORWARD(1), BACKWARD(-1);
		private int increment;

		private Direction(int increment) {
			this.increment = increment;
		}

		public int getIncrement() {
			return this.increment;
		}

		public Orientation getOrientation(Carrer way) {
			if (way instanceof VCarrer) {
				if (this == Direction.FORWARD) {
					return Orientation.SOUDTH; // =============================>>
				} else {
					return Orientation.NORTH; // ==============================>>
				}
			}

			if (way instanceof HCarrer) {
				if (this == Direction.FORWARD) {
					return Orientation.EAST; // ===============================>>
				} else {
					return Orientation.WEST; // ===============================>>
				}
			}

			return Orientation.WEST;
		}

		@Override
		public String toString() {
			switch (this) {
			case FORWARD:
				return "FORWARD";
			case BACKWARD:
				return "BACKWARD";
			}
			return "Undefined";
		}
	}

	public Direction getDirection() {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * private int maxSpeedInCmSecond; private int maxAcceleration; // In units
	 * compatibles with speed private int maxDeceleration; private volatile int
	 * acceleration; private boolean turn;
	 */
	private int cmLong;
	private int cmWidth;
	private volatile int cmPosition; // Car position relative to actual way
	private volatile int speedInCmSecond;
	private volatile long lastUpdateTime;
	private int speed;
	private double course;
	private float factorX, factorY;
	private Image imgCar;
	private volatile Carrer way;
	private Color color;
	private String idAvio;
	private Direction direction;
	private boolean innter;
	private Orientation orientation;
	private ArrayList<Carrer> Ruta, Salida;
	private EstatAvio estat;

	public static enum EstatAvio {

		HIDE, STOP, RUN, TAKINGOFF, LANDING, FLYING, ONFINGER
	};

	public Avio(String idAvio, Carrer way, int speed,
			ArrayList<Carrer> arrayListRuta, EstatAvio estat,
			ArrayList<Carrer> arrayDespegue) {
		this.idAvio = idAvio;
		this.cmLong = 800;
		this.cmWidth = 400;
		this.speed = 20;
		this.color = Color.CYAN;
		this.factorX = this.factorY = -1;
		this.course = -1;
		this.Ruta = arrayListRuta;
		this.Salida = arrayDespegue;
		this.setEstat(estat);

		this.cmPosition = 0;
		this.speedInCmSecond = 0;

		this.setWay(way);
		this.setDirection(Direction.FORWARD);
	}

	public void setEstatAvio(EstatAvio estat) {
		this.setEstat(estat);
	}

	public boolean isInter() {

		return this.way.insideAnyCrossRoad(this.cmPosition);
	}

	public Carrer getWay() {
		// TODO Auto-generated method stub
		return this.way;
	}

	public int getCmPosition() {
		return this.cmPosition;
	}

	public int getLongInCm() {
		// TODO Auto-generated method stub
		return this.cmLong;
	}

	public int getSpeedInCmSecond() {
		return speedInCmSecond;
	}

	public void setSpeedInCmSecond(int speedInCmSecond) {
		this.speedInCmSecond = speedInCmSecond;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getCourse() {
		return course;
	}

	public void setCourse(double course) {
		this.course = course;
	}

	public float getFactorX() {
		return factorX;
	}

	public void setFactorX(float factorX) {
		this.factorX = factorX;
	}

	public float getFactorY() {
		return factorY;
	}

	public void setFactorY(float factorY) {
		this.factorY = factorY;
	}

	public Image getImgCar() {
		return imgCar;
	}

	public void setImgCar(Image imgCar) {
		this.imgCar = imgCar;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getIdCar() {
		return idAvio;
	}

	public void setIdCar(String idCar) {
		this.idAvio = idCar;
	}

	public void setCmPosition(int cmPosition) {
		this.cmPosition = cmPosition;
	}

	public void setWay(Carrer way) {
		this.way = way;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getWidthInCm() {
		// TODO Auto-generated method stub
		return this.cmWidth;
	}

	public void setPositionInCm(int cmPos) {
		this.cmPosition = cmPos;

	}

	public int getPositionInCm() {
		return cmPosition;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public boolean isOnCrossRoad() {
		return this.getWay().insideAnyCrossRoad(this.getCmPosition());
	}

	public CrossRoad getCrossRoad() {
		return this.getWay().intersectedCrossRoad(this.getCmPosition());
	}

	public synchronized void paint(Graphics g, float factorX, float factorY,
			int offsetX, int offsetY) {
		int iniX = 0, iniY = 0, finX = 0, finY = 0;

		finY = (int) (((this.cmWidth) / factorY));
		finX = (int) (((this.cmWidth) / factorX));

		if (way instanceof HCarrer) {
			iniY = (int) ((((this.way.cmFinY + this.way.cmIniY) / 2 - 200) / factorY) + offsetY);
			iniX = (int) (((this.way.cmIniX + this.cmPosition) / factorX) + offsetX);

			try {

				this.imgCar = ImageIO.read(new File("planeAir.png"));
			} catch (Exception e) {
			}

		} else if (way instanceof VCarrer) {
			iniY = (int) (((this.way.cmIniY + this.cmPosition) / factorY) + offsetY);
			iniX = (int) ((((this.way.cmIniX + way.cmFinX) / 2 - 200) / factorX) + offsetX);

			try {

				this.imgCar = ImageIO.read(new File("planeAir2.png"));
			} catch (Exception e) {
			}

		}

		// Paint crossroad
		/*
		 * g.setColor(Color.MAGENTA); g.fillRect(iniX, iniY, finX, finY);
		 * 
		 * g.setColor(Color.BLACK); g.drawRect(iniX, iniY, finX, finY);
		 */
		g.drawImage(this.imgCar, iniX, iniY, finX, finY, null);

	}

	public void waitTime() {

		try {
			Thread.sleep(7);
			if (this.direction == Direction.FORWARD) {
				this.cmPosition += this.speed;
			} else if (this.direction == Direction.BACKWARD) {
				this.cmPosition -= this.speed;
			}
		} catch (InterruptedException ex) {
		}

	}

	public void run() {
		direction = Direction.FORWARD;

		while (true) {

			// System.out.println(this.getEstat());

			if (estat == EstatAvio.LANDING) {
				cambiarCalle(Ruta);
			} else if (estat == EstatAvio.ONFINGER) {
				onFinger();
				position = 0;
			}

			else if (estat == EstatAvio.TAKINGOFF) {
				onTakingOff(Salida);

			}

			// System.out.println("Pos: "+this.getCmPosition());

		}

	}

	private void onTakingOff(ArrayList<Carrer> salida) {

		this.setDirection(Direction.BACKWARD);
		this.setSpeed(5);

		// System.out.println(position);

		if (way.insideAnyCrossRoad(cmPosition)) {

			// System.out.println("is on a crossroad");

			cruce = way.intersectedCrossRoad(cmPosition);
			if (position < salida.size()) {
				if (cruce.getCarrer(way).equals(salida.get(position))) {
					Carrer anterior = way;
					this.way = cruce.getCarrer(way);
					this.direction = way.dir;
					this.cmPosition = this.way
							.getCmPosition(anterior.getCmPosX(this.cmPosition,
									this.direction), anterior.getCmPosY(
									this.cmPosition, this.direction),
									this.direction);

					position++;

				}
			}

		}
		waitTime();

		if (this.way.getId().contains("H2")) {
			System.out.println("Estoy en H2!");
			this.setDirection(Direction.FORWARD);
		}

		// TO- Do, que este metodo solo sea para salir del finger, hacer otro
		// que sirva para despegar?
	}

	private void onFinger() {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setEstat(EstatAvio.TAKINGOFF);

		// if (this.cmPosition > way.cmLong - this.cmLong) {
		// System.out.println("Estoy en el finger");
		//
		// // this.setSpeed(0);
		//
		//
		//
		//
		// }

	}

	private void cambiarCalle(ArrayList<Carrer> ruta2) {

		if (way.insideAnyCrossRoad(cmPosition)) {

			cruce = way.intersectedCrossRoad(cmPosition);
			if (position < ruta2.size()) {
				if (cruce.getCarrer(way).equals(ruta2.get(position))) {

					Carrer anterior = way;
					this.way = cruce.getCarrer(way);
					Carrer actual = way;

					this.direction = way.dir;

					this.cmPosition = this.way
							.getCmPosition(anterior.getCmPosX(this.cmPosition,
									this.direction), anterior.getCmPosY(
									this.cmPosition, this.direction),
									this.direction);

					position++;

				}
			}

		}

		waitTime();

		if (this.way.getId().contains("F1") && !this.isOnCrossRoad()) {
			this.setEstat(EstatAvio.ONFINGER);
		}

		// if (this.way.getId().contains("F1")) {
		//
		// this.setEstat(EstatAvio.ONFINGER);
		// }

	}

	public EstatAvio getEstat() {
		return estat;
	}

	public void setEstat(EstatAvio estat) {
		this.estat = estat;
	}

	public ArrayList<Carrer> getSalida() {
		return Salida;
	}

	public void setSalida(ArrayList<Carrer> salida) {
		Salida = salida;
	}
}
