class Node {
    String dataval;
    Node nextval;

    Node(String data) {
        dataval = data;
        nextval = null;
    }
}

class SLinkedList {
    Node headval;

    SLinkedList() {
        headval = null;
    }

    void listprint() {
        Node printval = headval;
        while (printval != null) {
            System.out.println("Value: " + printval.dataval);
            printval = printval.nextval;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SLinkedList list = new SLinkedList();
        list.headval = new Node("Monday");
        Node e2 = new Node("Tuesday");
        Node e3 = new Node("Wednesday");

        list.headval.nextval = e2;
        e2.nextval = e3;

        list.listprint();
    }
}
