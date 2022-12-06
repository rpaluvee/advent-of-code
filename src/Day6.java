import java.util.HashSet;
import java.util.Set;

public class Day6 {

    public static void main(String[] args) throws Exception {
        String input = Utils.readFileToString("input/6.txt");

        int partOneResponse = calc(input, 4);
        System.out.println(partOneResponse);

        int partTwoResponse = calc(input, 14);
        System.out.println(partTwoResponse);
    }

    private static int calc(String input, int requiredLength) {
        int result = 0;
        for (int i = requiredLength - 1; i < input.length(); i++) {
            Set<Character> set = new HashSet<>();
            for (int j = 0; j < requiredLength; j++) {
                set.add(input.charAt(i - j));
            }
            if (set.size() == requiredLength) {
                result = i + 1;
                break;
            }
        }
        return result;
    }

}
