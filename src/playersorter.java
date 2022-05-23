import java.util.Comparator;

/**
 * Die Klasse fuer die Sortierung der 'Player' Objekten.
 */
public class playersorter implements Comparator<Player> {
    /**
     *
     * @param o1 erste Spieler
     * @param o2 zweite Spieler
     * Diese zwei Spieler wurden kompariert.
     * @return  '-', falls maxscore von o1 groeßer ist
     *          '0', falls die maxscore-en gleich sind
     *          '1', falls maxscore von o2 groeßer ist.
     */
    @Override
    public int compare(Player o1, Player o2) {
        return o2.getScore() - o1.getScore();
    }

}
