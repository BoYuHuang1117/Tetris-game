package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static Tetris.MyElements.JLSZT_offsetTable;
import static Tetris.MyElements.I_offsetTable;
import static Tetris.MyElements.pos;


/***
 * Author: Bo-Yu Huang
 * Date: 6/5/20
 * A class extends JPanel for showing the primary playing area (Main area)
 *
 * Date: 6/16 fix the aspect ratio and mouseMotion issues
 * Date: 6/18 add drawing, falling and boundary check
 * Date: 6/20 add mouseScroll event function
 * Date: 6/22 add offset table
 */
public class PlayGround extends JPanel implements MouseListener, ActionListener, MouseMotionListener, MouseWheelListener{
    static Timer timer;

    private final int BOARD_WIDTH = 10;     // 10 px size in width
    private final int BOARD_HEIGHT = 20;    // 20 px size in height
    private final int px = 25;              // side length of the square in logical coordinate

    int xCenter, yCenter;
    float pixelSize, rWidth = 250.0F, rHeight = 500.0F;
    int maxX, maxY;

    private boolean pause = false;
    private boolean gameOver = false;
    private boolean fallingDone = false;
    public MyElements element;
    public int curX = 0, curY = 0;
    int leftMost = -125, bottonMost = -250;
    private int[][] board;

    // get nextBox from Parent Main and to set the nextshpae properly
    private NextShapeBox nextShapeBox;
    // For painting the boundary and the status box
    Point P1;
    Point P2;
    Point P3;
    Point P4;

    PlayGround(Main Parent){
        /***  10x20 grid
         * P1----------
         * |          |
         * |  P3---   |
         * |  |   |   |
         * |  ----P4  |
         * |          |
         * | --------P2
         ***/
        timer = new Timer(500,this);
        timer.start();
        board = new int[BOARD_HEIGHT+1][BOARD_WIDTH+1];
        nextShapeBox = Parent.nextBox;
        newElement();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        clearBoard();
    }

    void initSize() {
        Dimension d = getSize();                                // size of PlayGround JPanel
        maxX = d.width - 1; maxY = d.height - 1;
        pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
        xCenter = maxX/2; yCenter = maxY/2;
    }
    private int iX(float x) {return Math.round(xCenter + x / pixelSize);}
    private int iY(float y) {return Math.round(yCenter - y / pixelSize);}
    private int getType(int x, int y){ return board[y][x];}
    static public void stopTimer(){timer.stop();}

    @Override
    public void paintComponent(Graphics g){
        initSize();
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        P1 = new Point(iX(-rWidth/2), iY(rHeight/2));
        P2 = new Point(iX(rWidth/2), iY(-rHeight/2));
        g.drawRect(P1.X, P1.Y, P2.X-P1.X, P2.Y-P1.Y);

        drawEle(g);

        // 'pause' and 'game over' drawing
        if (pause) {
            P3 = new Point(iX(-50), iY(20));
            P4 = new Point(iX(50), iY(-20));
            g.setColor(Color.blue);
            g.drawRect(P3.X, P3.Y, P4.X-P3.X, P4.Y-P3.Y);
            g.setFont(new Font("TimesRoman", Font.BOLD + Font.PLAIN, Math.round(25/pixelSize)));
            g.drawString("PAUSE", iX(-45), iY(-5));
        }
        else if (gameOver){
            P3 = new Point(iX(-50), iY(20));
            P4 = new Point(iX(50), iY(-20));
            g.setColor(Color.blue);
            g.drawRect(P3.X, P3.Y, P4.X-P3.X, P4.Y-P3.Y);
            g.setFont(new Font("TimesRoman", Font.BOLD + Font.PLAIN, Math.round(17/pixelSize)));
            g.drawString("GameOver", iX(-47), iY(-5));
        }
    }
    private void drawEle(Graphics g){
        for (int i = 0; i < BOARD_WIDTH; i++){
            for (int j = 0; j < BOARD_HEIGHT; j++){
                if (getType(i,j) != 0){
                    g.setColor(element.getColor(board[j][i]));
                    g.fillRect(iX(leftMost+i*px),iY((j+1)*px+bottonMost),Math.round(px/pixelSize),Math.round(px/pixelSize));
                    g.setColor(Color.black);
                    g.drawRect(iX(leftMost+i*px),iY((j+1)*px+bottonMost),Math.round(px/pixelSize),Math.round(px/pixelSize));
                }
            }
        }
        if (element.shape() != 0) {
            for (int i = 0; i < 4; ++i) {
                int x = curX + element.x(i);
                int y = curY + element.y(i)+1;
                g.setColor(element.typeColor());
                g.fillRect(iX(leftMost+x*px),iY(y*px+bottonMost),Math.round(px/pixelSize),Math.round(px/pixelSize));

                g.setColor(Color.black);
                g.drawRect(iX(leftMost+x*px),iY(y*px+bottonMost),Math.round(px/pixelSize),Math.round(px/pixelSize));
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx > P1.X && mx < P2.X && my > P1.Y
                && my < P2.Y && !gameOver) {
            pause = true;
            timer.stop();
            repaint();
        }
        if((mx< P1.X || mx > P2.X || my < P1.Y
                || my > P2.Y) && (!gameOver)) {
            pause = false;
            timer.start();
            repaint();
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (!gameOver){
            pause = false;
            repaint();
            timer.start();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if((mx< P1.X || mx > P2.X || my < P1.Y
                || my > P2.Y) && (!gameOver)) {
            if (e.getButton() == 1)
                tryMove(element, curX-1,curY,0);

            if (e.getButton() == 2) { // scroll click event
                drop();   //testing
                System.out.println("P ScrollButton clicked!");
            }
            if (e.getButton() == 3)
                tryMove(element, curX+1,curY,0);
        }
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int mx = e.getX();
        int my = e.getY();
        int type = e.getWheelRotation();
        if((mx< P1.X || mx > P2.X || my < P1.Y
                || my > P2.Y) && (!gameOver)) {
            if (type > 0) {
                // toward user, CCW
                if (element.shape() == 7)
                    tryMove(element.CCW_turn(),curX,curY, -1);
                else if (element.shape() == 6) {
                    int next = (pos - 1 + 4) % 4;
                    for (int i = 0; i < 5; i++) {
                        if (tryMove(element.CCW_turn(), curX + I_offsetTable[pos][i][0] - I_offsetTable[next][i][0],
                                curY + I_offsetTable[pos][i][1] - I_offsetTable[next][i][1], -1))
                            break;
                    }
                }
                else {
                    int next = (pos - 1 + 4) % 4;
                    for (int i = 0; i < 5; i++) {
                        if(tryMove(element.CCW_turn(), curX + JLSZT_offsetTable[pos][i][0] - JLSZT_offsetTable[next][i][0],
                                curY + JLSZT_offsetTable[pos][i][1] - JLSZT_offsetTable[next][i][1], -1))
                            break;
                        //else
                        //    System.out.println("Type: " + element.shape() + "CCW fail on offset: " + i);

                    }
                }
            }
            else {
                // away user, CW
                if (element.shape() == 7)
                    tryMove(element.CW_turn(),curX,curY, 1);
                else if (element.shape() == 6) {
                    int next = (pos + 1 + 4) % 4;
                    for (int i = 0; i < 5; i++) {
                        if (tryMove(element.CW_turn(), curX + I_offsetTable[pos][i][0] - I_offsetTable[next][i][0],
                                curY + I_offsetTable[pos][i][1] - I_offsetTable[next][i][1], 1))
                            break;
                    }
                }
                else{
                    int next = (pos + 1 + 4) % 4;
                    for (int i = 0; i < 5; i++) {
                        if(tryMove(element.CW_turn(), curX + JLSZT_offsetTable[pos][i][0] - JLSZT_offsetTable[next][i][0],
                                curY + JLSZT_offsetTable[pos][i][1] - JLSZT_offsetTable[next][i][1], 1))
                            break;
                    }
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (fallingDone) {
            fallingDone = false;
            newElement();
        } else
            downOne();

        repaint();
    }

    private void newElement(){
        int type = nextShapeBox.transferShape();
        element = new MyElements(type);
        if (type != 6) {
            curX = 5;
            curY = BOARD_HEIGHT - 1 - element.maxY();
        }
        else{
            curX = 5;
            curY = BOARD_HEIGHT;
        }

        if (!tryMove(element, curX, curY, 0)){
            element.setNull();
            gameOver = true;
            timer.stop();
            repaint();
        }
    }

    private void clearBoard(){
        for (int i = 0; i < BOARD_HEIGHT; i++)
            for (int j = 0; j < BOARD_WIDTH; j++)
                board[i][j] = 0; // this square is not occupied by any element
    }

    private void downOne(){
        if (!tryMove(element, curX, curY-1, 0))
            elementDone();
    }

    private void elementDone() {
        for (int i = 0; i < 4; ++i) {
            int x = curX + element.x(i);
            int y = curY + element.y(i);
            board[y][x] = element.shape();
        }
        removeLines();

        if (!fallingDone) {
            newElement();
        }
    }

    private void removeLines(){
        fallingDone = true;
    }

    // public function for other class to access
    public boolean tryMove(MyElements newPiece, int newX, int newY, int moveType) {
        // moveType: translation = 0, CW = 1, CCW = -1
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY + newPiece.y(i);

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT+1)
                return false;
            if (getType(x, y) != 0)
                return false;
        }
        element = newPiece;
        curX = newX;
        curY = newY;
        pos = (pos + moveType + 4)%4;

        repaint();
        return true;
    }

    public void drop(){
        int newY = curY;

        while (newY > 0){
            if (!tryMove(element, curX, newY-1, 0))
                break;

            newY--;
        }
        elementDone();
    }
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseDragged(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e){ }
}
