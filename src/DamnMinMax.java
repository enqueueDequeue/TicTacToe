import java.util.HashMap;

public class DamnMinMax {
    private static final int WIN = 1;
    private static final int LOSE = -1;
    private static final int UNSOLVED = 0;

    private HashMap<Board, Integer> boardValueMap;

    public int minimax(Board b, Player p, Player o, int depth, Boolean isMax) {
        int score = value(b, p, o);
        int size = b.getLength();
        size = size * size;

        Integer cacheScore = boardValueMap.get(b);
        if (null != cacheScore) {
            if (depth < 3 ||
                depth > 10) {
                System.out.println("depth: " + depth);
                b.print();
                System.out.println("cacheScore: " + cacheScore);
            }
            return cacheScore;
        }

        if (WIN == score)
            return score;

        if (LOSE == score)
            return score;

        if (0 >= b.getVacancies())
            return UNSOLVED;

        int best;
        if (isMax) {
            best = Integer.MIN_VALUE;

            // Traverse all cells
            for (int i = 0; i < size; i++) {
                if (b.isVacant(i)) {
                    b.set(i, p);

                    best = Math.max(best, minimax(b, p, o, depth + 1, false));

                    b.reset(i);
                }
            }
        } else {
            best = Integer.MAX_VALUE;

            // Traverse all cells
            for (int i = 0; i < size; i++) {
                if (b.isVacant(i)) {
                    b.set(i, o);

                    best = Math.min(best, minimax(b, p, o, depth + 1, true));

                    b.reset(i);
                }
            }
        }

        boardValueMap.put(new Board(b), best);

        return best;
    }

    public int makeMove(Board b, Player p, Player o) {
        int bestVal = Integer.MIN_VALUE;
        int location = -1;
        int size = b.getLength();
        size *= size;

        if (null == boardValueMap) {
            System.out.println("creating map");
            int cacheValue = size;
            cacheValue *= cacheValue;
            cacheValue *= cacheValue;
            cacheValue *= size;
            boardValueMap = new HashMap<>(cacheValue);
            System.out.println("created map");
        }


        for (int i = 0; i < size; i++) {
            // Check if cell is empty
            if (b.isVacant(i)) {
                // Make the move
                b.set(i, p);

                int moveVal = minimax(b, p, o, 0, false);

                b.reset(i);

                if (moveVal > bestVal) {
                    location = i;
                    bestVal = moveVal;
                }
            }
        }

        return location;
    }

    public int value(Board b, Player p, Player o) {
        Player winner = b.checkWinner();

        if (p.equals(winner))
            return WIN;

        if (o.equals(winner))
            return LOSE;

        return UNSOLVED;
    }
}
