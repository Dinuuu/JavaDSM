package dsm;
import java.rmi.*;

public interface FabricaCerrojos extends Remote {
	Cerrojo iniciar(String s) throws RemoteException;
}
