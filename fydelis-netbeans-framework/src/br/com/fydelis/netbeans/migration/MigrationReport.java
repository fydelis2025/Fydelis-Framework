package br.com.fydelis.netbeans.migration;

import br.com.fydelis.netbeans.migration.Qt6MigrationEngine.Change;

import java.util.List;

public class MigrationReport {
    private final List<Change> alteracoes;

    public MigrationReport(List<Change> alteracoes) {
        this.alteracoes = alteracoes;
    }

    public String gerarTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE MIGRAÇÃO Qt5 → Qt6 ===\n");
        sb.append("Total de alterações detectadas: ").append(alteracoes.size()).append("\n\n");

        alteracoes.stream()
            .collect(java.util.stream.Collectors.groupingBy(c -> c.toString()))
            .forEach((arq, mudancas) -> {
                sb.append("📄 ").append(arq).append("\n");
                for (Change m : mudancas) {
                    sb.append("  L").append(m.linha)
                      .append(" [").append(m.tipo).append("] ")
                      .append(m.de).append(" → ").append(m.para).append("\n");
                }
                sb.append("\n");
            });

        sb.append("======================================\n");
        sb.append("Executado por: Fydelis NetBeans Framework\n");
        return sb.toString();
    }

    public List<Change> getAlteracoes() { return alteracoes; }
}
