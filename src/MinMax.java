public class MinMax {
    public int makeMove(Board b, Player p, Player o) {
        int maximizingLocation = -1;
        double maxValue = -1d;
        int size = b.getLength();

        double[][] weights = new double[size][size];

        size = size * size;

        for (int i = 0; i < size; ++i) {
            if (b.isVacant(i)) {
                double value;

                b.set(i, p);
                value = value(b, p, o, false, 1);

                if (value >= maxValue) {
                    maxValue = value;
                    maximizingLocation = i;
                }
                b.reset(i);

                weights[i / weights.length][i % weights.length] = value;
            }
        }

        printWeights(weights, b);

        return maximizingLocation;
    }

    public static void printWeights(double[][] weights, Board b) {
        System.out.println();
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                if (!b.isVacant(i, j))
                    System.out.print("######\t");
                else
                    System.out.printf("%.4f\t", weights[i][j]);
            }
            System.out.println();
        }
    }

    // wrong implementation
    public double value(Board b, Player p, Player o, boolean pTurn, int d) {
        Player winner;
        int size = b.getLength();
        double value = 0d;

        winner = b.checkWinner();

        if (p.equals(winner))
            return 1d;

        if (o.equals(winner))
            return -1d;

        if (Player.DRAW.equals(winner))
            return 0d;

        for (int i = 0; i < size * size; ++i) {
            if (b.isVacant(i)) {
                b.set(i, pTurn ? p : o);

                value += value(b, p, o, !pTurn, d + 1);

                b.reset(i);
            }
        }

        return value / ( b.getVacancies() * d );
    }
}
