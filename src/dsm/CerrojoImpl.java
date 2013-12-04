package dsm;
import java.rmi.*;
import java.rmi.server.*;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

    CerrojoImpl() throws RemoteException {
    }

    public synchronized void adquirir (boolean exc) throws RemoteException {
    }
    public synchronized boolean liberar() throws RemoteException {
        return true;
    }
}
