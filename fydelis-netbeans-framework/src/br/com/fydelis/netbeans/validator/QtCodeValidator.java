package br.com.fydelis.netbeans.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Severity;

public class QtCodeValidator {

    private static final Pattern PADRAO_FUNCOES_RISCO = Pattern.compile(
        "QRegExp|QString::split.*SkipEmptyParts",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern PADRAO_QT5_OBSOLETO = Pattern.compile(
        "Qt::SkipEmptyParts",
        Pattern.CASE_INSENSITIVE
    );

    public List<ErrorDescription> analisarDocumento(Document doc) {
        List<ErrorDescription> avisos = new ArrayList<>();
        try {
            String conteudo = doc.getText(0, doc.getLength());

            adicionarCorrespondencia(avisos, doc, conteudo,
                PADRAO_FUNCOES_RISCO, "Função insegura/obsoleta no Qt", Severity.WARNING);

            adicionarCorrespondencia(avisos, doc, conteudo,
                PADRAO_QT5_OBSOLETO, "Uso de API Qt5 obsoleta", Severity.HINT);

        } catch (BadLocationException e) {
            // ignorado
        }
        return avisos;
    }

    private void adicionarCorrespondencia(List<ErrorDescription> lista, Document doc,
            String texto, Pattern padrao, String mensagem, Severity nivel) {

        Matcher matcher = padrao.matcher(texto);
        while (matcher.find()) {
            try {
                lista.add(ErrorDescriptionFactory.createErrorDescription(
                    nivel,
                    mensagem,
                    doc,
                    doc.createPosition(matcher.start()),
                    doc.createPosition(matcher.end())
                ));
            } catch (BadLocationException e) {
                // posição inválida, ignora
            }
        }
    }
}