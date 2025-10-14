package utils.gals.exceptions;

public class SyntaticError extends AnalysisError
{
    private String encontrado;

    public SyntaticError(String msg, int position, String encontrado)
    {
        super(msg, position);
        this.encontrado = encontrado;
    }

    public SyntaticError(String msg, int position)
    {
        super(msg, position);
    }

    public SyntaticError(String msg)
    {
        super(msg);
    }

    public String getEncontrado() {
        return "encontrado " + encontrado;
    }
}