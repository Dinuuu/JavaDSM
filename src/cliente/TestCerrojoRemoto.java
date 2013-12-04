import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import dsm.*;

class TestCerrojoRemoto {
    static public String leerPalabra (Scanner e, String mens) {
	    System.out.println(mens);
            if (!e.hasNextLine())
                return null;
            return e.nextLine();
    }
    static public void main (String args[]) {
        if (args.length!=2) {
            System.err.println("Uso: TestCerrojoRemoto hostregistro numPuertoRegistro");
            return;
        }
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            FabricaCerrojos fab_cerr = (FabricaCerrojos) Naming.lookup("//" +
              args[0]+ ":" + args[1] + "/DSM_cerrojos");


            Cerrojo cerr = fab_cerr.iniciar("micerrojo");
            String leido;
	    Scanner ent = new Scanner(System.in);
            while ((leido = leerPalabra(ent, "?Acceso exclusivo (E) o compartido (C) (EOF para fin)?"))!= null) {
                cerr.adquirir((leido.length()>0) && (leido.charAt(0) == 'E'));
	        leerPalabra(ent, "Dentro de s.critica; pulse para continuar");
                cerr.liberar();
            }
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en TestCerrojoRemoto:");
            e.printStackTrace();
        }
    }
}
