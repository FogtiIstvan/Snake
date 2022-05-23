import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Diese Klasse ist fuer die Darstellung haftbar. Sie handelt eine Cardlayout,
 * die verschiedene Fenstern zu darstellen. Es gibt eine Anmeldungs Fenster, ein Menu Fenster,
 * ein Fenster fuer den Spielplatz, und ein Ranglist Fenster.
 * Die Eingaben sind mit ActionListeners geloest.
 */
public class MainFrame extends JFrame{
    private final GamePanel gp;
    private final JButton jb2;
    private final Container Mainpanels;
    private final CardLayout cl;
    private final JTextField jtf;
    private Player player;
    private final DefaultTableModel model;
    private int rows = 0, Diffi;
    private boolean notloaded= true;
    ArrayList<Player> playerslist;

    public MainFrame(){
        Mainpanels = getContentPane();
        cl = new CardLayout();
        Mainpanels.setLayout(cl);
        JPanel menucard = new JPanel(new GridLayout(5, 1));
        JPanel gamecard = new JPanel(new FlowLayout());
        JPanel rangcard = new JPanel(new FlowLayout());
        JPanel firstcard = new JPanel(null);
        Diffi = 75;
        //Player p;

        //FIRSTCARD---------------------------------------------------
        Mainpanels.add("c", firstcard);
        jtf = new JTextField("Enter your name");
        jtf.setEditable(true);
        firstcard.add(jtf);
        jtf.setBounds(400, 100, 200, 50);
        jtf.addKeyListener(new Keytomenu());

        //MENUCARD---------------------------------------------------
        Mainpanels.add("a", menucard);
        JPanel p1 = new JPanel(null);
        menucard.add(p1);
        JPanel p2 = new JPanel(null);
        menucard.add(p2);
        JPanel p3 = new JPanel(null);
        menucard.add(p3);
        JPanel p4 = new JPanel(null);
        menucard.add(p4);
        JPanel p5 = new JPanel(null);
        menucard.add(p5);

        JButton jb1 = new JButton("Let's play!");
        jb1.setBounds(400, 20, 200, 50);
        jb1.addActionListener(new gotoGame());
        p1.add(jb1);

        jb2 = new JButton("Easy");
        jb2.addActionListener(new changestage());
        jb2.setBounds(400, 20, 200, 50);
        p2.add(jb2);

        JButton jb3 = new JButton("Ranglist");
        jb3.addActionListener(new gotoranglist());
        jb3.setBounds(400, 20, 200, 50);
        p3.add(jb3);

        JButton close = new JButton("close");
        close.setBounds(400, 20, 200, 50);
        close.addActionListener(new MyExit());
        p4.add(close);

        JButton newplayer = new JButton("New player");
        newplayer.setBounds(400, 20, 200, 50);
        newplayer.addActionListener(new gotoFirstcard());
        p5.add(newplayer);


        //GAMECARD---------------------------------------------------
        Mainpanels.add("b", gamecard);

        gp = new GamePanel();
        gamecard.add(gp);
        gp.setFocusable(true);
        gp.setdiff(Diffi);
        JButton jb4 = new JButton("Go back to menu");
        jb4.addActionListener(new gotoMenufromGame());
        gamecard.add(jb4);



        //RANGCARD---------------------------------------------------
        Mainpanels.add("d", rangcard);

        model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Place");
        model.addColumn("Name");
        model.addColumn("Best score");
        Object[] o = {"Place", "Name", "Best score"};
        model.addRow(o);
        JButton jb5 = new JButton("Back to Menu");
        jb5.addActionListener(new gotoMenu());
        rangcard.add(jb5);
        rangcard.add(table);

        //END OF CLASS---------------------------------------------------
        setSize(1000, 650);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Klasse fuer 'Let's play' Button.
     */
    private class gotoGame implements ActionListener{
        /**
         * Onclick zeigt es das Spielfenster, und gibt ihn das Focus.
         * @param evt Click
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            cl.show(Mainpanels, "b");
            gp.requestFocus(true);
        }

    }
    /**
     * Klasse fuer 'Back to menu' Button.
     */
    public class gotoMenu implements ActionListener{
        /**
         * Zeigt das Menufenster.
         * @param evt
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            cl.show(Mainpanels, "a");
        }

    }
    /**
     * Klasse fuer 'New Player' Button.
     */
    private class gotoFirstcard implements ActionListener{
        /**
         * Zeigt das Anmeldungsfenster
         * @param evt
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            cl.show(Mainpanels, "c");
        }
    }
    /**
     * Klasse fuer 'Go back to menu' Button.
     */
    private class gotoMenufromGame implements ActionListener{
        /**
         * Zeigt das Menufenster. Addiert das Spieler zum Arraylist, und sortiert diese List.
         * @param evt
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            cl.show(Mainpanels, "a");
            addplayer(player, playerslist);
            playerslist.sort(new playersorter());
        }

    }
    /**
     * Klasse fuer 'Easy/Medium/Advanced' Button.
     */
    private class changestage implements ActionListener{
        /**
         * Waechselt die Niveaustufen des Spieles auf Click Event.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(jb2.getText().equals("Easy")){
                jb2.setText("Medium");
                Diffi = 60;
                gp.setdiff(Diffi);
            }else if(jb2.getText().equals("Medium")){
                jb2.setText("Advanced");
                Diffi = 45;
                gp.setdiff(Diffi);
            }else if(jb2.getText().equals("Advanced")){
                jb2.setText("Easy");
                Diffi = 75;
                gp.setdiff(Diffi);
            }
        }
    }
    /**
     * Klasse fuer 'Ranglist' Button.
     */
    private class gotoranglist implements ActionListener{
        /**
         * Zeigt den Benutzer die Spieler die bevor gespielt haben in eine geordnete Liste.
         * Es benutzt JTable zu visualisieren.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            cl.show(Mainpanels, "d");
            for(int i = rows;i>0;--i){
                model.removeRow(i);
            }
            int i = 1;
            for(Player pl : playerslist){
                Object[] ob = {i +".", pl.getName(), pl.getScore()};
                model.addRow(ob);
                i++;
                rows++;
            }
            rows = playerslist.size();
        }
    }
    /**
     * Klasse fuer 'Enter' Taste.
     */
    private class Keytomenu extends KeyAdapter{
        /**
         * Zeigt das Menufenster.
         * verwirklicht eine neue 'Player' Objekt mit der eingegebene Name in JTextField
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                cl.show(Mainpanels, "a");
                player = new Player(jtf.getText());
                if(notloaded){
                    playerslist = load("players.tmp");
                    notloaded = false;
                }
                gp.setPlayer(player);
            }
        }
    }

    /**
     * liest das File ein, wo die Liste von Spieler ist. Sie benutzt
     * Deserialisationsverfahrung.
     * @param file Name des Files, wovon wir einlesen.
     * @return gibt die Liste zurueck.
     */
    public ArrayList<Player> load(String file){
        ArrayList<Player> pl = new ArrayList<Player>();
        try {
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);
            pl = (ArrayList<Player>)oi.readObject();
            oi.close();
        } catch (Exception e) {
            System.out.println("Problem while loading");
        }
        return pl;
    }

    /**
     * Speichert die Liste mit Serialisationsverfahrung.
     * @param File das File wohin die Liste speichert wird.
     * @param list Die Liste, welche wir speichern wollen.
     */
    public void save(String File, ArrayList<Player> list){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(File));
            out.writeObject(list);
            out.close();
        } catch (Exception e) {
            System.out.println("Problem while saving");
        }
    }

    /**
     * Diese Funktion addiert die Spieler in unsere Liste. Sie erkennt, falls das Spieler schon gespielt
     * hat.
     * @param pl neue Spieler
     * @param list List von Spieler
     */
    public void addplayer(Player pl, ArrayList<Player> list){
        for(Player p : list){
            if(p.getName().equals(pl.getName())){
                pl.newscore(p.getScore());
                playerslist.remove(p);
                playerslist.add(pl);
                return;
            }
        }
        list.add(pl);
    }
    /**
     * Klasse fuer 'Close' Button.
     */
    private class MyExit implements ActionListener{
        /**
         * Speichert die Liste von Spieler, und tretet aus.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            save("players.tmp", playerslist);
            System.exit(0);
        }

    }
}

