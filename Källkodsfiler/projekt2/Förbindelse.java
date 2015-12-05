package projekt2;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Förbindelse extends JComponent {

    private Nod d1, d2;
    private int x, y;
    int aX, aY, bX, bY;

    public Förbindelse(Nod d1, Nod d2) {
        this.d1 = d1;
        this.d2 = d2;
        aX = d1.getX();
        aY = d1.getY();
        bX = d2.getX();
        bY = d2.getY();
        setBounds(10, 10, 1000, 1000);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(aX, aY, bX, bY);


    }
}
