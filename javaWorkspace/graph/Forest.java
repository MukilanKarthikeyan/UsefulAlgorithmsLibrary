package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Forest {
    /**
     * returns the path from a given node to its root in the forest
     * @param <T>
     * @param forest forest in question
     * @param node node being considered
     * @return path as a list from node to root
     */
    public static <T> List<T> pathToRoot(Map<T, MetaNode<T>> forest, T node) {
        List<T> path = new ArrayList<>();
        for (T curr = node; curr != null; curr = forest.get(curr).parent) {
            path.add(curr);
        }
        return path;
    }
}
