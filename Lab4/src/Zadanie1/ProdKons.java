package Zadanie1;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdKons {

	public int MAX = 10;
	public String[] pula = new String[MAX];
	public int we = 0;
	public int wy = 0;
	public int licz = 0;
	final Lock dostep = new ReentrantLock();
	final Condition pelny = dostep.newCondition();
	final Condition pusty = dostep.newCondition();
	
	public void wstaw(String nazwaW)
	{
		dostep.lock();
		
		try {
			if(licz == MAX)
			{
				pelny.await();
			}
			pula[we] = nazwaW;
			we = (we+1)%MAX;
			licz = licz+1;
			pusty.signal();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dostep.unlock();
		}
		
	}
	
	public String pobierz(String nazwaW, int i, String kons)
	{
		dostep.lock();
		
		try {
			if(licz == 0)
			{
				pusty.await();
			}
			nazwaW = pula[wy];
			wy = (wy+1)%MAX;
			licz = licz - 1;
			pelny.signal();
			System.out.println("[" + kons + ", " + i + "] >> Dana = " + nazwaW);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dostep.unlock();
		}
		return nazwaW;
	}
	
}


class Producent extends Thread{
	private int numer;
	private int N;
	String nazwaW;
	private ProdKons ProdKons;
	Random random = new Random();
	
	Producent(int numer, int N, ProdKons ProdKons){
		super("P-"+numer);
		this.N = N;
		this.ProdKons = ProdKons;
	}
	
	public void run() {
		for(int i =0; i<N; i++)
		{
			try {
				Thread.sleep(random.nextInt(9)+1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nazwaW = "[" + getName() + ", " + i + ", " + random.nextInt(99) + "]";
			ProdKons.wstaw(nazwaW);
		}
	}
}

class Konsument extends Thread{
	private int numer;
	private int N;
	String nazwaW;
	private ProdKons ProdKons;
	Random random = new Random();
	
	Konsument(int numer, int N, ProdKons ProdKons){
		super("K-"+numer);
		this.N = N;
		this.ProdKons = ProdKons;
	}
	
	public void run() {
		for(int i =0; i<N; i++)
		{
			nazwaW = ProdKons.pobierz(nazwaW, i, getName());
			try {
				Thread.sleep(random.nextInt(10)+2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
