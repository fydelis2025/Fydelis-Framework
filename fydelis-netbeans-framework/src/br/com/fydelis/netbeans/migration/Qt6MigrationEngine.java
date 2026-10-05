package br.com.fydelis.netbeans.migration;

import br.com.fydelis.netbeans.migration.rules.*;
import org.openide.filesystems.FileObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Qt6MigrationEngine {

    public static class Change {
        public String arquivo;
        public int linha;
        public String de;
        public String para;
        public String tipo;
    }

    private final List<Change> alteracoes = new ArrayList<>();
    private final boolean modoSimulacao;

    public Qt6MigrationEngine(boolean apenasSimular) {
        this.modoSimulacao = apenasSimular;
    }

    public MigrationReport processarProjeto(FileObject raiz) throws IOException {
        alteracoes.clear();
        percorrerPasta(raiz);
        return new MigrationReport(new ArrayList<>(alteracoes));
    }

    private void percorrerPasta(FileObject pasta) throws IOException {
        for (FileObject arq : pasta.getChildren()) {
            if (arq.isFolder()) {
                if (!isPastaIgnorada(arq.getName())) {
                    percorrerPasta(arq);
                }
            } else {
                if (isArquivoFonte(arq)) {
                    processarArquivo(arq);
                }
            }
        }
    }

    private void processarArquivo(FileObject arq) throws IOException {
        String conteudo = new String(arq.asBytes(), StandardCharsets.UTF_8);
        String original = conteudo;
        String caminho = arq.getPath();

        conteudo = QtClassRules.aplicar(conteudo, caminho, alteracoes);
        conteudo = QtMethodRules.aplicar(conteudo, caminho, alteracoes);
        conteudo = QtFlagRules.aplicar(conteudo, caminho, alteracoes);
        
        if (arq.getName().endsWith(".pro") || arq.getName().equals("CMakeLists.txt")) {
            conteudo = QtModuleRules.aplicar(conteudo, caminho, alteracoes);
        }

        if (!conteudo.equals(original) && !modoSimulacao) {
            BackupManager.criarBackup(arq);
            try (OutputStream out = arq.getOutputStream()) {
                out.write(conteudo.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private boolean isArquivoFonte(FileObject arq) {
        String nome = arq.getNameExt().toLowerCase();
        return nome.endsWith(".cpp") || nome.endsWith(".h") || nome.endsWith(".c")
            || nome.endsWith(".pro") || nome.endsWith(".pri") || nome.equals("CMakeLists.txt");
    }

    private boolean isPastaIgnorada(String nome) {
        return nome.equals("build") || nome.equals("bin") || nome.equals("lib")
            || nome.startsWith(".") || nome.equals("resources")
            || nome.equals(".fydelis_backup");
    }
}