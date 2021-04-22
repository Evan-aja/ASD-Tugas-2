package tugas_2;

import java.util.Scanner;

class Masuk {
	
	//untuk menambah tag baru (ADD;div atau ADD;html dsb)
	void ADD1(String selector,String namaTag,String namaId) {
		
	}
	//untuk menambah TEXT kedalam sebuah id (ADD;TEXT:#id)
	void ADD2(String TEXT/*untuk memanggil TEXT saja*/, String selector,String kalimat) {
		
	}
	//untuk menambah konten didalam sebuah id (ADD;#html)
	void ADD3(String namaId,String namaTag,String namaIdChild) {
		
	}
	void PRINT(String selector,int jumlahLine) {
		
	}
	void DELETE(String selector) {
		boolean ada = SEARCH(selector);
		if(ada==true) {
			
		}else{
			
		}
	}
	boolean SEARCH(String selector) {
		
		return false;
	}
}

public class Main {
	public static void main(String[] args) {
		Masuk learn=new Masuk();
		Scanner scan = new Scanner(System.in);
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
		scan.close();
		for(int j=0;j<tugas.length;j++) {
			String[]words=tugas[j].trim().split(";");
			if(words[0].equals("ADD")) {
				if(words[1].substring(0,1).equals("#")) {
					learn.ADD3(words[1],words[2],words[3]);
				}else if(words[1].equals("TEXT")) {
					learn.ADD2(words[1],words[2],words[3]);
				}else {
					learn.ADD1(words[1],words[2],words[3]);
				}
			}else if(words[0].equals("PRINT")) {
				int jumlah=Integer.parseInt(words[2]);
				learn.PRINT(words[1], jumlah);
			}else if(words[0].equals("DELETE")) {
				learn.DELETE(words[1]);
			}
		}
	}
}
