package graph;

public class MetaNode<T> {
        public T parent;
        public T root;
        public boolean isEven; //is it located on an odd level or even level

        public MetaNode(T parent, T root, boolean isEven){
            this.parent = parent;
            this.root = root;
            this.isEven = isEven;
        }
    }
