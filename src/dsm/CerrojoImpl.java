package dsm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

	private static final long serialVersionUID = 1L;
	private int lectores;
	private int escritores;

	CerrojoImpl() throws RemoteException {
	}

	public synchronized void adquirir(boolean exc) throws RemoteException {

		try {
			while (true) {
				if (exc) {
					if (lectores > 0 || escritores > 0) {
						wait();
					} else {
						escritores++;
						return;
					}

				} else if (escritores > 0) {
					wait();
				} else {
					lectores++;
					return;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public synchronized boolean liberar() throws RemoteException {

		if (escritores == 0 && lectores == 0)
			return false;

		if (lectores > 0) {
			lectores--;
			if (lectores == 0) {
				notifyAll();
				return true;
			}
		} else {
			escritores--;
			notifyAll();
			return true;
		}
		return true;

	}
}
