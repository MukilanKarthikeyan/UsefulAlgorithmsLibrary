package algorthims.MaximumMatching;

import java.util.*;

import algorthims.Greedy.GreedyMatching;
import graph.Graph;
import graph.Blossom;
import graph.MetaNode;

public class EdmondMatching {
    public static <T> Graph<T> EdMatching(Graph<T> graph){
        if (graph.isEmpty()) {
            return new Graph<>();
        }

        //speed up the algorhtim by providing a greedy matching to start from
        Graph<T> matching = GreedyMatching.greedyMatchingByDegree(graph);// this alone will fail for speciric odd cycles 
        
        //keep finding augmenting paths until none exist
        //Berge's lemma
        while (true) {
            List<T> augPath = findAugmentingPath(graph, matching);
            if (augPath == null || augPath.isEmpty()) {
                return matching;
            }
            updateMatching(matching, augPath);
        }
    }
    
    /**
     * returns an augmenting path for the matching of a given graph and current matching
     * @param <T>
     * @param graph teh graph to find a matching on 
     * @param matching current matching on the graph
     * @return an augmenting path for the matching on the graph
     */
    public static <T> List<T> findAugmentingPath(Graph<T> graph, Graph<T> matching) {
        Map<T, MetaNode<T>> forest = new HashMap<>();
        Queue<Edge<T>> checkEdges = new LinkedList<>();

        // add all exposed verticies to alternating tree
        for (T curr : graph) {
            if (matching.neighbors(curr).size() != 0) {
                continue;
            }
            forest.put(curr, new MetaNode<T>(null, curr, true));
            //mark all edges
            
            for (T vert : graph.neighbors(curr)) {
                checkEdges.add(new Edge<T>(curr, vert));
            }
            
        }

        while (!checkEdges.isEmpty()) {
            Edge<T> currEdge = checkEdges.remove();
            T v = currEdge.start;
            T w = currEdge.end;
            if (matching.containsEdge(v, w)) {
                    continue;
            }

            if (!forest.containsKey(w)) {
                // expand the alternating tree
                addToForest(graph, matching, forest, checkEdges, v, w);
                
            } else if (forest.get(w).isEven) {
                // w is already in the forest and has multiple edges we can process
                
                List<T> path = new ArrayList<>();

                if (forest.get(v).root.equals(forest.get(w).root)) {
                    // the verticeis of the edge share the same root in tree hence there is an odd cycle/ blossom
                    path = blossomRecursion(graph, matching, forest, v, w);
                } else {
                    //vw is an edge and verticies have differnt roots
                    //augmentign path is : path from v to root + path from root to w
                    for (T currNode = v; currNode != null; currNode = forest.get(currNode).parent) {
                        path.add(currNode);
                    }
                    Collections.reverse(path);
                    for (T currNode = w; currNode != null; currNode = forest.get(currNode).parent) {
                        path.add(currNode);
                    }
                }
                return path;
            }
        }
        return null;
    }

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
    
    public static <T> T lowestCommonAncestor(Map<T, MetaNode<T>> forest, T v, T w) {
        List<T> vPath = pathToRoot(forest, v);
        List<T> wPath = pathToRoot(forest, w);
        int vlen = vPath.size();
        int wlen = wPath.size();
        for (int i = 1; i < Math.min(vlen, wlen); i++ ) {
            if (!vPath.get(vlen - i).equals(wPath.get(wlen - i))) {
                return vPath.get(vlen - i - 1);
            }
        }
        return null;
    }

    public static <T> List<T> blossomRecursion(Graph<T> graph, Graph<T> matching, Map<T, MetaNode<T>> forest, T v, T w) {
        //graph only has an augmenting path if the contracted graph has an augmenting path
        Blossom<T> blossom = Blossom.findBlossom(forest, v, w);
        List<T> path = findAugmentingPath(Blossom.contractBlossom(graph, blossom), Blossom.contractBlossom(matching, blossom));
        return Blossom.liftPath(path, graph, blossom);
    }

    

    /**
     * adds w to the alternating forest along with the node w is matched with
     * @param <T>
     * @param graph the graph being considered
     * @param matching current matching in question
     * @param forest the alternating forest
     * @param checkEdges the queue we are workign with
     * @param v one end point of the edge
     * @param w other end point of the edge
     */
    public static <T> void addToForest(Graph<T> graph, Graph<T> matching, Map<T, MetaNode<T>> forest, Queue<Edge<T>> checkEdges, T v, T w) {
        //System.out.println("add to forest: " + v + " | " + w);
        T x = matching.neighbors(w).iterator().next();
        forest.put(w, new MetaNode<T>(v, forest.get(v).root, false));
        forest.put(x, new MetaNode<T>(w, forest.get(v).root, true));

        // add x to nodes to check

        
        for (T neighbor : graph.neighbors(x)) {
            checkEdges.add(new Edge<T>(x, neighbor));
        }
        
    }

    /**
     * given a matching and an alternating path that augments it, 
     * it changes the matching by finding the symetric difference
     * @param <T>
     * @param matching the current matching
     * @param augPath alternating path to augment the given matching
     */
    public static <T> void updateMatching(Graph<T> matching, List<T> augPath) {
        for (int i = 0; i < augPath.size() - 1; i++) {
            if (matching.containsEdge(augPath.get(i), augPath.get(i + 1))) {
                //System.out.print("remove edge");
                matching.removeEdge(augPath.get(i), augPath.get(i + 1));
            } else {
                //System.out.println("add edge: " + augPath.get(i) + " | " + augPath.get(i + 1));
                matching.addEdge(augPath.get(i), augPath.get(i + 1));                
            }
        }
        //System.out.println("end updating: " + matching.numEdges());
    }
}
