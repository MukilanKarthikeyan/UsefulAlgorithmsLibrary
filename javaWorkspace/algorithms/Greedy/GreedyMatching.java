package algorithms.Greedy;

import graph.Graph;

public class GreedyMatching {
    /**
     * creates a greedy matching by considering nodes of minimum degree
     * better matchign approximation than standard greedy matching 
     * @param <T>
     * @param graph graph to find a matching on
     * @return a maximal matching of graph as a Graph<T> type
     */
    public static <T> Graph<T> greedyMatchingByDegree(Graph<T> graph) {
        Graph<T> graphCopy = graph.deepCopy();
        Graph<T> greedyMatch = new Graph<>();
        for (T node : graphCopy) {
            greedyMatch.addNode(node);
        }

        while (graphCopy.size() > 0) {
            T nodeMinDegree = null;
            for (T node : graphCopy) {
                if (nodeMinDegree == null || graphCopy.nodeDegree(node) < graphCopy.nodeDegree(nodeMinDegree)) {
                    nodeMinDegree = node;
                }
            }
            if (graphCopy.nodeDegree(nodeMinDegree) == 0) {
                graphCopy.removeNode(nodeMinDegree);
                continue;
            }

            T secondSmallest = null;
            for (T neighbor : graphCopy.neighbors(nodeMinDegree)) {
                if (secondSmallest == null || graphCopy.nodeDegree(neighbor) < graphCopy.nodeDegree(secondSmallest)) {
                    secondSmallest = neighbor;
                }
            }

            greedyMatch.addEdge(nodeMinDegree, secondSmallest);
            graphCopy.removeNode(nodeMinDegree);
            graphCopy.removeNode(secondSmallest);
        }

        return greedyMatch;
    }
}
