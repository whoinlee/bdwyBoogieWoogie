package bdwyboogiewoogie;

import processing.core.PApplet;
import processing.core.PImage;


class RoofNum {
	
	PApplet parent;
	PImage srcImg;
	
	int x, y;
	int rowCount, colCount;
	int tileSize;
	int xSpeed;
	int transparency;
	
	
	RoofNum (PApplet p, PImage pSrcImg, int cellSize, int pX, int pY, int pRowCount, int pColCount)
	{
		parent = p;
		srcImg = pSrcImg;
		tileSize = cellSize;
		x = pX;
		y = pY;
		rowCount = pRowCount;
		colCount = pColCount;
		int dir = (parent.random(3)<1)? 1: -1;
		int speed = (int) (parent.random (2, 5) * dir);
		xSpeed = speed;
//		transparency = PApplet.floor((y*200)/(rowCount*tileSize));
		if (parent.random(4) < 1) {
			transparency = PApplet.floor(parent.random(125, 250));
		} else {
			transparency = PApplet.floor((y*200)/(rowCount*tileSize));
		}
	}
	
	void move() {
		x += xSpeed * tileSize;
		if ((x >= parent.width) || (x <= 0)) {
			x = PApplet.floor((float) parent.random(colCount))*tileSize;
		} 
		if ((y >= parent.height) || (y <= 0)) {
			y = PApplet.floor((float) parent.random(rowCount))*tileSize;
		}
	}
	
	void display() {
		parent.rectMode(PApplet.CORNER);
		parent.fill(0, 0, 0, transparency);
		parent.rect(x, y, tileSize-1, tileSize-1);
		parent.image(srcImg, x, y, tileSize, tileSize);
	}
	
	void stop() {
		xSpeed = 0;
	}
	
	int xLoc() {
		return x;
	}
	
	int yLoc() {
		return y;
	}
}