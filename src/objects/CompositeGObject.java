package objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		gObjects.add(gObject);
                recalculateRegion();
	}

	public void remove(GObject gObject) {
		gObjects.remove(gObject);
                recalculateRegion();
	}

	@Override
	public void move(int dX, int dY) {
		for(GObject item : gObjects) {
                    item.move(dX, dY);
                }
                recalculateRegion();
	}
	
	public void recalculateRegion() {
                int minX = Integer.MAX_VALUE;
                int minY = Integer.MAX_VALUE;
                int maxWidth = Integer.MIN_VALUE;
                int maxHeight = Integer.MIN_VALUE;
		for(GObject item : gObjects) {
                    minX = Math.min(minX, item.x);
                    minY = Math.min(minY, item.y);
                }
                for(GObject item : gObjects) {
                    maxWidth = Math.max(maxWidth, item.x-minX+item.width);
                    maxHeight = Math.max(maxHeight, item.y-minY+item.height);
                }
                x = minX;
                y = minY;
                width = maxWidth;
                height = maxHeight;
	}

	@Override
	public void paintObject(Graphics g) {
		for(GObject item : gObjects) {
                    item.paintObject(g);
                }
	}

	@Override
	public void paintLabel(Graphics g) {
		for(GObject item : gObjects) {
                    item.paintLabel(g);
                }
	}
	
}
