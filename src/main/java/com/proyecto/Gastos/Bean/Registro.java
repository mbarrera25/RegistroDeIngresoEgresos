package com.proyecto.Gastos.Bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
public class Registro {
    private int id;
    private Date fecha;
    private String concepto;
    private Double monto;
    private Double total;
    private String observaciones;
    private Integer mes;
    private Integer tipo;
    private TipoGastos cuenta;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fecha")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "concepto")
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @Basic
    @Column(name = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Basic
    @Column(name = "observaciones")
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Basic
    @Column(name = "mes")
    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    @Basic
    @Column(name = "tipo")
    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }




    @OneToOne
    @JoinColumn(name = "cuenta")
    public TipoGastos getCuenta() {
        return cuenta;
    }

    public void setCuenta(TipoGastos cuenta) {
        this.cuenta = cuenta;
    }
    @Basic
    @Column(name = "monto")

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registro registro = (Registro) o;
        return id == registro.id &&
                Objects.equals(fecha, registro.fecha) &&
                Objects.equals(concepto, registro.concepto) &&
                Objects.equals(total, registro.total) &&
                Objects.equals(observaciones, registro.observaciones) &&
                Objects.equals(mes, registro.mes) &&
                Objects.equals(tipo, registro.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, concepto, total, observaciones, mes, tipo);
    }
}
