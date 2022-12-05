import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Day4 {

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList("input/4.txt");

        int partOneResponse = calcPartOne(input);
        System.out.println(partOneResponse);

        int partTwoResponse = calcPartTwo(input);
        System.out.println(partTwoResponse);

    }

    private static int calcPartOne(List<String> input) {
        int result = 0;
        for (String line : input) {
            String[] pair = line.split(",");

            int firstSectionMin = Integer.parseInt(pair[0].split("-")[0]);
            int firstSectionMax = Integer.parseInt(pair[0].split("-")[1]);
            int secondSectionMin = Integer.parseInt(pair[1].split("-")[0]);
            int secondSectionMax = Integer.parseInt(pair[1].split("-")[1]);

            List<Integer> firstSectionList = IntStream.rangeClosed(firstSectionMin, firstSectionMax).boxed().toList();
            List<Integer> secondSectionList = IntStream.rangeClosed(secondSectionMin, secondSectionMax).boxed().toList();

            boolean firstSectionContainsSecond = Collections.indexOfSubList(firstSectionList, secondSectionList) != -1;
            boolean secondSectionContainsFirst = Collections.indexOfSubList(secondSectionList, firstSectionList) != -1;

            if (firstSectionContainsSecond || secondSectionContainsFirst) {
                result++;
            }
        }

        return result;
    }

    private static int calcPartTwo(List<String> input) {
        int result = 0;
        for (String line : input) {
            String[] pair = line.split(",");

            int firstSectionMin = Integer.parseInt(pair[0].split("-")[0]);
            int firstSectionMax = Integer.parseInt(pair[0].split("-")[1]);
            int secondSectionMin = Integer.parseInt(pair[1].split("-")[0]);
            int secondSectionMax = Integer.parseInt(pair[1].split("-")[1]);

            List<Integer> firstSectionList = IntStream.rangeClosed(firstSectionMin, firstSectionMax).boxed().toList();
            List<Integer> secondSectionList = IntStream.rangeClosed(secondSectionMin, secondSectionMax).boxed().toList();

            long count = firstSectionList.stream()
                    .distinct()
                    .filter(secondSectionList::contains)
                    .findFirst()
                    .orElse(0);

            if (count != 0) {
                result++;
            }
        }
        return result;
    }

}
