import java.util.Random;

public class Apple {
    /**
    * Attributen von Apple:
    *  - AppleX, AppleY sind die Koordinaten.
    *  - Widt und Unit_size hilfen uns die Koordinaten zu ausrechnen.
    *  - rand ist fuer die randomierte Platzierung von Apfeln.
     */
    private int AppleX;
    private int AppleY;
    int Width;
    int Unit_size;
    Random rand;

    Apple(int WIDTH, int UNIT_SIZE){
        rand = new Random();
        Width = WIDTH;
        Unit_size = UNIT_SIZE;
        AppleX = rand.nextInt(WIDTH/UNIT_SIZE)*UNIT_SIZE;
        AppleY = rand.nextInt(WIDTH/UNIT_SIZE)*UNIT_SIZE;
    }
    /**
    * Get Funktion fuer x Koordinaten.
     * @return x Koordinaten des Apfels
     */
    public int getx(){
        return AppleX;
    }
    /**
     * Get Funktion fuer y Koordinaten.
     * @return y Koordinaten des Apfels
     */
    public int gety(){
        return AppleY;
    }
    /**
    * Mit diesem Funktion überprüft die Schlange ob sie eine Apfel gefunden hat.
    * @param x x Koordinaten der Kopf der Schlange
     *@param y y Koordinaten der Kopf der Schlange
     *@return true falls die Schlange eine Apfel gefunden hat.
     *@return false falls die Schlange keine Apfel gefunden hat.
     */
    public boolean CheckApple(int x, int y){
        if(x == AppleX && y == AppleY) {
            AppleX = rand.nextInt(Width/Unit_size)*Unit_size;
            AppleY = rand.nextInt(Width/Unit_size)*Unit_size;
            return true;
        }
        return false;
    }
}
