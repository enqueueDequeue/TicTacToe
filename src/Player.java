import java.util.Scanner;

public class Player {
    public static final Player DRAW = new Player();

    public final char id;
    private final Scanner sc;
    private final DamnMinMax ai;

    // null Scanner will make the player user computer to solve
    private Player() {
        this.id = '!';
        this.sc = null;
        this.ai = null;
    }

    public Player(char id, Scanner sc) {
        if (Board.INVALID == id ||
            DRAW.id == id)
            throw new IllegalArgumentException();

        this.id = id;
        this.sc = sc;
        this.ai = new DamnMinMax();
    }

    public void turn(Board b) {
        if (null == sc) {
            makeMove(b);
        } else {
            int loc;
            while (!b.isVacant(loc = sc.nextInt())) {
                System.out.println("not vacant");
            }
            b.set(loc, this);
        }
    }

    private void makeMove(Board b) {
        if (null == ai)
            throw new IllegalStateException();

        Player o = b.getOpponent(this);
        int loc = ai.makeMove(b, this, o);
        b.set(loc, this);
    }
}
