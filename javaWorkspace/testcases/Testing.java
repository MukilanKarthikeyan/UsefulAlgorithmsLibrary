package testcases;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import algorithms.MaximumMatching.EdmondMatching;

public class Testing {
    public static void main(String[] args) {

        
        for (int q = 0; q < 100; q++) {
            Random rand = new Random(q);
            int[] testBana = new int[rand.nextInt(100)];
            for (int i = 0; i < testBana.length; i++) {
                testBana[i] = rand.nextInt();
            }

            System.out.print("Test " + q + ": ");
            int e = Clouds.solution(testBana);
            int c = EdmondMatching.solution(testBana);
            if (e != c) {
                System.out.println("fail!");
                
                System.out.println(e);
    
                System.out.println(c);
                System.out.println("\n");
            } else {
                System.out.println("passed!");
            }
        }

    }
    
    
 //passes 3/5
    public static class Clouds {
        
        public static class Graph<T> implements Iterable<T> {
            //using adjacney set here (epecting sparce graph) but it essentially become an adjaceny matrix
            private Map<T, Set<T>> undirGraph;
            
            public Graph() {
                this.undirGraph = new HashMap<T, Set<T>>();
            }
        
            public boolean addNode(T node){
                undirGraph.put(node, undirGraph.getOrDefault(node, new HashSet<>()));
                return true;
            }
        
            public boolean containsNode(T node) {
                return undirGraph.containsKey(node);
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
        
        }
        
        //lets build a forest the lorax would be proud of
        public static class MetaNode<T> {
            public T parent;
            public T root;
            public boolean isEven; //is it located on an odd level or even level

            public MetaNode(T parent, T root, boolean isEven){
                this.parent = parent;
                this.root = root;
                this.isEven = isEven;
            }
        }

        public static class Edge<T> {
            public T start;
            public T end;

            public Edge(T start, T end) {
                this.start = start;
                this.end = end;
            }
        }

        public static class Blossom<T> {
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
        }

        public static <T> Graph<T> maxMatching(Graph<T> graph){
        
            if (graph.isEmpty()) {
                return new Graph<>();
            }
            // no more augmenting paths found
            Graph<T> matching = greedyMatching(graph);
            
            while (true) {
                List<T> augPath = findAugmentingPath(graph, matching);
                if (augPath == null || augPath.isEmpty()) {   
                    return matching;
                }

                updateMatching(matching, augPath);
            }
        }
        public static <T> Graph<T> greedyMatching(Graph<T> graph) {
            Graph<T> greedy = new Graph<>();
            for (T node : graph) {
                greedy.addNode(node);
            }

            for (T node : graph) {
                if (greedy.neighbors(node).size() != 0) {
                    continue;
                }
                for (T end : graph.neighbors(node)) {
                    if (greedy.neighbors(end).size() != 0) {
                        continue;    
                    }
                    greedy.addEdge(node, end);  
                    break; 
                }
            }
            return greedy;
        }

        public static <T> List<T> findAugmentingPath(Graph<T> graph, Graph<T> matching) {
            matching.numEdges();
            Map<T, MetaNode<T>> forest = new HashMap<>();
            // nodes to check 
            Queue<Edge<T>> checkEdges = new LinkedList<>();

            for (T curr : graph) {
                if (matching.neighbors(curr).size() != 0) {
                    continue;
                }
                forest.put(curr, new MetaNode<T>(null, curr, true));
                //mark all edges
                
                for (T vert : graph.neighbors(curr)) {
                    //System.out.println("edge: " + curr + " | " + vert);
                    checkEdges.add(new Edge<T>(curr, vert));
                }
                
            }

            //System.out.println("starting queue ======");
            while (!checkEdges.isEmpty()) {
                Edge<T> currEdge = checkEdges.remove();
                T v = currEdge.start;
                T w = currEdge.end;
                //System.out.println("edge: " + v + " | " + w);
                if (matching.containsEdge(v, w)) {
                        continue;
                }
                //System.out.println("checking cases");

                if (!forest.containsKey(w)) {
                    // w is matched
                    addToForest(graph, matching, forest, checkEdges, v, w);
                    
                } else if (forest.get(w).isEven) {
                    
                    List<T> path = new ArrayList<>();
                    if (forest.get(v).root.equals(forest.get(w).root)) {
                        // blossom
                        path = blossomRecursion(graph, matching, forest, v, w);
                    } else {
                        //path from v to root + path from root to w
                        //vw is an edge and verticies have differnt roots
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
            Blossom<T> blossom = findBlossom(forest, v, w);
            List<T> path = findAugmentingPath(contractBlossom(graph, blossom), contractBlossom(matching, blossom));
            return liftPath(path, graph, blossom);
        }

        public static <T> Blossom<T> findBlossom(Map<T, MetaNode<T>> forest, T v, T w) {
            List<T> vPath = pathToRoot(forest, v);
            List<T> wPath = pathToRoot(forest, w);
            int vlen = vPath.size();
            int wlen = wPath.size();
            int lca = 1;
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

        public static <T> List<T> liftPath(List<T> path, Graph<T> graph, Blossom<T> blossom) {
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
                        if (graph.containsEdge(path.get(i + 1), blossom.cycle.get(c))) {
                            exitIndex = c;
                        }
                    }

                    //blossom cycle is listed clockwise
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

        public static <T> Graph<T> contractBlossom(Graph<T> graph, Blossom<T> blossom) {
            Graph<T> contracted = new Graph<T>();
            contracted.addNode(blossom.root);

            for (T node : graph) {
                if (blossom.contains(node)) {
                    continue;
                }
                
                contracted.addNode(node);
                for (T adj : graph.neighbors(node)) {
                    contracted.addEdge(node, ((blossom.contains(adj)) ? blossom.root : adj));
                }
            }

            return contracted;
        }

        public static <T> void addToForest(Graph<T> graph, Graph<T> matching, Map<T, MetaNode<T>> forest, Queue<Edge<T>> checkEdges, T v, T w) {
            //System.out.println("add to forest: " + v + " | " + w);
            T x = matching.neighbors(w).iterator().next();
            forest.put(w, new MetaNode<T>(v, forest.get(v).root, false));
            forest.put(x, new MetaNode<T>(w, forest.get(v).root, true));

            for (T neighbor : graph.neighbors(x)) {
                checkEdges.add(new Edge<T>(x, neighbor));
            }
            
        }

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

        public static boolean powerOfTwo(int num) {
            double pow = Math.log(num) / Math.log(2); // log base 2 of num
            return (pow % 1 == 0);
        }

        public static boolean validParing(int x, int y) {
            // if their sum dividied by the gcd is odd it will loop
            // (2,4) passes test but (1,5) fails
            // when will (x + y) / gcd(x, y) always be even? when x + y is a power of 2
            int num = (x + y) / gcd(x, y);
            return ((num - 1) & num) != 0;
        }

        public static int gcd(int x , int y) {
            if (y == 0) {
                return x;
            }
            return gcd(y, x % y);
        }

        public static int solution(int[] banana_list) {
            int len = banana_list.length;

            Graph<Integer> graph = new Graph<>();

            for (int i = 0; i < len; i++ ) {
                graph.addNode(i);
            }

            for (int i = 0; i < len; i++) {
                for (int j = i + 1; j < len; j++) {
                    if (validParing(banana_list[i], banana_list[j])) {
                        graph.addEdge(i, j);
                    }
                }
            }

            int mSize = maxMatching(graph).numEdges();
            //System.out.println("Clouds size:" + mSize);
            return mSize;
        }
    }
}
