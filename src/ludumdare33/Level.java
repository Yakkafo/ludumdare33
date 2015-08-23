package ludumdare33;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Level implements GameObject {
	private PApplet processing;
	private Belly belly;
	private Hair hair;
	private Cursor cursor;
	// Contour
	private final int MARGIN = 2;
	private final int THICKNESS = 0;
	private PVector topLeft;
	private PVector bottomRight;

	private ArrayList<GameObject> gameObjects;

	public Level(PApplet _processing, PVector _topLeft, PVector _bottomRight) {
		processing = _processing;
		topLeft = new PVector(_topLeft.x, _topLeft.y);
		bottomRight = new PVector(_bottomRight.x, _bottomRight.y);
		// Create elements from the level
		hair = new Hair(processing);
		cursor = new Cursor(processing);
		belly = new Belly(processing, hair, cursor, 
				new PVector(topLeft.x, (int) bottomRight.y * 9f / 10f),
				new PVector(bottomRight.x / 2, (int) bottomRight.y * 9f / 10f),
				new PVector(bottomRight.x, (int) bottomRight.y * 9f / 10f),
				bottomRight);
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(hair);
		gameObjects.add(belly);
		gameObjects.add(cursor);
		init();
	}

	@Override
	public void update() {
		hair.setCurrentFootX(belly.getAnchorPoint().x);
		hair.setCurrentFootY(belly.getAnchorPoint().y);
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().update();
	}

	@Override
	public void display() {
		processing.fill(221, 234, 255);
		processing.noStroke();
		processing.beginShape();
		// Exterior part of shape, clockwise winding
		processing.vertex(topLeft.x, topLeft.y, -5);
		processing.vertex(bottomRight.x, topLeft.y, -5);
		processing.vertex(bottomRight.x, bottomRight.y, -5);
		processing.vertex(topLeft.x, bottomRight.y, -5);
		processing.endShape();
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().display();
		
		processing.fill(0);
		processing.beginShape();
		// Exterior part of shape, clockwise winding
		processing.vertex(topLeft.x, topLeft.y);
		processing.vertex(bottomRight.x, topLeft.y);
		processing.vertex(bottomRight.x, bottomRight.y);
		processing.vertex(topLeft.x, bottomRight.y);
		// Interior part of shape, counter-clockwise winding
		processing.beginContour();
		processing.vertex(topLeft.x + MARGIN, topLeft.y + MARGIN);
		processing.vertex(bottomRight.x - MARGIN, topLeft.y + MARGIN);
		processing.vertex(bottomRight.x - MARGIN, bottomRight.y - MARGIN);
		processing.vertex(topLeft.x + MARGIN, bottomRight.y - MARGIN);
		processing.endContour();
		processing.endShape(PConstants.CLOSE);
	}

	@Override
	public void init() {
		hair.setCurrentFootX(belly.getAnchorPoint().x);
		hair.setCurrentFootY(belly.getAnchorPoint().y);
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().init();
	}
	
	public void mousePressed() {
		belly.justGrabBelly();
		if(belly.isGrabbable())
			cursor.setGrabbingCursor();
		else
			cursor.setGrabbingCursor();
		belly.grabBelly();
	}

	public void mouseDragged() {
		cursor.setGrabbingCursor();
		belly.grabBelly();
	}

	public void mouseReleased() {
		belly.releaseBelly();
		cursor.setBasicCursor();
	}
	
	public void mouseMoved() {
		if(belly.isGrabbable())
			cursor.setGrabbableCursor();
		else
			cursor.setBasicCursor();
	}
}
