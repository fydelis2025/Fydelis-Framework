package br.com.fydelis.netbeans.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;

@ActionID(
    category = "Fydelis",
    id = "br.com.fydelis.netbeans.actions.FydelisStatusAction"
)
@ActionRegistration(
    displayName = "Fydelis: Status do Sistema"
)
@ActionReference(path = "Menu/Tools", position = 100)
public class FydelisStatusAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
            "Fydelis Framework ativado — pronto para Qt/C++",
            "Fydelis Framework",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}