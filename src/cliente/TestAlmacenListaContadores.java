import java.util.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import dsm.*;



class TestAlmacenListaContadores {
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
            
    static void imprimirListaCont(List l) {
        if (l.isEmpty())
             System.out.println("Lista vacia");
        else
        for (Object nodo: l) {
             Contador c = (Contador) nodo;
             System.out.println(c.getN() + ": " + c.getC());
        }
    }
    static int n=0;
@SuppressWarnings("unchecked")
    static void actualizarListaCont(List l) {
        for (Object nodo: l) {
            Contador c = (Contador) nodo;
            c.incC();
        }
        Contador c = new Contador("contador" + n++);
        l.add(c);
    }

    static public void main (String args[]) {
        if (args.length!=2) {
            System.err.println("Uso: TestAlmacenListaContadores hostregistro numPuertoRegistro");
            return;
        }
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            Almacen alm = (Almacen) Naming.lookup("//" + args[0] + ":"
              + args[1] + "/DSM_almacen");

            List [] v = new List[1];;
            v[0] = new LinkedList<Contador>();

            ObjetoCompartido oc = new ObjetoCompartido("miobjeto", v);

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
                imprimirListaCont(v[0]);


                System.out.println("Va a escribir en el objeto");
                actualizarListaCont(v[0]);

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
            System.err.println("Excepcion en TestAlmacenListaContadores:");
            e.printStackTrace();

        }
    }
}
