package com.example.facturacioncarpintero.SQLite.entidades;

public class FacturasAdd {
  private String NombreCliente;
  private String NombreVendedor;
  private String Factura;
  private String Fecha;
  private double Abono;
  private double Descuento;
  private int NumReferencia;
  private  double SaldoRes;
  private int idCuentasxCobrar;
  private int idPagoCxC;
  private String Observaciones;

    public FacturasAdd() {
    }



    public FacturasAdd(String nombreCliente, String nombreVendedor, String factura, String fecha, double abono, double descuento, int numReferencia, double saldoRes, int idcuentasxCobrar, int idpagoCxC, String observaciones) {
        NombreCliente = nombreCliente;
        NombreVendedor = nombreVendedor;
        Factura = factura;
        Fecha = fecha;
        Abono = abono;
        Descuento = descuento;
        NumReferencia = numReferencia;
        SaldoRes = saldoRes;
        idCuentasxCobrar=idcuentasxCobrar;
        idPagoCxC=idpagoCxC;
        Observaciones=observaciones;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public String getNombreVendedor() {
        return NombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        NombreVendedor = nombreVendedor;
    }

    public String getFactura() {
        return Factura;
    }

    public void setFactura(String factura) {
        Factura = factura;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public double getAbono() {
        return Abono;
    }

    public void setAbono(double abono) {
        Abono = abono;
    }

    public double getDescuento() {
        return Descuento;
    }

    public void setDescuento(double descuento) {
        Descuento = descuento;
    }

    public int getNumReferencia() {
        return NumReferencia;
    }

    public void setNumReferencia(int numReferencia) {
        NumReferencia = numReferencia;
    }

    public double getSaldoRes() {
        return SaldoRes;
    }

    public void setSaldoRes(double saldoRes) {
        SaldoRes = saldoRes;
    }


    public int getIdCuentasxCobrar() { return idCuentasxCobrar; }

    public void setIdCuentasxCobrar(int idCuentasxCobrar) { this.idCuentasxCobrar = idCuentasxCobrar; }

    public int getIdPagoCxC() { return idPagoCxC; }

    public void setIdPagoCxC(int idPagoCxC) { this.idPagoCxC = idPagoCxC; }

    public String getObservaciones() { return Observaciones; }

    public void setObservaciones(String observaciones) { Observaciones = observaciones; }
}