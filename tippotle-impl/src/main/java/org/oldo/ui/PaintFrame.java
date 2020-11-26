package org.oldo.ui;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public final class PaintFrame extends JFrame {

    private final PaintPanel paintPanel = new PaintPanel();

    public PaintFrame() {
        super("Paint");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout());

        add(paintPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                super.windowClosing(evt);
                try {
                    ImageIO.write(paintPanel.getImage(), "png",
                            new File("/home/oliver/image.png"));
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    public void display() {
        setSize(400, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PaintFrame().display();
    }
}
