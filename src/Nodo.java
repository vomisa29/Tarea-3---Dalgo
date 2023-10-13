
public class Nodo {
	private int idNodo;
	private int pesoActual;
	private int idNodoPredecesor;
	private int color;// 0 para blanco, 1 para gris, 2 para negro
	private int profundidad;
	private int descubrimiento;
	private int finalizacion;

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public Nodo(int idNodo) {
		this.idNodo = idNodo;
	}

	public int getIdNodo() {
		return idNodo;
	}

	public void setIdNodo(int idNodo) {
		this.idNodo = idNodo;
	}

	public int getPesoActual() {
		return pesoActual;
	}

	public void setPesoActual(int pesoActual) {
		this.pesoActual = pesoActual;
	}

	public int getIdNodoPredecesor() {
		return idNodoPredecesor;
	}

	public void setIdNodoPredecesor(int idNodoPredecesor) {
		this.idNodoPredecesor = idNodoPredecesor;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getDescubrimiento() {
		return descubrimiento;
	}

	public void setDescubrimiento(int descubrimiento) {
		this.descubrimiento = descubrimiento;
	}

	public int getFinalizacion() {
		return finalizacion;
	}

	public void setFinalizacion(int finalizacion) {
		this.finalizacion = finalizacion;
	}
}
