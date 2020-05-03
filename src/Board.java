public class Board {
    enum Direction {
        HORIZONTAL,   // direction: -
        VERTICAL,     // direction: |
        DIAGONAL_1_3, // direction: /
        DIAGONAL_2_4  // direction: \
    }

    public static final char INVALID = '-';
    public final Player x;
    public final Player y;
    private final char[][] state;
    private int vacancies;

    public Board(int size, Player x, Player y) {
        this.state = new char[size][size];
        this.x = x;
        this.y = y;
        this.vacancies = size * size;

        for (int i = 0 ; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                state[i][j] = INVALID;
            }
        }
    }

    public Board(Board b) {
        this.x = b.x;
        this.y = b.y;
        this.vacancies = b.vacancies;
        char[][] state = new char[b.state.length][b.state.length];
        char[][] copyState = b.state;

        for (int i = 0, len = state.length; i < len; ++i) {
            for (int j = 0; j < len; ++j) {
                state[i][j] = copyState[i][j];
            }
        }

        this.state = state;
    }

    public void checkValidity(char p) {
        if (x.id != p &&
            y.id != p)
            throw new IllegalArgumentException();
    }

    public Player getPlayer(char p) {
        checkValidity(p);

        return (x.id == p) ? x : y;
    }

    public Player getOpponent(Player p) {
        checkValidity(p.id);

        return p.equals(x) ? y : x;
    }

    public void print() {
        print(this);
    }

    public void printColor() {
        printColor(this);
    }

    public static void printColor(Board board) {
        System.out.println();
        for (int i = 0; i < board.state.length; ++i) {
            for (int j = 0; j < board.state[i].length; ++j) {
                System.out.print("\u001B[31m" + board.state[i][j] + "\033[0m" + " ");
            }
            System.out.println();
        }
    }

    public void set(int loc, Player p) {
        checkValidity(p.id);

        --vacancies;
        state[loc / state.length][loc % state.length] = p.id;
    }

    public void reset(int loc) {
        ++vacancies;
        state[loc / state.length][loc % state.length] = INVALID;
    }

    // 0 indexed
    public boolean isVacant(int loc) {
        return isVacant(loc / state.length, loc % state.length);
    }

    public boolean isVacant(int x, int y) {
        return isVacant(state, x, y);
    }

    public static boolean isVacant(char[][] state, int x, int y) {
        return state[x][y] == INVALID;
    }

    public static void print(Board board) {
        System.out.println();
        for (int i = 0; i < board.state.length; ++i) {
            for (int j = 0; j < board.state[i].length; ++j) {
                System.out.print(board.state[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getVacancies() {
        return vacancies;
    }

    public int getLength() {
        return state.length;
    }

    public Player checkWinner() {
        char winner = checkWinner(this.state);

        if (INVALID != winner)
            return this.getPlayer(winner);

        if (isDraw())
            return Player.DRAW;

        return null;
    }

    public boolean isDraw() {
        return 0 >= getVacancies();
    }

    // doesn't check for draw state
    private static char checkWinner(char[][] state) {
        char ret;

        for (int i = 0; i < state.length; ++i) {
            ret = checkWinner(state, i, Direction.HORIZONTAL);
            if (Board.INVALID != ret) {
                return ret;
            }
        }

        for (int i = 0; i < state.length; ++i) {
            ret = checkWinner(state, i, Direction.VERTICAL);
            if (Board.INVALID != ret) {
                return ret;
            }
        }

        ret = checkWinner(state, 0, Direction.DIAGONAL_1_3);
        if (Board.INVALID != ret) {
            return ret;
        }

        ret = checkWinner(state, 0, Direction.DIAGONAL_2_4);

        return ret;
    }

    private static char checkWinner(char[][] state, int n, Direction direction) {
        if (0 >= state.length) return Board.INVALID;
        if (0 > n || state.length <= n) return Board.INVALID;

        char winner = Board.INVALID;

        switch (direction) {
            case HORIZONTAL -> {
                winner = state[n][0];

                for (int i = 1; i < state.length; ++i) {
                    if (state[n][i - 1] != state[n][i]) {
                        winner = Board.INVALID;
                        break;
                    }
                }
            }

            case VERTICAL -> {
                winner = state[0][n];

                for (int i = 1; i < state.length; ++i) {
                    if (state[i - 1][n] != state[i][n]) {
                        winner = Board.INVALID;
                        break;
                    }
                }
            }

            case DIAGONAL_1_3 -> {
                winner = state[0][state.length - 1];

                for (int i = 1; i < state.length; ++i) {
                    if (state[i - 1][state.length - i] != state[i][state.length - 1 - i]) {
                        winner = Board.INVALID;
                        break;
                    }
                }
            }

            case DIAGONAL_2_4 -> {
                winner = state[0][0];

                for (int i = 1; i < state.length; ++i) {
                    if (state[i - 1][i - 1] != state[i][i]) {
                        winner = Board.INVALID;
                        break;
                    }
                }
            }
        }

        return winner;
    }

    // NOTE: this doesn't consider the players to be the same
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Board b = (Board) o;

        for (int i = 0, len = state.length; i < len; ++i) {
            for (int j = 0; j < len; ++j) {
                if (b.state[i][j] != state[i][j])
                    return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = vacancies;

        for (char[] s : state) {
            for (char c : s) {
                result += c;
            }
        }

        return result;
    }
}
