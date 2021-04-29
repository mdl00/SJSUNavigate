import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Map {
    private HashMap<String, Node> nodes;
    private static HashMap<String, String> abbrevName; // Ex. Dr. Martin Luther King, Jr. Library -> KING
    private static HashMap<String, String> fullName; // Ex. KING -> Dr. Martin Luther King, Jr. Library
    private static HashMap<String, String> capitalizedName; // Ex. engineering building -> Engineering Building

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
                    Node node1 = getNodeByName(info[1]);
                    Node node2 = getNodeByName(info[2]);
                    if (info.length == 3) {
                        // By default, add neighbor using Manhattan distance.
                        Node.addNeighbor(node1, node2);
                    } else {
                        // Add neighbor using specified distance.
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

    // Initialize abbrevName, fullName, and capitalizedName.
    public static void initialize(String fileName) {
        abbrevName = new HashMap<>();
        fullName = new HashMap<>();
        capitalizedName = new HashMap<>();
        try {
            File input = new File(fileName);
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] nameAndAbbrev = line.split("; ");
                String full = nameAndAbbrev[0];
                String abbrev = nameAndAbbrev[1];
                String allLowerCase = full.toLowerCase();
                abbrevName.put(full, abbrev);
                fullName.put(abbrev, full);
                capitalizedName.put(allLowerCase, full);
            }
            scan.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Node getNodeByName(String name) {
        return nodes.get(name);
    }

    public static String getAbbrevName(String fullName) {
        return abbrevName.get(fullName);
    }

    public static String getFullName(String abbrevName) {
        return fullName.get(abbrevName);
    }

    public static String getCapitalizedName(String lowerCaseName) {
        return capitalizedName.get(lowerCaseName);
    }

    public static void main(String[] args) {
        String fileName = args[0];
        initialize("BuildingNames.txt");
        Path.findShortestPathByName(fileName, "51-68", "24-57");
        System.out.println(getFullName("KING"));
    }
}
