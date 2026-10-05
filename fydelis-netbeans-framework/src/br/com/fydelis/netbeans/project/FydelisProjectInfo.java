package br.com.fydelis.netbeans.project;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class FydelisProjectInfo implements ProjectInformation {

    private final FydelisQtProject projeto;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public FydelisProjectInfo(FydelisQtProject p) {
        this.projeto = p;
    }

    @Override
    public String getName() {
        return projeto.getProjectDirectory().getName();
    }

    @Override
    public String getDisplayName() {
        return "Fydelis Qt — " + getName();
    }

    @Override
    public Icon getIcon() {
        String caminho = "/resources/icon_qt.png";
        java.net.URL url = getClass().getResource(caminho);
        if (url != null) {
            return new ImageIcon(url);
        }
        return new ImageIcon();
    }

    @Override
    public Project getProject() {
        return projeto;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }
}