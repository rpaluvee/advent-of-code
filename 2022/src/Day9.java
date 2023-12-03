import java.io.IOException;
import java.util.*;

public class Day9 {

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList("input/9.txt");

        Position head = new Position(0, 0);
        Position tail = new Position(0, 0);
        List<Position> bigTail = Arrays.asList(
                new Position(0, 0),
                new Position(0, 0),
                new Position(0, 0),
                new Position(0, 0),
                new Position(0, 0),
                new Position(0, 0),
                new Position(0, 0),
                new Position(0, 0),
                new Position(0, 0)
        );

        Set<Position> visitedPositionsPartOne = new HashSet<>();
        Set<Position> visitedPositionsPartTwo = new HashSet<>();
        visitedPositionsPartOne.add(tail.getPosition());
        visitedPositionsPartTwo.add(bigTail.get(bigTail.size() - 1));

        for (String line : input) {
            String direction = line.split(" ")[0];
            int nrOfMoves = Integer.parseInt(line.split(" ")[1]);
            for (int i = 0; i < nrOfMoves; i++) {
                switch (direction) {
                    case "U" -> head.y++;
                    case "R" -> head.x++;
                    case "D" -> head.y--;
                    case "L" -> head.x--;
                }
                // PART 1
                if (!isTailTouchingHead(head, tail)) {
                    tail = calculateTailPos(head, tail);
                    visitedPositionsPartOne.add(tail.getPosition());
                }
                // PART 2
                for (int t = 0; t < bigTail.size(); t++) {
                    Position knot = bigTail.get(t);
                    Position knotHead = t == 0 ? head : bigTail.get(t - 1);
                    if (!isTailTouchingHead(knotHead, knot)) {
                        knot = calculateTailPos(knotHead, knot);
                        if (t == bigTail.size() - 1) {
                            visitedPositionsPartTwo.add(knot.getPosition());
                        }
                    }
                }
            }
        }

        System.out.println(visitedPositionsPartOne.size());
        System.out.println(visitedPositionsPartTwo.size());
    }

    private static Position calculateTailPos(Position head, Position tail) {
        if (tail.x == head.x) {
            if (tail.y < head.y) {
                tail.y++;
            } else {
                tail.y--;
            }
        } else if (tail.y == head.y) {
            if (tail.x < head.x) {
                tail.x++;
            } else {
                tail.x--;
            }
        } else { // diagonal
            if (head.y > tail.y) {
                if (head.x > tail.x) {
                    tail.x++;
                } else {
                    tail.x--;
                }
                tail.y++;
            } else {
                if (head.x > tail.x) {
                    tail.x++;
                } else {
                    tail.x--;
                }
                tail.y--;
            }
        }
        return new Position(tail.x, tail.y);
    }

    private static boolean isTailTouchingHead(Position head, Position tail) {
        if (head.equals(tail)) {
            return true;
        }
        Position top = new Position(tail.x, tail.y + 1);
        Position topRight = new Position(tail.x + 1, tail.y + 1);
        Position right = new Position(tail.x + 1, tail.y);
        Position bottomRight = new Position(tail.x + 1, tail.y - 1);
        Position bottom = new Position(tail.x, tail.y - 1);
        Position bottomLeft = new Position(tail.x - 1, tail.y - 1);
        Position left = new Position(tail.x - 1, tail.y);
        Position topLeft = new Position(tail.x - 1, tail.y + 1);

        return List.of(top, topRight, right, bottomRight, bottom, bottomLeft, left, topLeft)
                .contains(head);
    }

    static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position getPosition() {
            return new Position(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Position position = (Position) obj;
            return position.x == x && position.y == y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
