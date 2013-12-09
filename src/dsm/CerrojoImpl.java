package dsm;

import java.rmi.*;
import java.rmi.server.*;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

	private static final long serialVersionUID = 1L;
	int lectores;
	int escritores;

	CerrojoImpl() throws RemoteException {
	}

	public synchronized void adquirir(boolean exc) throws RemoteException {
		try {
			if (escritores > 0)
				wait();
			if (exc) {
				if (lectores > 0) {
					wait();
				}
				escritores++;
			} else
				lectores++;
		} catch (InterruptedException e) {
			if (exc) {
				escritores++;
			} else
				lectores++;

		}

	}

	public synchronized boolean liberar() throws RemoteException {

		if (escritores == 0 && lectores == 0)
			return false;

		if (lectores > 0) {
			lectores--;
			if (lectores == 0) {
				notify();
			}
		}
		if (escritores > 0) {
			escritores--;
			if (escritores == 0)
				notifyAll();
			else
				throw new NullPointerException();
		}

		return true;
	}
}
