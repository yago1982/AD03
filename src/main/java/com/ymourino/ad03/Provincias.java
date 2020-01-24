package com.ymourino.ad03;

import java.util.List;

/**
 * <p>Clase para poder deserializar las provincias desde el fichero JSON proporcionado en
 * el enunciado del ejercicio.</p>
 */
public class Provincias {
    private List<Provincia> provincias;

    public Provincias() {
        provincias = null;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }
}
