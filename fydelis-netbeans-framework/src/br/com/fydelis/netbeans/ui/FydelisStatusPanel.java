package br.com.fydelis.netbeans.ui;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

import javax.swing.*;
import java.awt.*;

@TopComponent.Description(
    preferredID = "FydelisStatusPanel",
    iconBase = "br/com/fydelis/netbeans/resources/icon_status.png",
    persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@ActionID(category = "Window", id = "br.com.fydelis.netbeans.ui.FydelisStatusPanel")
@ActionReference(path = "Menu/Window")
@Messages({
    "CTL_FydelisPanel=Fydelis — Status do Ambiente",
    "LBL_Pronto=✅ Fydelis Framework ativo | Qt6 Pronto"
})
public final class FydelisStatusPanel extends TopComponent {

    public FydelisStatusPanel() {
        setName(Bundle.CTL_FydelisPanel());
        setLayout(new BorderLayout());
        
        JPanel painel = new JPanel(new GridLayout(3, 1, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        painel.add(new JLabel(Bundle.LBL_Pronto()));
        painel.add(new JLabel("📂 Projeto: Nenhum aberto"));
        painel.add(new JLabel("🔧 Validador: Ativo"));
        
        add(painel, BorderLayout.NORTH);
    }
}