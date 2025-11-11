package utils.gals;

import java.util.Stack;

import utils.gals.exceptions.SemanticError;

public class Semantico implements Constants
{
    private Stack<String> pilha_tipos = new Stack<>();
    private String codigo;

    public void executeAction(int action, Token token)	throws SemanticError
    {
        switch (action) {
            case 100:
                // acao100();
                break;
            case 101:
                // acao101();
                break;
            case 102:
                // acao102();
                break;
            case 103:
                // acao103();
                break;
            case 104:
                // acao104();
                break;
            case 105:
                // acao105();
                break;
            default:
                break;
        }

        System.out.println("Ação #"+action+", Token: "+token);
    }	

    private void acao100 () { //formatar
      codigo.concat(".assembly extern mscorlib {}" +
                       ".assembly _programa{}" +
                       ".module _programa.exe" +
                       "" +
                       ".class public _unica{" +
                       ".method static public void _principal(){" +
                       ".entrypoint");
    }

    private void acao103 (Token token) {
        pilha_tipos.push("int64");
        codigo.concat("ldc.i8 " + token.getLexeme());
        codigo.concat("conv.r8");
    }

    private void acao101 () {
      codigo.concat("ret"+
                       "}" +
                       "}");
    }

}
