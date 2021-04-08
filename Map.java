import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Map {
    private HashMap<String, Node> nodes;
    private String fileName;

    // Construct a Map using a file.
    public Map(String fileName) {
        try {
            File input = new File(fileName);
            this.fileName = fileName;
            nodes = new HashMap<>();
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] info = line.split("; ");
                if (info[0].equals("New")) {
                    Node node = new Node(info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]));
                    nodes.put(info[1], node);
                } else {
                    if (info.length == 3) {
                        // By default, add neighbor using Manhattan distance.
                        Node node1 = getNodeByName(info[1]);
                        Node node2 = getNodeByName(info[2]);
                        Node.addNeighbor(node1, node2);
                    } else {
                        // Add neighbor using specified distance.
                        Node node1 = getNodeByName(info[1]);
                        Node node2 = getNodeByName(info[2]);
                        double distance = Double.parseDouble(info[3]);
                        Node.addNeighbor(node1, node2, distance);
                    }

                }
            }
            scan.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public Node getNodeByName(String name) {
        return nodes.get(name);
    }

    public static void main(String[] args) {
        String fileName = args[0];
        Map map = new Map(fileName);
        Path path = Path.findPath(fileName, "6-1", "4-8");
        System.out.println(path);
        System.out.println(path.findTotalDistance());

        /*
        0 1 2 3 4 5 6 7 8    <- x
        1 A - B ----L
        2 \     \       F
        3  \      C --/
        4   D --/   \
        5   |\    G - E
        6   | H -/     \I
        7   J   \-- K
        8     \-M -/

        ^y
        */
    }
}
