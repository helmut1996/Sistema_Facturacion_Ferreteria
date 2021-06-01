package com.example.facturacionlibreria.SQLite.entidades;

public class ProductosAdd {
    private String id_producto;
    private String nombreproduc;
    private int cantidad;
    private double precios;
    private String imagenProducto;
    private int idcliente;
    private int idvendedor;
    private String tipoprecio;
    public ProductosAdd() {
    }


    public ProductosAdd(int idcliente, int idvendedor, String id_producto, String nombreproduc, int cantidad, double precios, String imagenProducto, String tipoprecio) {
        this.id_producto = id_producto;
        this.nombreproduc = nombreproduc;
        this.cantidad = cantidad;
        this.precios = precios;
        this.imagenProducto= imagenProducto;
        this.idcliente = idcliente;
        this.idvendedor = idvendedor;
        this.tipoprecio=tipoprecio;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombreproduc() {
        return nombreproduc;
    }

    public void setNombreproduc(String nombreproduc) {
        this.nombreproduc = nombreproduc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecios() {
        return precios;
    }

    public void setPrecios(double precios) {
        this.precios = precios;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(int idvendedor) {
        this.idvendedor = idvendedor;
    }


    public String getTipoprecio() { return tipoprecio; }

    public void setTipoprecio(String tipoprecio) { this.tipoprecio = tipoprecio; }
}