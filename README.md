# Tetris-game

## This is a Tetris game with an adaptive window size and play with a mouse.

<img width="552" alt="Capture" src="https://user-images.githubusercontent.com/38172621/96472787-79eaf380-11f6-11eb-83b0-751ab4ef846d.PNG">

## How to play!
- **Step 1**  
  Select your customized window size and playground size.
- **Step 2**
  1. right click: Tetromino move right
  2. left click: Tetromino move left
- **Step 3**
  1. wheel move forward: Tetromino rotate counter-clockwise
  2. wheel move backward: Tetromino rotate clockwise
- **Step 4**  
  Offset included! You can try your awesome move without crashing the wall or existing Tetrominoes!!
<img width="376" alt="Capture2" src="https://user-images.githubusercontent.com/38172621/96475042-2c23ba80-11f9-11eb-8250-b50ad645b803.PNG">


## Additional functions
- The game will be pause if the cursor is within the main playground area. 
<img width="635" alt="Capture1" src="https://user-images.githubusercontent.com/38172621/96473194-f2ea4b00-11f6-11eb-9928-5ac66883f27c.PNG">

- When the cursor move within the falling piece, the Tetromino will randomly change its type **with penalty** on your score.  

![gif-change shape](https://user-images.githubusercontent.com/38172621/96476738-38107c00-11fb-11eb-8d46-089d22bebbe0.gif)

## Install instruction
- Make sure Java is successfully installed which includes Java JDK and compiler.
[ref link](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-22-04)
```
java -version
javac -version
```
- Run above commands to check environment setting and availability.
- cd to src folder, compile and run program.
```
javac Tetris/*.java
java Tetris/Main
```