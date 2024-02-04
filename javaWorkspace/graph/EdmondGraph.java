package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class EdmondGraph<T> implements Iterable<T> {
    //using adjacney set here (epecting sparce graph) but it essentially become an adjaceny matrix
    private Map<T, Set<T>> undirGraph;
    
    public EdmondGraph() {
        this.undirGraph = new HashMap<T, Set<T>>();
    }

    public boolean addNode(T node){
        undirGraph.put(node, undirGraph.getOrDefault(node, new HashSet<>()));
        return true;
    }

    public boolean removeNode(T node) {
        if (!this.containsNode(node)) {
            return false;
        }
        for (T curr : undirGraph.keySet()) {
            undirGraph.get(curr).remove(node);
        }

        undirGraph.remove(node);
        return true;
    }

    public boolean containsNode(T node) {
        return undirGraph.containsKey(node);
    }

    public int nodeDegree(T node) {
        if (!containsNode(node)) {
            return -1;
        }
        return undirGraph.get(node).size();
    }

    public boolean addEdge(T a, T b) {
        if (!containsNode(a) || !containsNode(b)) {
            return false;
        }
        undirGraph.get(a).add(b);
        undirGraph.get(b).add(a);
        return true;
    }

    public boolean removeEdge(T a, T b) {
        if (!containsNode(a) || !containsNode(b)) {
            return false;
        }
        undirGraph.get(a).remove(b);
        undirGraph.get(b).remove(a);
        return true;
    }

    public boolean containsEdge(T a, T b) {
        if (!containsNode(a) || !containsNode(b)) {
            return false;
        }
        return undirGraph.get(a).contains(b);
    }

    public Iterator<T> iterator(){
        return undirGraph.keySet().iterator();
    }

    public Set<T> neighbors(T node) {
        return undirGraph.getOrDefault(node, new HashSet<T>());
    }

    public int size() {
        return undirGraph.size();
    }

    public int numEdges() {
        int count = 0;
        for (T element : undirGraph.keySet()) {
            count += undirGraph.get(element).size();
        }
        return count/2;
    }

    public boolean isEmpty() {
        return undirGraph.isEmpty();
    }

    public EdmondGraph<T> deepCopy() {
        EdmondGraph<T> copy = new EdmondGraph<>();
        for (T node : undirGraph.keySet()) {
            copy.addNode(node);
        }
        for (T node : undirGraph.keySet()) {
            for (T end : undirGraph.get(node)) {
                copy.addEdge(node, end);
            }
        }

        return copy;
    }

}