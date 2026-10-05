package br.com.fydelis.netbeans.migration;

import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Messages({
    "CTL_MigrarQt6=Migrar para Qt6",
    "MSG_Simulacao=Modo simulação — nenhuma alteração foi feita.\n\nRelatório:\n",
    "MSG_Confirmar=Foram detectadas %d alterações.\nAplicar mudanças nos arquivos?",
    "MSG_NenhumAjuste=✅ Nenhum ajuste necessário — código já compatível com Qt6!",
    "MSG_Concluido=✅ Migração concluída!"
})
@ActionID(category = "Fydelis", id = "br.com.fydelis.netbeans.migracaoQt6")
@ActionRegistration(displayName = "#CTL_MigrarQt6")
@ActionReference(path = "Menu/Fydelis", position = 20)
public final class Qt6MigrationWizard extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // ✅ Pega projetos abertos
            Project[] projetos = OpenProjects.getDefault().getOpenProjects();
            if (projetos.length == 0) {
                JOptionPane.showMessageDialog(null, "Abra um projeto primeiro!");
                return;
            }

            Project projetoAtual = projetos[0];
            FileObject raiz = projetoAtual.getProjectDirectory(); // ✅ SEM ERRO!

            // Passo 1: Simulação
            Qt6MigrationEngine motor = new Qt6MigrationEngine(true);
            MigrationReport relatorio = motor.processarProjeto(raiz);

            if (relatorio.getAlteracoes().isEmpty()) {
                JOptionPane.showMessageDialog(null, Bundle.MSG_NenhumAjuste());
                return;
            }

            // Confirmação
            int resp = JOptionPane.showConfirmDialog(null,
                String.format(Bundle.MSG_Confirmar(), relatorio.getAlteracoes().size()),
                Bundle.CTL_MigrarQt6(),
                JOptionPane.YES_NO_CANCEL_OPTION);

            if (resp == JOptionPane.YES_OPTION) {
                motor = new Qt6MigrationEngine(false);
                relatorio = motor.processarProjeto(raiz);
                JOptionPane.showMessageDialog(null, Bundle.MSG_Concluido());
            } else {
                JOptionPane.showMessageDialog(null, Bundle.MSG_Simulacao() + relatorio.gerarTexto());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Erro: " + ex.getMessage(),
                "Fydelis — Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private static final class Bundle {
        static String CTL_MigrarQt6() { return NbBundle.getMessage(Qt6MigrationWizard.class, "CTL_MigrarQt6"); }
        static String MSG_Simulacao() { return NbBundle.getMessage(Qt6MigrationWizard.class, "MSG_Simulacao"); }
        static String MSG_Confirmar() { return NbBundle.getMessage(Qt6MigrationWizard.class, "MSG_Confirmar"); }
        static String MSG_NenhumAjuste() { return NbBundle.getMessage(Qt6MigrationWizard.class, "MSG_NenhumAjuste"); }
        static String MSG_Concluido() { return NbBundle.getMessage(Qt6MigrationWizard.class, "MSG_Concluido"); }
    }
}