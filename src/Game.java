import java.util.Scanner;

public class Game {
    private static final int X_IS_AI = 1;
    private static final int Y_IS_AI = 2;
    private static final int NO_AI   = 0;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int size = 3;
        int thoughtDepth = Integer.MAX_VALUE;
        int whoIsAi = NO_AI;

        if (args.length >= 1) {
            whoIsAi = Integer.parseInt(args[0]);
        }

        if (args.length >= 2) {
            size = Integer.parseInt(args[1]);
        }

        if (args.length >= 3) {
            thoughtDepth = Integer.parseInt(args[2]);
        }

        Player x = new Player('x', isFlagSet(whoIsAi, X_IS_AI) ? null : sc, thoughtDepth);
        Player y = new Player('y', isFlagSet(whoIsAi, Y_IS_AI) ? null : sc, thoughtDepth);
        Board b = new Board(size, x, y);

        String message;
        Player winner;

        while (true) {
            winner = b.checkWinner();

            if (null != winner)
                break;

            b.print();
            x.turn(b);

            winner = b.checkWinner();

            if (null != winner)
                break;

            b.print();
            y.turn(b);
        }

        System.out.println("----------------------");
        b.print();
        System.out.println("----------------------");

        message = (Player.DRAW.equals(winner)) ? "draw" : "winner: " + winner.id;

        System.out.println(message);
        sc.close();
    }

    private static boolean isFlagSet(int flag, int FLAG) {
        return (flag & FLAG) == FLAG;
    }
}
