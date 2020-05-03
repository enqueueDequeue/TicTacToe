import java.util.HashMap;

public class MinMaxDP extends MinMax {
    private HashMap<Board, Double> boardProbabilityMap;

    @Override
    public double value(Board b, Player p, Player o, boolean pTurn, int depth) {
        if (null == boardProbabilityMap) {
            int length = b.getLength();
            boardProbabilityMap = new HashMap<>(length * length);
        }

        Double cacheValue = boardProbabilityMap.get(b);
        if (null != cacheValue)
            return cacheValue;

        Player winner;
        int size = b.getLength();
        double value = -1d;

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
                double moveValue = value(b, p, o, !pTurn, depth + 1);

                value += moveValue;

                b.reset(i);
            }
        }

        value = value / b.getVacancies();

        boardProbabilityMap.put(new Board(b), value);

        return value;
    }
}
