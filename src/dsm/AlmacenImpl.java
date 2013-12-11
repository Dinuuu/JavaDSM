package dsm;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class AlmacenImpl extends UnicastRemoteObject implements Almacen {

	private static final long serialVersionUID = 1L;
	List<ObjetoCompartido> objetosCompartidos = new ArrayList<ObjetoCompartido>();

	public AlmacenImpl() throws RemoteException {
	}

	public synchronized List<ObjetoCompartido> leerObjetos(
			List<CabeceraObjetoCompartido> lcab) throws RemoteException {

		List<ObjetoCompartido> resp = new ArrayList<ObjetoCompartido>();

		for (CabeceraObjetoCompartido cab : lcab) {
			ObjetoCompartido o = get(cab.getNombre());
			if (o != null) {
				if (cab.getVersion() < o.getCabecera().getVersion())
					resp.add(o);
			}
		}
		if (resp.size() != 0)
			return resp;
		else
			return null;
	}

	public synchronized void escribirObjetos(List<ObjetoCompartido> loc)
			throws RemoteException {

		for (ObjetoCompartido obj : loc) {
			ObjetoCompartido o = get(obj.getCabecera().getNombre());
			if (o == null)
				objetosCompartidos.add(obj);
			else {
				o.setObjeto(obj.getObjeto());
				o.setVersion(obj.getCabecera().getVersion());

			}
		}

	}

	private ObjetoCompartido get(String nombre) {

		for (ObjetoCompartido o : objetosCompartidos) {
			if (o.getCabecera().getNombre().equals(nombre))
				return o;
		}
		return null;

	}
}
