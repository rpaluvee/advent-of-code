import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day8 {

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList("input/8.txt");
        int nrOfLines = input.size();

        // build matrix
        int[][] matrix = new int[nrOfLines][input.get(0).length()];
        for (int y = 0; y < nrOfLines; y++) {
            for(int x = 0; x < input.get(y).length(); x++) {
                String value = String.valueOf(input.get(y).charAt(x));
                matrix[y][x] = Integer.parseInt(value);
            }
        }

        // solution
        int nrOfVisibleTrees = 0;
        List<Integer> scores = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (isVisible(x, y, matrix, nrOfLines)) {
                    nrOfVisibleTrees++;
                }
                scores.add(getScore(x, y, matrix, nrOfLines));
            }
        }

        // PART 1
        System.out.println(nrOfVisibleTrees);
        // PART 2
        System.out.println(Collections.max(scores));
    }

    private static boolean isVisible(int x, int y, int[][] matrix, int nrOfLines) {
        boolean isEdge = (x == 0 || y == 0 || x == matrix[y].length - 1 || y == nrOfLines - 1);
        return isEdge ||
                !isBlockedFromLeft(x, y, matrix).blocked ||
                !isBlockedFromRight(x, y, matrix).blocked ||
                !isBlockedFromTop(x, y, matrix).blocked ||
                !isBlockedFromBottom(x, y, matrix, nrOfLines).blocked;
    }

    private static int getScore(int x, int y, int[][] matrix, int nrOfLines) {
        return isBlockedFromLeft(x, y, matrix).score *
                isBlockedFromRight(x, y, matrix).score *
                isBlockedFromTop(x, y, matrix).score *
                isBlockedFromBottom(x, y, matrix, nrOfLines).score;
    }

    private static Result isBlockedFromLeft(int x, int y, int[][] matrix) {
        Result result = new Result();
        for (int i = x - 1; i >= 0; i--) {
            result.score++;
            if (matrix[y][x] <= matrix[y][i]) {
                result.blocked = true;
                break;
            }
        }
        return result;
    }

    private static Result isBlockedFromRight(int x, int y, int[][] matrix) {
        Result result = new Result();
        for (int i = x + 1; i < matrix[y].length; i++) {
            result.score++;
            if (matrix[y][x] <= matrix[y][i]) {
                result.blocked = true;
                break;
            }
        }
        return result;
    }

    private static Result isBlockedFromTop(int x, int y, int[][] matrix) {
        Result result = new Result();
        for (int i = y - 1; i >= 0; i--) {
            result.score++;
            if (matrix[y][x] <= matrix[i][x]) {
                result.blocked = true;
                break;
            }
        }
        return result;
    }

    private static Result isBlockedFromBottom(int x, int y, int[][] matrix, int nrOfLines) {
        Result result = new Result();
        for (int i = y + 1; i < nrOfLines; i++) {
            result.score++;
            if (matrix[y][x] <= matrix[i][x]) {
                result.blocked = true;
                break;
            }
        }
        return result;
    }

    static class Result {
        int score = 0;
        boolean blocked = false;
    }

}
