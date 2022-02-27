package Zadanie3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Widelce {
	
	final Lock dostep = new ReentrantLock();
	final Condition lokaj = dostep.newCondition();
	final List <Condition>widelec = new ArrayList<Condition>();
	public Boolean[] zajety = new Boolean[] {false,false,false,false,false};
	public static int jest = 0;
	
	public void wez(String filo,int i, int[] zajetyWid,int numer)
	{
		dostep.lock();
		try {
			for(int j=0;j<5;j++)
			{
				if(zajety[j] == true)
				{
					zajetyWid[j] = 1;
				}
				else {
					zajetyWid[j] = 0;
				}
			}
			System.out.println(">>>(1)[ "+ filo +", "+i+" ] :: [ "+zajetyWid[0]+", "+zajetyWid[1]+", "+zajetyWid[2]+", "+zajetyWid[3]+", "+zajetyWid[4]+" ] - "+ jest);
			if(jest == 4)
			{
				lokaj.await();
			}
			jest = jest + 1;
			if(zajety[numer])
			{
				widelec.get(numer).await();
			}
			zajety[numer] = true;
			if(zajety[(numer+1)%5])
			{
				widelec.get((numer+1)%5).await();
			}
			zajety[(numer+1)%5] = true;
			for(int j=0;j<5;j++)
			{
				if(zajety[j] == true)
				{
					zajetyWid[j] = 1;
				}
				else {
					zajetyWid[j] = 0;
				}
			}
			System.out.println(">>>(2)[ "+ filo +", "+i+" ] :: [ "+zajetyWid[0]+", "+zajetyWid[1]+", "+zajetyWid[2]+", "+zajetyWid[3]+", "+zajetyWid[4]+" ] - "+ jest);
 		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dostep.unlock();
		}
	}
	
	public void odloz(String filo,int i, int[] zajetyWid,int numer)
	{
		dostep.lock();
		try {
			for(int j=0;j<5;j++)
			{
				if(zajety[j] == true)
				{
					zajetyWid[j] = 1;
				}
				else {
					zajetyWid[j] = 0;
				}
			}
			System.out.println("<<<(1)[ "+ filo +", "+i+" ] :: [ "+zajetyWid[0]+", "+zajetyWid[1]+", "+zajetyWid[2]+", "+zajetyWid[3]+", "+zajetyWid[4]+" ] - "+ jest);
			zajety[numer] = false;
			widelec.get(numer).signal();
			zajety[(numer+1)%5] = false;
			widelec.get((numer+1)%5).signal();
			jest = jest-1;
			lokaj.signal();
			for(int j=0;j<5;j++)
			{
				if(zajety[j] == true)
				{
					zajetyWid[j] = 1;
				}
				else {
					zajetyWid[j] = 0;
				}
			}
			System.out.println("<<<(2)[ "+ filo +", "+i+" ] :: [ "+zajetyWid[0]+", "+zajetyWid[1]+", "+zajetyWid[2]+", "+zajetyWid[3]+", "+zajetyWid[4]+" ] - "+ jest);
		}finally {
			dostep.unlock();
		}
	}
}
