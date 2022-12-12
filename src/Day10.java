import java.io.IOException;
import java.util.List;

public class Day10 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.readFileToList("input/10.txt");

        int signalStrength = 0;
        int x = 1;
        int cycle = 1;
        int index = 0;
        StringBuilder pixelRow = new StringBuilder();
        for (String line : lines) {
            String cmd = line.split(" ")[0];
            int cycleCount = cmd.equals("noop") ? 1 : 2;
            while (cycleCount > 0) {
                // PART 2
                pixelRow.append(Math.abs(index - x) <= 1 ? "#" : ".");
                if (++index % 40 == 0) {
                    System.out.println(pixelRow);
                    pixelRow = new StringBuilder();
                    index = 0;
                }

                cycle++;
                switch (cmd) {
                    case "noop" -> cycleCount--;
                    case "addx" -> {
                        if (--cycleCount == 0) {
                            x += Integer.parseInt(line.split(" ")[1]);
                        }
                    }
                }
                // PART 1
                if (cycle == 20 || (cycle - 20) % 40 == 0) {
                    signalStrength += x * cycle;
                }
            }
        }
        System.out.println(signalStrength);
    }

}
