package dsm;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSMCerrojo {

	String nombre;
	FabricaCerrojos fabrica;
	Almacen almacen;
	Map<String, ObjetoCompartido> objetos = new HashMap<String, ObjetoCompartido>();
	Cerrojo cer;
	boolean lector;

	public DSMCerrojo(String nom) throws RemoteException,
			MalformedURLException, NotBoundException {

		String servidor = System.getenv("SERVIDOR");
		String puerto = System.getenv("PUERTO");
		this.fabrica = (FabricaCerrojos) Naming.lookup("rmi://" + servidor
				+ ":" + puerto + "/DSM_cerrojos");
		this.almacen = (Almacen) Naming.lookup("rmi://" + servidor + ":"
				+ puerto + "/DSM_almacen");
		this.nombre = nom;
		this.cer = this.fabrica.iniciar(nom);

	}

	public void asociar(ObjetoCompartido o) {
		objetos.put(o.getCabecera().getNombre(), o);
	}

	public void desasociar(ObjetoCompartido o) {

		objetos.remove(o.getCabecera().getNombre());
	}

	public boolean adquirir(boolean exc) throws RemoteException {

		this.cer.adquirir(exc);

		List<CabeceraObjetoCompartido> cabeceras = listaCabeceras();

		if (cabeceras != null) {
			List<ObjetoCompartido> nuevos = almacen.leerObjetos(cabeceras);

			if (nuevos != null)
				for (ObjetoCompartido o : nuevos) {
					objetos.remove(o.getCabecera().getNombre());
					objetos.put(o.getCabecera().getNombre(), o);
				}
		}
		lector = !exc;
		return true;
	}

	private List<CabeceraObjetoCompartido> listaCabeceras() {

		Collection<ObjetoCompartido> objetosComp = objetos.values();
		List<CabeceraObjetoCompartido> resp = new ArrayList<CabeceraObjetoCompartido>();
		for (ObjetoCompartido o : objetosComp) {
			resp.add(o.getCabecera());
		}
		if (resp.size() == 0)
			return null;
		return resp;
	}

	public boolean liberar() throws RemoteException {

		if (!lector) {
			List<ObjetoCompartido> obs = new ArrayList<ObjetoCompartido>();

			for (ObjetoCompartido o : objetos.values()) {
				obs.add(o);
			}

			if (obs.size() != 0)
				almacen.escribirObjetos(obs);
		}
		boolean resp = cer.liberar();

		System.out.println(resp);
		return resp;
	}
}
