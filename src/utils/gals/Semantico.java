package utils.gals;

import interfaces.ITipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import utils.gals.exceptions.SemanticError;

public class Semantico implements Constants
{
    // #region controle de expressões

    /// Pilha de tipos que estão na memoria
    private Stack<String> pilha_tipos = new Stack<>();
    /// Guarda o operador relacional atual
    private String operador_relacional = "";

    // #endregion

    /// Código objeto gerado
    private String codigo_objeto = "";

    // #region controle de rotulos

    /// Pilha de rótulos para controle de desvios
    private Stack<String> pilha_rotulos = new Stack<>();
    /// Contador para criação de novos rótulos
    private int contadorRotulos = 1;

    // #endregion

    // #region declaração de variavel

    /// Guarda o tipo da variavel que vai ser declarada
    private String tipo = "";
    /// Lista de identificadores que vão ser declarados
    private List<String> lista_identificadores = new ArrayList<>();
    /// Mapa de variaveis que foram declaradas e seu tipo
    private Map<String, String> tabela_simbolos = new HashMap<>();
    /// Controle de variáveis inicializadas
    private Set<String> variaveis_inicializadas = new HashSet<>();

    // #endregion

    /// Cria um novo rótulo único, para utilizar no desvio de condições e laços
    private String novoRotulo() {
        return "L" + (contadorRotulos++);
    }

    public String getCodigo() {
        return codigo_objeto;
    }

    public void executeAction(int action, Token token) throws SemanticError
    {
        switch (action) {
            case 100 -> acao100();
            case 101 -> acao101();
            case 102 -> acao102();
            case 103 -> acao103(token);
            case 104 -> acao104(token);
            case 105 -> acao105(token);
            case 106 -> acao106(token);
            case 107 -> acao107(token);
            case 108 -> acao108(token);
            case 109 -> acao109(token);
            case 110 -> acao110();
            case 111 -> acao111(token);
            case 112 -> acao112(token);
            case 113 -> acao113(token);
            case 114 -> acao114(token);
            case 115 -> acao115();
            case 116 -> acao116();
            case 117 -> acao117();
            case 118 -> acao118();
            case 119 -> acao119(token);
            case 120 -> acao120(token);
            case 121 -> acao121(token);
            case 122 -> acao122(token);
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
    }

    /// comando para iniciar projeto
    private void acao100() {
        codigo_objeto = codigo_objeto.concat(".assembly extern mscorlib {}\n" +
                                ".assembly _programa{}\n" +
                                ".module _programa.exe\n" +
                                "\n" +
                                ".class public _unica{\n" +
                                "\t.method static public void _principal(){\n" +
                                "\t.entrypoint\n\n");
    }

    /// comando para finalizar projeto
    private void acao101() {
        codigo_objeto = codigo_objeto.concat("\nret\n"+
                                "}\n" +
                                "}\n");
    }

    /// comando para escrever o que está na pilha
    private void acao102() {
        String tipo = pilha_tipos.pop();

        if (tipo.equals(ITipo.INT))
            codigo_objeto = codigo_objeto.concat("conv.i8\n");

        codigo_objeto = codigo_objeto.concat("call void [mscorlib]System.Console::Write("+ tipo +")\n");
    }

    /// comando para empilhar tipo int64
    private void acao103(Token token) {
        pilha_tipos.push(ITipo.INT);
        codigo_objeto = codigo_objeto.concat("ldc.i8 " + token.getLexeme() + "\n");
        codigo_objeto = codigo_objeto.concat("conv.r8\n");
    }

    /// comando para empilhar tipo float64
    private void acao104(Token token) {
        pilha_tipos.push(ITipo.FLOAT);
        codigo_objeto = codigo_objeto.concat("ldc.r8 " + token.getLexeme() + "\n");
    }

    /// comando para empilhar tipo string
    private void acao105(Token token) {
        pilha_tipos.push(ITipo.STRING);
        String lexeme = token.getLexeme();

        // Se já vem com aspas remove-as
        if (lexeme.startsWith("\"") && lexeme.endsWith("\""))
            lexeme = lexeme.substring(1, lexeme.length() - 1);

        codigo_objeto = codigo_objeto.concat("ldstr \"" + lexeme + "\"\n");
    }

    /// comando para somar dois números
    private void acao106(Token token) throws SemanticError {
        String t2 = pilha_tipos.pop();
        String t1 = pilha_tipos.pop();

        if (t1.equals(ITipo.STRING) || t2.equals(ITipo.STRING)) {
            throw new SemanticError(
                "operação de soma inválida entre tipos",
                token.getPosition()
            );
        }
        else if (t1.equals(ITipo.INT) && t2.equals(ITipo.INT))
            pilha_tipos.push(ITipo.INT);
        else
            pilha_tipos.push(ITipo.FLOAT);

        codigo_objeto = codigo_objeto.concat("add\n");
    }

    /// comando para subtrair dois números
    private void acao107(Token token) throws SemanticError {
        String t2 = pilha_tipos.pop();
        String t1 = pilha_tipos.pop();

        if (t1.equals(ITipo.STRING) || t2.equals(ITipo.STRING)) {
            throw new SemanticError(
                "operação de subtração inválida entre tipos",
                token.getPosition()
            );
        }
        else if (t1.equals(ITipo.INT) && t2.equals(ITipo.INT))
            pilha_tipos.push(ITipo.INT);
        else
            pilha_tipos.push(ITipo.FLOAT);

        codigo_objeto = codigo_objeto.concat("sub\n");
    }

    /// comando para multiplicar dois números
    private void acao108(Token token) throws SemanticError {
        String t2 = pilha_tipos.pop();
        String t1 = pilha_tipos.pop();

        if (t1.equals(ITipo.STRING) || t2.equals(ITipo.STRING)) {
            throw new SemanticError(
                "operação de multiplicação inválida entre tipos",
                token.getPosition()
            );
        }
        if (t1.equals(ITipo.INT) && t2.equals(ITipo.INT))
            pilha_tipos.push(ITipo.INT);
        else
            pilha_tipos.push(ITipo.FLOAT);

        codigo_objeto = codigo_objeto.concat("mul\n");
    }

    /// comando para dividir dois números
    private void acao109(Token token) throws SemanticError {
        String t1 = pilha_tipos.pop();
        String t2 = pilha_tipos.pop();

        if (t1.equals(ITipo.STRING) || t2.equals(ITipo.STRING)) {
            throw new SemanticError(
                "operação de subtração inválida entre tipos",
                token.getPosition()
            );
        }

        pilha_tipos.push(ITipo.FLOAT);

        codigo_objeto = codigo_objeto.concat("div\n");
    }

    /// comando para transformar número em negativo no topo da pilha
    private void acao110() {
        codigo_objeto = codigo_objeto.concat("neg\n");
    }

    /// comando para guardar o operador relacional
    private void acao111(Token token) {
        operador_relacional = token.getLexeme();
    }

    /// comando para operações relacionais
    private void acao112(Token token) throws SemanticError {
        String t1 = pilha_tipos.pop();
        String t2 = pilha_tipos.pop();

        if (t1.equals(ITipo.STRING) && !t2.equals(ITipo.STRING) || !t1.equals(ITipo.STRING) && t2.equals(ITipo.STRING)) {
            throw new SemanticError(
                "tipos incompatíveis em operação relacional",
                token.getPosition()
            );
        }

        pilha_tipos.push(ITipo.BOOL);

        switch (operador_relacional) {
            case "==" -> codigo_objeto = codigo_objeto.concat("ceq\n");

            case "~=" -> {
                codigo_objeto = codigo_objeto.concat("ceq\n");
                codigo_objeto = codigo_objeto.concat("ldc.i4.0\n");
                codigo_objeto = codigo_objeto.concat("ceq\n");
            }

            case "<" -> codigo_objeto = codigo_objeto.concat("clt\n");

            case ">" -> codigo_objeto = codigo_objeto.concat("cgt\n");

            default -> {}
        }
    }

    /// comando para fazer "and"
    private void acao113(Token token) throws SemanticError {
        String t1 = pilha_tipos.pop();
        String t2 = pilha_tipos.pop();

        if (!t1.equals(ITipo.BOOL) || !t2.equals(ITipo.BOOL)) {
            throw new SemanticError(
                "tipos incompatíveis em operação lógica",
                token.getPosition()
            );
        }

        pilha_tipos.push(ITipo.BOOL);

        codigo_objeto = codigo_objeto.concat("and\n");
    }

    /// comando para fazer "or"
    private void acao114(Token token) throws SemanticError {
        String t1 = pilha_tipos.pop();
        String t2 = pilha_tipos.pop();

        if (!t1.equals(ITipo.BOOL) || !t2.equals(ITipo.BOOL)) {
            throw new SemanticError(
                "tipos incompatíveis em operação lógica",
                token.getPosition()
            );
        }

        pilha_tipos.push(ITipo.BOOL);

        codigo_objeto = codigo_objeto.concat("or\n");
    }

    /// comando para empilhar tipo booleano true
    private void acao115() {
        pilha_tipos.push(ITipo.BOOL);
        codigo_objeto = codigo_objeto.concat("ldc.i4.1\n");
    }
    
    /// comando para empilhar tipo booleano false
    private void acao116() {
        pilha_tipos.push(ITipo.BOOL);
        codigo_objeto = codigo_objeto.concat("ldc.i4.0\n");
    }
    
    /// comando para fazer "not"
    private void acao117() {
        codigo_objeto = codigo_objeto.concat("ldc.i4.1\n");
        codigo_objeto = codigo_objeto.concat("xor\n");
    }

    /// comando para escrever quebra de linha na saída padrão
    private void acao118() {
        codigo_objeto = codigo_objeto.concat("ldstr \"\\n\"\n");
        codigo_objeto = codigo_objeto.concat("call void [mscorlib]System.Console::Write(string)\n");
    }

    /// comando para adicionar ao locals
    private void acao119(Token token) throws SemanticError {
        if (lista_identificadores.size() > 1) {
            // Gera o código objeto de declaração (.locals) para múltiplas variáveis
            StringBuilder declaracoes = new StringBuilder(".locals (");
            
            for (int i = 0; i < lista_identificadores.size(); i++) {
                String id = lista_identificadores.get(i);

                if (tabela_simbolos.containsKey(id)) {
                    throw new SemanticError(
                        "variável \"" + id + "\" já foi declarada",
                        token.getPosition()
                    );
                }
                
                // Insere na tabela de símbolos conforme tipo declarado
                tabela_simbolos.put(id, tipo);
                
                declaracoes.append(tipo).append(" ").append(id);
                
                if (i < lista_identificadores.size() - 1) {
                    declaracoes.append(",");
                }
            }
            
            declaracoes.append(")\n");
            codigo_objeto = codigo_objeto.concat(declaracoes.toString());
        } else {
            String id = lista_identificadores.get(0);

            if (tabela_simbolos.containsKey(id)) {
                throw new SemanticError(
                    "variável \"" + id + "\" já foi declarada",
                    token.getPosition()
                );
            }

            // Insere na tabela de símbolos conforme tipo declarado
            tabela_simbolos.put(id, tipo);

            // Gera o código objeto de declaração (.locals) para uma variável
            codigo_objeto = codigo_objeto.concat(".locals (" + tipo + " " + id + ")\n");
        }

        // Limpa a lista de ids usados na declaração
        lista_identificadores.clear();
    }

    /// comando para pegar qual o tipo da variavel
    private void acao120(Token token) {
        String tipoFonte = token.getLexeme();

        switch (tipoFonte) {
            case "int" -> tipo = ITipo.INT;
            case "float" -> tipo = ITipo.FLOAT;
            case "string" -> tipo = ITipo.STRING;
            case "bool" -> tipo = ITipo.BOOL;
            default -> {}
        }
    }

    /// comando para adicionar na lista de IDs
    private void acao121(Token token) {
        lista_identificadores.add(token.getLexeme());
    }

    /// comando para atribuição
    private void acao122(Token token) throws SemanticError {
        // Recupera o id onde vai armazenar
        String id = lista_identificadores.get(0);

        // NOVO: Verifica se a variável foi declarada
        if (!tabela_simbolos.containsKey(id)) {
            throw new SemanticError(
                "variável \"" + id + "\" não foi declarada",
                token.getPosition()
            );
        }
        // Remove o tipo avaliado pela expressão
        String tipoExpressao = pilha_tipos.pop();

        if (!tabela_simbolos.get(id).equals(tipoExpressao)) {
            throw new SemanticError(
                "atribuição inválida entre tipos",
                token.getPosition()
            );
        }

        // Se expressao for int64 → converter antes de armazenar
        if (tipoExpressao.equals(ITipo.INT)) {
            codigo_objeto = codigo_objeto.concat("conv.i8\n");
        }

        // Gera código IL da atribuição
        codigo_objeto = codigo_objeto.concat("stloc " + id + "\n");

        // Marca a variável como inicializada
        variaveis_inicializadas.add(id);

        // Limpa lista
        lista_identificadores.clear();
    }

    /// comando para leitura de entrada
    private void acao123(Token token) throws SemanticError {
        String id = token.getLexeme();

        // Verifica tipo do id
        String tipoId = tabela_simbolos.get(id);

        // Verifica se a variável foi declarada
        if (tipoId == null) {
            throw new SemanticError(
                "variável \"" + id + "\" não foi declarada",
                token.getPosition()
            );
        }

        // Não pode ler bool
        if (tipoId.equals(ITipo.BOOL)) {
            throw new SemanticError(
                id + " inválido para comando de entrada", 
                token.getPosition()
            );
        }

        // Gera leitura básica: ReadLine()
        codigo_objeto = codigo_objeto.concat("call string [mscorlib]System.Console::ReadLine()\n");

        // Conversões conforme tipo
        switch (tipoId) {
            case ITipo.INT -> 
                codigo_objeto = codigo_objeto.concat("call int64 [mscorlib]System.Int64::Parse(string)\n");
            case ITipo.FLOAT ->
                codigo_objeto = codigo_objeto.concat("call float64 [mscorlib]System.Double::Parse(string)\n");
            case ITipo.STRING -> { }
            default -> {}
        }

        // Armazena o valor lido
        codigo_objeto = codigo_objeto.concat("stloc " + id + "\n");

        // Marca a variável como inicializada (pois recebeu um valor da entrada)
        variaveis_inicializadas.add(id);
    }

    /// comando para escrever constante string
    private void acao124(Token token) {
        String lexeme = token.getLexeme();
        if (lexeme.startsWith("\"") && lexeme.endsWith("\"")) {
            lexeme = lexeme.substring(1, lexeme.length() - 1);
        }
        
        // Carregar a constante string
        codigo_objeto = codigo_objeto.concat("ldstr \"" + lexeme + "\"\n");

        // Escrever no console
        codigo_objeto = codigo_objeto.concat("call void [mscorlib]System.Console::Write(string)\n");
    }

    /// comando if - verificação de expressão
    private void acao125(Token token) throws SemanticError {
        // Desempilha o tipo da expressão
        String tipoExpressao = pilha_tipos.pop();

        // Verifica se a expressão é do tipo bool
        if (!tipoExpressao.equals(ITipo.BOOL)) {
            throw new SemanticError(
                "expressão incompatível em comando de seleção",
                token.getPosition()
            );
        }

        // Cria um novo rótulo
        String rotulo = novoRotulo();

        // Gera código para desviar caso a expressão seja falsa
        codigo_objeto = codigo_objeto.concat("brfalse " + rotulo + "\n");

        // Empilha o rótulo para uso posterior na ação #126 / #127
        pilha_rotulos.push(rotulo);
    }

    /// comando if/else - fim do bloco
    private void acao126() {
        // Recupera o rótulo que marca o fim do if/else
        String rotulo = pilha_rotulos.pop();

        // Rotula a próxima instrução
        codigo_objeto = codigo_objeto.concat(rotulo + ":\n");
    }

    /// comando else - início do bloco
    private void acao127() {
        // Cria rótulo para o fim total do if (após o else)
        String rotulo2 = novoRotulo();

        // Desvio incondicional para depois do if/else
        codigo_objeto = codigo_objeto.concat("br " + rotulo2 + "\n");

        // Recupera o rótulo do false (criado na #125)
        String rotulo1 = pilha_rotulos.pop();

        // Marca o início da parte do ELSE
        codigo_objeto = codigo_objeto.concat(rotulo1 + ":\n");

        // Empilha o rótulo2 para resolução posterior (#126)
        pilha_rotulos.push(rotulo2);
    }

    /// comando while - início do laço
    private void acao128() {
        // Cria rótulo para o início do laço
        String rotulo = novoRotulo();

        // Marca o início do laço
        codigo_objeto = codigo_objeto.concat(rotulo + ":\n");

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
        
        // Se true → volta para inicio do while
        codigo_objeto = codigo_objeto.concat("brtrue " + rotulo + "\n");
    }

    /// comando para carregar identificador em expressão
    private void acao130(Token token) throws SemanticError {
        String id = token.getLexeme();

        // NOVO: Verifica se a variável foi declarada
        if (!tabela_simbolos.containsKey(id)) {
            throw new SemanticError(
                "variável \"" + id + "\" não foi declarada",
                token.getPosition()
            );
        }

        // NOVO: Verifica se a variável foi inicializada
        if (!variaveis_inicializadas.contains(id)) {
            throw new SemanticError(
                "variável \"" + id + "\" não foi inicializada",
                token.getPosition()
            );
        }

        // Recupera o tipo do identificador na tabela de símbolos
        String tipoId = tabela_simbolos.get(id);

        // Empilha o tipo correspondente
        pilha_tipos.push(tipoId);

        // Carrega o valor do identificador
        codigo_objeto = codigo_objeto.concat("ldloc " + id + "\n");

        // Se o id for int64 → converter para float64
        if (tipoId.equals(ITipo.INT)) {
            codigo_objeto = codigo_objeto.concat("conv.r8\n");
        }
    }
}