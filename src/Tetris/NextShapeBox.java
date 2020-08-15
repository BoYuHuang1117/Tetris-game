package Tetris;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Color;

/***
 * Author: Bo-Yu Huang
 * Date: 6/5/20
 * A class extends JPanel for displaying upcoming element
 *
 * Date: 6/18 add transfer function from NextShapeBox to PlayGround
 *
 */
public class NextShapeBox extends JPanel {
    MyElements nextElement;
    NextShapeBox(){
        /*** 6x4 grid
         * P1----------
         * |          |
         * |          |
         * | ---------P2
         ***/
        nextElement = new MyElements();
    }
    Point P1;
    Point P2;
    int xCenter, yCenter;
    float pixelSize, rWidth = 170.0F, rHeight = 120.0F;
    int maxX, maxY;

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

        P1 = new Point(iX(-rWidth/2+10), iY(rHeight/2-10));
        P2 = new Point(iX(rWidth/2-10), iY(-rHeight/2+10));

        g.drawRect(P1.X, P1.Y, P2.X-P1.X, P2.Y-P1.Y);

        int px = 25;
        for (int i = 0; i < 4; ++i) {
            int leftMost = -75, bottonMost = -50;
            int x = 2 + nextElement.x(i);
            int y = 3 + nextElement.y(i);
            g.setColor(nextElement.typeColor());
            g.fillRect(iX(leftMost+x*px),iY(y*px+bottonMost),Math.round(px/pixelSize),Math.round(px/pixelSize));
            g.setColor(Color.black);
            g.drawRect(iX(leftMost+x*px),iY(y*px+bottonMost),Math.round(px/pixelSize),Math.round(px/pixelSize));
        }
    }

    public int transferShape(){
        int type = nextElement.shape();
        nextElement = new MyElements();
        repaint();
        return type;
    }
}
