package dsm;
import java.io.*;

public class ObjetoCompartido implements Serializable {

    private CabeceraObjetoCompartido cab;
    private Object [] objeto;

    public ObjetoCompartido(String n, Object [] o) {
        cab = new CabeceraObjetoCompartido(n);
        objeto=o;
    }
    public CabeceraObjetoCompartido getCabecera() {
        return cab;
    }
    public void setVersion(int v) {
        cab.setVersion(v);
    }
    public int incVersion() {
        int v;
        v=cab.getVersion();
        cab.setVersion(v+1);
        return v;
    }
    public Object [] getObjeto() {
        return objeto;
    }
    public boolean setObjeto(Object [] nuevo) {
        boolean res;
	if (res = (nuevo.getClass() == objeto.getClass()))
            System.arraycopy(nuevo, 0, objeto, 0, objeto.length);
        return res;
    }
    public boolean setObjetoCompartido(ObjetoCompartido nuevo) {
        boolean res;
        if (res = (nuevo.getCabecera().getNombre().equals(cab.getNombre()))) {
            if (res = (setObjeto(nuevo.getObjeto()))) 
            	cab.setVersion(nuevo.getCabecera().getVersion());
        }
        return res;
    }
}
