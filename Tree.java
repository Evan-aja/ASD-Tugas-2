package tugas_2;

import java.util.Vector;

class Node {
    /*
        Selector 2
        tag != #
        id == #
     */
    String selector,nameTag,id,text;
    Vector<Node>child = new Vector<>();

    Node(){

    };
    Node(String selector,String nameTag,String id,String text){
        this.selector = selector;
        this.nameTag = nameTag;
        this.id = id;
        this.text = text;
    }
    Node(String selector,String nameTag,String id){
        this.selector = selector;
        this.nameTag = nameTag;
        this.id = id;
    }
    /*
        add cabang dari suatu selector
     */
    public void addChild(String selector,String nameTag,String id,String text){
        child.add(new Node(selector, nameTag, id, text));
    }
}

public class Tree {
    Node root;

    Tree(String selector, String tagName, String id) {
        this.root = new Node(selector, tagName, id);
    }
    /*
        cek duplikat id
     */
    private Vector<Node> checkIsSame(String id,Node node){
    	Vector<Node> temp = new Vector<>();
        if (node.id!=null) {
            if (node.id.equals(id)) {
                temp.add(node);
            } else {
                for (int i = 0; i < node.child.size(); i++) {
                    Vector<Node> found = checkIsSame(id, node.child.get(i));
                    for (Node x : found) {
                        temp.add(x);
                    }
                }
            }
        }
        return temp;
    }
    /*
        addTreeNode by ID
     */
    public void addNodeByID(String selector, String tagName, String id, String text) {
    	String namaSelector = selector;
        if (selector.startsWith("#")){
            namaSelector = namaSelector.substring(1);
        }
        if (checkIsSame(id,root).size()>0){
            System.out.println(id+" sudah ada");
            return;
        }
        Vector<Node> temp = selectorFinder(selector, root);
        if (temp.size() != 0) {
            for (Node x : temp) {
                x.addChild(selector, tagName, id, text);
                System.out.printf("tambah <%s id=\"%s\"> pada %s\n",tagName,id,namaSelector);
            }
        } else {
            System.out.println("tidak ditemukan " + namaSelector);
        }
    }
   /*
        addTreeNode by nameTag
    */
    public void addNodeByTag(String selector, String tagName, String id, String text) {
    	String namaSelector = selector;
        if (selector.startsWith("#")){
            namaSelector = namaSelector.substring(1);
        }
        Vector<Node>temp = tagFinder(selector,root);
        int index = 1;
        if (temp.size() != 0) {
            for (Node x : temp) {
                if (temp.size()>1){
                    if (checkIsSame(id+index,root).size()==0) {
                        x.addChild(selector, tagName, id + index, text);
                        System.out.printf("tambah <%s id=\"%s\"> pada %s\n",tagName,id+index,namaSelector);
                    }
                    else System.out.println(id+index+" sudah ada");
                }else {
                    x.addChild(selector, tagName, id, text);
                    System.out.printf("tambah <%s id=\"%s\"> pada %s\n", tagName, id, namaSelector);
                }
                index++;
            }
        } else {
            System.out.println("tidak ditemukan " + selector);
        }
    }
    /*
        cari address selector
     */
    private Vector<Node> selectorFinder(String selector, Node node) {
    	Vector<Node> temp = new Vector<>();
        if ((selector.startsWith("#") ? "#" + node.id : node.selector).equals(selector)) {
            temp.add(node);
        }  else {
            for (int i = 0; i < node.child.size(); i++) {
                Vector<Node> found = selectorFinder(selector, node.child.get(i));
                for (Node x : found) {
                    temp.add(x);
                }
            }
        }
        return temp;
    }
    /*
        cari address tagName
     */
    private Vector<Node> tagFinder(String selector,Node node){
        Vector<Node> temp = new Vector<>();
        if (node.nameTag!=null) {
            if (node.nameTag.equals(selector)) {
                temp.add(node);
            }
        }
        for (int i = 0; i < node.child.size(); i++) {
                Vector<Node> found = tagFinder(selector, node.child.get(i));
                for (Node x : found) {
                    temp.add(x);
                }
            }
        return temp;
    }
    /*
        print rekursif
     */
    private void recursivePrint(Node node, int maxdepth, StringBuilder garis) {
    	boolean sekip = false;
        if (maxdepth == -1) return;
        if (node.text == null) {
            if (node.child.size() != 0) {
                if (node.child.size() > 1 && node.child.get(0).text != null) {
                    System.out.printf("%s<%s id=\"%s>\n", garis, node.nameTag, node.id);
                } else if (node.child.get(0).text != null && node.child.get(0).child.size() == 0) {
                    System.out.printf("%s<%s id=\"%s>", garis, node.nameTag, node.id);
                    System.out.printf("%s", node.child.get(0).text);
                    sekip = true;
                } else {
                    System.out.printf("%s<%s id=\"%s>\n", garis, node.nameTag, node.id);
                }
                garis.append("---");
                for (int i = 0; i < node.child.size(); i++) {
                    Node p = node.child.get(i);
                    if (sekip) {
                        continue;
                    } else {
                        recursivePrint(p, --maxdepth, garis);
                        ++maxdepth;
                    }
                }
                garis.delete(0, 3);
                if (node.child.size() > 1 && node.child.get(0).text != null) {
                    System.out.printf("%s</%s>\n", garis, node.nameTag);
                } else if (node.child.get(0).text != null) {
                    System.out.printf("</%s>\n", node.nameTag);
                } else
                    System.out.printf("%s</%s>\n", garis, node.nameTag);
            } else {
                System.out.printf("%s<%s id=\"%s>", garis, node.nameTag, node.id);
                System.out.printf("\n%s</%s>\n", garis, node.nameTag);
            }
        } else {
            System.out.println(garis + node.text);
        }
    }
    /*
        print
     */
    public void print(String selector, int maxdepth) {
    	Vector<Node> temp;
        if (selector.startsWith("#")) {
            temp = selectorFinder(selector, root);
            StringBuilder stringBuilder = new StringBuilder();
            if (temp.size() != 0) {
                for (Node s : temp) {
                    recursivePrint(s, maxdepth, stringBuilder);
                }
            }
        } else {
            temp = tagFinder(selector, root);
            StringBuilder stringBuilder = new StringBuilder();
            if (temp.size() != 0) {
                for (Node s : temp) {
                    recursivePrint(s, maxdepth, stringBuilder);
                }
            }
        }
    }
    /*
		search printer (print dengan awalan #)
     */
    public void search(String id) {
    	print(id,100000);
    }
    /*
        add Node text
     */
    public void addTextNode(String selector, String text) {
        Vector<Node> temp = selectorFinder(selector, root);
        for (Node x : temp) {
            x.addChild(x.selector, null, null, text);
        }
    }
    /*
        parent dari suatu child untuk method delete
     */
    private Vector<Node> parentFinder(String selector,Node node) {
        Vector<Node> temp = new Vector<>();
        if (node.child.size() != 0) {
            for (int i = 0; i < node.child.size(); i++) {
                if ((selector.startsWith("#") ? "#" + node.child.get(i).id : node.child.get(i).selector).equals(selector)) {
                    temp.add(node);
                    return temp;
                }
                Vector<Node> found = parentFinder(selector, node.child.get(i));
                for (Node x : found) {
                    temp.add(x);
                }
            }
        }
        return temp;
    }
    /*
        deleteTreeNode
     */
    public void deleteTreeNode(String selector) {
        Vector<Node> temp = selectorFinder(selector, root);
        Vector<Node> parent = parentFinder(selector,root);
        if (temp.size()!=0&&parent.size()!=0){
            for (Node x : temp) {
                for (Node y : parent) {
                    y.child.remove(x);
                }
            }
        }else System.out.print("tidak ditemukan " + selector);
    }
}
