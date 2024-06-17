package BST;

public class NodeTree<T extends Comparable<T>> implements NodeTreeInterface<T>{
    private T data;
    private NodeTree<T> leftChild;
    private NodeTree<T> rightChild;

    public NodeTree(T data) {
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public NodeTree<T> getLeft() {
        return leftChild;
    }

    @Override
    public NodeTree<T> getRight() {
        return rightChild;
    }

    public void setLeft(NodeTree<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRight(NodeTree<T> rightChild) {
        this.rightChild = rightChild;
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    public void setData(T data) {
        if(data == null) throw new IllegalArgumentException();
        this.data = data;
    }

    public T getMinOfRight(){
        NodeTree<T> aux = this;
        NodeTree<T> prev = aux;
        while (aux.getLeft() != null){
            prev = aux;
            aux = aux.getLeft();
        }
        prev.leftChild = null;
        return aux.getData();
    }

    public NodeTree<T> insert(T data) {

        if(data.compareTo(this.getData())>=0){
            if(rightChild == null) rightChild = new NodeTree<>(data);
            else this.setRight(this.getRight().insert(data));
        }
        else {
            if(leftChild == null) leftChild = new NodeTree<>(data);
            else this.setLeft(this.getLeft().insert(data));
        }
        return this;
    }

    public boolean hasChild(T myData) {
        if(data.compareTo(myData)==0)
            return true;
        if(data.compareTo(myData)>0 && leftChild!=null)
            return leftChild.hasChild(myData);
        if(data.compareTo(myData)<0 && rightChild!=null)
            return rightChild.hasChild(myData);
        return false;
    }

}
