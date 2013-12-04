package dsm;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public class AlmacenImpl extends UnicastRemoteObject implements Almacen {


    public AlmacenImpl() throws RemoteException {
    }
    public synchronized	List<ObjetoCompartido> leerObjetos(List<CabeceraObjetoCompartido> lcab)
      throws RemoteException {

	return null;
    }
    public synchronized void escribirObjetos(List<ObjetoCompartido> loc)
     throws RemoteException  {
    }
}

