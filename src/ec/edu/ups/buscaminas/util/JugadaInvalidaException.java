package ec.edu.ups.buscaminas.util;

public class JugadaInvalidaException extends Exception {
	private static final long serialVersionUID = 1L;
    // Constructor que recibe el mensaje de error
    public JugadaInvalidaException(String message) {
        super(message);
    }

    // Constructor que permite encadenar la excepción con otra causa
    public JugadaInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }
}