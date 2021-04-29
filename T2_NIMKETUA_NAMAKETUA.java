import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class T2_NIMKETUA_NAMAKETUA {
    public static void main(String[] args) throws FileNotFoundException {
        /*
            init root dari pohonKurma
         */
        Tree pohonKurma = new Tree("html","html","html");
        File file =  new File("Tugas02.2/database.txt");
        Scanner scanner = new Scanner(file);
        /*
            Proccess
         */
        while (scanner.hasNextLine()){
            String input = scanner.nextLine();
            String[]splitInput = input.split(";");
            if (splitInput[0].equals("ADD")){
                if (splitInput[1].startsWith("#")){
                    pohonKurma.addTreeNodeID(splitInput[1].toLowerCase(), splitInput[2].toLowerCase(),splitInput[3].toLowerCase(),null);
                }else if (splitInput[1].equals("TEXT")){
                    pohonKurma.addTextNode(splitInput[2].toLowerCase(),splitInput[3].toLowerCase());
                    splitInput[2] = splitInput[2].substring(1);
                    System.out.println("tambah teks \""+splitInput[3]+"\" pada "+splitInput[2].toLowerCase());
                }else{
                    pohonKurma.addTreeNodeNameTag(splitInput[1].toLowerCase(), splitInput[2].toLowerCase(),splitInput[3].toLowerCase(),null);
                }
            }else if (splitInput[0].equals("DELETE")){
                pohonKurma.deleteTreeNode(splitInput[1].toLowerCase());
            }else if (splitInput[0].equals("PRINT")){
                pohonKurma.print(splitInput[1].toLowerCase(),Integer.parseInt(splitInput[2]));
            }else if (splitInput[0].equals("SEARCH")){
                /*
                    bingung ama penjelasan methodnya
                 */
                pohonKurma.searchTreeNode(splitInput[1]);
            }
        }
    }
}
class Node {
    /*
        Selector ada 2
        tag yg gamake #
        id yang make #
     */
    String selector, nameTag, id, text;
    Vector<Node> child = new Vector<>();
    Node parent;

    Node(String selector, String nameTag, String id, String text, Node parent) {
        this.selector = selector;
        this.nameTag = nameTag;
        this.id = id;
        this.text = text;
        this.parent = parent;
    }

    Node(String selector, String nameTag, String id) {
        this.selector = selector;
        this.nameTag = nameTag;
        this.id = id;
    }

    /*
        buat add cabang dari suatu selector
     */
    public void addChild(String selector, String nameTag, String id, String text, Node parent) {
        child.add(new Node(selector, nameTag, id, text, parent));
    }
}

 class Tree {
    Node root;

    Tree(String selector, String tagName, String id) {
        this.root = new Node(selector, tagName, id);
    }
    /*
        check klo id nya ada yang sama
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
    public void addTreeNodeID(String selector, String tagName, String id, String text) {
        String namaSelector = selector;
        if (selector.startsWith("#")){
            namaSelector = namaSelector.substring(1);
        }
        if (checkIsSame(id,root).size()>0){
            System.out.println(id+" sudah ada");
            return;
        }
        Vector<Node> temp = searchingSelector(selector, root);
        if (temp.size() != 0) {
            for (Node x : temp) {
                x.addChild(selector, tagName, id, text,x);
                System.out.printf("tambah <%s id=\"%s\"> pada %s\n",tagName,id,namaSelector);
            }
        } else {
            System.out.println("tidak ditemukan " + namaSelector);
        }
    }
    /*
         addTreeNode by nameTag
     */
    public void addTreeNodeNameTag(String selector, String tagName, String id, String text) {
        String namaSelector = selector;
        if (selector.startsWith("#")){
            namaSelector = namaSelector.substring(1);
        }
        Vector<Node>temp = searchingTagname(selector,root);
        int index = 1;
        if (temp.size() != 0) {
            for (Node x : temp) {
                if (temp.size()>1){
                    if (checkIsSame(id+index,root).size()==0) {
                        x.addChild(selector, tagName, id + index, text,x);
                        System.out.printf("tambah <%s id=\"%s\"> pada %s\n",tagName,id+index,namaSelector);
                    }
                    else System.out.println(id+index+" sudah ada");
                }else {
                    x.addChild(selector, tagName, id, text,x);
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
    private Vector<Node> searchingSelector(String selector, Node node) {
        Vector<Node> temp = new Vector<>();
        if ((selector.startsWith("#") ? "#" + node.id : node.selector).equals(selector)) {
            temp.add(node);
        }  else {
            for (int i = 0; i < node.child.size(); i++) {
                Vector<Node> found = searchingSelector(selector, node.child.get(i));
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
    private Vector<Node> searchingTagname(String selector,Node node){
        Vector<Node> temp = new Vector<>();
        if (node.nameTag!=null) {
            if (node.nameTag.equals(selector)) {
                temp.add(node);
            }
        }
        for (int i = 0; i < node.child.size(); i++) {
            Vector<Node> found = searchingTagname(selector, node.child.get(i));
            for (Node x : found) {
                temp.add(x);
            }
        }
        return temp;
    }
    /*
        buat print rekursif
     */
    private void methodPrint(Node node, int maxdepth, StringBuilder garis) {
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
                        methodPrint(p, --maxdepth, garis);
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
        manggil print
     */
    public void print(String selector, int maxdepth) {
        Vector<Node> temp;
        if (selector.startsWith("#")) {
            temp = searchingSelector(selector, root);
            StringBuilder stringBuilder = new StringBuilder();
            if (temp.size() != 0) {
                for (Node s : temp) {
                    methodPrint(s, maxdepth, stringBuilder);
                }
            }
        } else {
            temp = searchingTagname(selector, root);
            StringBuilder stringBuilder = new StringBuilder();
            if (temp.size() != 0) {
                for (Node s : temp) {
                    methodPrint(s, maxdepth, stringBuilder);
                }
            }
        }
    }
    /*
        add Node text
     */
    public void addTextNode(String selector, String text) {
        Vector<Node> temp = searchingSelector(selector, root);
        for (Node x : temp) {
            x.addChild(x.selector, null, null, text,x);
        }
    }
    /*
        Ngesearch dari root sampai ke Node tersebut, tetapi siblingnya ga ngikut
     */
    private Vector<Node> searchTreeNodeMethod(Node node){
        Vector<Node> temp = new Vector<>();
        if (node.parent!=null){
            Vector<Node> found = searchTreeNodeMethod(node.parent);
            for (Node x : found) {
                temp.add(x);
            }
        }
        temp.add(node);
        return temp;
    }
    /*
        print search method
     */
    public void searchTreeNode(String selector){
        Vector<Node> temp = searchingSelector(selector,root);
        Vector<Node> tempp = new Vector<>();
        if (temp.size()!=0) {
            for (Node x : temp) {
                tempp=searchTreeNodeMethod(x);
            }
        }
        if (tempp.size()!=0){
            StringBuilder stringBuilder = new StringBuilder("");
            for (int i = 0; i < tempp.size(); i++) {
                Node x = tempp.get(i);
                if (i==tempp.size()-1){
                    System.out.printf(stringBuilder+"<%s id=\"%s\">",x.nameTag,x.id);
                }else
                    System.out.printf(stringBuilder+"<%s id=\"%s\">\n",x.nameTag,x.id);
                stringBuilder.append("---");
            }
            for (int i = tempp.size()-1; i > -1; i--) {
                Node x = tempp.get(i);
                stringBuilder.delete(0,3);
                if (i==tempp.size()-1) System.out.printf("</%s>\n",x.nameTag);
                else System.out.printf(stringBuilder+"</%s>\n",x.nameTag);
            }
        }else System.out.println("tidak ditemukan "+selector);

    }
    /*
        deleteTreeNode
     */
    public void deleteTreeNode(String selector) {
        Vector<Node> temp = new Vector<>();
        if (selector.startsWith("#")) temp = searchingSelector(selector, root);
        else temp = searchingTagname(selector,root);
        if (temp.size()!=0){
            for (Node x : temp) {
                x.parent.child.remove(x);
            }
        }else System.out.println("tidak ditemukan " + selector);
    }
}


