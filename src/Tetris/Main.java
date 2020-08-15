package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static Tetris.MyElements.*;
import static Tetris.MyElements.JLSZT_offsetTable;

/***
 * Author: Bo-Yu Huang
 * Date: 6/4/20
 * The main compiling program using JFrame to contain multiple classes that extend JPanel
 *
 * Date: 6/20 add mouseClicked and mouseScroll event functions
 */
public class Main extends JFrame implements MouseListener, MouseWheelListener {
    public static void main(String[] args) {
        var game = new Main();
        game.setVisible(true);
    }

    JPanel winArea;
    PlayGround playBoard;
    JPanel rightBox;
    NextShapeBox nextBox;
    ScoreBoard scoreBoard;
    QuitButton qButtom;

        public Main() {
            super("Tetris");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            addMouseListener(this);
            addMouseWheelListener(this);

            int W = 501, H = 601;
            setSize(W, H); // 500, 600

            winArea = new JPanel(new GridBagLayout());
            nextBox = new NextShapeBox();
            playBoard = new PlayGround(this);
            rightBox = new JPanel();
            scoreBoard = new ScoreBoard();
            qButtom = new QuitButton(this);

            winArea.setLayout(new BoxLayout(winArea, BoxLayout.X_AXIS));
            rightBox.setLayout(new BoxLayout(rightBox, BoxLayout.Y_AXIS));

            winArea.setPreferredSize(new Dimension(W-1,H-1));
            playBoard.setPreferredSize(new Dimension((int)(W*(float)300/W), (int)(H*(float)600/H)));
            rightBox.setPreferredSize(new Dimension((int)(W*(float)200/W),(int)(H*(float)600/H)));
            nextBox.setPreferredSize(new Dimension((int)(W*(float)200/W),(int)(H*(float)150/H)));
            scoreBoard.setPreferredSize(new Dimension((int)(W*(float)200/W),(int)(H*(float)300/H)));
            qButtom.setPreferredSize(new Dimension((int)(W*(float)200/W),(int)(H*(float)150/H)));

            rightBox.add(nextBox);
            rightBox.add(scoreBoard);
            rightBox.add(qButtom);

            winArea.add(playBoard, BorderLayout.WEST);
            winArea.add(rightBox,BorderLayout.EAST);
            this.add(winArea);

            //check the border of each panel
            //winArea.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
            //playBoard.setBorder(BorderFactory.createLineBorder(Color.black,2));
            //rightBox.setBorder(BorderFactory.createLineBorder(Color.green,2));
            //nextBox.setBorder(BorderFactory.createLineBorder(Color.red,2));
            //scoreBoard.setBorder(BorderFactory.createLineBorder(Color.red,2));
            //qButtom.setBorder(BorderFactory.createLineBorder(Color.red,2));
            //pauseLabel.setBorder(BorderFactory.createLineBorder(Color.red,2));
            //System.out.println("Window width: "+W+" Window height "+H);

            //setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            setVisible(true);
        }

    @Override
    public void mouseClicked(MouseEvent e) {
            if (e.getButton() == 1)
                playBoard.tryMove(playBoard.element, playBoard.curX-1,playBoard.curY, 0);
                
            if (e.getButton() == 2) // scroll click event
                System.out.println("ScrollButton clicked!");
            
            if (e.getButton() == 3)
                playBoard.tryMove(playBoard.element, playBoard.curX+1,playBoard.curY, 0);
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
            int W = getWidth();
            int mx = e.getX();
            if (mx > (int)(W*(float)300/500)-3) {
                // avoid calling when cursor is in the main area (pause activate)
                int type = e.getWheelRotation();
                if (type > 0)
                    // toward user, CCW
                    if (playBoard.element.shape() == 7)
                        playBoard.tryMove(playBoard.element.CCW_turn(),playBoard.curX,playBoard.curY, -1);
                    else if (playBoard.element.shape() == 6) {
                        int next = (pos - 1 + 4) % 4;
                        for (int i = 0; i < 5; i++) {
                            if (playBoard.tryMove(playBoard.element.CCW_turn(), playBoard.curX + I_offsetTable[pos][i][0] - I_offsetTable[next][i][0],
                                    playBoard.curY + I_offsetTable[pos][i][1] - I_offsetTable[next][i][1], -1))
                                break;
                        }
                    }
                    else {
                        int next = (pos - 1 + 4) % 4;
                        for (int i = 0; i < 5; i++) {
                            if(playBoard.tryMove(playBoard.element.CCW_turn(), playBoard.curX + JLSZT_offsetTable[pos][i][0] - JLSZT_offsetTable[next][i][0],
                                    playBoard.curY + JLSZT_offsetTable[pos][i][1] - JLSZT_offsetTable[next][i][1], -1))
                                break;
                        }
                    }
                else {
                    // away user, CW
                    if (playBoard.element.shape() == 7)
                        playBoard.tryMove(playBoard.element.CW_turn(),playBoard.curX,playBoard.curY, 1);
                    else if (playBoard.element.shape() == 6) {
                        int next = (pos + 1 + 4) % 4;
                        for (int i = 0; i < 5; i++) {
                            if (playBoard.tryMove(playBoard.element.CW_turn(), playBoard.curX + I_offsetTable[pos][i][0] - I_offsetTable[next][i][0],
                                    playBoard.curY + I_offsetTable[pos][i][1] - I_offsetTable[next][i][1], 1))
                                break;
                        }
                    }
                    else{
                        int next = (pos + 1 + 4) % 4;
                        for (int i = 0; i < 5; i++) {
                            if(playBoard.tryMove(playBoard.element.CW_turn(), playBoard.curX + JLSZT_offsetTable[pos][i][0] - JLSZT_offsetTable[next][i][0],
                                    playBoard.curY + JLSZT_offsetTable[pos][i][1] - JLSZT_offsetTable[next][i][1], 1))
                                break;
                        }
                    }
                }
            }
    }
    @Override
    public void mousePressed(MouseEvent e) {   }
    @Override
    public void mouseReleased(MouseEvent e) {   }
    @Override
    public void mouseEntered(MouseEvent e) {   }
    @Override
    public void mouseExited(MouseEvent e) {    }
}
