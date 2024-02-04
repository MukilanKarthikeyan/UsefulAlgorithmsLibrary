package graph;
import java.util.*;

public class Node<T> {
    T data;
    Node<T> parent;
    Node<T> root;
    //childern in order form left to right
    List<Node<T>> childern;
    //undodrded set of childern;
    HashSet<Node<T>> childernSet; 
    public Node(T data) {
        this.data = data;
    }

}
