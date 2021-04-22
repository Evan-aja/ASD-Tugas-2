package tugas_2;

import java.util.Scanner;

class Masuk {
	
	void ADD() {
		
	}
	void PRINT() {
		
	}
	void DELETE() {
		
	}
}

public class Main {
	public static void main(String[] args) {
//		Masuk learn=new Masuk();
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
	}
}
