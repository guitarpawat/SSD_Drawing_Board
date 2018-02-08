package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
                repaint();
	}
	
	public void groupAll() {
                CompositeGObject cpt = new CompositeGObject();
                for(GObject item : gObjects) {
                    cpt.add(item);
                }
                cpt.selected();
                gObjects.clear();
                gObjects.add(cpt);
                repaint();
	}

	public void deleteSelected() {
                if(target != null) gObjects.remove(target);
                repaint();
	}
	
	public void clear() {
		gObjects.clear();
                repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

                private int x;
                private int y;
                
		private void deselectAll() {
                        if(target != null) target.deselected();
			target = null;
                        repaint();
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
                        int x = e.getX();
                        int y = e.getY();
                        this.x = x;
                        this.y = y;
                        boolean hit = false;
                        for(int i=gObjects.size()-1; i>=0; i--) {
                            GObject item = gObjects.get(i);
                            if(item.pointerHit(x, y)) {
                                if(target != null) target.deselected();
                                item.selected();
                                target = item;
                                gObjects.remove(target);
                                gObjects.add(target);
                                hit = true;
                                break;
                            }
                        }
                        if(!hit) deselectAll();
                        repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
                        int x = e.getX();
                        int y = e.getY();
                        if(target != null) target.move(x-this.x, y-this.y);
                        this.x = x;
                        this.y = y;
                        repaint();
		}
	}
	
}