import java.io.IOException;
import java.util.List;

public class Day3 {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String ITEMS = ALPHABET + ALPHABET.toUpperCase();

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList("input/3.txt");

        int partOneResponse = calcPartOne(input);
        System.out.println(partOneResponse);

        int partTwoResponse = calcPartTwo(input);
        System.out.println(partTwoResponse);
    }

    private static int calcPartOne(List<String> input) {
        int result = 0;
        for (String line : input) {
            int middleIndex = line.length() / 2;
            String leftPart = line.substring(0, middleIndex);
            String rightPart = line.substring(middleIndex);

            boolean isFound = false;
            for (char leftChar : leftPart.toCharArray()) {
                if (isFound) {
                    break;
                }
                for (char rightChar : rightPart.toCharArray()) {
                    if (leftChar == rightChar) {
                        isFound = true;
                        result += ITEMS.indexOf(leftChar) + 1;
                        break;
                    }
                }
            }
        }
        return result;
    }

    private static int calcPartTwo(List<String> input) {
        int result = 0;
        for (int i = 0; i < input.size(); i += 3) {
            String firstGrp = input.get(i);
            String secondGrp = input.get(i + 1);
            String thirdGrp = input.get(i + 2);
            for (char firstGrpChar : firstGrp.toCharArray()) {
                boolean isFoundFromSecondGrp = false;
                boolean isFoundFromThirdGrp = false;
                for (char secondGrpChar : secondGrp.toCharArray()) {
                    if (firstGrpChar == secondGrpChar) {
                        isFoundFromSecondGrp = true;
                        break;
                    }
                }
                for (char thirdGrpChar : thirdGrp.toCharArray()) {
                    if (firstGrpChar == thirdGrpChar) {
                        isFoundFromThirdGrp = true;
                        break;
                    }
                }
                if (isFoundFromSecondGrp && isFoundFromThirdGrp) {
                    result += ITEMS.indexOf(firstGrpChar) + 1;
                    break;
                }
            }
        }
        return result;
    }

}
