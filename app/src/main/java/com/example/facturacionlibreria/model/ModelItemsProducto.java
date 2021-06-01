package com.example.facturacionlibreria.model;

import java.io.Serializable;

public class ModelItemsProducto implements Serializable {
    private String nombreP;
    private String unidadmedidaP;
    private String info1;
    private String info2;
    private String info3;
    private String info4;
    private String info5;
    private int stock;
    private String producto;
    private double precioP;
    private String img;
    private  int idproducto;

    public ModelItemsProducto() {
    }


    public ModelItemsProducto(String nombreP, String unidadmedidaP, double precioP, String producto, String info1, String info2, String info3, String info4, String info5, String img, int stock, int idproducto ) {
        this.nombreP = nombreP;
        this.unidadmedidaP = unidadmedidaP;
        this.precioP = precioP;
        this.producto = producto;
        this.info1= info1;
        this.info2= info2;
        this.info3= info3;
        this.info4= info4;
        this.info5= info5;
        this.img= img;
        this.stock= stock;
        this.idproducto=idproducto;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getUnidadmedidaP() {
        return unidadmedidaP;
    }

    public void setUnidadmedidaP(String unidadmedidaP) {
        this.unidadmedidaP = unidadmedidaP;
    }

    public double getPrecioP() {
        return precioP;
    }

    public void setPrecioP(double precioP) {
        this.precioP = precioP;
    }

    public String getProducto() {return producto; }

    public void setProducto(String producto) { this.producto = producto; }

    public String getInfo1() { return info1; }

    public void setInfo1(String info1) { this.info1 = info1; }

    public String getInfo2() { return info2; }

    public void setInfo2(String info2) { this.info2 = info2; }

    public String getInfo3() { return info3; }

    public void setInfo3(String info3) { this.info3 = info3; }

    public String getInfo4() { return info4; }

    public void setInfo4(String info4) { this.info4 = info4; }

    public String getInfo5() { return info5; }

    public void setInfo5(String info5) { this.info5 = info5; }

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    public int getIdproducto() { return idproducto; }

    public void setIdproducto(int idproducto) { this.idproducto = idproducto; }

}