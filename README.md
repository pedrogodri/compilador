# Como Executar Arquivo JAR

Este guia explica como executar um arquivo `.jar` (Java Archive) em sistemas Windows, tanto pelo prompt de comando (CMD) quanto por duplo clique.

## Pré-requisitos

Antes de executar o arquivo JAR, certifique-se de que o Java Runtime Environment (JRE) ou Java Development Kit (JDK) esteja instalado na sua última versão no seu sistema operacional.

## Método 1: Executar pelo CMD (Prompt de Comando)

### Comando
```bash
java -jar compilador.jar
```

### Passos Detalhados:

1. **Abrir o Prompt de Comando:**
   - Pressione `Win + R`
   - Digite `cmd` e pressione Enter
   - Ou procure por "Prompt de Comando" no menu Iniciar

2. **Navegar até o diretório do arquivo JAR:**
   ```bash
   cd "C:\caminho\para\seu\arquivo"
   ```

3. **Executar o comando:**
   ```bash
   java -jar meuarquivo.jar
   ```

### Exemplo Prático:
```bash
C:\Users\Usuario> cd "C:\MeusProjetos\MinhaAplicacao"
C:\MeusProjetos\MinhaAplicacao> java -jar compilador.jar
```

## Método 2: Executar por Duplo Clique

Para executar um arquivo JAR com duplo clique, é necessário que o Windows associe arquivos `.jar` ao Java.

### Configuração Automática (Recomendada)

1. **Clique com o botão direito** no arquivo `.jar`
2. Selecione **"Abrir com"**
3. Escolha **"Escolher outro aplicativo"**
4. Procure por **"Java(TM) Platform SE binary"** ou **"javaw.exe"**
   - Geralmente localizado em: `C:\Program Files\Java\jre[versão_atualizada]\bin\javaw.exe`
5. Marque a opção **"Sempre usar este aplicativo para abrir arquivos .jar"**
6. Clique em **OK**

## Solução de Problemas Comuns

### Erro: "java não é reconhecido como comando interno"
- **Causa:** Java não está instalado ou não está no PATH do sistema
- **Solução:** Instale o Java e adicione ao PATH do sistema

### Erro: "A Java Exception has occurred"
- **Causa:** Versão do Java e JDK incompatível
- **Solução:** Instale a versão mais atualizada Java e JDK

## Parâmetros Úteis do Java

| Parâmetro | Descrição | Exemplo |
|-----------|-----------|---------|
| `-jar` | Especifica arquivo JAR | `-jar app.jar` |
| `-cp` ou `-classpath` | Define classpath | `-cp libs/*` |

## Suporte

Se você encontrar problemas:

1. Verifique se o Java está instalado corretamente
2. Verifique se o JDK está instalado corretamente
3. Verifique as permissões de execução do arquivo

## Dicas

✅ Mantenha Java e JDK sempre atualizados
✅ Execute como administrador se houver problemas de permissão
