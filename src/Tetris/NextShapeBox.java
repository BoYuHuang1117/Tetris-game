package Tetris;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Color;
/***
 * Author: Bo-Yu Huang
 * Date: 6/5/20
 * A class extends JPanel for displaying upcoming element
 */
public class NextShapeBox extends JPanel {
    NextShapeBox(){
        /***
         * P1----------
         * |          |
         * |          |
         * | ---------P2
         ***/
    }
    Point P1;
    Point P2;
    int xCenter, yCenter;
    float pixelSize, rWidth = 200.0F, rHeight = 180.0F;
    int maxX, maxY;
    Point[] pts = new Point[4];

    void initgr() {
        Dimension d = getSize();
        maxX = d.width - 1; maxY = d.height - 1;
        pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
        xCenter = maxX/2; yCenter = maxY/2;
    }
    int iX(float x) {return Math.round(xCenter + x / pixelSize);}
    int iY(float y) {return Math.round(yCenter - y / pixelSize);}

    @Override
    public void paintComponent(Graphics g){
        initgr();
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        P1 = new Point(iX(-rWidth/2+20), iY(rHeight/2-20));
        P2 = new Point(iX(rWidth/2-20), iY(-rHeight/2+20));

        g.drawRect(P1.X, P1.Y, P2.X-P1.X, P2.Y-P1.Y);
        /*System.out.println("Nextshape");
        System.out.println("Width: "+ (P2.X-P1.X)+" Height: "+ (P2.Y-P1.Y));
        System.out.println("maxX:"+ maxX + "maxY: "+ maxY);*/

        // draw left L, side = 40px
        MyElements element = new MyElements();
        Color color = element.getColor(4);

        pts[0] = new Point(iX(-60),iY(0));
        pts[1] = new Point(iX(-20),iY(0));
        pts[2] = new Point(iX(20),iY(0));
        pts[3] = new Point(iX(20),iY(40));
        g.setColor(color);
        for (int i = 0; i < 4; i++)
            g.fillRect(pts[i].X,pts[i].Y,Math.round(40/pixelSize),Math.round(40/pixelSize));
        g.setColor(Color.black);
        for (int i = 0; i < 4; i++)
            g.drawRect(pts[i].X,pts[i].Y,Math.round(40/pixelSize),Math.round(40/pixelSize));
    }
}
