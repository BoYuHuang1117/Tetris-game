package Tetris;

import java.awt.Color;
import java.util.Random;
/***
 * Author: Bo-Yu Huang
 * Date: 6/10/20
 * A class for choosing consistent drawing color and future element recognition
 *
 * Date: 6/18 define color and relative coordinate for each type
 * Date: 6/20 add clock wise and counter-clock wise rotation functions
 * Date: 6/22 add offset table to implement kick test
 */
public class MyElements {
    static int[][][] JLSZT_offsetTable = new int[][][]{
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
            {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
            {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}}
    };
    static int[][][] I_offsetTable = new int[][][]{
        {{0, 0}, {-1, 0}, {2, 0}, {-1, 0}, {2, 0}},
        {{-1, 0}, {0, 0}, {0, 0}, {0, 1}, {0, -2}},
        {{-1, 1}, {1, 1}, {-2, 1}, {1, 0}, {-2, 0}},
        {{0, 1}, {0, 1}, {0, 1}, {0, -1}, {0, 2}}
    };
    static int pos;

    /** type define
     * 0 -> no element
     * 1 -> J, purple
     * 2 -> L, red
     * 3 -> S, yellow
     * 4 -> T, orange
     * 5 -> Z, blue
     * 6 -> I, green
     * 7 -> O, cyan
     */
    private int coords[][];
    private int[][][] coordsTable;

    private int type;
    private Color[] colors;
    MyElements(){
        // used in create a element in PlayGround and transfer to NextShapeBox
        coords = new int[4][2];
        coordsTable = new int[][][] {
                { { 0, 0 }, { 0, 0 },  { 0, 0 },  { 0, 0 } },
                { { 0, 0 }, { 0, -1},  { 1, -1 }, { 2, -1} },
                { { 0, -1}, { 1, -1},  { 2, -1 }, { 2, 0 } },
                { { 1, 0 }, { 0, 0 },  { 0, -1 }, { -1,-1} },
                { { 0, 0 }, { -1,-1},  { 0, -1 }, { 1, -1} },
                { { -1,0 }, { 0, 0 },  { 0, -1 }, { 1, -1} },
                { { -1,0 }, { 0, 0 },  { 1, 0  }, { 2, 0 } },
                { { 0, 0 }, { 1, 0 },  { 1, -1 }, { 0,-1 } }
        };
        type = new Random().nextInt(7)+1;  // [0...6]+1
        //System.out.println("initial: "+type);
        for (int i = 0; i < 4; i++)
            System.arraycopy(coordsTable[type], 0, coords, 0, 4);

        colors = new Color[7];
        colors[0] = new Color(75,0,130);        // J
        colors[1] = Color.red;                           // L
        colors[2] = Color.yellow;                        // S
        colors[3] = Color.orange;                        // T
        colors[4] = new Color(31, 97, 141 );    // Z
        colors[5] = Color.green;                         // I
        colors[6] = new Color(93, 173, 226 );    // O
    }

    MyElements(int n){
        // used in NextShapeBox to PlayGround
        type = n;
        pos = 0;
        coords = new int[4][2];
        coordsTable = new int[][][] {
                { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
                { { -1, 1 },  { -1, 0 },  { 0, 0 },   { 1, 0 } },
                { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
                { { -1, 0 },  { 0, 0 },   { 0, 1 },   { 1, 1 } },
                { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
                { { -1, 1 },  { 0, 1 },   { 0, 0 },   { 1, 0 } },
                { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 2, 0 } },
                { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } }
        };
        //System.out.println("initial: "+type);
        for (int i = 0; i < 4; i++)
            System.arraycopy(coordsTable[type], 0, coords, 0, 4);

        colors = new Color[7];
        colors[0] = new Color(75,0,130);        // J
        colors[1] = Color.red;                          // L
        colors[2] = Color.yellow;                       // S
        colors[3] = Color.orange;                       // T
        colors[4] = new Color(31, 97, 141 );    // Z
        colors[5] = Color.green;                        // I
        colors[6] = new Color(93, 173, 226 );    // O
    }
    public void setNull() {type = 0; }
    public int shape() { return type; }
    public Color getColor(int n) { return colors[n-1]; }
    public Color typeColor() { return colors[type-1]; }

    int maxY() {
        int m = coords[0][1];
        for (int i = 1; i < 4; i++)
            m = Math.max(m, coords[i][1]);
        return m;
    }
    private void setX(int index, int x) {
        coords[index][0] = x;
    }
    private void setY(int index, int y) {
        coords[index][1] = y;
    }
    public int x(int index) {
        return coords[index][0];
    }
    public int y(int index) {
        return coords[index][1];
    }

    public MyElements CW_turn(){
        /**
         * Clock wise turn matrix
         * 0  | 1
         * -------
         * -1 | 0
         **/
        if (type == 7)
            return this;
        var testing = new MyElements(type);

        for (int i = 0; i < 4; i++){
            testing.setX(i,y(i));
            testing.setY(i,-x(i));
        }

        return testing;
    }

    public MyElements CCW_turn(){
        /**
         * Counter clock wise turn matrix
         * 0 | -1
         * -------
         * 1 | 0
         **/
        if (type == 7)
            return this;
        var testing = new MyElements(type);

        for (int i = 0; i < 4; i++){
            testing.setX(i,-y(i));
            testing.setY(i,x(i));
        }

        return testing;
    }
}
