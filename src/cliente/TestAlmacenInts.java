import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import dsm.*;


class TestAlmacenInts {
    static public String leerPalabra (Scanner e, String mens) {
        System.out.println(mens);
        if (!e.hasNextLine())
            return null;
        return e.nextLine();
    }
    static public ObjetoCompartido leerObjeto(Almacen al, ObjetoCompartido oc)
      throws RemoteException {
        List<CabeceraObjetoCompartido> lcab = new
            LinkedList<CabeceraObjetoCompartido>();

        lcab.add(oc.getCabecera());
        List<ObjetoCompartido> lres;
        lres = al.leerObjetos(lcab);
        if (lres == null)
            return null;
        else
            return lres.get(0);
    }
    static public void escribirObjeto(Almacen al, ObjetoCompartido oc)
      throws RemoteException {
        List<ObjetoCompartido> loc = new LinkedList<ObjetoCompartido>();

        loc.add(oc);
        al.escribirObjetos(loc);
     }
            
    static public void main (String args[]) {
        if (args.length!=2) {
            System.err.println("Uso: TestAlmacenInts hostregistro numPuertoRegistro");
            return;
        }
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            Almacen alm = (Almacen) Naming.lookup("//" + args[0] + ":"
              + args[1] + "/DSM_almacen");

            Integer [] s = new Integer[5];
            for (int i=0; i<s.length; i++)
                s[i] = new Integer(i);

            ObjetoCompartido oc = new ObjetoCompartido("miobjeto", s);

            String leido;
            Scanner ent = new Scanner(System.in);
            while (true) {
                System.out.println("Va a leer el objeto compartido");
                ObjetoCompartido res;
                res = leerObjeto(alm, oc);
                if (res == null)
                    System.out.println("Version local ya estaba actualizada");
                else 
                    if (!oc.setObjetoCompartido(res)) {
                        System.err.println("Error leyendo objeto de distinta clase");
                        System.exit(1);
                    }
	
                System.out.println("Valor del objeto: ");
                for (int i=0; i<s.length; i++)
                    System.out.println("s[" + i + "]=" + s[i]);

                System.out.println("Va a escribir en el objeto");
                for (int i=0; i<s.length; i++)
                    s[i]++;
                oc.incVersion();
                escribirObjeto(alm, oc);
                if (leerPalabra(ent, "Pulse para continuar (EOF fin)")==null)
                    break;
            }
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en TestAlmacenInts:");
            e.printStackTrace();

        }
    }
}
