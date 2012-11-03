package net.doepner.ui;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.JPanel;

public class PaintPanel extends JPanel {

	private static final BasicStroke STROKE = new BasicStroke(4f,
			BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

	private final Deque<Point> points = new LinkedList<>();

	public PaintPanel() {
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				addPoint(e);
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				cutPoints();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final int clicks = e.getClickCount();
				if (clicks == 1) {
					addDot(e);
				} 
				if (clicks == 2) {
					clearPoints();
				}
			}
		});
	}

	private void clearPoints() {
		points.clear();
		repaint();	
	}

	private void addDot(MouseEvent e) {
		final Point p = e.getPoint();
		points.add(p);
		points.add(p);
		repaint();
	}
	
	private void addPoint(MouseEvent e) {
		points.add(e.getPoint());
		repaint();
	}

	private void cutPoints() {
		if (points.isEmpty() || points.getLast() != null) {
			points.add(null);
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(STROKE);
		g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

		Point pp = null; // "previous point"
		for (Point p : points) {
			if (pp != null && p != null) {
				g.drawLine(pp.x, pp.y, p.x, p.y);
			}
			pp = p;
		}
	}

	public BufferedImage getImage() {
		final BufferedImage image = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		paint(image.getGraphics());
		return image;
	}
}