package Zadanie2;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PisCzyt {

	final Lock dostep = new ReentrantLock();
	final Condition czytelnicy = dostep.newCondition();
	final Condition pisarze = dostep.newCondition();
	int licz_czyt = 0;
	int licz_pis = 0;
	int czyt_pocz = 0;
	int pis_pocz = 0;
	
	
	public void Pocz_czytania(String czyt, int i)
	{
		dostep.lock();
		try {
			System.out.println(">>(1)[ "+ czyt + ", " + i + " ] :: [ licz_czyt = " + licz_czyt + ", czyt_pocz = " + czyt_pocz + ", licz_pis = " + licz_pis + ", pis_pocz = " + pis_pocz + "]");
			if(licz_pis + pis_pocz > 0)
			{
				czyt_pocz = czyt_pocz + 1;
				czytelnicy.await();
				czyt_pocz = czyt_pocz - 1;
			}
			licz_czyt = licz_czyt + 1;
			System.out.println(">>(2)[ "+ czyt + ", " + i + " ] :: [ licz_czyt = " + licz_czyt + ", czyt_pocz = " + czyt_pocz + ", licz_pis = " + licz_pis + ", pis_pocz = " + pis_pocz + "]");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dostep.unlock();
		}
	}
	
	public void Kon_czytania(String czyt, int i)
	{
		dostep.lock();
		
		
		try {
			System.out.println("<<(1)[ "+ czyt + ", " + i + " ] :: [ licz_czyt = " + licz_czyt + ", czyt_pocz = " + czyt_pocz + ", licz_pis = " + licz_pis + ", pis_pocz = " + pis_pocz + "]");
			licz_czyt = licz_czyt - 1;
			if(licz_czyt == 0)
			{
				pisarze.signal();
			}
			System.out.println("<<(2)[ "+ czyt + ", " + i + " ] :: [ licz_czyt = " + licz_czyt + ", czyt_pocz = " + czyt_pocz + ", licz_pis = " + licz_pis + ", pis_pocz = " + pis_pocz + "]");
		}finally {
			dostep.unlock();
		}
		
		
	}
	
	
	public void Pocz_pisania(String pis, int i)
	{
		dostep.lock();
		try {
			System.out.println("==>(1)[ "+ pis + ", " + i + " ] :: [ licz_czyt = " + licz_czyt + ", czyt_pocz = " + czyt_pocz + ", licz_pis = " + licz_pis + ", pis_pocz = " + pis_pocz + "]");
			if(licz_czyt + licz_pis > 0)
			{
				pis_pocz = pis_pocz + 1;
				pisarze.await();
				pis_pocz = pis_pocz - 1;
			}
			licz_pis = 1;
			System.out.println("==>(2)[ "+ pis + ", " + i + " ] :: [ licz_czyt = " + licz_czyt + ", czyt_pocz = " + czyt_pocz + ", licz_pis = " + licz_pis + ", pis_pocz = " + pis_pocz + "]");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dostep.unlock();
		}
	}
	
	public void Kon_pisania(String pis, int i)
	{
		dostep.lock();
		
		try {
			System.out.println("<==(1)[ "+ pis + ", " + i + " ] :: [ licz_czyt = " + licz_czyt + ", czyt_pocz = " + czyt_pocz + ", licz_pis = " + licz_pis + ", pis_pocz = " + pis_pocz + "]");
			licz_pis = 0;
			if(czyt_pocz > 0)
			{
				czytelnicy.signalAll();
			}
			else
			{
				pisarze.signal();
			}
			System.out.println("<==(2)[ "+ pis + ", " + i + " ] :: [ licz_czyt = " + licz_czyt + ", czyt_pocz = " + czyt_pocz + ", licz_pis = " + licz_pis + ", pis_pocz = " + pis_pocz + "]");
		}finally {
			dostep.unlock();
		}
		
	}
	
	
}

class Czytelnik extends Thread
{
	private int numer;
	private int N;
	String nazwaW;
	private PisCzyt PisCzyt;
	Random random = new Random();
	
	Czytelnik(int numer, int N, PisCzyt PisCzyt){
		super("C-"+numer);
		this.N = N;
		this.PisCzyt = PisCzyt;
	}
	
	public void run() {
		for(int i = 0; i<N; i++)
		{
			try {
				Thread.sleep(random.nextInt(10)+5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PisCzyt.Pocz_czytania(getName(), i);
			try {
				Thread.sleep(random.nextInt(4)+1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PisCzyt.Kon_czytania(getName(), i);
		}
	}
}

class Pisarz extends Thread
{
	private int numer;
	private int N;
	String nazwaW;
	private PisCzyt PisCzyt;
	Random random = new Random();
	
	Pisarz(int numer, int N, PisCzyt PisCzyt){
		super("P-"+numer);
		this.N = N;
		this.PisCzyt = PisCzyt;
	}
	
	public void run() {
		for(int i = 0; i<N; i++)
		{
			try {
				Thread.sleep(random.nextInt(10)+5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PisCzyt.Pocz_pisania(getName(), i);
			try {
				Thread.sleep(random.nextInt(4)+1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PisCzyt.Kon_pisania(getName(), i);
		}
	}
}
