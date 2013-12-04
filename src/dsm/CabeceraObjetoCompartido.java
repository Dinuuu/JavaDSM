package dsm;
import java.io.*;

public class CabeceraObjetoCompartido implements Serializable  {

	private String nombre;
	private int version=0;

	protected CabeceraObjetoCompartido(String n) {
		nombre=n;
	}
	public String getNombre() {
		return nombre;
	}
	public int getVersion() {
		return version;
	}
	protected void setVersion(int v) {
		version=v;
	}
}
