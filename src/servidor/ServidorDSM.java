import java.rmi.*;
import java.rmi.server.*;
import dsm.*;

class ServidorDSM  {

    static public void main (String args[])  {
        if (args.length!=1) {
            System.err.println("Uso: ServidorDSM numPuertoRegistro");
            return;
        }
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            FabricaCerrojosImpl srv_cerr = new FabricaCerrojosImpl();
            Naming.rebind("rmi://localhost:" + args[0] + "/DSM_cerrojos", srv_cerr);
            AlmacenImpl srv_alm = new AlmacenImpl();
            Naming.rebind("rmi://localhost:" + args[0] + "/DSM_almacen", srv_alm);
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Excepcion en ServidorDSM:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
