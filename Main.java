package tugas_2;

import java.util.Scanner;

public class Main {
	public static Scanner scan=new Scanner(System.in);;
    public static void main(String[] args){
        /*
            init root dari pohonKurma
         */
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
        /*
            Process
         */
		for(int j=0;j<tugas.length;j++) {
			try{
				String []words=tugas[j].trim().split(";");
				if(words[0].equals("ADD")) {
					if(words[1].startsWith("#")) {
						pohonKurma.addNodeByID(words[1], words[2], words[3],null);
					}else if(words[1].equals("TEXT")) {
						pohonKurma.addTextNode(words[2],words[3]);
						words[2] = words[2].substring(1);
	                    System.out.println("tambah teks \""+words[3]+"\" pada "+words[2].toLowerCase());
					}else {
						pohonKurma.addNodeByTag(words[1], words[2], words[3],null);
					}
				}else if(words[0].equals("DELETE")) {
					pohonKurma.deleteTreeNode(words[1]);
				}else if(words[0].equals("SEARCH")) {
					pohonKurma.search(words[1]);
				}else if(words[0].equals("PRINT")) {
					pohonKurma.print(words[1], Integer.parseInt(words[2]));
				}
			}catch (Exception e){
			}
		}
    }
}
