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
    public double findTotalDistance() {
        double distance = 0;
        for (int i = 1; i < path.size(); i++) {
            Node node1 = path.get(i - 1);
            Node node2 = path.get(i);
            int index = node1.getNeighbors().indexOf(node2);
            distance += node1.getDistances().get(index);
        }
        return distance;
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

    // Find the Path from start Node to destination Node.
    public static Path findPath(String fileName, String startNodeName, String destinationNodeName) {
        LinkedList<Node> path = new LinkedList<>();
        // Set up.
        Map map = new Map(fileName);
        if (map.getNodeByName(startNodeName) == null)
            throw new NoSuchElementException("Start Node not found; Start Node: " + startNodeName);
        if (map.getNodeByName(destinationNodeName) == null)
            throw new NoSuchElementException("Destination Node not found; Destination Node: " + destinationNodeName);
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
                //int distance = start.findManhattanDistance(node) + node.findManhattanDistance(destination);
                double distance = start.findEuclideanDistance(node) + node.findEuclideanDistance(destination);
                if (distance < minDistance && !node.isVisited()) {
                    nextNode = node;
                    minDistance = distance;
                }
            }
            if (nextNode == null) {
                // There's no unvisited neighboring Node, meaning that all neighboring Nodes have
                // already been visited; we have reached a dead end.
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
        double path1Distance = path1.findTotalDistance();
        double path2Distance = path2.findTotalDistance();
        System.out.println(path1 + "; distance: " + path1Distance);
        System.out.println(path2 + "; distance: " + path2Distance);
        Path shortest;
        if (path2Distance < path1Distance)
            shortest = path2.getReversedPath();
        else
            shortest = path1;
        System.out.println("Shortest: " + shortest + "; distance: " + shortest.findTotalDistance() + "\n");
        return shortest;
    }

    private Path getReversedPath() {
        LinkedList<Node> reversed = new LinkedList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversed.add(path.get(i));
        }
        return new Path(reversed);
    }

    public String toString() {
        return path.toString();
    }

    public static void main(String[] args) {
        String fileName = args[0];
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] info = line.split(" ");
            String node1 = info[0];
            String node2 = info[1];
            findShortestPathByName(fileName, node1, node2);
        }

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