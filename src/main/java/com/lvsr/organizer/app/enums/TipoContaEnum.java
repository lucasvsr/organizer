package com.lvsr.organizer.app.enums;

public enum TipoContaEnum {

    BANCARIA("Conta bancária"),
    CREDITO("Cartão de crédito"),
    INVESTIMENTO("Conta corrente (investimentos)");

    public final String label;

    TipoContaEnum(String label) {

        this.label = label;

    }

}
