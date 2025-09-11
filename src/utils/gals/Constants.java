package utils.gals;

import java.util.HashMap;
import java.util.Map;

public interface Constants extends ScannerConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_identificador = 2;
    int t_cint = 3;
    int t_cfloat = 4;
    int t_cstring = 5;
    int t_pr_add = 6;
    int t_pr_and = 7;
    int t_pr_begin = 8;
    int t_pr_bool = 9;
    int t_pr_count = 10;
    int t_pr_delete = 11;
    int t_pr_do = 12;
    int t_pr_elementof = 13;
    int t_pr_else = 14;
    int t_pr_end = 15;
    int t_pr_false = 16;
    int t_pr_float = 17;
    int t_pr_if = 18;
    int t_pr_int = 19;
    int t_pr_list = 20;
    int t_pr_not = 21;
    int t_pr_or = 22;
    int t_pr_print = 23;
    int t_pr_read = 24;
    int t_pr_size = 25;
    int t_pr_string = 26;
    int t_pr_true = 27;
    int t_pr_until = 28;
    int t_TOKEN_29 = 29; //"+"
    int t_TOKEN_30 = 30; //"-"
    int t_TOKEN_31 = 31; //"*"
    int t_TOKEN_32 = 32; //"/"
    int t_TOKEN_33 = 33; //"=="
    int t_TOKEN_34 = 34; //"~="
    int t_TOKEN_35 = 35; //"<"
    int t_TOKEN_36 = 36; //">"
    int t_TOKEN_37 = 37; //"="
    int t_TOKEN_38 = 38; //"<-"
    int t_TOKEN_39 = 39; //"("
    int t_TOKEN_40 = 40; //")"
    int t_TOKEN_41 = 41; //";"

    Map<Integer, String> TOKEN_NAMES = new HashMap<>() {{
        put(EPSILON, "EPSILON");
        put(DOLLAR, "DOLLAR");
        put(t_identificador, "identificador");
        put(t_cint, "constante_int");
        put(t_cfloat, "constante_float");
        put(t_cstring, "constante_string");
        put(t_pr_add, "palavra reservada");
        put(t_pr_and, "palavra reservada");
        put(t_pr_begin, "palavra reservada");
        put(t_pr_bool, "palavra reservada");
        put(t_pr_count, "palavra reservada");
        put(t_pr_delete, "palavra reservada");
        put(t_pr_do, "palavra reservada");
        put(t_pr_elementof, "palavra reservada");
        put(t_pr_else, "palavra reservada");
        put(t_pr_end, "palavra reservada");
        put(t_pr_false, "palavra reservada");
        put(t_pr_float, "palavra reservada");
        put(t_pr_if, "palavra reservada");
        put(t_pr_int, "palavra reservada");
        put(t_pr_list, "palavra reservada");
        put(t_pr_not, "palavra reservada");
        put(t_pr_or, "palavra reservada");
        put(t_pr_print, "palavra reservada");
        put(t_pr_read, "palavra reservada");
        put(t_pr_size, "palavra reservada");
        put(t_pr_string, "palavra reservada");
        put(t_pr_true, "palavra reservada");
        put(t_pr_until, "palavra reservada");
        put(t_TOKEN_29, "símbolo especial");
        put(t_TOKEN_30, "símbolo especial");
        put(t_TOKEN_31, "símbolo especial");
        put(t_TOKEN_32, "símbolo especial");
        put(t_TOKEN_33, "símbolo especial");
        put(t_TOKEN_34, "símbolo especial");
        put(t_TOKEN_35, "símbolo especial");
        put(t_TOKEN_36, "símbolo especial");
        put(t_TOKEN_37, "símbolo especial");
        put(t_TOKEN_38, "símbolo especial");
        put(t_TOKEN_39, "símbolo especial");
        put(t_TOKEN_40, "símbolo especial");
        put(t_TOKEN_41, "símbolo especial");
    }};
}
