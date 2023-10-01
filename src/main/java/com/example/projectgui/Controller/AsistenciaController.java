package com.example.projectgui.Controller;


import com.example.projectgui.SessionManager;
import com.example.projectgui.UserSession;

public class AsistenciaController {
    // Obtenemos la instancia de UserSession desde SesionManager
    UserSession userSession = SessionManager.getUsuarioSesion();
    String usernamee = userSession.getUsername();
    String rol = userSession.getRol();
    int idUsuario = userSession.getIdUsuario();
    int idEmpleado = userSession.getIdEmpleado();


}
