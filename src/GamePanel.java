import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
* Die Panel welche in Mainframe platziert wird. Es funktioniert unabhaengig von der
* Jframe, in welchen es platziert ist. Es braucht aber eine Klasse Snake, Apple, und Player
* zu funktionieren.
 */

public class GamePanel extends JPanel{
    /**
    * Die nötige Attributen sind hier, am Anfang hergestellt, aber nicht alle sind initialisiert.
    * Width und Height bestimmt die größe der Spielplatz, und UNIT_SIZE steht für die größe der einzelnen
    * Zellen. WIDTH, und HEIGHT müssen eine mehrfache von UNIT_SIZE sein. GAME_UNIT ist die Anzahl
    * der Zellen. Die boolean Variablen sind:
    * - runs: true, falls ein Spiel ist unterwegs, und falsch andererfall(wenn wir in menu sind usw...)
    * - newgame: Wenn wir nicht die erste Spiel in einem "Game" Spielen.
    * Dir, ist für die Richtung unserer Schlange. Timer spielt die Rolle von "Zeit".
    * Difficulty bestimmt die Geschwindigkeit der Schlange. player ist der Spieler, der die gerade
    * laufende Spiel spielt.
     */
    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT_SIZE = 24;
    static final int GAME_UNIT = 625;
    boolean runs = false, newgame = true;
    private final Snake snake;
    private final Apple apple;
    private char Dir = 'R';
    private Timer tim;
    int Difficulty;
    private Player player;
    /**
    * Default Konstruktor fuer GamePanel
    * Zuerst werden die gruendliche Eigenschaften unserer JPanel eingestellt.
    * Apple, und Snake werden beide auch gut fuer unsere JPanel verwirklicht.
     */
    public GamePanel(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        apple = new Apple(WIDTH, UNIT_SIZE);
        snake = new Snake(GAME_UNIT, apple, UNIT_SIZE);
        snake.resetsnake();
    }
    /**
    * Diese Funktion wird gerufen, wenn wir das Spiel beginnen. Diese Funktion ist in
    * MyKeyAdapter zu 'S' gebunden. Im jedem Fall wird der Spielplatz immer wiedergezeichnet, und Dir auf 'R'
    * eingestellt. Weil jede 'startgame()' eine neue Spiel bedeutet, muessen wir runs immmer am Anfang auf
    * 'true' einstellen. Dann kommen noch zwei Zweigen. Wenn wir noch keine Spiel gespielt haben
    * muessen wir das Timer initialisieren. 'Delay' haengt von der Difficulty ab. Der Actionlistener
    * der Timer wird immer wieder angerufen, und sorgt für das Spielmechanik. In anderem Fall (keine neue Spiel),
     * muessen wir das 'Delay' wiedereinstellen abhaengig von der 'Difficulty', und das Timer wieder laufen lassen.
     */
    public void startgame(){
        repaint();
        Dir='R';
        runs = true;
        //--------------------
        if(newgame){
            tim = new Timer(Difficulty, new ActionListener(){
                boolean grow = false;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (grow){
                        snake.grow();
                        Checkwin();
                    }
                    grow = false;
                    if(runs){
                        grow = snake.move(Dir);
                        runs = CheckCollision();
                        repaint();
                    }else if (!runs){
                        reset();
                    }
                }
            });
            tim.start();
        }else if (!newgame){
            tim.setDelay(Difficulty);
            tim.restart();
        }
        //--------------------
    }

    /**
     * Ueberprueft in jedem Rund, ob die Schlange mit dem Wand oder mit sich selbst getroffen hat, oder nicht.
     *
     * @return false, falls ein Anstoß passiert ist.
     *         true, falls keine Anstoß passiert ist.
     */
    public boolean CheckCollision(){
        if(snake.getsnakex(0) < 0 || snake.getsnakex(0) >= WIDTH) return false;
        if(snake.getsnakey(0) < 0 || snake.getsnakey(0) >= HEIGHT) return false;
        for(int i = snake.getBodylength(); i>0; --i){
            if(snake.getsnakex(i) == snake.getsnakex(0) && snake.getsnakey(i) == snake.getsnakey(0))
                return false;
        }
        return true;
    }

    /**
     * Ueberprueft ob das Spieler das Spiel gewonnen hat oder nicht.
     */
    public void Checkwin(){
        if(snake.getBodylength() == 625){
            runs = false;
        }
    }

    /**
     * Stellt Difficulty ein
     * @param Diff die neue Niveaustufe des Spiels.
     */
    public void setdiff(int Diff){
        Difficulty = Diff;
    }

    /**
     * Am Ende eines Spieles wird diese Funktion gerufen.
     * Es stellt alles in den Anfang Zustand.
     */
    public void reset(){
        snake.resetsnake(Difficulty, player);
        newgame = false;
    }

    /**
     *  Stellt ein neues Spieler ein.
     * @param p Spieler des naechstes Spieles
     */
    public void setPlayer(Player p){
        player = p;
    }

    //Background actions---------------------------------------------------------------------

    /**
     * Hilffunktion fuer Graphics.
     * @param g Graphics Objekt
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Diese Funktion wird im Timer immer gerufen, alles zu zeichnen.
     * Sie Zeichnet die Schlange, den Spielplatz, und die Apfel.
     * @param g Graphics Objekt
     */
    public void draw(Graphics g){
        for(int i = 0; i < WIDTH; i+=24){
            g.drawLine(0, i, WIDTH, i);
            g.drawLine(i, 0, i, HEIGHT);

        }
        g.setColor(Color.RED);
        g.fillOval(apple.getx(), apple.gety(), UNIT_SIZE, UNIT_SIZE);


        for(int i = snake.getBodylength(); i>0; --i){
            g.setColor(Color.GREEN);
            g.fillRect(snake.getsnakex(i), snake.getsnakey(i), UNIT_SIZE, UNIT_SIZE);
        }
    }

    /**
     * Eine Innere Klasse, welche fuer die Eingaben von der Tastatur sorgt.
     */
    private class MyKeyAdapter extends KeyAdapter{
        /**
         * Diese Funktion erkennt die einzelne Tasten, und behandelt sie.
         * Das Spiel kann man mit den vier Pfeilen kontrollieren. Mit s kann man ein neues Spiel beginnen.
         * @param e Einkommende Taste
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(Dir != 'D') Dir = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(Dir != 'U') Dir = 'D';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(Dir != 'L') Dir = 'R';
                    break;
                case KeyEvent.VK_LEFT:
                    if(Dir != 'R') Dir = 'L';
                    break;
                case KeyEvent.VK_S:
                    startgame();
                    break;
            }
        }
    }
}

