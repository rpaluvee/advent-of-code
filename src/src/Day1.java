import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws Exception {
        List<String> list = Utils.readFileToList("input/1.txt");
        List<Integer> results = new ArrayList<>();
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            if (line.isEmpty()) {
                results.add(result);
                result = 0;
            } else {
                result += Integer.parseInt(line);
                if (i == list.size() - 1) {
                    results.add(result);
                }
            }
        }
        int first = Collections.max(results);
        results.remove(Integer.valueOf(first));

        int second = Collections.max(results);
        results.remove(Integer.valueOf(second));

        int third = Collections.max(results);
        results.remove(Integer.valueOf(third));

        System.out.println(first + second + third);
    }

}