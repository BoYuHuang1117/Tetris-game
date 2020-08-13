package Tetris;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/***
 * Author: Bo-Yu Huang
 * Date: 6/4/20
 * The main compiling program using JFrame to contain multiple classes that extend JPanel
 */
public class Main extends JFrame {

    public static void main(String[] args) { new Main();}
        Main() {
            super("Tetris");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            
            int W = 501, H = 601;
            setSize(W, H); // 500, 600

            JPanel winArea = new JPanel(new GridBagLayout());
            winArea.setLayout(new BoxLayout(winArea, BoxLayout.X_AXIS));
            winArea.setPreferredSize(new Dimension(W-1,H-1));

            PlayGround playBoard = new PlayGround();
            playBoard.setPreferredSize(new Dimension((int)(W*(float)250/W), (int)(H*(float)450/H)));
            winArea.add(playBoard, BorderLayout.WEST);

            JPanel rightBox = new JPanel(new GridBagLayout());
            rightBox.setLayout(new BoxLayout(rightBox, BoxLayout.Y_AXIS));
            rightBox.setPreferredSize(new Dimension((int)(W*(float)150/W),(int)(H*(float)400/H)));
            winArea.add(rightBox,BorderLayout.EAST);

            NextShapeBox nextBox = new NextShapeBox();
            nextBox.setPreferredSize(new Dimension((int)(W*(float)150/W),(int)(H*(float)100/H)));
            rightBox.add(nextBox);

            ScoreBoard scoreBoard = new ScoreBoard();
            scoreBoard.setPreferredSize(new Dimension((int)(W*(float)150/W),(int)(H*(float)150/H)));
            rightBox.add(scoreBoard);

            QuitButton qButtom = new QuitButton();
            qButtom.setPreferredSize(new Dimension((int)(W*(float)100/W),(int)(H*(float)50/H)));
            rightBox.add(qButtom);

            this.add(winArea);

            //check the border of each panel
            //ZPanels.setBorder(BorderFactory.createLineBorder(Color.blue,10));
            //winArea.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
            //playBoard.setBorder(BorderFactory.createLineBorder(Color.MAGENTA,2));
            //rightBox.setBorder(BorderFactory.createLineBorder(Color.green,2));
            //nextBox.setBorder(BorderFactory.createLineBorder(Color.red,2));
            //scoreBoard.setBorder(BorderFactory.createLineBorder(Color.red,2));
            //qButtom.setBorder(BorderFactory.createLineBorder(Color.red,2));
            //pauseLabel.setBorder(BorderFactory.createLineBorder(Color.red,2));
            //System.out.println("Window width: "+W+" Window height "+H);

            //setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            setVisible(true);
        }

}
