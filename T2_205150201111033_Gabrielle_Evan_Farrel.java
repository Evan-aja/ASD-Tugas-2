import java.util.Scanner;
import java.util.Vector;

//205150200111041_1 Al'Ravie Mutiar Mahesa_1
//205150200111031_2 Arundaon Ramadhani Yudistira_2
//205150201111034_3 Miftahul Ihsan_3

public class T2_205150201111033_Gabrielle_Evan_Farrel {
	public static Scanner scan=new Scanner(System.in);;
    public static void main(String[] args){
//          initial root of pohonKurma
        Tree pohonKurma = new Tree("html","html","html");
        String[] tugas=new String[2];
		int i=0;
		boolean loop=true;
		while (loop) {
			tugas[i]=scan.nextLine();
			if(tugas[i].equals("")) break;
			i++;
			if(i==tugas.length-1) {
				String[] tmp=tugas;
				tugas=new String[tmp.length*2];
				System.arraycopy(tmp, 0, tugas, 0, tmp.length);
			}
		}
//          Process
		for(int j=0;j<tugas.length;j++) {
			try{
				String []words=tugas[j].trim().split(";");
				if(words[0].equals("ADD")) {
					if(words[1].startsWith("#")) {
						pohonKurma.addTreeNodeID(words[1], words[2], words[3],null);
					}else if(words[1].equals("TEXT")) {
						pohonKurma.addTextNode(words[2],words[3]);
						words[2] = words[2].substring(1);
	                    System.out.println("tambah teks \""+words[3]+"\" pada "+words[2].toLowerCase());
					}else {
						pohonKurma.addTreeNodeNameTag(words[1], words[2], words[3],null);
					}
				}else if(words[0].equals("DELETE")) {
					pohonKurma.deleteTreeNode(words[1]);
				}else if(words[0].equals("SEARCH")) {
					pohonKurma.searchTreeNode(words[1]);
				}else if(words[0].equals("PRINT")) {
					pohonKurma.print(words[1], Integer.parseInt(words[2]));
				}
			}catch (Exception e){
			}
		}
    }
}
class Node {
	
//      2 selector
//      tag tanpa #
//      id dengan #
    
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

//      membuat add cabang dari suatu selector
    public void addChild(String selector, String nameTag, String id, String text, Node parent) {
        child.add(new Node(selector, nameTag, id, text, parent));
    }
}

 class Tree {
    Node root;

    Tree(String selector, String tagName, String id) {
        this.root = new Node(selector, tagName, id);
    }
//      melakukan cek duplikasi Node
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
//      addTreeNode by ID
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
//     	addTreeNode by nameTag
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
// 		cari address selector
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
//  	cari address tagName
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
//  	rekursif print
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
//  	print caller
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
//      add Node text
    public void addTextNode(String selector, String text) {
        Vector<Node> temp = searchingSelector(selector, root);
        for (Node x : temp) {
            x.addChild(x.selector, null, null, text,x);
        }
    }
//      pencarian method search tanpa sibling
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
//      print search method
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
//    	deleteTreeNode
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


