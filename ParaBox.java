package Tetris;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.Hashtable;

import static Tetris.PlayGround.scoreFactor;
import static Tetris.PlayGround.nRows;
import static Tetris.PlayGround.speedFactor;
import static Tetris.PlayGround.BOARD_WIDTH;
import static Tetris.PlayGround.BOARD_HEIGHT;
import static Tetris.PlayGround.px;
import static Tetris.PlayGround.settingDone;

/***
 * Author: Bo-Yu Huang
 * Date: 7/6/20
 * A class extends JPanel for demonstrating multiple JSliders and JLabels
 * Date: add a button listener
 *
 * Date: 7/9/20 fix speedFactor decimal display issue
 */

public class ParaBox extends JPanel implements ChangeListener, MouseListener, MouseWheelListener {
    JLabel scoreFLabel = new JLabel("Scoring factor: " + scoreFactor);
    JLabel rowsLabel = new JLabel("N - number of rows: " + nRows);
    JLabel speedFLabel = new JLabel("Speed factor: " + speedFactor);
    JLabel widthLabel = new JLabel("Width: " + BOARD_WIDTH);
    JLabel heightLabel = new JLabel("Height: " + BOARD_HEIGHT);
    JLabel sizeLabel = new JLabel("Square size: " + px);

    JSlider scoreF = new JSlider(JSlider.HORIZONTAL,1,15,5);
    JSlider rowsReq = new JSlider(JSlider.HORIZONTAL, 20, 40, 20);
    JSlider speedF = new JSlider(JSlider.HORIZONTAL, 1, 10, 3);
    JSlider playWidth = new JSlider(JSlider.HORIZONTAL, 10, 20, 10);
    JSlider playHeight = new JSlider(JSlider.HORIZONTAL, 20, 30, 20);
    JSlider squSize = new JSlider(JSlider.HORIZONTAL, 20, 50, 20);

    JButton sButtom = new JButton("START");
    Main parent;

    ParaBox(Main Parent){
        add(sButtom);
        addMouseListener(this);
        addMouseWheelListener(this);
        sButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreF.setVisible(false);
                rowsReq.setVisible(false);
                speedF.setVisible(false);
                playWidth.setVisible(false);
                playHeight.setVisible(false);
                squSize.setVisible(false);
                settingDone = true;
                parent.changeWindowSize();
                PlayGround.startTimer();
                sButtom.setVisible(false);
            }
        });

        parent = Parent;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scoreF.addChangeListener(this);
        scoreF.setMajorTickSpacing(4);
        scoreF.setMinorTickSpacing(1);
        scoreF.setPaintTicks(true);
        scoreF.setPaintLabels(true);

        rowsReq.addChangeListener(this);
        rowsReq.setMajorTickSpacing(4);
        rowsReq.setMinorTickSpacing(1);
        rowsReq.setPaintTicks(true);
        rowsReq.setPaintLabels(true);

        speedF.addChangeListener(this);
        speedF.setMajorTickSpacing(3);
        //Create the label table
        Hashtable speedTable = new Hashtable();
        speedTable.put( 1, new JLabel("0.1") );
        speedTable.put( 4, new JLabel("0.4") );
        speedTable.put( 7, new JLabel("0.7") );
        speedTable.put( 10, new JLabel("1.0") );

        speedF.setLabelTable( speedTable );
        speedF.setMinorTickSpacing(1);
        speedF.setPaintTicks(true);
        speedF.setPaintLabels(true);

        playWidth.addChangeListener(this);
        playWidth.setMajorTickSpacing(3);
        playWidth.setMinorTickSpacing(1);
        playWidth.setPaintTicks(true);
        playWidth.setPaintLabels(true);

        playHeight.addChangeListener(this);
        playHeight.setMajorTickSpacing(4);
        playHeight.setMinorTickSpacing(1);
        playHeight.setPaintTicks(true);
        playHeight.setPaintLabels(true);

        squSize.addChangeListener(this);
        squSize.setMajorTickSpacing(4);
        squSize.setMinorTickSpacing(1);
        squSize.setPaintTicks(true);
        squSize.setPaintLabels(true);

        this.add(scoreFLabel);
        this.add(scoreF);
        this.add(rowsLabel);
        this.add(rowsReq);
        this.add(speedFLabel);
        this.add(speedF);
        this.add(widthLabel);
        this.add(playWidth);
        this.add(heightLabel);
        this.add(playHeight);
        this.add(sizeLabel);
        this.add(squSize);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        scoreFactor = scoreF.getValue();
        nRows = rowsReq.getValue();
        speedFactor = (double)Math.round(speedF.getValue())*10e-2;
        speedFactor = Math. round(speedFactor * 10) / 10.0;     // make it #.##
        BOARD_WIDTH = playWidth.getValue();
        BOARD_HEIGHT = playHeight.getValue();
        px = squSize.getValue();

        scoreFLabel.setText("Scoring factor: " + scoreFactor);
        rowsLabel.setText("N - number of rows: " + nRows);
        speedFLabel.setText("Speed factor: " + speedFactor);
        widthLabel.setText("Width: " + BOARD_WIDTH);
        heightLabel.setText("Height: " + BOARD_HEIGHT);
        sizeLabel.setText("Square size: " + px);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // void function in case double calling in Mousescrolling when cursor is in the "Start" button area
    }
    @Override
    public void mouseClicked(MouseEvent e) {  parent.mouseClicked(e); }
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
