import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList("input/5.txt");

        List<Stack<String>> crateStacksPartOne = getCrateColumnsAsStacks(input);
        List<Stack<String>> crateStacksPartTwo = getCrateColumnsAsStacks(input);

        int emptyLineIndex = input.indexOf("");
        List<String> movesList = input.subList(emptyLineIndex + 1, input.size());

        for (String move : movesList) {
            Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
            Matcher matcher = pattern.matcher(move);
            if (!matcher.find()) {
                break;
            }

            int moveNr = Integer.parseInt(matcher.group(1));
            int fromNr = Integer.parseInt(matcher.group(2));
            int toNr = Integer.parseInt(matcher.group(3));

            // PART 1
            Stack<String> fromStackPartOne = crateStacksPartOne.get(fromNr - 1);
            Stack<String> toStackPartOne = crateStacksPartOne.get(toNr - 1);
            for (int i = 0; i < moveNr; i++) {
                if (!fromStackPartOne.isEmpty()) {
                    String movedCrate = fromStackPartOne.pop();
                    toStackPartOne.push(movedCrate);
                }
            }

            // PART 2
            Stack<String> fromStackPartTwo = crateStacksPartTwo.get(fromNr - 1);
            Stack<String> toStackPartTwo = crateStacksPartTwo.get(toNr - 1);
            List<String> groupedCrates = new ArrayList<>();
            for (int i = 0; i < moveNr; i++) {
                if (!fromStackPartTwo.isEmpty()) {
                    if (moveNr > 1) {
                        groupedCrates.add(fromStackPartTwo.pop());
                    } else {
                        String movedCrate = fromStackPartTwo.pop();
                        toStackPartTwo.push(movedCrate);
                    }
                }
            }
            Collections.reverse(groupedCrates);
            for (String crate : groupedCrates) {
                toStackPartTwo.push(crate);
            }
        }

        String partOneResponse = crateStacksPartOne.stream().map(Stack::peek).collect(Collectors.joining());
        System.out.println(partOneResponse);

        String partTwoResponse = crateStacksPartTwo.stream().map(Stack::peek).collect(Collectors.joining());
        System.out.println(partTwoResponse);
    }

    private static List<Stack<String>> getCrateColumnsAsStacks(List<String> input) {
        List<Stack<String>> stacks = new ArrayList<>();

        List<List<String>> allCrates = getCrateLinesAsList(input);
        int nrOfColumns = allCrates.get(0).size();

        for (int i = 0; i < nrOfColumns; i++) {
            Stack<String> crateStack = new Stack<>();
            for (List<String> cratesList : allCrates) {
                String crate = cratesList.get(i);
                if (!crate.equals("*")) {
                    crateStack.push(crate);
                }
            }
            stacks.add(crateStack);
        }
        System.out.println(stacks);
        return stacks;
    }

    private static List<List<String>> getCrateLinesAsList(List<String> input) {
        List<List<String>> allCrates = new ArrayList<>();

        int emptyLineIndex = input.indexOf("");
        List<String> cratePlacementLines = input.subList(0, emptyLineIndex - 1);

        for (String line : cratePlacementLines) {
            line = line.replaceAll(" {4}", " [*]");
            List<String> crates = Arrays.stream(line.split("] \\["))
                    .map(e -> e.replaceAll("\\[", "")
                            .replaceAll("]", "")
                            .trim())
                    .toList();
            System.out.println(crates);
            allCrates.add(crates);
        }
        // to bottom-up order
        Collections.reverse(allCrates);
        return allCrates;
    }

}
