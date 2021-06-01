package com.example.facturacionlibreria.SQLite.ulilidades;

public class utilidadesFact {
    public static final String TABLA_RECIBO="recibo";
    public static final String CAMPO_NOMBRE_CLIEBTE="nombrecliente";
    public static final String CAMPO_FACTURA="factura";
    public static final String CAMPO_FECHA="fecha";
    public static final String CAMPO_ABONO="abono";
    public static final String CAMPO_DESCUENTO="descuento";
    public static final String CAMPO_NUMERO_RESF="numeroreferencia";
    public static final String CAMPO_SALDO_RES="saldorestante";
    public static final String CAMPO_CUENTASXCOBRAR="idcuentasxcobrar";
    public static final String CAMPO_OBSERVACIONES="observaciones";

    public static final  String CREAR_TABLA_RECIBO= "CREATE TABLE "+TABLA_RECIBO+"("+CAMPO_NOMBRE_CLIEBTE+" TEXT ,"+CAMPO_FACTURA+" TEXT, "+CAMPO_FECHA+" TEXT, "  +CAMPO_ABONO+" REAL, "+CAMPO_DESCUENTO+" REAL, "+CAMPO_SALDO_RES+" REAL,"+CAMPO_NUMERO_RESF+" INTEGER,"+CAMPO_CUENTASXCOBRAR+" INTEGER,"+CAMPO_OBSERVACIONES+" TEXT)";
}
