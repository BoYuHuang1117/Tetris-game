package Tetris;

import java.awt.Color;
import java.util.Random;
/***
 * Author: Bo-Yu Huang
 * Date: 6/10/20
 * A class for choosing consistent drawing color and future element recognition
 */
public class MyElements {
    private Color[] colors;
    private int element;
    MyElements(){
        colors = new Color[7];
        colors[0] = Color.green;    // line
        colors[1] = Color.blue;     // left N
        colors[2] = Color.yellow;   // right N
        colors[3] = Color.magenta;  // -2-
        colors[4] = Color.red;      // left L
        colors[5] = Color.orange;   // right L
        colors[6] = Color.pink;     // square
    }

    private void setElement(int n) {element = n;}
    public Color getColor(int n) {
        //element = new Random().nextInt(7);  // [0...6]
        //System.out.println("element: "+element);
        return colors[n];
    }
}
