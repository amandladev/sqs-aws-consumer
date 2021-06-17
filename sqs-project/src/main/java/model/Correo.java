package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Correo {
    private Integer cod_cliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String username;
    private String password;
    private Integer dni;
    private Integer telefono;
    private String email;
    private String pedidos;
}
