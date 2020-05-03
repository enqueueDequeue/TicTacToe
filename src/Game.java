import java.util.Scanner;

public class Game {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int size = 3;

        if (args.length >= 1) {
            size = Integer.parseInt(args[0]);
        }

        Player x = new Player('x', null);
        Player y = new Player('y', sc);
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
}
