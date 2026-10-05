package br.com.fydelis.netbeans.migration.rules;

import br.com.fydelis.netbeans.migration.Qt6MigrationEngine.Change;

import java.util.List;
import java.util.regex.*;

public class QtModuleRules {

    private static final String[][] REGRAS = {
        {"QT\\s*\\+=\\s*widgets\\s*$", "QT += core gui widgets", "Garante presença de core+gui"},
        {"QT\\s*\\+\\=\\s*xml\\b", "QT += core", "QtXml fundiu no Qt6 → use QtCore"},
        {"QT\\s*\\+\\=\\s*script\\b", "QT += qml", "QtScript substituído por QML/JS Engine"},
        {"qtHaveModule\\s*\\(xml\\)", "false /* QtXml removido no Qt6 */", "Módulo obsoleto"}
    };

    public static String aplicar(String texto, String arquivo, List<Change> listaAlteracoes) {
        if (!arquivo.endsWith(".pro") && !arquivo.contains("CMakeLists")) return texto;
        
        String resultado = texto;
        for (String[] regra : REGRAS) {
            Pattern padrao = Pattern.compile(regra[0], Pattern.MULTILINE);
            Matcher matcher = padrao.matcher(resultado);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                Change c = new Change();
                c.arquivo = arquivo;
                c.linha = contarLinha(texto, matcher.start());
                c.de = matcher.group();
                c.para = regra[1];
                c.tipo = "MODULO";
                listaAlteracoes.add(c);
                matcher.appendReplacement(sb, regra[1]);
            }
            matcher.appendTail(sb);
            resultado = sb.toString();
        }
        return resultado;
    }

    private static int contarLinha(String texto, int posicao) {
        return (int) texto.substring(0, posicao).chars().filter(ch -> ch == '\n').sum() + 1;
    }
}
