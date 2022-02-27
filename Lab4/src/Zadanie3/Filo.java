package Zadanie3;

import java.util.Random;

public class Filo extends Thread{

	int numer;
	int N;
	String nazwaW;
	Widelce widelec;
	Random random = new Random();
	int[] zajetyWid = new int[5];
	
	Filo(int numer, int N, Widelce widelec){
		super("F-"+numer);
		this.N = N;
		this.widelec = widelec;
		this.numer = numer;
	}
	
	public void run() {
		for(int i=0;i<N;i++)
		{
			try {
				Thread.sleep(random.nextInt(10)+5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			widelec.wez(getName(), i, zajetyWid, numer);
			
			try {
				Thread.sleep(random.nextInt(4)+1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			widelec.odloz(getName(), i, zajetyWid, numer);
		}
	}
	
}
