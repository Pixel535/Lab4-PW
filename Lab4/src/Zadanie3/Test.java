package Zadanie3;


public class Test {

	public static void main(String[] args) {
		
		Widelce widelec = new Widelce();
		widelec.widelec.add (widelec.dostep.newCondition());
		widelec.widelec.add (widelec.dostep.newCondition());
		widelec.widelec.add (widelec.dostep.newCondition());
		widelec.widelec.add (widelec.dostep.newCondition());
		widelec.widelec.add (widelec.dostep.newCondition());
		
		Filo f1 = new Filo(0,100,widelec);
		Filo f2 = new Filo(1,100,widelec);
		Filo f3 = new Filo(2,100,widelec);
		Filo f4 = new Filo(3,100,widelec);
		Filo f5 = new Filo(4,100,widelec);
		
		f1.start();
		f2.start();
		f3.start();
		f4.start();
		f5.start();
		
		try {
			f1.join();
			f2.join();
			f3.join();
			f4.join();
			f5.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
