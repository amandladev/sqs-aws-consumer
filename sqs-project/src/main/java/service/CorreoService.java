package service;

import model.Correo;

import java.util.List;

public class CorreoService implements ICorreo{
    Correo correo;

    @Override
    public List<Correo> listAllCorreos() {
        return null;
    }

    @Override
    public String showCorreoName() {
        return correo.getEmail();
    }
}
