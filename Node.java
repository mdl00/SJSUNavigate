import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Node {
    private final String name;
    private final int x;
    private final int y;
    private final ArrayList<Node> neighbors;
    private final ArrayList<Double> distances;
    private boolean visited;

    // Construct a new Node with unknown or unspecified neighbors.
    public Node(String name, int x, int y) {
        this(name, x, y, new ArrayList<>(1), new ArrayList<>(1));
    }

    // Construct a new Node with known neighbors and distances to each neighbors.
    public Node(String name, int x, int y, ArrayList<Node> neighbors, ArrayList<Double> distances) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.neighbors = neighbors;
        this.distances = distances;
        visited = false;
    }

    // Add a neighboring Node to this Node with unknown distance.
    // Distance is calculated in Manhattan distance by default.
    public static void addNeighbor(Node node1, Node node2) {
        int distance = node1.findManhattanDistance(node2);
        addNeighbor(node1, node2, distance);
    }

    // Add a neighbor Node to this Node with known distance.
    public static void addNeighbor(Node node1, Node node2, double distance) {
        if (!node1.neighbors.contains(node2)) {
            node1.neighbors.add(node2);
            node1.distances.add(distance);
            node2.neighbors.add(node1);
            node2.distances.add(distance);
        }
    }

    public void updateVisited(boolean newValue) {
        visited = newValue;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public ArrayList<Double> getDistances() {
        return distances;
    }

    // Get the distance between a neighboring Node and this Node.
    public double getDistanceOf(Node node) {
        if (!neighbors.contains(node))
            throw new NoSuchElementException("The input Node is not a neighbor of this Node; Input Node: " + node
                    + "; This Node: " + this);
        int index = neighbors.indexOf(node);
        return distances.get(index);
    }

    public boolean isVisited() {
        return visited;
    }

    // Find the Manhattan distance between 2 Nodes.
    public int findManhattanDistance(Node destination) {
        int xChange = Math.abs(x - destination.x);
        int yChange = Math.abs(y - destination.y);
        return xChange + yChange;
    }

    // Find the Euclidean distance between 2 Nodes.
    public double findEuclideanDistance(Node destination) {
        int xChange = Math.abs(this.x - destination.x);
        int yChange = Math.abs(this.y - destination.y);
        return Math.sqrt(Math.pow(xChange, 2) + Math.pow(yChange, 2));
    }

    public static double roundToTwoDecimalPlaces(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(number));
    }

    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        Node nodeA = new Node("1-1", 1, 1);
        Node nodeB = new Node("3-1", 3, 1);
        Node nodeC = new Node("5-3", 5, 3);

        addNeighbor(nodeA, nodeB);
        addNeighbor(nodeB, nodeC);
    }
}
