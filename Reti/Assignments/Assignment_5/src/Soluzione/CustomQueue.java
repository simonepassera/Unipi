/**
 *	Laboratorio di Reti A
 *	Implementazione di una generica coda bloccante (senza vincoli di capacita')
 *	@author Matteo Loporchio
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class CustomQueue<T> {
	// Nota: la coda e' implementata mediante una LinkedList
	private LinkedList<T> queue = new LinkedList<T>();
	private final Lock lock = new ReentrantLock();
	private final Condition emptyCond = lock.newCondition(); 
	
	/**
	 *	Inserisce un elemento in fondo alla coda.
	 *	@param element l'elemento da inserire
	 */
	public void put(T element) {
		lock.lock();
		try {
			queue.add(element);
			emptyCond.signal();
		}
		finally {lock.unlock();}
	}

	/**	
	 *	Estrae un elemento dalla coda.
	 *	@return il primo elemento della coda
	 */
	public T take() throws InterruptedException {
		T result = null;
		lock.lock();
		try {
			while (queue.isEmpty()) emptyCond.await();
			result = queue.removeFirst();
		}
		finally {lock.unlock();}
		return result;
	}
}
