package br.com.fydelis.netbeans.ui;
/** Localizable strings for {@link br.com.fydelis.netbeans.ui}. */
class Bundle {
    /**
     * @return <i>Fydelis — Status do Ambiente</i>
     * @see FydelisStatusPanel
     */
    static String CTL_FydelisPanel() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_FydelisPanel");
    }
    /**
     * @return <i>✅ Fydelis Framework ativo | Qt6 Pronto</i>
     * @see FydelisStatusPanel
     */
    static String LBL_Pronto() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_Pronto");
    }
    private Bundle() {}
}
