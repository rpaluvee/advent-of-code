import java.util.*;

public class Day7 {

    public static void main(String[] args) throws Exception {
        List<String> input = Utils.readFileToList("input/7.txt");

        TreeNode rootNode = buildTree(input);
        List<TreeNode> dirNodes = findDirNodes(rootNode).stream()
                .peek(node -> node.size = getNodeSize(node))
                .toList();

        int partOneResponse = dirNodes.stream()
                .filter(node -> node.size <= 100000)
                .mapToInt(node -> node.size)
                .sum();
        System.out.println(partOneResponse);

        int partTwoResponse = calcPartTwo(dirNodes);
        System.out.println(partTwoResponse);
    }

    private static int calcPartTwo(List<TreeNode> dirNodes) {
        TreeNode rootDir = dirNodes.stream()
                .filter(node -> node.parent == null)
                .findFirst()
                .orElse(null);

        int freeSpace = 70000000 - getNodeSize(rootDir);
        int neededSpace = Math.abs(freeSpace - 30000000);

        return dirNodes.stream()
                .filter(node -> node.size >= neededSpace)
                .mapToInt(node -> node.size)
                .min()
                .orElse(0);
    }

    private static TreeNode buildTree(List<String> input) {
        Stack<TreeNode> stack = new Stack<>();
        for (String line : input) {
            if (line.startsWith("$ cd ")) {
                String val = line.split(" ")[2];
                switch (val) {
                    case "/" -> {
                        TreeNode parentNode = new TreeNode("/", null);
                        stack.push(parentNode);
                    }
                    case ".." -> stack.pop();
                    default -> {
                        TreeNode parentNode = stack.peek();
                        TreeNode currentNode = parentNode.children.stream()
                                .filter(e -> e.value.equals(val))
                                .findFirst()
                                .orElse(null);
                        stack.push(currentNode);
                    }
                }
            }
            if (!line.startsWith("$")) { // after ls
                String fileName = line.split(" ")[1];
                TreeNode parentNode = stack.peek();
                if (line.startsWith("dir")) {
                    TreeNode childNode = new TreeNode(fileName, parentNode);
                    parentNode.children.add(childNode);
                } else {
                    int size = Integer.parseInt(line.split(" ")[0]);
                    TreeNode childNode = new TreeNode(fileName, parentNode, size);
                    parentNode.children.add(childNode);
                }
            }
        }
        return stack.get(0);
    }

    static List<TreeNode> findDirNodes(TreeNode root) {
        List<TreeNode> dirNodes = new ArrayList<>();
        Stack<TreeNode> nodes = new Stack<>();
        nodes.push(root);

        while (!nodes.isEmpty()) {
            TreeNode currentNode = nodes.pop();
            if (currentNode != null) {
                if (!isLeafNode(currentNode)) {
                    dirNodes.add(currentNode);
                }
                for (TreeNode childNode : currentNode.children) {
                    nodes.push(childNode);
                }
            }
        }
        return dirNodes;
    }

    static int getNodeSize(TreeNode node) {
        int size = 0;
        if (node != null) {
            if (isLeafNode(node)) {
                size += node.size;
            } else {
                for (TreeNode childNode : node.children) {
                    if (isLeafNode(childNode)) {
                        size += childNode.size;
                    } else {
                        size += getNodeSize(childNode);
                    }
                }
            }
        }
        return size;
    }

    static boolean isLeafNode(TreeNode node) {
        return node.children.isEmpty();
    }

    static class TreeNode {
        String value;
        int size;
        List<TreeNode> children = new LinkedList<>();
        TreeNode parent;

        TreeNode(String value, TreeNode parent) {
            this.value = value;
            this.parent = parent;
        }
        TreeNode(String value, TreeNode parent, int size) {
            this.value = value;
            this.parent = parent;
            this.size = size;
        }

    }

}
