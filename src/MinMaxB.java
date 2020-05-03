public class MinMaxB {
    private static class Pair {
        final int location;
        final int value;

        Pair(int location, int value) {
            this.location = location;
            this.value = value;
        }
    }
    private static final int LOG_DEPTH = 1;

    private static final int UNSOLVED = 0;
    private static final int WIN = 1;
    private static final int LOSE = -1;

    public static int makeMove(Board b, Player p, Player o) {
        return makeMove(b, p, o, 0).location;
    }

    private static Pair makeMove(Board b, Player p, Player o, int depth) {
        int maximizingLocation = -1;
        int maxValue = Integer.MIN_VALUE;
        int size = b.getLength();

        int[][] weights = new int[size][size];

        size = size * size;

        int totalValue = 0;
        for (int i = 0; i < size; ++i) {
            if (b.isVacant(i)) {
                int value;

                b.set(i, p);
                value = value(b, p, o);

//                if (WIN  == value ||
//                    LOSE == value) {
//                    b.reset(i);
//                    return new Pair(i, value);
//                }

                value = value - makeMove(b, o, p, depth + 1).value;

                totalValue += value;

                if (value >= maxValue) {
                    maxValue = value;
                    maximizingLocation = i;
                }
                b.reset(i);

                weights[i / weights.length][i % weights.length] = value;
            }
        }

        printWeights(depth, weights, b);

        return new Pair(maximizingLocation, totalValue);
    }

    private static void printWeights(int depth, int[][] weights, Board b) {
        if (LOG_DEPTH > depth) {
            System.out.println();
            for (int i = 0; i < weights.length; i++) {
                System.out.print(depth + ": ");
                for (int j = 0; j < weights[i].length; j++) {
                    if (!b.isVacant(i, j))
                        System.out.print('#' + "\t");
                    else
                        System.out.print(weights[i][j] + "\t");
                }
                System.out.println();
            }
        }
    }

    // if draw, then UNSOLVED value is passed, since there is no
    // effect on the upcoming moves
    public static int value(Board b, Player p, Player o) {
        Player winner = b.checkWinner();

        if (p.equals(winner))
            return WIN;

        if (o.equals(winner))
            return LOSE;

        return UNSOLVED;
    }
}
