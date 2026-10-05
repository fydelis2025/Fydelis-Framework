package br.com.fydelis.netbeans.migration;

import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupManager {
    private static final String PASTA_BACKUP = ".fydelis_backup";

    public static FileObject criarBackup(FileObject arquivo) throws IOException {
        FileObject pastaPai = arquivo.getParent();
        if (pastaPai == null) return null;

        FileObject pastaBackup = pastaPai.getFileObject(PASTA_BACKUP);
        if (pastaBackup == null) {
            pastaBackup = pastaPai.createFolder(PASTA_BACKUP);
        }

        String timestamp = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        );
        String nomeBackup = arquivo.getName() + "_" + timestamp + "." + arquivo.getExt();
        
        Path origem = FileUtil.toPath(arquivo);
        Path destino = FileUtil.toPath(pastaBackup).resolve(nomeBackup);
        
        Files.copy(origem, destino, StandardCopyOption.COPY_ATTRIBUTES);
        return pastaBackup.getFileObject(nomeBackup);
    }
}