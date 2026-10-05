package br.com.fydelis.netbeans.migration;
/** Localizable strings for {@link br.com.fydelis.netbeans.migration}. */
class Bundle {
    /**
     * @return <i>Migrar para Qt6</i>
     * @see Qt6MigrationWizard
     */
    static String CTL_MigrarQt6() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_MigrarQt6");
    }
    /**
     * @return <i>✅ Migração concluída!</i>
     * @see Qt6MigrationWizard
     */
    static String MSG_Concluido() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_Concluido");
    }
    /**
     * @return <i>Foram detectadas %d alterações.<br>Aplicar mudanças nos arquivos?</i>
     * @see Qt6MigrationWizard
     */
    static String MSG_Confirmar() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_Confirmar");
    }
    /**
     * @return <i>✅ Nenhum ajuste necessário — código já compatível com Qt6!</i>
     * @see Qt6MigrationWizard
     */
    static String MSG_NenhumAjuste() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_NenhumAjuste");
    }
    /**
     * @return <i>Modo simulação — nenhuma alteração foi feita.<br><br>Relatório:<br></i>
     * @see Qt6MigrationWizard
     */
    static String MSG_Simulacao() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_Simulacao");
    }
    private Bundle() {}
}
