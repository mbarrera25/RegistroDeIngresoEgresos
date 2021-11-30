package com.proyecto.Gastos.constantes;

public interface constante_respuestas {

    String _STATUS = "status";
    String _BODY= "body";
    String ERROR= "error";
    String TITLE= "title";
    String TEXT= "text";

    String MSJ_ERROR_TOKEN = "no tienes acceso a este recurso";
    String MSJ_ERROR = "Se ha producido un error ";
    String ERROR_DESCONOCIDO = "Ocurrió un error. Favor vuelva a intentar.";
    String MSJ_EXITO = "Operación exitosa";
    String MSJ_DUPLICADO = "La opcion que intenta agregar ya existe";
    String MSJ_CAMBIO_NOMBRE = "no se pudo cambiar el nombre, intente nuevamente dentro de ";
    String MSJ_ERROR_PARAMETROS = "se ha producido un error, por favor revisar los parametros enviados";
    String MSJ_INACTIVO = "El usuario se encuentra desactivado, comuniquese con administracion de mosi o al correo soporte@mosi.com";
    String MSJ_BLOQUEADO = "El usuario se encuentra bloqueado, comuniquese con administracion de mosi o al correo soporte@mosi.com";

    String TITULO_BLOQUEADO= " Usuario Bloqueado";
    String TITULO_INACTIVO= " Usuario Inactivo";


    Object MSJ_ERROR_USUARIO = "No posees una cuenta en MOSI. Comunicate con administracion al correo administracion@mosi.com para gestionar tu cuenta!";
}
