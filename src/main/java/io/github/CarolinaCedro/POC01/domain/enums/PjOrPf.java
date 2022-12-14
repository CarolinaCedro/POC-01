package io.github.CarolinaCedro.POC01.domain.enums;

import io.github.CarolinaCedro.POC01.domain.CpfOrCnpjInterfaces.CnpjGroup;
import io.github.CarolinaCedro.POC01.domain.CpfOrCnpjInterfaces.CpfGroup;


public enum PjOrPf {


    PF("Física","CPF","000.000.000-00", CpfGroup.class),
    PJ("Jurídica","CNPJ","000.000.000-00", CnpjGroup.class);

    private final String description;
    private final String document;
    private final String mask;
    private final Class<?> group;

    PjOrPf(String description, String document, String mask, Class<?> group) {
        this.description = description;
        this.document = document;
        this.mask = mask;
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public String getDocument() {
        return document;
    }

    public String getMask() {
        return mask;
    }

    public Class<?> getGroup() {
        return group;
    }
}
