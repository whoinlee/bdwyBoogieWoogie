package bdwyboogiewoogie;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;


public class BdwyBoogieWoogie extends PApplet {
	
	private static final long serialVersionUID = 1L;

	PImage bkgImage;

	int animSpeed = 8;
	int stageW = 528;
	int stageH = 528;
	int cellUnit = 44;			//-- 33, 44, 66
	int offset = 0;
	
	int colCount = floor(stageW/cellUnit); 
	int rowCount = floor(stageH/cellUnit);
	int tileCount = rowCount*colCount;
	int initIndex = 0;
	
	PImage[] imageTiles;
	
	//-- roofNums
	int roofNumCount = 4;
	PImage[] roofNums;
	ArrayList<RoofNum> roofNumArr;
	
	//-- arrows
	int maxArrowCount = ceil((float)colCount/2);
	PShape arrowShape;
	ArrayList<Arrow> arrowArr;
	
	//-- offDutys
	int offDutyCount = 4;
	PImage offDutyOn, offDutyOff;
	ArrayList<OffDuty> offDutyArr;
	
	
	public void setup() {
		size(528, 528);
		frameRate(animSpeed);	//-- default:60
		
		//-- background
		background(10);			//-- black
		bkgImage = loadImage("newYork528x528_4.jpg");
		
		//-- arrows
		arrowShape = loadShape("arrow3x2.svg");
		arrowArr = new ArrayList<Arrow>();
		
		//-- cab rooftop offDuty sign
		offDutyOn = loadImage("offDutyOn66.png");
		offDutyOff = loadImage("offDutyOff66.png");
		
		noCursor();
		smooth();
//		noLoop();
		
		roofNums = new PImage[roofNumCount];
		for (int i = 0; i < roofNumCount; i++ ) { 
			roofNums[i] = loadImage("roofNum" + i + ".png");
		}
		roofNumArr = new ArrayList<RoofNum>();
		
		offDutyArr = new ArrayList<OffDuty>();
	}

	public void draw() {
		clear();
		cropTiles();
		
		initIndex++;
		if (initIndex > colCount) initIndex = 0;
		
		//-- tile display
		int i=0;
		int overlayAlpha = 255;					//-- 100%: opaque
		for (int rowY = 0; rowY < rowCount; rowY++){
		    for (int colX = 0; colX < colCount; colX++){
		    	image(imageTiles[i], colX*cellUnit + offset, rowY*cellUnit + offset, cellUnit-1, cellUnit-1);
		    	if (random(7)<1) {
		    		int c = 0;					//-- black
		    		if (random(3)<1) c = 255;	//-- white
		    		if (rowY <= (rowCount/2+1)) {
		    			overlayAlpha = (c == 255)? floor((float) random (50, 150)) : floor((float) random (5, 75));
		    		}
		    		else {
		    			overlayAlpha = (c == 255)? floor((float) random (100, 225)) : floor((float) random (70, 255));
		    		}
		    		rectMode(CORNER);
		    		fill(c, overlayAlpha);
		    		rect(colX*cellUnit + offset, rowY*cellUnit + offset, cellUnit-1, cellUnit-1);
		    	}
		    	i++;
		    }
		}
		
		//-- arrow display ---------------------------//
		arrowArr.add(new Arrow(this, arrowShape, cellUnit, floor((float)random(colCount))*cellUnit + offset, floor((float)random(rowCount))*cellUnit + offset, rowCount, colCount));
		if (random (3) < 1) 
		arrowArr.add(new Arrow(this, arrowShape, cellUnit, floor((float)random(colCount))*cellUnit + offset, floor((float)random(rowCount))*cellUnit + offset, rowCount, colCount));
		
		//-- Iterate through the ArrayList and get each Arrow
		//-- The ArrayList keeps track of the total number of arrowArr.
		boolean isOut = false;
		for (int j = 0; j < arrowArr.size(); j++ ) { 
		    Arrow a = (Arrow) arrowArr.get(j);
		    a.move();
		    if ((a.xLoc() >= stageW) || (a.xLoc() <0)) isOut = true;
		    if ((a.yLoc() >= stageH) || (a.yLoc() <0)) isOut = true;
		    if (isOut) {
		    	a.stop();
		    	arrowArr.remove(j);
		    } else {
		    	a.display();
		    }
		}
	
		//-- If the ArrayList has more than maxArrowCount elements in it, we delete the first element, using remove().
		while (arrowArr.size() > maxArrowCount) {
			arrowArr.get(0).stop();
		    arrowArr.remove(0); 
		}
		
		//-- "rootNums" display ----------------------//
		int numIndex = (int) random (roofNumCount);
		roofNumArr.add(new RoofNum(this, roofNums[numIndex], cellUnit, floor((float)random(colCount))*cellUnit, floor(rowCount/2 + (float)random(rowCount/2))*cellUnit, rowCount, colCount));
		if (random(3)<1) {
			int newIndex = (int) random (3);
			roofNumArr.add(new RoofNum(this, roofNums[newIndex], cellUnit, floor((float)random(colCount))*cellUnit, floor(rowCount/4 + (float)random(3*rowCount/4))*cellUnit, rowCount, colCount));
		}
		for (int l = 0; l < roofNumArr.size(); l++ ) { 
		    RoofNum r = (RoofNum) roofNumArr.get(l);
		    r.move();
		    if ((r.xLoc() >= stageW) || (r.xLoc() <0)) isOut = true;
		    if ((r.yLoc() >= stageH) || (r.yLoc() <0)) isOut = true;
		    if (isOut) {
		    	r.stop();
		    	roofNumArr.remove(l);
		    } else {
		    	r.display();
		    }
		}
		while (roofNumArr.size() > roofNumCount) {
			roofNumArr.get(0).stop();
			roofNumArr.remove(0); 
		}
		
		//-- "offDuty" display -----------------------//
		if (offDutyArr.size() < offDutyCount) {
			int interval = 1000 + offDutyArr.size()*1000;
			int repeat;
			if (random(5)<=3) {
				repeat = 2;
			} else {
				repeat = 1;
			}
			offDutyArr.add(new OffDuty(this, offDutyOn, offDutyOff, interval, repeat, cellUnit, rowCount, colCount));
		}
		
		for (int m = 0; m < offDutyArr.size(); m++ ) { 
		    OffDuty o = (OffDuty) offDutyArr.get(m);
		    o.display();
		}
	}
	
	public void cropTiles() {
	  imageTiles = new PImage[tileCount];

	  int i = 0;
	  int cropX = 0;
	  int cropY = 0;
	  for (int rowY = 0; rowY < rowCount; rowY++){
	    for (int colX = 0; colX < (colCount-initIndex); colX++){

	      cropX = (int) random((colX + initIndex)*cellUnit - cellUnit/8, (colX + initIndex)*cellUnit + cellUnit/8);
	      cropY = (int) random(rowY*cellUnit - cellUnit/8, rowY*cellUnit + cellUnit/8);
	      cropX = constrain(cropX, 0, width-cellUnit);
	      cropY = constrain(cropY, 0, height-cellUnit);

	      if (random(8)<1) {
	    	  //-- for magnifying later
	    	  if (random(2)<1) {
	    		  imageTiles[i++] = bkgImage.get(cropX + cellUnit/4, cropY + cellUnit/4, cellUnit/2, cellUnit/2);
	    	  } else {
	    		  imageTiles[i++] = bkgImage.get(cropX - cellUnit/4, cropY - cellUnit/4, floor((float)(cellUnit*1.75)), floor((float)(cellUnit*1.75)));
	    	  }
	      } else {
	    	  imageTiles[i++] = bkgImage.get(cropX, cropY, cellUnit-1, cellUnit-1);
	      }
	    }//for colX:front
	    for (int colX = (colCount-initIndex); colX < colCount; colX++){

	      cropX = (int) random((colX - colCount + initIndex)*cellUnit - cellUnit/4, colX*cellUnit + cellUnit/4);
	      cropY = (int) random(rowY*cellUnit - cellUnit/4, rowY*cellUnit + cellUnit/4);
	      cropX = constrain(cropX, 0, width-cellUnit);
	      cropY = constrain(cropY, 0, height-cellUnit);

	      if (random(10)<1) {
	    	  imageTiles[i++] = bkgImage.get(cropX + cellUnit/4, cropY + cellUnit/4, cellUnit/2, cellUnit/2);
	      } else {
	    	  imageTiles[i++] = bkgImage.get(cropX, cropY, cellUnit-1, cellUnit-1);
	      }
	    }//for colX:end
	  }
	}
}