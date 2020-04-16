package types;

public class Usuario {
private int idusuario;
private String nombre;
private Long telefonoU;
private String CorreoUsuario;
private Long telefonoE;
private String modeloMoto;





public Usuario() {
	super();
}





public Usuario(int idusuario, String nombre, Long telefonoU, String correoUsuario, Long telefonoE, String modeloMoto) {
	super();
	this.idusuario = idusuario;
	this.nombre = nombre;
	this.telefonoU = telefonoU;
	CorreoUsuario = correoUsuario;
	this.telefonoE = telefonoE;
	this.modeloMoto = modeloMoto;
}





public int getIdusuario() {
	return idusuario;
}





public void setIdusuario(int idusuario) {
	this.idusuario = idusuario;
}





public String getNombre() {
	return nombre;
}





public void setNombre(String nombre) {
	this.nombre = nombre;
}





public Long getTelefonoU() {
	return telefonoU;
}





public void setTelefonoU(Long telefonoU) {
	this.telefonoU = telefonoU;
}





public String getCorreoUsuario() {
	return CorreoUsuario;
}





public void setCorreoUsuario(String correoUsuario) {
	CorreoUsuario = correoUsuario;
}





public Long getTelefonoE() {
	return telefonoE;
}





public void setTelefonoE(Long telefonoE) {
	this.telefonoE = telefonoE;
}





public String getModeloMoto() {
	return modeloMoto;
}





public void setModeloMoto(String modeloMoto) {
	this.modeloMoto = modeloMoto;
}





@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((CorreoUsuario == null) ? 0 : CorreoUsuario.hashCode());
	result = prime * result + idusuario;
	result = prime * result + ((modeloMoto == null) ? 0 : modeloMoto.hashCode());
	result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
	result = prime * result + ((telefonoE == null) ? 0 : telefonoE.hashCode());
	result = prime * result + ((telefonoU == null) ? 0 : telefonoU.hashCode());
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
	Usuario other = (Usuario) obj;
	if (CorreoUsuario == null) {
		if (other.CorreoUsuario != null)
			return false;
	} else if (!CorreoUsuario.equals(other.CorreoUsuario))
		return false;
	if (idusuario != other.idusuario)
		return false;
	if (modeloMoto == null) {
		if (other.modeloMoto != null)
			return false;
	} else if (!modeloMoto.equals(other.modeloMoto))
		return false;
	if (nombre == null) {
		if (other.nombre != null)
			return false;
	} else if (!nombre.equals(other.nombre))
		return false;
	if (telefonoE == null) {
		if (other.telefonoE != null)
			return false;
	} else if (!telefonoE.equals(other.telefonoE))
		return false;
	if (telefonoU == null) {
		if (other.telefonoU != null)
			return false;
	} else if (!telefonoU.equals(other.telefonoU))
		return false;
	return true;
}





@Override
public String toString() {
	return "Usuario [idusuario=" + idusuario + ", nombre=" + nombre + ", telefonoU=" + telefonoU + ", CorreoUsuario="
			+ CorreoUsuario + ", telefonoE=" + telefonoE + ", modeloMoto=" + modeloMoto + "]";
}





}
