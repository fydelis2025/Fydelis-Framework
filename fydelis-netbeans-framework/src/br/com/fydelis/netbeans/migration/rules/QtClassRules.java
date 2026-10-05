package br.com.fydelis.netbeans.migration.rules;

import br.com.fydelis.netbeans.migration.Qt6MigrationEngine.Change;

import java.util.List;
import java.util.regex.*;

public class QtClassRules {

    private static final String[][] REGRAS = {
        {"QRegExp\\b", "QRegularExpression"},
        {"QRegExpValidator\\b", "QRegularExpressionValidator"},
        {"QXmlSimpleReader\\b", "QXmlStreamReader"},
        {"QBuffer::data\\(\\)", "buffer()"},
        {"QString::split\\s*\\(\\s*([^,]+),\\s*Qt::SkipEmptyParts\\)",
         "String.join(\"\\1\", Qt::SkipEmptyParts → Qt::SplitBehaviorFlags::SkipEmptyParts)"}
    };

    public static String aplicar(String texto, String arquivo, List<Change> listaAlteracoes) {
        String resultado = texto;
        for (String[] regra : REGRAS) {
            Pattern padrao = Pattern.compile(regra[0]);
            Matcher matcher = padrao.matcher(resultado);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                Change c = new Change();
                c.arquivo = arquivo;
                c.linha = contarLinha(texto, matcher.start());
                c.de = matcher.group();
                c.para = regra[1];
                c.tipo = "CLASSE";
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
