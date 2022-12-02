import java.util.List;

public class Day2 {
    public static void main(String[] args) throws Exception {
        List<String> list = Utils.readFileToList("input/2.txt");

        int partOneResult = calcPartOne(list);
        System.out.println(partOneResult);

        int partTwoResult = calcPartTwo(list);
        System.out.println(partTwoResult);
    }

    private static int calcPartOne(List<String> input) {
        return calcResult(input);
    }

    private static int calcPartTwo(List<String> input) {
        // X = lose
        // Y = draw
        // Z = win
        List<String> modifiedList = input.stream()
                .map(e -> {
                    String firstCol = e.split(" ")[0];
                    String secondCol = e.split(" ")[1];
                    String modifiedSecondCol = "";
                    switch (secondCol) {
                        case "X" -> {
                            switch (firstCol) {
                                case "A" -> modifiedSecondCol = "Z";
                                case "B" -> modifiedSecondCol = "X";
                                case "C" -> modifiedSecondCol = "Y";
                            }
                        }
                        case "Y" -> {
                            switch (firstCol) {
                                case "A" -> modifiedSecondCol = "X";
                                case "B" -> modifiedSecondCol = "Y";
                                case "C" -> modifiedSecondCol = "Z";
                            }
                        }
                        case "Z" -> {
                            switch (firstCol) {
                                case "A" -> modifiedSecondCol = "Y";
                                case "B" -> modifiedSecondCol = "Z";
                                case "C" -> modifiedSecondCol = "X";
                            }
                        }
                    }
                    return firstCol + " " + modifiedSecondCol;
                }).toList();
        return calcResult(modifiedList);
    }

    private static int calcResult(List<String> inputList) {
        int result = 0;
        for (String s : inputList) {
            // A = X = rock = 1
            // B = Y = paper = 2
            // C = Z = scissors = 3
            switch (s) {
                case "A X", "B Y", "C Z" -> result += 3;
                case "A Y", "B Z", "C X" -> result += 6;
            }
        }

        List<String> myChoices = inputList.stream().map(e -> e.split(" ")[1]).toList();
        for (String myChoice : myChoices) {
            switch (myChoice) {
                case "X" -> result += 1;
                case "Y" -> result += 2;
                case "Z" -> result += 3;
            }
        }

        return result;
    }

}