package com.example.facturacioncarpintero.model;

public class itemList {
    String nombre,zona;
    int codigo;
    int idCliente;
    double limiteCredito;

    public itemList() {
    }

    public itemList(String nombre, String zona, int codigo, int idCliente, double limiteCredito) {
        this.nombre = nombre;
        this.zona = zona;
        this.codigo = codigo;
        this.idCliente = idCliente;
        this.limiteCredito = limiteCredito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
}
