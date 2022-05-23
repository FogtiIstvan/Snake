/**
* Die Klasse der Schlange.
 */
public class Snake {
    /**
    * Die Attributen von 'Snake':
    *  - Bodylength: Laenge der Schlange;
    *  - SnakeX, SnakeY: Diese Arrays beeinhalten die Köreperteilen der Schlange;
    *  - apple: Der Apfel, welche unsere Schlaneg sucht;
    *  - UNIT_SIZE, GAME_UNIT: Diese Werten muss die Schlange kenne sich gut zu platzieren.
     */
    int Bodylength;
    final int[] SnakeX;
    final int[] SnakeY;
    Apple apple;
    int UNIT_SIZE;
    int GAME_UNIT;

    public Snake(int x, Apple apple, int U){
        this.Bodylength = 6;
        SnakeX = new int[x];
        SnakeY = new int[x];
        this.apple = apple;
        UNIT_SIZE = U;
        GAME_UNIT = x;
    }
    /**
    * Mit dieser Funktion bewegt sich die Schlange. Als Parameter nimmt sie 'Dir' von GamePanel ein.
    * Zuerst schiebt diese Funktion die Schlange mit einem PLatz weiter. Dann abhaengig von 'Dir'
    * platzieren wir der Kopf der Schlange in eine Richtung.
     * @return
     */
    public boolean move(char Dir){
        for(int i=Bodylength; i>0; --i){
            SnakeX[i] = SnakeX[i-1];
            SnakeY[i] = SnakeY[i-1];
        }

        switch (Dir) {
            case 'U' -> SnakeY[0] = SnakeY[0] - UNIT_SIZE;
            case 'D' -> SnakeY[0] = SnakeY[0] + UNIT_SIZE;
            case 'L' -> SnakeX[0] = SnakeX[0] - UNIT_SIZE;
            case 'R' -> SnakeX[0] = SnakeX[0] + UNIT_SIZE;
        }

        return apple.CheckApple(SnakeX[1], SnakeY[1]);
    }
    /**
    * Get Funktion fuer Bodylength
     * @return gibt Bodylength zurück
     */
    public int getBodylength(){
        return Bodylength;
    }
    /**
     * Get Funktion fuer x Koordinaten der Schlange
     * @param i Nummer der beforderte x Koordinate
     * @return gibt die i-te x Koordinate der Schlange zurück,
     */
    public int getsnakex(int i){
        return SnakeX[i];
    }
    /**
     * Get Funktion fuer y Koordinaten der Schlange
     * @param i Nummer der beforderte y Koordinate
     * @return gibt die i-te y Koordinate der Schlange zurück,
     */
    public int getsnakey(int i){
        return SnakeY[i];
    }
    /**
    * Wenn die Schlange eine Apfel gefunden hat waechst seine 'Bodylength' mit eins.
     */
    public void grow(){
        Bodylength++;
    }
    /**
    *Am Ende alles Spieles, wird dieses gerufen. Dieses gibt eine richtige Punktzahl den Spieler,
    * stellt 'Bodylength' auf 6, und platziert die Schlange auf die richtige Position.
     */
    public void resetsnake(int Diff, Player p){
        switch (Diff) {
            case 75 -> p.newscore(10 * (Bodylength - 6));
            case 60 -> p.newscore(20 * (Bodylength - 6));
            case 45 -> p.newscore(30 * (Bodylength - 6));
        }
        Bodylength = 6;
        for(int i = 0;i<GAME_UNIT;++i){
            SnakeX[i] = 0;
            SnakeY[i] = 0;
        }

    }
    /**
    * Dieses ist wie die andere 'resetsnake()' Funktion. Aber dieses ist nur am Anfang des ersten
    * Spieles angerufen. Es muss keine Punktzahl fuer Spieler einstellen.
     */
    public void resetsnake(){
        Bodylength = 6;
        for(int i = 0;i<GAME_UNIT;++i){
            SnakeX[i] = 0;
            SnakeY[i] = 0;
        }

    }
}

