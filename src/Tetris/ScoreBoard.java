package Tetris;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
/***
 * Author: Bo-Yu Huang
 * Date: 6/5/20
 * A class extends JPanel for demonstrating three strings on the right
 */
public class ScoreBoard extends JPanel {
    ScoreBoard(){
        /***
         * P1---------|
         * |          |
         * P2---------|
         * |          |
         * P3---------|
         ***/
    }
    Point P1;
    Point P2;
    Point P3;
    int xCenter, yCenter;
    float pixelSize, rWidth = 200.0F, rHeight = 300.0F;
    int maxX, maxY;

    public int level = 1, lines = 0, scores = 0;

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

        P1 = new Point(iX(-rWidth/2+10), iY(rHeight/2-55));
        P2 = new Point(P1.X, iY(rHeight/2-130));
        P3 = new Point(P1.X, iY(rHeight/2-205));

        Font style = new Font ("TimesRoman", Font.BOLD + Font.PLAIN, Math.min(maxX,maxY)/10);
        g.setFont(style);
        g.drawString(
                "Level:       "+level, P1.X, P1.Y);
        g.drawString(
                "Lines:       "+lines, P2.X, P2.Y);
        g.drawString(
                "Score:      "+scores, P3.X, P3.Y);
        /*System.out.println("Score");
        System.out.println("Width: "+ P1.X+" Height: "+ (P2.Y-P1.Y));
        System.out.println("maxX:"+ maxX + "maxY: "+ maxY);*/
    }
}
