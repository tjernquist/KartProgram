package projekt2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.*;

class Nod extends JComponent {

    private String namn;
    private boolean vald = false;
    private int x, y;

    public Nod(String namn, int x, int y) {
        this.namn = namn;
        setBounds(x, y, 70, 50);
        setPreferredSize(new Dimension(20, 20));
        setMaximumSize(new Dimension(20, 60));
        setMinimumSize(new Dimension(20, 20));

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font font = Font.getFont("Arial");
        setFont(font);
        g.drawString(namn, 0, 30); 

        if (vald) {
            g.setColor(Color.red);
            g.fillRect(0, 0, 20, 20);
        } else {
            g.setColor(Color.yellow);
            g.fillRect(0, 0, 20, 20);
        }
    }

    public void setVald(boolean b) {
        vald = b;
        repaint();
    }

    public boolean isVald() {
        return vald;
    }

    public String getNamn() {
        return namn;
    }
}
