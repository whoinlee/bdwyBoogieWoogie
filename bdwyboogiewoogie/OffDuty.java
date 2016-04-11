package bdwyboogiewoogie;

import processing.core.PApplet;
import processing.core.PImage;


class OffDuty {
	
	PApplet parent;
	PImage srcOnImg, srcOffImg;
	
	int interval;
	int repeat;
	int repeatOrg;
	int row, col;
	int x, y;
	int rowCount, colCount;
	int tileSize;
	int trans, offTrans;

	Timer showTimer, flipTimer;
	boolean isHiding = true;
	boolean isOn = false;
	boolean isOff = false;
	
	
	OffDuty (PApplet p, PImage pOnImg, PImage pOffImg, int pInterval, int pRepeat, int cellSize, int pRowCount, int pColCount)
	{
		parent = p;
		srcOnImg = pOnImg;
		srcOffImg = pOffImg;
		interval = pInterval;
		repeat = pRepeat;
		repeatOrg = pRepeat;
		tileSize = cellSize;
		rowCount = pRowCount;
		colCount = pColCount;
		
		trans = (int) parent.random(175, 225);
		
		row = (int) parent.random(rowCount);
		col = (int) parent.random(colCount);
		
		showTimer = new Timer(parent, interval);
		showTimer.start();
		
		if (repeat == 1) {
//			if (parent.random(2)<1) {
				flipTimer = new Timer(parent, 500);
//			} else {
//				flipTimer = new Timer(parent, 150);
//			}
		} else {
			flipTimer = new Timer(parent, 150);
		}
	}
	
	void display() {
		if (showTimer.isFinished()) {
			if (isOn) {
				//-- stops at on, then disappear
//				if (repeat>0) {
//					displayOff();
//				} else {
//					hide();
//				}
				//-- stops at off, then disappear
				if (flipTimer.isFinished()) {
					displayOff();
				} else {
					//-- stays at On
					parent.rectMode(PApplet.CORNER);
					parent.fill(0, 0, 0, trans);
					parent.rect(x, y, tileSize-1, tileSize-1);
					parent.image(srcOnImg, x, y, tileSize, tileSize);
				}
			} else if (isOff) {
//				if (repeat>0) {
//					displayOn();
//				} else {
//					hide();
//				}
				if (flipTimer.isFinished()) {
					if (repeat>0) {
						displayOn();
					} else {
						hide();
					}
				} else {
					//-- stayw at Off
					parent.rectMode(PApplet.CORNER);
					parent.fill(0, 0, 0, offTrans);
					parent.rect(x, y, tileSize-1, tileSize-1);
					parent.image(srcOffImg, x, y, tileSize, tileSize);
				}
			} else if (isHiding) {
				displayOn();
			}
		}
	}
	
	void displayOn() {
		if (repeat == repeatOrg) {
			if ((parent.random(2)<1) || (repeatOrg == 1)) {
				trans = (int) parent.random(200, 250);
			} else {
				//-- proportional to y position
				trans = (int) parent.random(175, 225)*(y/(tileSize*rowCount)) + 25;
			}
			offTrans = PApplet.min(trans + 25, 255);
		}
		
		repeat--;

		x = tileSize*col;
		y = tileSize*row;
		
		parent.rectMode(PApplet.CORNER);
		parent.fill(0, 0, 0, trans);
		parent.rect(x, y, tileSize-1, tileSize-1);
		parent.image(srcOnImg, x, y, tileSize, tileSize);
		
		isOn = true;
		isOff = false;
		isHiding = false;
		flipTimer.start();
	}
	
	void displayOff() {
		parent.rectMode(PApplet.CORNER);
		parent.fill(0, 0, 0, offTrans);
		parent.rect(x, y, tileSize-1, tileSize-1);
		parent.image(srcOffImg, x, y, tileSize, tileSize);
		
		isOn = false;
		isOff = true;
		isHiding = false;
		flipTimer.start();
	}
	
	void hide() {
		row = (int) parent.random(rowCount);
		col = (int) parent.random(colCount);
		
		isOn = false;
		isOff = false;
		isHiding = true;
		repeat = repeatOrg;
		showTimer.start();
	}
	
	int xLoc() {
		return x;
	}
	
	int yLoc() {
		return y;
	}
}