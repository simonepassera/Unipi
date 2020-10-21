public class TestCoinsCollection{
    public static void main (String [] args){	
	//  Commentare uno solo tra i due comandi seguenti
	CoinsCollection coll = new CoinsCollectionImpl();
	// CoinsCollection coll = new CoinsCollectionImplAlt();
	coll.createCC();
	coll.addCoin(50);
	coll.addCoin(100);
	coll.addCoin(200);
	System.out.println("balance: " + coll.balance());
	try{
	    coll.addCoin(-100);
	}
	catch (IllegalArgumentException e){
	    e.printStackTrace();
	    System.out.println("Test passed...");
	}
	try{
	    coll.addCoin(0);
	}
	catch (IllegalArgumentException e){
	    e.printStackTrace();
	    System.out.println("Test passed...");
	}
	try{
	    coll.addCoin(null);
	}
	catch (IllegalArgumentException e){
	    e.printStackTrace();
	    System.out.println("Test passed...");
	}
	CoinsCollection nuova;
	int bal_coll, bal_new, bal_coll1;
	try{
	    bal_coll = coll.balance();
	    nuova = coll.makeChange(150);
	    bal_new = nuova.balance();
	    bal_coll1 = coll.balance();
	    System.out.println("coll: " + bal_coll + " nuova: " + bal_new + " coll-after: " + bal_coll1); 
	}
	catch (IllegalArgumentException e){
	    e.printStackTrace();
	    System.out.println("Test passed...");
	}catch (NoChangeException e){	    
	    e.printStackTrace();
	    System.out.println("Test passed...");
	}
	
	try{
	    bal_coll = coll.balance();
	    nuova = coll.makeChange(150);
	    bal_new = nuova.balance();
	    bal_coll1 = coll.balance();
	    System.out.println("coll: " + bal_coll + " nuova: " + bal_new + " coll-after: " + bal_coll1); 
	}
	catch (IllegalArgumentException e){
	    e.printStackTrace();
	    System.out.println("Test passed...");
	}catch (NoChangeException e){	    
	    e.printStackTrace();
	    System.out.println("Test passed...");
	}
    }
}
