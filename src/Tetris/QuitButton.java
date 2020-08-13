package Tetris;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/***
 * Author: Bo-Yu Huang
 * Date: 6/5/20
 * A class extends JPanel for QUIT button to close the window
 */
public class QuitButton extends JPanel{
    QuitButton(){
        /***
         * P1----------
         * |          |
         * |          |
         * | ---------P2
         ***/
        add(qButtom);
        qButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
            }
        });
    }
    Point P1;
    Point P2;
    int xCenter, yCenter;
    float pixelSize, rWidth = 150.0F, rHeight = 100.0F;
    int maxX, maxY;
    JButton qButtom = new JButton("QUIT");

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

        P1 = new Point(iX(-rWidth/2+25), iY(rHeight/2-40));
        P2 = new Point(iX(rWidth/2-25), iY(-rHeight/2+20));
        qButtom.setLocation(P1.X,P1.Y);
        qButtom.setSize(P2.X-P1.X,P2.Y-P1.Y);
        qButtom.setForeground(Color.black);
        qButtom.setBackground(Color.white);
        qButtom.setFont(new Font ("TimesRoman", Font.BOLD + Font.PLAIN, Math.min(maxX,maxY)/4));
        qButtom.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        qButtom.setVisible(true);
        /*System.out.println("QuitButtom");
        System.out.println("Width: "+ (P2.X-P1.X)+" Height: "+ (P2.Y-P1.Y));
        System.out.println("maxX:"+ maxX + "maxY: "+ maxY);*/
    }
}
