package utils.gals.Exceptions;

public class SimboloInvalidoError extends LexicalError
{
    private char caracter;

    public SimboloInvalidoError(String msg, int position, char caracter)
    {
        super(msg, position);
        this.caracter = caracter;
    }

    public SimboloInvalidoError(String msg, int position)
    {
        super(msg, position);
    }

    public SimboloInvalidoError(String msg)
    {
        super(msg);
    }

    public char getCaracter() {
        return caracter;
    }
}
