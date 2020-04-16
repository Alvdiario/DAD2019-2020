package types;

public class Dispositivo {

	
	private Integer iddispositivo;
	private String ip ;
	private String nombre;
	private Integer idusuario;
	private Long initialtimestamp;
	
	public Dispositivo(Integer iddispositivo, String ip, String nombre, Integer idusuario, Long timestamp) {
		super();
		this.iddispositivo = iddispositivo;
		this.ip = ip;
		this.nombre = nombre;
		this.idusuario = idusuario;
		this.initialtimestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Dispositivo [iddispositivo=" + iddispositivo + ", ip=" + ip + ", nombre=" + nombre + ", idusuario="
				+ idusuario + ", timestamp=" + initialtimestamp + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iddispositivo == null) ? 0 : iddispositivo.hashCode());
		result = prime * result + ((idusuario == null) ? 0 : idusuario.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((initialtimestamp == null) ? 0 : initialtimestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dispositivo other = (Dispositivo) obj;
		if (iddispositivo == null) {
			if (other.iddispositivo != null)
				return false;
		} else if (!iddispositivo.equals(other.iddispositivo))
			return false;
		if (idusuario == null) {
			if (other.idusuario != null)
				return false;
		} else if (!idusuario.equals(other.idusuario))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (initialtimestamp == null) {
			if (other.initialtimestamp != null)
				return false;
		} else if (!initialtimestamp.equals(other.initialtimestamp))
			return false;
		return true;
	}

	
	
	public Integer getIddispositivo() {
		return iddispositivo;
	}

	public void setIddispositivo(Integer iddispositivo) {
		this.iddispositivo = iddispositivo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public Long getTimestamp() {
		return initialtimestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.initialtimestamp = timestamp;
	}
	
	
	
	
	
	
	
	
	
}
