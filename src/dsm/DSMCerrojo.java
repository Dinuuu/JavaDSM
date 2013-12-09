package dsm;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class DSMCerrojo {

	String nombre;
	List<ObjetoCompartido> objetos = new ArrayList<ObjetoCompartido>();
	FabricaCerrojos fabrica;
	Almacen almacen;

	public DSMCerrojo(String nom) throws RemoteException,
			MalformedURLException, NotBoundException {

		String servidor = System.getenv("SERVIDOR");
		String puerto = System.getenv("PUERTO");
		this.fabrica = (FabricaCerrojos) Naming.lookup("rmi://" + servidor
				+ ":" + puerto + "/DSM_cerrojos");
		this.almacen = (Almacen) Naming.lookup("rmi://" + servidor + ":"
				+ puerto + "/DSM_almacen");
		this.nombre = nom;

	}

	public void asociar(ObjetoCompartido o) {
		objetos.add(o);
	}

	public void desasociar(ObjetoCompartido o) {
		for (int i = 0; i < objetos.size(); i++) {
			ObjetoCompartido obj = objetos.get(i);
			if (obj.getCabecera().getNombre() == o.getCabecera().getNombre())
				objetos.remove(i);
		}
	}

	public boolean adquirir(boolean exc) throws RemoteException {

		return true;
	}

	public boolean liberar() throws RemoteException {

		for (ObjetoCompartido o : objetos)
			o.incVersion();
		return true;
	}
}
