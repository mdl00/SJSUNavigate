import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Path {
    private final LinkedList<Node> path;

    // Construct a new Path.
    public Path(LinkedList<Node> path) {
        if (path == null)
            throw new NullPointerException();
        this.path = path;
    }

    // Find the total distance of a Path.
    public double findTotalDistance(boolean unitInFoot) {
        double distance = 0;
        for (int i = 1; i < path.size(); i++) {
            Node node1 = path.get(i - 1);
            Node node2 = path.get(i);
            distance += node1.getDistanceOf(node2);
        }
        int campusLength = 2000; // Unit: foot
        double footMeterConversion = 0.3048; // 1 ft = 0.3048 m
        double distanceInFoot = distance * campusLength / 128;
        return unitInFoot ? distanceInFoot : distanceInFoot * footMeterConversion;
    }

    // Find the total Manhattan distance of a Path.
    public int findTotalManhattanDistance() {
        int distance = 0;
        for (int i = 1; i < path.size(); i++) {
            Node node1 = path.get(i - 1);
            Node node2 = path.get(i);
            distance += node1.findManhattanDistance(node2);
        }
        return distance;
    }

    // Find the total Euclidean distance of a Path.
    public double findTotalEuclideanDistance() {
        double distance = 0;
        for (int i = 1; i < path.size(); i++) {
            Node node1 = path.get(i - 1);
            Node node2 = path.get(i);
            distance += node1.findEuclideanDistance(node2);
        }
        return distance;
    }

    // Fine the estimated time it takes to walk from Start to Destination.
    public int findEstimatedTime() {
        int walkingSpeed = 275; // Unit: foot/min
        return (int) findTotalDistance(true) / walkingSpeed;
    }

    // Find the Path from start Node to destination Node.
    public static Path findPath(String fileName, String startNodeName, String destinationNodeName) {
        Map map = new Map(fileName);
        if (map.getNodeByName(startNodeName) == null)
            throw new NoSuchElementException("Start Node not found; Start Node: " + startNodeName);
        if (map.getNodeByName(destinationNodeName) == null)
            throw new NoSuchElementException("Destination Node not found; Destination Node: " + destinationNodeName);

        // Set up.
        LinkedList<Node> path = new LinkedList<>();
        Node start = map.getNodeByName(startNodeName);
        Node destination = map.getNodeByName(destinationNodeName);
        path.add(start);
        Node current = start;
        Node nextNode;
        current.updateVisited(true);
        boolean found = false;

        // Traverse the Map.
        while (!found) {
            nextNode = null;
            double minDistance = Double.MAX_VALUE;

            // Find the unvisited neighboring Node that is closest to start Node and destination Node.
            for (int i = 0; i < current.getNeighbors().size(); i++) {
                Node node = current.getNeighbors().get(i);
                //int weightedDistance = start.findManhattanDistance(node) / 2 + node.findManhattanDistance(destination);
                double weightedDistance = start.findEuclideanDistance(node) / 2 + node.findEuclideanDistance(destination);
                if (weightedDistance < minDistance && !node.isVisited()) {
                    nextNode = node;
                    minDistance = weightedDistance;
                }
            }

            if (nextNode == null) {
                // There's no unvisited neighboring Node, meaning that all neighboring Nodes have
                // already been visited; we have reached a dead end; go back to the previous Node.
                path.removeLast();
                current = path.getLast();
            } else {
                path.add(nextNode);
                if (nextNode.equals(destination)) {
                    // Found the destination Node.
                    found = true;
                } else {
                    current = nextNode;
                    current.updateVisited(true);
                }
            }
        }
        return new Path(path);
    }

    // Find the shortest Path between 2 Nodes.
    public static Path findShortestPathByName(String fileName, String node1Name, String node2Name) {
        Path path1 = findPath(fileName, node1Name, node2Name);
        Path path2 = findPath(fileName, node2Name, node1Name);
        double path1Distance = Node.roundToTwoDecimalPlaces(path1.findTotalDistance(true));
        double path2Distance = Node.roundToTwoDecimalPlaces(path2.findTotalDistance(true));
        return path2Distance < path1Distance ? path2.getReversedPath() : path1;
    }

    private Path getReversedPath() {
        LinkedList<Node> reversed = new LinkedList<>();
        for (Node node : path)
            reversed.addFirst(node);
        return new Path(reversed);
    }

    // Check if the input is an abbreviation, correctly spelt, or incorrectly spelt
    private static String getCorrectName(String name) {
        if (Map.getFullName(name) != null) return Map.getFullName(name);
        String lowerCase = name.toLowerCase();
        if (Map.getCapitalizedName(lowerCase) != null) return Map.getCapitalizedName(lowerCase);
        return name;
    }

    public LinkedList<Node> getPath() {
        return path;
    }

    public String toString() {
        return path.toString();
    }

    public static void main(String[] args) {
        // For the input file, use MapInfo.txt
        String fileName = args[0];
        Map.initialize("BuildingNames.txt");
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] info = line.split(" to ");
            String node1 = getCorrectName(info[0]);
            String node2 = getCorrectName(info[1]);
            Path shortest = findShortestPathByName(fileName, node1, node2);
            System.out.println("From [" + node1 + "] to [" + node2 + "]");
            System.out.println(shortest + "; distance: " + (int) shortest.findTotalDistance(true)
                    + " ft; estimated time: " + shortest.findEstimatedTime() + " min\n");
        }
    }
}
