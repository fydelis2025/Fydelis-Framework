package br.com.fydelis.netbeans.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

// ❌ REMOVIDO @ProjectTypeRegistration — não existe nesta versão
@ServiceProvider(service = ProjectFactory.class)
public class FydelisQtProjectType implements ProjectFactory {

    @Override
    public boolean isProject(FileObject dir) {
        boolean temProArquivo = false;
        for (FileObject filho : dir.getChildren()) {
            if (filho.getNameExt().endsWith(".pro")) {
                temProArquivo = true;
                break;
            }
        }
        return dir.getFileObject("fydelis.project") != null
            || dir.getFileObject("CMakeLists.txt") != null
            || temProArquivo;
    }

    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        if (!isProject(dir)) return null;
        return new FydelisQtProject(dir, state);
    }

    @Override
    public void saveProject(Project project) throws IOException {
        // Salvo automaticamente pelo sistema de arquivos
    }
}