package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

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
 * Date: 7/1 add removeLines function and repaint the score board
 * Date: 7/6 change several variables to static
 */
public class PlayGround extends JPanel implements MouseListener, ActionListener, MouseMotionListener, MouseWheelListener{
    /////////////////////////////////////////////////////////////////
    static Timer timer;
    static int FS = 500;
    static int scoreFactor = 5, nRows = 5, level = 1, lines = 0, scores = 0;
    static double speedFactor = 0.3;
    static int BOARD_WIDTH = 10;     // initial 10 px size in width
    static int BOARD_HEIGHT = 20;    // initial 20 px size in height
    static int px = 25;              // side length of the square in logical coordinate
    static boolean settingDone = false;
    /////////////////////////////////////////////////////////////////
    int xCenter, yCenter;
    float pixelSize, rWidth = (float)BOARD_WIDTH*px, rHeight = (float)BOARD_HEIGHT*px;
    int maxX, maxY;

    private boolean pause = false;
    private boolean change = false;
    private boolean gameOver = false;
    private boolean fallingDone = false;
    public MyElements element;
    public int curX = 0, curY = 0;
    int leftMost = -125, bottonMost = -250;
    private int[][] board;

    // get nextBox from Parent Main and to set the nextshpae properly
    private NextShapeBox nextShapeBox;
    private ScoreBoard ScoreBoard;
    // For painting the boundary and the status box
    Point P1;
    Point P2;
    Point P3;
    Point P4;

    PlayGround(Main Parent){
        /***  BOARD_WIDTHxBOARD_HEIGHT grid
         * P1----------
         * |          |
         * |  P3---   |
         * |  |   |   |
         * |  ----P4  |
         * |          |
         * | --------P2
         ***/
        timer = new Timer(FS,this);
        //timer.start();
        nextShapeBox = Parent.nextBox;
        ScoreBoard = Parent.scoreBoard;
        board = new int[BOARD_HEIGHT+1][BOARD_WIDTH+1];
        newElement();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        clearBoard();
    }

    void initSize() {
        Dimension d = getSize();                              // size of PlayGround JPanel
        rWidth = (float)(BOARD_WIDTH*px); rHeight = (float)(BOARD_HEIGHT*px);
        leftMost = -(BOARD_WIDTH*px)/2; bottonMost = -(BOARD_HEIGHT*px)/2;
        maxX = d.width - 1; maxY = d.height - 1;
        pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
        xCenter = maxX/2; yCenter = maxY/2;
    }
    private int iX(float x) {return Math.round(xCenter + x / pixelSize);}
    private int iY(float y) {return Math.round(yCenter - y / pixelSize);}
    float fx(int x) {return (x - xCenter) * pixelSize;}
    float fy(int y) {return (yCenter - y) * pixelSize;}
    private int getType(int x, int y){ return board[y][x];}
    public void initializeBoard() {board = new int[BOARD_HEIGHT+1][BOARD_WIDTH+1];}
    static public void startTimer() {timer.start();}
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

    private void changeEle(){
        int E1 = nextShapeBox.getType();
        int E2 = element.shape();
        int E3 = E1;
        while (E3 == E1 || E3 == E2){
            System.out.println("try"+E3);
            E3 = new Random().nextInt(7)+1;  // [0...6]+1
            element = new MyElements(E3);
            if (!tryMove(element, curX, curY, 0)){
                E3 = E1;
                continue;
            }
        }
        change = true;
        repaint();
        scores -= level*scoreFactor; // lose score every time if shape changed once!?
        if (scores <= 0)
            scores = 0;
        ScoreBoard.updateScore();
    }

    private boolean insidePoly(int mX, int mY){
        float mx = fx(mX), my = fy(mY);

        //int count = 0;
        for (int i = 0; i < 4; i++){
            int x = curX + element.x(i);
            int y = curY + element.y(i)+1;
            if (my > y*px+bottonMost || my < (y-1)*px+bottonMost){continue;}
            if (leftMost+x*px > mx){continue;}
            else if (leftMost+(x+1)*px > mx){
                return true;
            }
        }

        return false;
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx > P1.X && mx < P2.X && my > P1.Y
                && my < P2.Y && !gameOver && settingDone) {
            pause = true;
            timer.stop();
            repaint();
            // point-inside-polygon test
            if (!change && insidePoly(mx,my)){
                changeEle();
            }
            else if (!insidePoly(mx,my))
                change = false;
        }
        if((mx< P1.X || mx > P2.X || my < P1.Y
                || my > P2.Y) && !gameOver && settingDone) {
            pause = false;
            change = false;
            timer.start();
            repaint();
        }

    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (!gameOver && settingDone){
            pause = false;
            change = false;
            repaint();
            timer.start();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if((mx< P1.X || mx > P2.X || my < P1.Y
                || my > P2.Y) && (!gameOver) && settingDone) {
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
                || my > P2.Y) && (!gameOver) && settingDone) {
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
        // find the full rows
        int count = 0;

        for (int i = BOARD_HEIGHT-1; i >= 0; i--){
            boolean lineIsFull = true;

            for (int j = 0; j < BOARD_WIDTH; j++){
                if (board[i][j] == 0){
                    lineIsFull = false;
                    break;
                }
            }
            if (lineIsFull){
                count++;
                for (int k = i; k < BOARD_HEIGHT-1;k++){
                    for (int j = 0; j < BOARD_WIDTH; j++)
                        board[k][j] = board[k+1][j];
                }
            }
        }

        if (count != 0){
            fallingDone = true;
            countLines(count);
            element = new MyElements(0);
            repaint();
        }
    }
    public void countLines(int n){
        lines += n;
        if (lines >= nRows) {
            System.out.println("level up");
            speedUP();
        }
        countScore();
        ScoreBoard.updateScore();
    }

    public void speedUP(){
        lines = 0;
        level += 1;
        timer.setInitialDelay(Math.round(FS*(1+(float)(level*speedFactor))));
        System.out.println(Math.round(FS*(1+(float)(level*speedFactor))));
    }
    public void countScore() { scores += level*scoreFactor; }

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
