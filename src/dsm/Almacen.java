package dsm;
import java.util.*;
import java.rmi.*;

public interface Almacen extends Remote {
    List <ObjetoCompartido> leerObjetos(List <CabeceraObjetoCompartido> lcab)
		throws RemoteException;
    void escribirObjetos(List<ObjetoCompartido> loc) throws RemoteException;
}
