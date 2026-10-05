package br.com.fydelis.netbeans.core;

import br.com.fydelis.netbeans.validator.QtCodeValidator;
import org.openide.modules.ModuleInstall;

public class FydelisFramework extends ModuleInstall {
    private static FydelisFramework instance;

    @Override
    public void restored() {
        instance = this;

        // ✅ Texto direto — sem Bundle, sem erro
        System.out.println("Fydelis Framework ativado — pronto para Qt/C++");

        inicializarValidadores();
    }

    private void inicializarValidadores() {
        QtCodeValidator validador = new QtCodeValidator();
        System.out.println("Validador Qt5→Qt6 carregado");
    }

    public static FydelisFramework getInstance() {
        return instance;
    }

    @Override
    public void close() {
        instance = null;
        super.close();
    }
}