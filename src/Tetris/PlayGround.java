package Tetris;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;

/***
 * Author: Bo-Yu Huang
 * Date: 6/5/20
 * A class extends JPanel for showing the primary playing area (Main area)
 */
public class PlayGround extends JPanel implements MouseListener {
    PlayGround(){
        /***
         * P1----------
         * |          |
         * |  P3---   |
         * |  |   |   |
         * |  ----P4  |
         * |          |
         * | --------P2
         ***/
        addKeyListener(new TAdapter());
        addMouseListener(this);
    }
    Point P1;
    Point P2;
    Point P3;
    Point P4;
    int xCenter, yCenter;
    float pixelSize, rWidth = 270.0F, rHeight = 520.0F;
    int maxX, maxY;
    boolean pause = false;
    Point[] pts = new Point[4];

    void initgr() {
        Dimension d = getSize();        // size of PlayGround JPanel
        maxX = d.width - 1; maxY = d.height - 1;
        pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
        xCenter = maxX/2; yCenter = maxY/2;
    }
    int iX(float x) {return Math.round(xCenter + x / pixelSize);}
    int iY(float y) {return Math.round(yCenter - y / pixelSize);}

    /*@Override
    public void actionPerformed(ActionEvent e) {
        // future possible keyboard action
    }*/

    @Override
    public void paintComponent(Graphics g){
        initgr();
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        P1 = new Point(iX(-rWidth/2+10), iY(rHeight/2-10));
        P2 = new Point(iX(rWidth/2-10), iY(-rHeight/2+10));
        g.drawRect(P1.X, P1.Y, P2.X-P1.X, P2.Y-P1.Y);
        /*System.out.println("playground");
        System.out.println("Width: "+ (P2.X-P1.X)+" Height: "+ (P2.Y-P1.Y));
        System.out.println("maxX:"+ maxX + "maxY: "+ maxY);*/

        if (pause) {
            P3 = new Point(iX(-50), iY(20));
            P4 = new Point(iX(50), iY(-20));
            g.setColor(Color.blue);
            g.drawRect(P3.X, P3.Y, P4.X-P3.X, P4.Y-P3.Y);
            g.setFont(new Font("TimesRoman", Font.BOLD + Font.PLAIN, Math.min(maxX,maxY)/12));
            g.drawString("PAUSE", iX(-40), iY(-10));
            //System.out.println("Width: "+ (P4.X-P3.X)+" Height: "+ (P4.Y-P3.Y));
        }

        // draw straight Line, side = 25px
        MyElements element = new MyElements();

        pts[0] = new Point(iX(-25),iY(200));
        pts[1] = new Point(iX(0),iY(200));
        pts[2] = new Point(iX(25),iY(200));
        pts[3] = new Point(iX(50),iY(200));
        drawEle(g,element.getColor(0));

        // draw Left n
        pts[0] = new Point(iX(25),iY(-200));
        pts[1] = new Point(iX(25),iY(-175));
        pts[2] = new Point(iX(50),iY(-175));
        pts[3] = new Point(iX(50),iY(-150));
        drawEle(g,element.getColor(1));

        // draw right n
        pts[0] = new Point(iX(25),iY(-225));
        pts[1] = new Point(iX(50),iY(-225));
        pts[2] = new Point(iX(50),iY(-200));
        pts[3] = new Point(iX(75),iY(-200));
        drawEle(g,element.getColor(2));
    }
    private void drawEle(Graphics g,Color color){
        g.setColor(color);
        for (int i = 0; i < 4; i++)
            g.fillRect(pts[i].X,pts[i].Y,Math.round(25/pixelSize),Math.round(25/pixelSize));
        g.setColor(Color.black);
        for (int i = 0; i < 4; i++)
            g.drawRect(pts[i].X,pts[i].Y,Math.round(25/pixelSize),Math.round(25/pixelSize));
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        pause = true;
        repaint();
        //System.out.println("mouseEntered");
    }
    @Override
    public void mouseExited(MouseEvent e) {
        pause = false;
        repaint();
        //System.out.println("mouseExited");
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    private void doDrawing(Graphics g) {
        // possible sub-function to paintComponent focusing on falling elements
        var size = getSize();
    }
}

class TAdapter extends KeyAdapter {
    // future keyboard event application
    @Override
    public void keyPressed(KeyEvent e) {

    }
}
