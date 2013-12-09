package dsm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class FabricaCerrojosImpl extends UnicastRemoteObject implements
		FabricaCerrojos {

	private static final long serialVersionUID = 1L;

	Map<String, Cerrojo> cerrojos = new HashMap<String, Cerrojo>();

	public FabricaCerrojosImpl() throws RemoteException {
	}

	public synchronized Cerrojo iniciar(String s) throws RemoteException {

		if (cerrojos.containsKey(s))
			return cerrojos.get(s);

		Cerrojo cer = new CerrojoImpl();
		cerrojos.put(s, cer);

		return cer;
	}
}
