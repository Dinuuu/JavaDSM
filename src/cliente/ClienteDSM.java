import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import dsm.*;

public class ClienteDSM {
    static String leerPalabra (Scanner e, String mens) {
        System.out.println(mens);
        if (!e.hasNextLine())
            return null;
        return e.nextLine();
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

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        StringBuffer [] v1 = new StringBuffer[1];
        v1[0] = new StringBuffer();

        Integer [] v2 = new Integer[1];
        v2[0] = new Integer(0);

        List [] v3 = new List[1];;
        v3[0] = new LinkedList<Contador>();


        ObjetoCompartido o1 = new ObjetoCompartido("objecto1", v1);
        ObjetoCompartido o2 = new ObjetoCompartido("objecto2", v2);
        ObjetoCompartido o3 = new ObjetoCompartido("objecto3", v3);

        DSMCerrojo cerrojo = null;
        try {
            cerrojo = new DSMCerrojo("cerrojo");
            cerrojo.asociar(o1);
            cerrojo.asociar(o2);
            cerrojo.asociar(o3);

            String leido;
            Scanner ent = new Scanner(System.in);
            while ((leido = leerPalabra(ent, "?Acceso exclusivo (E) o compartido (C) (EOF para fin)?"))!= null) {
                boolean exc = ((leido.length()>0) && (leido.charAt(0) == 'E'));
                if (!cerrojo.adquirir(exc)) {
                    System.err.println("Error en adquirir");
                    return;
                }
                System.out.println("Valores de objetos al entrar:");
                System.out.println("v1 (StringBuffer)");
                System.out.println(v1[0]);
                System.out.println("v2 (Integer)");
                System.out.println(v2[0]);
                System.out.println("v3 (Lista de contadores)");
                imprimirListaCont(v3[0]);
                if (exc) {
                    System.out.println("S.critica exc: cambia valor objetos");
                    v1[0].append("|hola");
                    v2[0]++;
                    actualizarListaCont(v3[0]);
                }
                leerPalabra(ent, "Va a salir de s.critica; pulse para continuar");
                if (!cerrojo.liberar()) {
                    System.err.println("Error en liberar");
                    return;
                }
            }
            cerrojo.desasociar(o1);
            cerrojo.desasociar(o2);
            cerrojo.desasociar(o3);
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en ClienteDSM:");
            e.printStackTrace();
        }
    }
}
