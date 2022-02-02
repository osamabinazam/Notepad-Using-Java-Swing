package main.java;

public class UndoAndRedo {

    private static Node  headNode, tailNode;


    public static void Push  (String data){
        Node newNode = new Node(data);
        if(headNode == null){
                headNode = newNode;
                tailNode = newNode;
        }
        else {
            if (tailNode.nextNode == null)
                    tailNode.nextNode = newNode;
                    newNode.prevNode = tailNode;
                    tailNode = newNode;
        }
    }


    public static String Pop (){
        if (headNode == null && tailNode == null)
            return null;
        else{
            if (tailNode == headNode)   {
                String tempString = tailNode.data;
                tailNode = headNode = null;
                return tempString;
            }
            else {
                String tempString = tailNode.data;
                tailNode = tailNode.prevNode;
                tailNode.nextNode=null;
            return  tempString;
            }
        }
    }

    // public static void main(String[] args) {
    //         UndoAndRedo obj = new UndoAndRedo();
    //         obj.Push("Osama");
    //         obj.Push("Bin");
    //         obj.Push("Azam");

    //         System.out.println(obj.Pop());
    //         System.out.println(obj.Pop());
    //         System.out.println(obj.Pop());

    // }
}

class Node {
    String data;
    Node prevNode ,nextNode;
    Node (String data ){
        this.data = data;
        prevNode = nextNode = null;
    }
}