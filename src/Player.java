import java.io.Serializable;

/**
* Diese Klasse ist fuer die Spieler. Es soll 'Serializable' sein, weil wir die Spieler
* in einem Arraylist einschreiben, und dann am Ende alles Spieles serialisieren.
 */
public class Player implements Serializable{
    /**
    * Alle Player hat eine Username fuer Identifizierung,
    * un eine int maxscore der seine Maximum Punktzahl speichert.
     */
    private final String Username;
    private int maxscore;
    /**
    *Konstruktor. Am Anfang hat alle Spieler eine Name und 0 Punkt.
     */
    public Player(String name){
        Username = name;
        maxscore = 0;
    }
    /**
    * Einstellung fuer maxscore. Am Ende alles Spieles wird diese Funktion gerufen.
    * Eine neue maxscore wird nur dann eingestellt, wenn es groeßer ist alls die Alte.
    *
    * @param i die neue moegliche Punktzahl.
     */
    public void newscore(int i){
        if(i>maxscore) maxscore = i;
    }

    /**
    * Get Fuktionen fuer Username
     * @return Username
     */
    public String getName(){
        return Username;
    }
    /**
     * Get Fuktionen fuer maxscore
     * @return maxscore
     */
    public int getScore(){
        return maxscore;
    }

}
