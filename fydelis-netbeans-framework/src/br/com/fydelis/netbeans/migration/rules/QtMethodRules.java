package br.com.fydelis.netbeans.migration.rules;

import br.com.fydelis.netbeans.migration.Qt6MigrationEngine.Change;

import java.util.List;
import java.util.regex.*;

public class QtMethodRules {

    // Formato: {padrão_de_busca, substituição, observação}
    private static final String[][] REGRAS = {
        // === QString ===
        {"QString::split\\s*\\(\\s*([^,]+),\\s*Qt::SkipEmptyParts\\)",
         "Qt::SplitBehaviorFlags::SkipEmptyParts",
         "SplitBehavior mudou de enum em Qt6"},

        {"QString::midRef\\s*\\(", "QString::mid(",
         "midRef() removido — use mid() que agora é eficiente"},

        {"QString::leftRef\\s*\\(", "QString::left(",
         "leftRef() removido — use left()"},

        {"QString::rightRef\\s*\\(", "QString::right(",
         "rightRef() removido — use right()"},

        {"QString::section\\s*\\(([^,]+),\\s*([^,]+),\\s*([^,]+),\\s*QString::SectionDefault\\)",
         "section($1, $2, $3)",
         "SectionDefault removido, é o padrão agora"},

        // === QFont ===
        {"QFont::setPixelSize\\s*\\(", "setPixelSize — assinatura mantida, verifique valor",
         "Atenção: lógica interna mudou em Qt6"},

        {"QFont::pixelSize\\s*\\(", "pixelSize",
         "Retorno agora int consistente, sem -1 lógico"},

        // === QFile/QFileInfo ===
        {"QFile::symLinkTarget\\s*\\(", "QFile::symLinkTarget — agora método estático",
         "Mudou de instância para estático em Qt6"},

        {"QFileInfo::symLinkTarget\\s*\\(", "QFileInfo::symLinkTarget — método removido, use QFile",
         "Use QFile::symLinkTarget()"},

        // === QWidget ===
        {"QWidget::setVisible\\s*\\(\\s*!isVisible\\(\\)\\s*\\)", "setVisible(!isVisible())",
         "Sem alteração, mas foco/exibição reescrito internamente"},

        {"QWidget::paintEngine\\s*\\(", "⚠️ paintEngine() é obsoleto — reescreva paintEvent",
         "Não recomendado em Qt6"},

        // === QDateTime/QDate/QTime ===
        {"QDateTime::toMSecsSinceEpoch\\s*\\(", "toMSecsSinceEpoch",
         "Disponível, mas use toSecsSinceEpoch() se possível"},

        {"QDateTime::fromMSecsSinceEpoch\\s*\\(", "fromMSecsSinceEpoch",
         "Agora método estático preferencial"},

        {"QDate::currentDate\\s*\\(\\).addDays", "QDate::currentDate().addDays",
         "Sem alteração, mas fuso horário padrão é UTC em algumas APIs"},

        // === Qt Global ===
        {"qrand\\s*\\(\\)", "QRandomGenerator::global()->generate()",
         "qrand/qsrnd removidos — use QRandomGenerator"},

        {"qsrand\\s*\\(", "Remova a semente — QRandomGenerator é auto-semeado",
         "qsrand() não existe mais"},

        {"qSort\\s*\\(", "std::sort",
         "qSort removido — use algoritmos da STL padrão"},

        {"qGreater\\s*\\(", "std::greater",
         "Use comparadores padrão C++"},

        {"qLess\\s*\\(", "std::less",
         "Use comparadores padrão C++"},

        // === QVariant ===
        {"QVariant::toInt\\s*\\(&([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\)",
         "bool ok; $1 = value.toInt(&ok);",
         "Assinatura mantida, mas comportamento de erro mudou"},

        {"QVariant::value<", "value<",
         "Verifique se includes estão corretos — necessita #include <QVariant>"},

        // === QCoreApplication ===
        {"QCoreApplication::translate\\s*\\(", "tr()",
         "Preferir tr() — translate assinatura mantida mas comportamento de codificação mudou"}
    };

    public static String aplicar(String texto, String arquivo, List<Change> listaAlteracoes) {
        String resultado = texto;

        for (String[] regra : REGRAS) {
            String padraoBusca = regra[0];
            String substituto = regra[1];

            Pattern padrao = Pattern.compile(padraoBusca);
            Matcher matcher = padrao.matcher(resultado);
            StringBuffer saida = new StringBuffer();

            while (matcher.find()) {
                Change alteracao = new Change();
                alteracao.arquivo = arquivo;
                alteracao.linha = contarLinha(texto, matcher.start());
                alteracao.de = matcher.group();
                alteracao.para = substituto;
                alteracao.tipo = "METODO";

                listaAlteracoes.add(alteracao);
                matcher.appendReplacement(saida, substituto.replace("$", "\\$"));
            }

            matcher.appendTail(saida);
            resultado = saida.toString();
        }

        return resultado;
    }

    private static int contarLinha(String texto, int posicao) {
        return (int) texto.substring(0, posicao)
                .chars()
                .filter(c -> c == '\n')
                .count() + 1;
    }
}
