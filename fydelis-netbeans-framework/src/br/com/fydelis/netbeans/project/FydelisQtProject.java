package br.com.fydelis.netbeans.project;

import br.com.fydelis.netbeans.project.utils.TemplateHelper;
import java.io.IOException;
import java.io.OutputStream;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class FydelisQtProject implements Project {

    private final FileObject pastaRaiz;
    private final ProjectState estado;
    private final InstanceContent conteudo;
    private final Lookup busca;

    public FydelisQtProject(FileObject projectDir, ProjectState state) {
        this.pastaRaiz = projectDir;
        this.estado = state;
        this.conteudo = new InstanceContent();
        this.conteudo.add(new FydelisProjectInfo(this));
        this.busca = new AbstractLookup(conteudo);
    }

    @Override
    public FileObject getProjectDirectory() {
        return pastaRaiz;
    }

    @Override
    public Lookup getLookup() {
        return busca;
    }

    // ✅ SEM @Override — este método NÃO é da interface Project!
    public ProjectInformation getProjectInformation() {
        return getLookup().lookup(ProjectInformation.class);
    }

    public void criarEstruturaPadrao() throws IOException {
        FileObject raiz = getProjectDirectory();

        if (raiz.getFileObject("src") == null) raiz.createFolder("src");
        if (raiz.getFileObject("include") == null) raiz.createFolder("include");
        if (raiz.getFileObject("resources") == null) raiz.createFolder("resources");

        if (raiz.getFileObject("app.pro") == null) {
            try (OutputStream out = raiz.createData("app.pro").getOutputStream()) {
                out.write(TemplateHelper.gerarArquivoPro(getName()).getBytes("UTF-8"));
            }
        }
    }

    public String getName() {
        return getProjectDirectory().getName();
    }

    public ProjectState getState() {
        return estado;
    }
}