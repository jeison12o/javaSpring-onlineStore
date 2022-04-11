function confirmarEliminarCuenta(){
	var respuesta = confirm("¿Estás seguro de que deseas eliminar la cuenta?");
	if (respuesta === true) {
		return true;
	} else {
		return false;
	}
}



