package br.com.fydelis.netbeans.validator;

import org.openide.util.NbBundle;

public final class Bundle {
    private Bundle() {}

    public static String MSG_FuncaoInsegura() {
        return NbBundle.getMessage(Bundle.class, "MSG_FuncaoInsegura");
    }

    public static String MSG_Qt5ModoEncontrado() {
        return NbBundle.getMessage(Bundle.class, "MSG_Qt5ModoEncontrado");
    }
}