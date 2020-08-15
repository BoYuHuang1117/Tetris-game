package Tetris;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.*;

/***
 * Author: Bo-Yu Huang
 * Date: 6/5/20
 * A class extends JPanel for QUIT button to close the window
 *
 * Date: 6/16 make timer static variable and add stop timer in button click event
 * Date: 6/20 add mouse event to prevent double calling and call parent main function
 */
public class QuitButton extends JPanel implements MouseWheelListener, MouseListener {
    QuitButton(Main Parent){
        /***
         * P1----------
         * |          |
         * |          |
         * | ---------P2
         ***/
        parent = Parent;
        add(qButtom);
        addMouseListener(this);
        addMouseWheelListener(this);
        qButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                PlayGround.stopTimer();
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
    Main parent;

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
        qButtom.setFont(new Font ("TimesRoman", Font.BOLD + Font.PLAIN, Math.round(15/pixelSize)));
        qButtom.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        qButtom.setVisible(true);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // void function in case double calling in Mousescrolling when cursor is in the "Quit" button area
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        parent.mouseClicked(e);
    }
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e){ }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
