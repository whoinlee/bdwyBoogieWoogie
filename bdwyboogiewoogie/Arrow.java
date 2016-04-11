package bdwyboogiewoogie;

import processing.core.PApplet;
import processing.core.PShape;


class Arrow {
	
	PApplet parent;
	PShape srcImg;
	
	float x, y;
	int rowCount, colCount;
	int tileSize;
	int xSpeed, ySpeed;
	float angle = 0;
	boolean noArrow = false;
	
	
	Arrow (PApplet p, PShape pSrcImg, int cellSize, float pX, float pY, int pRowCount, int pColCount)
	{
		parent = p;
		srcImg = pSrcImg;
		tileSize = cellSize;
		x = pX;
		y = pY;
		rowCount = pRowCount;
		colCount = pColCount;
		int dir = (parent.random(2)<1)? 1: -1;
		int speed = (int) PApplet.floor((float) (parent.random (3, 5)) * dir) ;
		if (parent.random(2) <= 1) {
			xSpeed = speed;
			ySpeed = 0;
			if (xSpeed>0){
				angle = PApplet.PI;		//-- arrow going to the right
			}
		} else {
			xSpeed = 0;
			ySpeed = speed;
			if (ySpeed>0){
				angle = -PApplet.PI/2;	//-- arrow going down
			} else {
				angle = PApplet.PI/2;	//-- arrow going up
			}
		}

		if (parent.random(6)<1) noArrow = true;
	}
	
	void move() {
		x += xSpeed * tileSize;
		y += ySpeed * tileSize;
		if ((x >= parent.width) || (x <= 0)) {
			x = PApplet.floor((float) parent.random(colCount))*tileSize;
		} 
		if ((y >= parent.height) || (y <= 0)) {
			y = PApplet.floor((float) parent.random(rowCount))*tileSize;
		}
//		PApplet.println("x:" + x + ", y:" + y);
	}
	
	void display() {
//		PApplet.println(parent.height);
		parent.pushMatrix();
		parent.translate(x + tileSize/2, y + tileSize/2);
		parent.rectMode(PApplet.CENTER);
		int alpha;
		if (parent.random(4)<1) {
			alpha = 0;
			parent.fill(248, 248, 8, alpha);
		} else if (y < parent.height/2) {
			if (parent.random(2)<1) {
				alpha = PApplet.floor((float) parent.random (5, 150));
			} else {
				//-- y proportional
				alpha = PApplet.floor((float) (y/parent.height)*parent.random (10, 255));
			}
			parent.fill(248, 248, 8, alpha);
		} else {
			alpha = PApplet.floor((float) parent.random (100, 250));
			parent.fill(245 + parent.random(10), 150 + parent.random(105), 5 + parent.random(70), alpha);
			if (parent.random(4)<1) parent.fill(248, 248, 8, alpha);
		}
		
//		parent.fill(255, 255, 255, alpha);		//white
//		parent.fill(255, 204, 0, alpha);		//dark yellow: ffcc00
//		parent.fill(248, 248, 8, alpha);		//light yellow
//		parent.fill(251, 246, 208, alpha);		//ivory	
//		parent.fill(0, 0, 0, alpha);			//black
		
		parent.rect(0, 0, tileSize-1, tileSize-1);
		parent.rotate(angle);
		parent.shapeMode(PApplet.CENTER);
		parent.noFill();
		//
		srcImg.disableStyle();
		//parent.fill(0, 0, 0, 255 - alpha/255);	//black arrow
		parent.fill(0, 0, 0, 255);					//black arrow
		//
		parent.noStroke();
		if (!noArrow) parent.shape(srcImg, 0, 0, tileSize, tileSize);
		parent.popMatrix();
	}
	
	void stop() {
		xSpeed = 0;
		ySpeed = 0;
	}
	
	float xLoc() {
		return x;
	}
	
	float yLoc() {
		return y;
	}
}