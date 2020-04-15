package types;

public class Usuario {
private int idusuario;
private String nombre;
private char CorreoUsuario;
private char telefonoU;
private char telefonoE;
private char modeloMoto;



public Usuario() {
	super();
}



public Usuario(int idusuario, String nombre, char correoUsuario, char telefonoU, char telefonoE, char modeloMoto) {
	super();
	this.idusuario = idusuario;
	this.nombre = nombre;
	CorreoUsuario = correoUsuario;
	this.telefonoU = telefonoU;
	this.telefonoE = telefonoE;
	this.modeloMoto = modeloMoto;
}

public Usuario(int idusuario, String nombre) {
	super();
	this.idusuario = idusuario;
	this.nombre = nombre;
	
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



public char getCorreoUsuario() {
	return CorreoUsuario;
}



public void setCorreoUsuario(char correoUsuario) {
	CorreoUsuario = correoUsuario;
}



public char getTelefonoU() {
	return telefonoU;
}



public void setTelefonoU(char telefonoU) {
	this.telefonoU = telefonoU;
}



public char getTelefonoE() {
	return telefonoE;
}



public void setTelefonoE(char telefonoE) {
	this.telefonoE = telefonoE;
}



public char getModeloMoto() {
	return modeloMoto;
}



public void setModeloMoto(char modeloMoto) {
	this.modeloMoto = modeloMoto;
}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + CorreoUsuario;
	result = prime * result + idusuario;
	result = prime * result + modeloMoto;
	result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
	result = prime * result + telefonoE;
	result = prime * result + telefonoU;
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
	if (CorreoUsuario != other.CorreoUsuario)
		return false;
	if (idusuario != other.idusuario)
		return false;
	if (modeloMoto != other.modeloMoto)
		return false;
	if (nombre == null) {
		if (other.nombre != null)
			return false;
	} else if (!nombre.equals(other.nombre))
		return false;
	if (telefonoE != other.telefonoE)
		return false;
	if (telefonoU != other.telefonoU)
		return false;
	return true;
}



@Override
public String toString() {
	return "Usuario [idusuario=" + idusuario + ", nombre=" + nombre + ", CorreoUsuario=" + CorreoUsuario
			+ ", telefonoU=" + telefonoU + ", telefonoE=" + telefonoE + ", modeloMoto=" + modeloMoto + "]";
}



}
