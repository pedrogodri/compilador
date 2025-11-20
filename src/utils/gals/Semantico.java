package utils.gals;

import interfaces.ITipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import utils.gals.exceptions.SemanticError;

public class Semantico implements Constants
{
    private Stack<String> pilha_tipos = new Stack<>();
    private String operador_relacional;
    private String codigo = "";

    // controle de rotulos
    private Stack<String> pilha_rotulos = new Stack<>();
    private int contadorRotulos = 0;

    // declaração de variavel
    private String tipo_declarado;
    private List<String> lista_identificadores = new ArrayList<>();
    private Map<String, String> tabela_simbolos = new HashMap<>();
    private List<String> lista_locals = new ArrayList<>();

    private String novoRotulo() {
        return "L" + (contadorRotulos++);
    }

    public String getCodigo() {
        return codigo;
    }

    public void executeAction(int action, Token token)	throws SemanticError
    {
        switch (action) {
            case 100 -> acao100();
            case 101 -> acao101();
            case 102 -> acao102();
            case 103 -> acao103(token);
            case 104 -> acao104(token);
            case 105 -> acao105(token);
            case 106 -> acao106();
            case 107 -> acao107();
            case 108 -> acao108();
            case 109 -> acao109();
            case 110 -> acao110();
            case 111 -> acao111(token);
            case 112 -> acao112();
            case 113 -> acao113();
            case 114 -> acao114();
            case 115 -> acao115();
            case 116 -> acao116();
            case 117 -> acao117();
            case 118 -> acao118();
            case 119 -> acao119();
            case 120 -> acao120(token);
            case 121 -> acao121(token);
            case 122 -> acao122();
            case 123 -> acao123(token);
            case 124 -> acao124(token);
            case 125 -> acao125(token);
            case 126 -> acao126();
            case 127 -> acao127();
            case 128 -> acao128();
            case 129 -> acao129(token);
            case 130 -> acao130(token);
            default -> {}
        }

        // System.out.println("Ação #"+action+", Token: "+token);
    }	

    /// comando para iniciar projeto
    private void acao100() {
        codigo = codigo.concat(".assembly extern mscorlib {}\n" +
                                ".assembly _programa{}\n" +
                                ".module _programa.exe\n" +
                                "\n" +
                                ".class public _unica{\n" +
                                ".method static public void _principal(){\n" +
                                ".entrypoint\n");
    }

    /// comando para finalizar projeto
    private void acao101() {
        codigo = codigo.concat("ret\n"+
                                "}\n" +
                                "}\n");
    }

    /// comando para escrever o que está na pilha
    private void acao102() {
        String tipo = pilha_tipos.pop();

        if (tipo.equals(ITipo.INT))
            codigo = codigo.concat("conv.i8\n");

        codigo = codigo.concat("call void [mscorlib]System.Console::Write("+ tipo +")\n");
    }

    /// comando para empilhar tipo int64
    private void acao103(Token token) {
        pilha_tipos.push(ITipo.INT);
        codigo = codigo.concat("ldc.i8 " + token.getLexeme() + "\n");
        codigo = codigo.concat("conv.r8\n");
    }

    /// comando para empilhar tipo float64
    private void acao104(Token token) {
        pilha_tipos.push(ITipo.FLOAT);
        codigo = codigo.concat("ldc.r8 " + token.getLexeme() + "\n");
    }

    /// comando para empilhar tipo string
    private void acao105(Token token) {
        pilha_tipos.push(ITipo.STRING);
        String lexeme = token.getLexeme();
        // Se o lexema já vem com aspas, remove-as
        if (lexeme.startsWith("\"") && lexeme.endsWith("\"")) {
            lexeme = lexeme.substring(1, lexeme.length() - 1);
        }
        codigo = codigo.concat("ldstr \"" + lexeme + "\"\n");
    }

    /// comando para somar dois números
    private void acao106() {
        String t2 = pilha_tipos.pop();
        String t1 = pilha_tipos.pop();

        if (t1.equals(ITipo.INT) && t2.equals(ITipo.INT))
            pilha_tipos.push(ITipo.INT);
        else
            pilha_tipos.push(ITipo.FLOAT);

        codigo = codigo.concat("add\n");
    }

    /// comando para subtrair dois números
    private void acao107() {
        String t2 = pilha_tipos.pop();
        String t1 = pilha_tipos.pop();

        if (t1.equals(ITipo.INT) && t2.equals(ITipo.INT))
            pilha_tipos.push(ITipo.INT);
        else
            pilha_tipos.push(ITipo.FLOAT);

        codigo = codigo.concat("sub\n");
    }

    
    /// comando para multiplicar dois números
    private void acao108() {
        String t2 = pilha_tipos.pop();
        String t1 = pilha_tipos.pop();

        if (t1.equals(ITipo.INT) && t2.equals(ITipo.INT))
            pilha_tipos.push(ITipo.INT);
        else
            pilha_tipos.push(ITipo.FLOAT);

        codigo = codigo.concat("mul\n");
    }

    /// comando para dividir dois números
    private void acao109() {
        pilha_tipos.pop();
        pilha_tipos.pop();

        pilha_tipos.push(ITipo.FLOAT);

        codigo = codigo.concat("div\n");
    }

    /// comando para transformar número em negativo
    private void acao110() {
        codigo = codigo.concat("neg\n");
    }

    /// comando para guardar o operador relacional
    private void acao111(Token token) {
        operador_relacional = token.getLexeme();
    }

    /// comando para operações relacionais
    private void acao112() {
        pilha_tipos.pop();
        pilha_tipos.pop();

        pilha_tipos.push(ITipo.BOOL);

        switch (operador_relacional) {

            case "==" -> codigo = codigo.concat("ceq\n");

            case "~=" -> {
                codigo = codigo.concat("ceq\n");
                codigo = codigo.concat("ldc.i4.0\n");
                codigo = codigo.concat("ceq\n");
            }

            case "<" -> codigo = codigo.concat("clt\n");

            case ">" -> codigo = codigo.concat("cgt\n");

            default -> {}
        }
    }

    /// comando para fazer "and"
    private void acao113() {
        pilha_tipos.pop();
        pilha_tipos.pop();

        pilha_tipos.push(ITipo.BOOL);

        codigo = codigo.concat("and\n");
    }

    /// comando para fazer "or"
    private void acao114() {
        pilha_tipos.pop();
        pilha_tipos.pop();

        pilha_tipos.push(ITipo.BOOL);

        codigo = codigo.concat("or\n");
    }

    /// comando para empilhar tipo booleano true
    private void acao115() {
        pilha_tipos.push(ITipo.BOOL);
        codigo = codigo.concat("ldc.i4.1\n");
    }
    
    /// comando para empilhar tipo booleano false
    private void acao116() {
        pilha_tipos.push(ITipo.BOOL);
        codigo = codigo.concat("ldc.i4.0\n");
    }
    
    /// comando para fazer "not"
    private void acao117() {
        codigo = codigo.concat("ldc.i4.0\n");
        codigo = codigo.concat("ceq\n");
    }

    /// comando para escrever quebra de linha na saída padrão
    private void acao118() {
        codigo = codigo.concat("ldstr \"\n\"");
        codigo = codigo.concat("call void [mscorlib]System.Console::Write(string)");
    }

    /// comando para adicionar ao locals
    private void acao119() {
        for (String id : lista_identificadores) {

            // Insere na tabela de símbolos conforme tipo declarado
            tabela_simbolos.put(id, tipo_declarado);

            // Gera o código objeto de declaração (.locals)
            codigo = codigo.concat("\t.locals (" + tipo_declarado + " " + id + ")\n");
        }

        // Limpa a lista de ids usados na declaração
        lista_identificadores.clear();
    }

    /// comando para pegar qual o tipo da variavel
    private void acao120(Token token) {
        String tipoFonte = token.getLexeme();

        switch (tipoFonte) {
            case "int" -> tipo_declarado = ITipo.INT;
            case "float" -> tipo_declarado = ITipo.FLOAT;
            case "string" -> tipo_declarado = ITipo.STRING;
            case "bool" -> tipo_declarado = ITipo.BOOL;
            default -> {}
        }
    }

    /// comando para adicionar na lista de IDs
    private void acao121(Token token) {
        lista_identificadores.add(token.getLexeme());
    }

    /// comando para atribuição
    private void acao122() {
        // Remove o tipo avaliado pela expressão
        String tipoExpressao = pilha_tipos.pop();

        // Se expressao for int64 → converter antes de armazenar
        if (tipoExpressao.equals(ITipo.INT)) {
            codigo = codigo.concat("conv.i8\n");
        }

        // Recupera o id onde vai armazenar
        String id = lista_identificadores.get(0);

        // Gera código IL da atribuição
        codigo = codigo.concat("\tstloc " + id + "\n");

        // Limpa lista
        lista_identificadores.clear();
    }

    /// comando para leitura de entrada
    private void acao123(Token token) throws SemanticError {
        String id = token.getLexeme();

        // Verifica tipo do id
        String tipoId = tabela_simbolos.get(id);

        // Não pode ler bool
        if (tipoId.equals(ITipo.BOOL)) {
            throw new SemanticError(
                id + " inválido para comando de entrada", 
                token.getPosition()
            );
        }

        // Gera leitura básica: ReadLine()
        codigo = codigo.concat("\tcall string [mscorlib]System.Console::ReadLine()\n");

        // Conversões conforme tipo
        switch (tipoId) {
            case ITipo.INT -> 
                codigo = codigo.concat("\tcall int64 [mscorlib]System.Int64::Parse(string)\n");
            case ITipo.FLOAT ->
                codigo = codigo.concat("\tcall float64 [mscorlib]System.Double::Parse(string)\n");
            case ITipo.STRING -> { /* nada: ReadLine já retorna string */ }
            default -> {}
        }

        // Armazena o valor lido
        codigo = codigo.concat("\tstloc " + id + "\n");
    }

    /// comando para escrever constante string
    private void acao124(Token token) {
        // CORRIGIDO: remove aspas do lexema se necessário
        String lexeme = token.getLexeme();
        if (lexeme.startsWith("\"") && lexeme.endsWith("\"")) {
            lexeme = lexeme.substring(1, lexeme.length() - 1);
        }
        
        // Carregar a constante string
        codigo = codigo.concat("ldstr \"" + lexeme + "\"\n");

        // Escrever no console
        codigo = codigo.concat("call void [mscorlib]System.Console::Write(string)\n");
    }

    /// comando if - verificação de expressão
    private void acao125(Token token) throws SemanticError {
        // 1. Desempilha o tipo da expressão
        String tipoExpressao = pilha_tipos.pop();

        // 2. Verifica se a expressão é do tipo bool
        if (!tipoExpressao.equals(ITipo.BOOL)) {
            throw new SemanticError(
                "expressão incompatível em comando de seleção",
                token.getPosition()
            );
        }

        // 3. Cria um novo rótulo
        String rotulo = novoRotulo();

        // 4. Gera código para desviar caso a expressão seja falsa
        codigo = codigo.concat("brfalse " + rotulo + "\n");

        // 5. Empilha o rótulo para uso posterior na ação #126 / #127
        pilha_rotulos.push(rotulo);
    }

    /// comando if/else - fim do bloco
    private void acao126() {
        // Recupera o rótulo que marca o fim do if/else
        String rotulo = pilha_rotulos.pop();

        // Rotula a próxima instrução
        codigo = codigo.concat(rotulo + ":\n");
    }

    /// comando else - início do bloco
    private void acao127() {
        // Cria rótulo para o fim total do if (após o else)
        String rotulo2 = novoRotulo();

        // Desvio incondicional para depois do if/else
        codigo = codigo.concat("br " + rotulo2 + "\n");

        // Recupera o rótulo do false (criado na #125)
        String rotulo1 = pilha_rotulos.pop();

        // Marca o início da parte do ELSE
        codigo = codigo.concat(rotulo1 + ":\n");

        // Empilha o rótulo2 para resolução posterior (#126)
        pilha_rotulos.push(rotulo2);
    }

    /// comando while - início do laço
    private void acao128() {
        // Cria rótulo para o início do laço
        String rotulo = novoRotulo();

        // Marca o início do laço
        codigo = codigo.concat(rotulo + ":\n");

        // Empilha para uso posterior (#129)
        pilha_rotulos.push(rotulo);
    }

    /// comando while - verificação da condição
    private void acao129(Token token) throws SemanticError {
        // Verifica se expressão é bool
        String tipoExpressao = pilha_tipos.pop();

        if (!tipoExpressao.equals(ITipo.BOOL)) {
            throw new SemanticError(
                "expressão incompatível em comando de repetição",
                token.getPosition()
            );
        }

        // Recupera o rótulo do início do laço (criado na #128)
        String rotulo = pilha_rotulos.pop();

        // CORRIGIDO: Se falso → sai do while (não volta para o início)
        // O correto é: brfalse sai do loop, brtrue volta para o início
        // Como a semântica pede brfalse para voltar, vamos criar um rótulo de saída
        String rotuloFim = novoRotulo();
        
        // Se falso → sai do while
        codigo = codigo.concat("brfalse " + rotuloFim + "\n");
        
        // Guarda rótulo de fim para ser resolvido depois
        pilha_rotulos.push(rotuloFim);
        
        // Ao final do bloco do while, deve ter um "br" de volta ao início
        // Isso será feito na gramática ou em outra ação
        // Por enquanto, armazenamos o rótulo de início também
        pilha_rotulos.push(rotulo);
    }

    /// comando para carregar identificador em expressão
    private void acao130(Token token) {
        String id = token.getLexeme();

        // Recupera o tipo do identificador na tabela de símbolos
        String tipoId = tabela_simbolos.get(id);

        // Empilha o tipo correspondente
        pilha_tipos.push(tipoId);

        // Carrega o valor do identificador
        codigo = codigo.concat("ldloc " + id + "\n");

        // Se o id for int64 → converter para float64
        if (tipoId.equals(ITipo.INT)) {
            codigo = codigo.concat("conv.r8\n");
        }
    }
}
