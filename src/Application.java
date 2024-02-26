import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.awt.event.*;

public class Application extends JFrame implements Runnable, MouseListener {
    // member data
    private static final Dimension WindowSize = new Dimension(800,800);
    private final BufferStrategy strategy;
    private final Graphics offscreenGraphics;
    private final Boolean[][][] gameStateArray = new Boolean[40][40][2];
    private Boolean isGameStarted = false;

    // class constructor
    public Application(){
        // adding the mouse listener
        this.addMouseListener(this);

        // initialise the game state array to all false
        for(int i = 0; i < 40; i++){
            for(int j = 0; j < 40; j++){
                gameStateArray[i][j][0] = false;
            }
        }

        // Display the window, centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2 - WindowSize.width/2;
        int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);
        this.setTitle("Conway's Game of Life");

        // initialise double-buffering
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        offscreenGraphics = strategy.getDrawGraphics();

        // create and start our animation thread
        Thread t = new Thread(this);
        t.start();

    }

    public void run() {
        while (1 == 1) {
            //sleep for 1/50 sec
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            // periodically calling the repaint method
            repaint();

        }
    }

    public void checkNeighbours(){
        int  neighbours;

        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 40; y++) {
                neighbours = 0;
                // count the live neighbours of cell [x][y][0]
                for (int xx = -1; xx <= 1; xx++) {
                    for (int yy = -1; yy <= 1; yy++) {
                        if (xx != 0 || yy != 0) {
                            // check cell [x+xx][y+yy][0]
                            // but.. what if x+xx==-1, etc. ?
                                if(gameStateArray[((x+xx)%39)+1][((y+yy)%39)+1][0]){
                                    neighbours++;
                                }
                        }
                    }
                }
                if(neighbours > 0){
                    System.out.println(neighbours);
                }

            }
        }
    }

    // mouse events which must be implemented for MouseListener
    @Override
    public void mousePressed(MouseEvent e) {
        // get the mouse co-ordinates when the mouse is clicked
        Point mouseClick = e.getPoint();

        if (!isGameStarted) {
            // checking if the start button has been pressed
            if (mouseClick.x > 15 && mouseClick.x < 72 && mouseClick.y > 40 && mouseClick.y < 60) {
                isGameStarted = true;
                System.out.println("Start has been clicked");
            }
            // checking if the Random button was pressed
            else if (mouseClick.x > 87 && mouseClick.x < 174 && mouseClick.y > 40 && mouseClick.y < 60) {
                isGameStarted = true;
                System.out.println("Random has been clicked");
            }
            else {
                // toggle the state of the square at the corresponding index of the game state array
                gameStateArray[(int) mouseClick.x / 20][(int) mouseClick.y / 20][0] = !gameStateArray[(int) mouseClick.x / 20][(int) mouseClick.y / 20][0];
            }

        }
        this.repaint();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        g = offscreenGraphics;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 800);
        // looping through the game state array
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                // if the current cell is true then paint a white square at those co-ordinates
                if (gameStateArray[i][j][0]) {
                    g.setColor(Color.WHITE);
                    g.fillRect(i * 20, j * 20, 20, 20);
                }
            }

        }
        if(!isGameStarted){
            g.setColor(Color.GREEN);
            g.fillRect(15, 40, 57, 20);
            g.fillRect(87, 40, 87, 20);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("Start", 20, 57);
            g.drawString("Random", 92, 57);
        }

        // flip the buffers off-screen<-->on-screen
        strategy.show();
    }

    public static void main(String[] args){
        Application a = new Application();
    }

}
