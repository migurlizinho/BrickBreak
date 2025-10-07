import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame{

    public Main(){
        init();
    }

    public void init(){
        setTitle("BrickBreak");
        int width = 1280;
        int height = 720;
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        GamePanel gamePanel = new GamePanel(width, height);
        add(gamePanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                gamePanel.start();
            }
        });
        pack();
        gamePanel.requestFocus();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}