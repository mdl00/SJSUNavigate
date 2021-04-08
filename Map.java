import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Map {
    private HashMap<String, Node> nodes;

    // Construct a Map using a file.
    public Map(String fileName) {
        try {
            File input = new File(fileName);
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
            exception.printStackTrace();
        }
    }

    public Node getNodeByName(String name) {
        return nodes.get(name);
    }

    public static void main(String[] args) {
        String fileName = args[0];
        Path.findShortestPathByName(fileName, "24-103", "106-25");
    }
}
