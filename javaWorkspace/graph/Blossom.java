package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Blossom<T> {
    T root;
    List<T> cycle;
    Set<T> nodes;


    public Blossom(T root, List<T> cycle) {
        this.root = root;
        this.cycle = cycle;
        this.nodes = new HashSet<T>(cycle); 
    }

    public boolean contains(T node) {
        return nodes.contains(node);
    }


    /**
     * finds the odd  in the forest and returns it as a Blossom class
     * @param <T>
     * @param forest
     * @param v end point of edge that creates a blossom
     * @param w end point of edge that creates a blossom
     * @return the blossom as a Blossom class
     */
    public static <T> Blossom<T> findBlossom(Map<T, MetaNode<T>> forest, T v, T w) {
        List<T> vPath = Forest.pathToRoot(forest, v);
        List<T> wPath = Forest.pathToRoot(forest, w);
        int vlen = vPath.size();
        int wlen = wPath.size();
        int lca = 1;
        // finds the lowest common ancestor -> the root of the blossom
        for (int i = 1; i < Math.min(vlen, wlen); i++ ) {
            if (!vPath.get(vlen - i).equals(wPath.get(wlen - i))) {
                lca = i;
                break;
            }
        }

        List<T> cycle = new ArrayList<>();
        for (int i = vlen - lca; i >= 0; i--) {
            cycle.add(vPath.get(i));
        }
        for (int i = 0; i <= wlen - lca; i++) {
            cycle.add(wPath.get(i));
        } 
        return new Blossom<T>(vPath.get(vlen - lca), cycle);
    }
    
    /**
     * finds the correct augmenting path through a blossom
     * @param <T>
     * @param path the augmenting path of the contracted graph
     * @param graph the graph with the blossom uncontracted
     * @param blossom the blossom that the graph was been contracted by 
     * @return an augmenting path to the uncontracted graph
     */
    public static <T> List<T> liftPath(List<T> path, EdmondGraph<T> graph, Blossom<T> blossom) {
        //locate blossom
        if (path == null) {
            return path;
        }
        int indx = path.indexOf(blossom.root);

        if (indx % 2 == 1) { 
            Collections.reverse(path);
        }

        List<T> resPath = new LinkedList<>();
        for (int i = 0; i < path.size(); i++) {
            if (i == indx) {
                // NOTE: there can only be one node in the cycle leaving the blossom
                // because assuming there were two nodes leaving to the same node 
                // would mean that the odd cycle is split into an even cycle and an odd cycle
                // by the second edge and the even cycle would not be in the blossom
        

                int exitIndex = -1;
                for (int c = 0; c < blossom.cycle.size(); c++) {
                    //blossom will never be at the ends - path is from exposed node to exposed node
                    //graph.containsEdge(path.get((i + 1) % path.size()), blossom.cycle.get(c))
                    if (graph.containsEdge(path.get(i + 1), blossom.cycle.get(c))) {
                        exitIndex = c;
                    }
                }

                //blossom cycle is listed clockwise
                // if at an even index the path is clockwise 
                //-> node located at an even index will end with a matched edge when traversed clockwise
                int start = (exitIndex % 2 == 0) ? 0 : blossom.cycle.size() - 1;
                int wise = (exitIndex % 2 == 0) ? 1 : -1;

                for (int c = start; c <= exitIndex; c += wise) {
                    resPath.add(blossom.cycle.get(c));
                } 
            } else {
                resPath.add(path.get(i));
            }
        }
        return resPath;

    }
    
    /**
     * contracts the blossom and returns a graph with the coresponding supernode representing the blossom
     * @param <T>
     * @param graph the graph containing the blossom
     * @param blossom list of nodes that make up the blossom
     * @return graph with the blossom contracted into a single supernode
     */
    public static <T> EdmondGraph<T> contractBlossom(EdmondGraph<T> graph, Blossom<T> blossom) {
        EdmondGraph<T> contracted = new EdmondGraph<T>();
        contracted.addNode(blossom.root);

        for (T node : graph) {
            if (blossom.contains(node)) {
                continue;
            }
            
            contracted.addNode(node);
            for (T adj : graph.neighbors(node)) {
                contracted.addEdge(node, ((blossom.contains(adj)) ? blossom.root : adj));
                /*
                if (blossom.contains(adj)) {
                    contracted.addEdge(node, blossom.root);
                } else {
                    contracted.addEdge(node, adj);
                }
                */
            }
        }

        return contracted;
    }
}