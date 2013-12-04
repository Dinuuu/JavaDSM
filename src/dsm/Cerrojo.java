package dsm;
import java.rmi.*;

public interface Cerrojo extends Remote {
    public void adquirir (boolean exclusivo) throws RemoteException;
    boolean liberar () throws RemoteException;
}
