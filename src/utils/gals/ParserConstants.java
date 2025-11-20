package utils.gals;

public interface ParserConstants
{
    int START_SYMBOL = 43;

    int FIRST_NON_TERMINAL    = 43;
    int FIRST_SEMANTIC_ACTION = 84;

    int[][] PARSER_TABLE =
    {
        { -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1,  1, -1, -1, -1, -1, -1, -1,  1, -1, -1,  1, -1, -1, -1, -1,  1,  1,  1,  1, -1, -1,  1,  1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1,  5, -1, -1, -1, -1, -1, -1,  4, -1, -1,  5, -1, -1, -1, -1,  4,  5,  4,  4, -1, -1,  5,  5, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, -1, -1, -1, -1, -1,  9, -1, -1, -1, -1,  8,  7, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, 11, -1, 11, 11, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1, -1, -1, 19, -1, 19, 20, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1, 17, -1, 16, -1, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 48, 48, 48, 48, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 48, -1, -1, -1, -1, 48, -1, -1, -1, -1, -1, 48, -1, 48, 48, -1, -1, -1, -1, -1, -1, -1, -1, 48, -1, -1, -1 },
        { -1, 29, 29, 29, 29, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 29, -1, -1, -1, -1, 29, -1, -1, -1, -1, -1, 29, -1, 29, 29, -1, -1, -1, -1, -1, -1, -1, -1, 29, -1, -1, -1 },
        { -1, 30, 30, 30, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, -1, 30, -1, -1, -1, -1, -1, 30, -1, 30, 30, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1 },
        { -1, 32, -1, -1, 32, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 36, -1, -1, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 38, 38, 38, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, 38, -1, 38, 38, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1 },
        { -1, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, 42, -1, -1, -1, -1, -1, 42, -1, -1, -1, -1, 42, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 45, 46, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 49, -1, -1, -1, -1, 50, -1, -1, -1, -1, 49, -1, -1, -1, -1, -1, 49, -1, -1, -1, 51, 49, 49, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 49, 49, 49 },
        { -1, 52, 52, 52, 52, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 54, -1, -1, -1, -1, 55, -1, -1, -1, -1, -1, 53, -1, 52, 52, -1, -1, -1, -1, -1, -1, -1, -1, 52, -1, -1, -1 },
        { -1, 56, 56, 56, 56, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 56, 56, -1, -1, -1, -1, -1, -1, -1, -1, 56, -1, -1, -1 },
        { -1, 57, -1, -1, -1, -1, 57, -1, -1, -1, -1, 57, -1, -1, -1, -1, -1, 57, -1, -1, -1, 57, 57, 57, -1, -1, -1, -1, -1, -1, -1, -1, 58, 58, 58, 58, -1, -1, -1, 57, 57, 57 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 59, 60, 61, 62, -1, -1, -1, -1, -1, -1 },
        { -1, 63, 63, 63, 63, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 63, 63, -1, -1, -1, -1, -1, -1, -1, -1, 63, -1, -1, -1 },
        { -1, 64, -1, -1, -1, -1, 64, -1, -1, -1, -1, 64, -1, -1, -1, -1, -1, 64, -1, -1, -1, 64, 64, 64, -1, -1, -1, -1, 65, 66, -1, -1, 64, 64, 64, 64, -1, -1, -1, 64, 64, 64 },
        { -1, 67, 67, 67, 67, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 67, 67, -1, -1, -1, -1, -1, -1, -1, -1, 67, -1, -1, -1 },
        { -1, 68, -1, -1, -1, -1, 68, -1, -1, -1, -1, 68, -1, -1, -1, -1, -1, 68, -1, -1, -1, 68, 68, 68, -1, -1, -1, -1, 68, 68, 69, 70, 68, 68, 68, 68, -1, -1, -1, 68, 68, 68 },
        { -1, 71, 72, 73, 74, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 76, 77, -1, -1, -1, -1, -1, -1, -1, -1, 75, -1, -1, -1 },
        { -1, 78, -1, -1, -1, -1, 78, -1, -1, 79, -1, 78, 81, -1, -1, -1, -1, 78, -1, -1, -1, 78, 78, 78, 80, -1, -1, -1, 78, 78, 78, 78, 78, 78, 78, 78, -1, -1, -1, 78, 78, 78 },
        { -1,  3, -1, -1, -1, -1, -1, -1,  3, -1, -1,  3, -1, -1,  2, -1,  3,  3,  3,  3, -1, -1,  3,  3, -1,  3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, 23, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 24, -1, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 26, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 27, -1, -1, -1, -1, 27, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 28, 28, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, 14 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, 34 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 39, -1, 40 },
        { -1, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, -1, 43, 43, -1, -1, 44, -1, -1, -1, -1, 44, 44, -1, -1, -1, 43, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
    };

    int[][] PRODUCTIONS = 
    {
        { 184,   8,  44,  15, 185 },
        {  45,  41,  75 },
        {   0 },
        {  44 },
        {  47 },
        {  46 },
        {  78 },
        {  48 },
        {  49 },
        {  50 },
        {  51 },
        {  52,  53, 203 },
        {   2, 205,  80 },
        {   0 },
        {  42,  53 },
        {   9, 204 },
        {  19, 204 },
        {  17, 204 },
        {  26, 204 },
        {  54 },
        {  55 },
        {  20,  39,  54,  42,   3,  40 },
        {  37,  56, 206 },
        {  38,  56, 206 },
        {   6,  39,  57,  42,  58,  40 },
        {  11,  39,  58,  40 },
        {   2, 205,  79 },
        {  77 },
        {  76 },
        {  56 },
        {  56 },
        {  24,  39,  59,  40 },
        {  60,   2, 207,  81 },
        {   0 },
        {  42,  59 },
        {   5, 208,  42 },
        {   0 },
        {  23,  39,  61,  40, 202 },
        {  56, 186,  82 },
        {   0 },
        {  42,  61 },
        {  18,  56, 209,  62,  63,  15, 210 },
        {  46,  41,  83 },
        {   0 },
        {  62 },
        {  14, 211,  62 },
        {   0 },
        {  12, 212,  62,  28,  56, 213 },
        {  65,  64 },
        {   0 },
        {   7,  65, 197,  64 },
        {  22,  65, 198,  64 },
        {  66 },
        {  27, 199 },
        {  16, 200 },
        {  21,  65, 201 },
        {  69,  67 },
        {   0 },
        {  68, 195,  69, 196 },
        {  33 },
        {  34 },
        {  35 },
        {  36 },
        {  71,  70 },
        {   0 },
        {  29,  71, 190,  70 },
        {  30,  71, 191,  70 },
        {  73,  72 },
        {   0 },
        {  31,  73, 192,  72 },
        {  32,  73, 193,  72 },
        {   2, 214,  74 },
        {   3, 187 },
        {   4, 188 },
        {   5, 189 },
        {  39,  56,  40 },
        {  29,  73 },
        {  30,  73, 194 },
        {   0 },
        {  10 },
        {  25 },
        {  13,  39,  56,  40 }
    };

    String[] PARSER_ERROR =
    {
        "", // Era esperado fim de programa
        "esperado EOF", // Era esperado fim de programa
        "esperado identificador", // Era esperado identificador
        "esperado constante_int", // Era esperado cint
        "esperado constante_float", // Era esperado cfloat
        "esperado constante_string", // Era esperado cstring
        "esperado add", // Era esperado pr_add
        "esperado and", // Era esperado pr_and
        "esperado begin", // Era esperado pr_begin
        "esperado bool", // Era esperado pr_bool
        "esperado count", // Era esperado pr_count
        "esperado delete", // Era esperado pr_delete
        "esperado do", // Era esperado pr_do
        "esperado elementOf", // Era esperado pr_elementof
        "esperado else", // Era esperado pr_else
        "esperado end", // Era esperado pr_end
        "esperado false", // Era esperado pr_false
        "esperado float", // Era esperado pr_float
        "esperado if", // Era esperado pr_if
        "esperado int", // Era esperado pr_int
        "esperado list", // Era esperado pr_list
        "esperado not", // Era esperado pr_not
        "esperado or", // Era esperado pr_or
        "esperado print", // Era esperado pr_print
        "esperado read", // Era esperado pr_read
        "esperado size", // Era esperado pr_size
        "esperado string", // Era esperado pr_string
        "esperado true", // Era esperado pr_true
        "esperado until", // Era esperado pr_until
        "esperado +", // Era esperado +
        "esperado -", // Era esperado -
        "esperado *", // Era esperado *
        "esperado /", // Era esperado /
        "esperado ==", // Era esperado ==
        "esperado ~=", // Era esperado ~=
        "esperado <", // Era esperado <
        "esperado >", // Era esperado >
        "esperado =", // Era esperado =
        "esperado <-", // Era esperado <-
        "esperado (", // Era esperado (
        "esperado )", // Era esperado )
        "esperado ;", // Era esperado ;
        "esperado ,", // Era esperado ,
        "esperado begin", // <programa> inválido
        "esperado identificador tipo if do print read", // <lista_instrucoes> inválido
        "esperado identificador tipo do print if read", // <instrucao> inválido
        "esperado identificador do if print read", // <comando> inválido
        "esperado tipo", // <dec_variavel> inválido
        "esperado read", // <comando_entrada_dados> inválido
        "esperado print", // <comando_saida_dados> inválido
        "esperado if", // <comando_selecao> inválido
        "esperado do", // <comando_repeticao> inválido
        "esperado tipo", // <tipo> inválido
        "esperado identificador", // <lista_id> inválido
        "esperado tipo primitivo", // <tipo_simples> inválido
        "esperado tipo", // <lista_tipo> inválido
        "esperado expressão", // <expressao> inválido
        "esperado identificador constante_int constante_float constante_string false not true + -(", // <elemento> inválido
        "esperado identificador constante_int constante_float constante_string false not true + - (", // <posicao> inválido
        "esperado identificador constante_string", // <lista_entrada> inválido
        "esperado identificador constante_string", // <string_read> inválido
        "esperado expressão", // <lista_expressoes> inválido
        "esperado identificador do if print read", // <lista_comandos> inválido
        "esperado else end", // <else> inválido
        "esperado expressão", // <expressao_> inválido
        "esperado expressão", // <valor> inválido
        "esperado expressão", // <relacional> inválido
        "esperado expressão", // <relacional_> inválido
        "esperado == ~= < >", // <operador_relacional> inválido
        "esperado expressão", // <aritmetica> inválido
        "esperado expressão", // <aritmetica_> inválido
        "esperado expressão", // <termo> inválido
        "esperado expressão", // <termo_> inválido
        "esperado expressão", // <fator> inválido
        "esperado expressão", // <fator_> inválido
        "esperado identificador tipo do end if print read", // <lista_instrucoes_> inválido
        "esperado = <-", // <comando_atribuicao_> inválido
        "esperado add delete", // <comando_manipulacao_lista_> inválido
        "esperado identificador", // <comando_intermediario_atr_mani> inválido
        "esperado add delete = <-", // <comando_intermediario_atr_mani_> inválido
        "esperado ; ,", // <lista_id_> inválido
        "esperado ) ,", // <lista_entrada_> inválido
        "esperado expressão", // <lista_expressoes_> inválido
        "esperado identificador do else end if print read until" // <lista_comandos_> inválido
    };
}
