package aeroport;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;


import aeroport.Avio;
import aeroport.Avio.Direction;
import aeroport.Carrer;


public class HCarrer extends Carrer {

private CrossRoad cr;

public HCarrer(String idWay, int cmWayWidth, int cmWayMark, int cmLong, int cmPosIniX, int cmPosIniY, Direction dir) {
super(idWay, cmWayWidth, cmWayMark, cmLong, cmPosIniX, cmPosIniY, dir);

this.cmFinX = this.cmIniX + this.cmLong;
this.cmFinY = this.cmIniY + this.cmWidth;
}

@Override
public boolean posIsInside(int cmPosition, Direction direction) {
int cmPosX = cmPosX = this.cmIniX + cmPosition;

if (cmPosX < this.cmIniX) {
return false;
}
if (cmPosX > this.cmFinX) {
return false;
}

return true;
}

@Override
public int distanceToCrossRoadInCm(CrossRoad cr, Avio car) {
int crossRoadPos;

crossRoadPos = cr.getIniX();
if (car.getDirection() == Direction.FORWARD) {
crossRoadPos = cr.getFinX();
}
return car.getDirection().getIncrement() * (crossRoadPos - this.getCmPosX(car.getCmPosition(), car.getDirection()));
}

@Override
public CrossRoad inFrontCrossRoad(Avio car) {
int minDistance;
int actualDistance;
int crossRoadPos;
CrossRoad inFrontCR, actualCR;
Iterator<CrossRoad> itr;

inFrontCR = null;
minDistance = this.getCmLong() + 1;
itr = this.crossRoads.iterator();
while (itr.hasNext()) {
actualCR = itr.next();

crossRoadPos = actualCR.getIniX();
if (car.getDirection() == Direction.FORWARD) {
crossRoadPos = actualCR.getFinX();
}
actualDistance = car.getDirection().getIncrement() * (crossRoadPos - this.getCmPosX(car.getCmPosition(), car.getDirection()));

if ((actualDistance < minDistance) && (actualDistance > 0)) {
minDistance = actualDistance;
inFrontCR = actualCR;
}
}

return inFrontCR;
}

@Override
public boolean insideAnyCrossRoad(int cmPosition) {
return this.intersectedCrossRoad(cmPosition) != null;
}

@Override
public boolean insideThisCrossRoad(int cmPosX, CrossRoad crossRoad) {
return ((cmPosX >= crossRoad.getIniX())
&& (cmPosX <= crossRoad.getFinX()));
}

@Override
public CrossRoad intersectedCrossRoad(int cmPosition) {
CrossRoad cr;
int cmPosX;

cmPosX = this.getCmPosX(cmPosition, Direction.FORWARD);
Iterator<CrossRoad> itr = this.crossRoads.iterator();
while (itr.hasNext()) {
cr = itr.next();

if (this.insideThisCrossRoad(cmPosX, cr)) {
return cr; // ==============================================>>
}
}

return null;
}

public double getCourse(int cmPosition, Direction direction) {
if (direction == null) {
return 0; // =====================================================>>
}

if (direction == Direction.FORWARD) {
return Math.PI / 2;
} else {
return Math.PI * 3 / 2;
}
}

public int getCmPosX(int cmPosition, Avio.Direction direction) {
int cmPosX;

cmPosX = this.cmIniX + cmPosition;
if (cmPosX < this.cmIniX || cmPosX > this.cmFinX) {
return -1; // ============== Off road ============================>>
}

return cmPosX;
}

public int getCmPosY(int cmPosition, Avio.Direction direction) {
if (direction == Avio.Direction.FORWARD) {
return this.cmFinY - (this.cmWidth / 4);
} else {
return this.cmIniY + (this.cmWidth / 4);
}
}

public int getCmPosition(int cmPosX, int cmPosY, Avio.Direction direction) {
int cmPosition;

cmPosition = cmPosX - this.cmIniX;
if (cmPosX < this.cmIniX || cmPosX > this.cmFinX) {
return -1; // ============== Off road ============================>>
}

return cmPosition;
}


@Override
public void paint(Graphics g, float factorX, float factorY, int offsetX, int offsetY) {
int wayWidth;
int wayMark;
int widthMark;
int xIni, yIni, xFin, yFin;
Graphics2D g2d;
BasicStroke stk;

wayMark = (int) ((float) this.cmMark / factorY);

if (wayMark <= 0) {
return;
}

wayWidth = (int) ((float) this.cmWidth / factorY);
xIni = (int) (((float) this.cmIniX / factorX) + offsetX);
yIni = (int) (((float) this.cmIniY / factorY) + offsetY);
xFin = (int) (((float) this.cmFinX / factorX) + offsetX);
yFin = (int) (((float) this.cmFinY / factorY) + offsetY);


// Road
g2d = (Graphics2D) g;
GradientPaint gp5 =
new GradientPaint(0, yIni + 2, Color.decode("0x404040"), 0, yIni + wayWidth / 2.9F, Color.decode("0x1111111"), true);
g2d.setPaint(gp5);
g.fillRect(xIni, yIni, xFin - xIni, yFin - yIni);
g.setColor(Color.BLACK);
g.drawRect(xIni, yIni, xFin - xIni, yFin - yIni);


}



@Override
public void addCrossRoad(CrossRoad cr) {
this.crossRoads.add(cr);

// Adjusts road dimensions
if (cr.getIniX() < this.cmIniX) {
this.cmIniX = cr.getIniX();
}

if (cr.getFinX() > this.cmFinX) {
this.cmFinX = cr.getFinX();
}
}

 

}