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
    private final Boolean[][] gameStateArray = new Boolean[40][40];
    private Boolean isGameStarted = false;

    // class constructor
    public Application(){
        // adding the mouse listener
        this.addMouseListener(this);

        // initialise the game state array to all false
        for(int i = 0; i < 40; i++){
            for(int j = 0; j < 40; j++){
                gameStateArray[i][j] = false;
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
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
            // periodically calling the repaint method
            repaint();

        }
    }

    // mouse events which must be implemented for MouseListener
    @Override
    public void mousePressed(MouseEvent e) {
        // get the mouse co-ordinates when the mouse is clicked
        Point mouseClick = e.getPoint();

        // toggle the state of the square at the corresponding index of the game state array
        gameStateArray[(int)mouseClick.x/20][(int)mouseClick.y/20] = !gameStateArray[(int)mouseClick.x/20][(int)mouseClick.y/20];
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

        // Checking the game state
        if(isGameStarted) {
            // looping through the game stata array
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 40; j++) {
                    // if the current cell is true then paint a white square at those co-ordinates
                    if (gameStateArray[i][j]) {
                        g.setColor(Color.WHITE);
                        g.fillRect(i * 20, j * 20, 20, 20);
                    }
                    // Otherwise paint a black square at those co-ordinates
                    else {
                        g.setColor(Color.BLACK);
                        g.fillRect(i * 20, j * 20, 20, 20);
                    }
                }
            }
        }

        // flip the buffers off-screen<-->on-screen
        strategy.show();
    }

    public static void main(String[] args){
        Application a = new Application();
    }

}
