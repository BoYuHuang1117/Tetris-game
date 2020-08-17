package Tetris;
/***
 * Author: Bo-Yu Huang
 * Date: 6/10/20
 * A class for storing 2D point coordinate
 */
public class Point {
    public int X;
    public int Y;
    public Point(int x, int y){ X= x; Y = y;}

    public void falling(){ Y+=25;}

    public void CW_turn(){

    }

    public void CCW_turn(){

    }
}
