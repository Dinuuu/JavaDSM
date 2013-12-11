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
	List<ObjetoCompartido> objetos = new ArrayList<ObjetoCompartido>();
	Cerrojo cer;
	boolean exclusivo;

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
		objetos.add(o);
	}

	public void desasociar(ObjetoCompartido o) {

		for (int i = 0; i < objetos.size(); i++) {
			ObjetoCompartido obj = objetos.get(i);
			if (obj.getCabecera().getNombre()
					.equals(o.getCabecera().getNombre())) {
				objetos.remove(i);
				return;
			}
		}
	}

	public boolean adquirir(boolean exc) throws RemoteException {

		this.exclusivo = exc;
		this.cer.adquirir(exc);

		List<CabeceraObjetoCompartido> cabeceras = listaCabeceras();
		if (cabeceras != null) {
			List<ObjetoCompartido> nuevos = almacen.leerObjetos(cabeceras);

			if (nuevos != null) {

				for (ObjetoCompartido obj : nuevos) {
					ObjetoCompartido o = get(obj.getCabecera().getNombre());
					if (o != null) {
						o.setObjeto(obj.getObjeto());
						o.setVersion(obj.getCabecera().getVersion());

					}
				}
			}
		}

		return true;
	}

	private ObjetoCompartido get(String nombre) {

		for (ObjetoCompartido o : objetos) {
			if (o.getCabecera().getNombre().equals(nombre))
				return o;
		}
		return null;

	}

	private List<CabeceraObjetoCompartido> listaCabeceras() {

		List<CabeceraObjetoCompartido> resp = new ArrayList<CabeceraObjetoCompartido>();
		for (ObjetoCompartido o : objetos) {
			resp.add(o.getCabecera());
		}
		if (resp.size() == 0)
			return null;
		return resp;
	}

	public boolean liberar() throws RemoteException {

		if (exclusivo) {
			List<ObjetoCompartido> obs = new ArrayList<ObjetoCompartido>();

			for (ObjetoCompartido o : objetos) {
				o.incVersion();
				obs.add(o);
			}

			almacen.escribirObjetos(obs);
		}
		return cer.liberar();
	}
}
