package com.proyecto.Gastos.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Registro", schema = "dbo", catalog = "Gastos")
public class RegistroGastos {
    private int id;
    private Date fecha;
    private Double total;
    private String observaciones;
    private Integer mes;
    private Integer tipo;
    private Double monto;
    private String cuenta;
    private TipoGastos tipoGastos;

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
    @JsonProperty("fecha")
    @Column(name = "fecha")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Basic
    @JsonProperty("total")
    @Column(name = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Basic
    @JsonProperty("observaciones")
    @Column(name = "observaciones")
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Basic
    @JsonProperty("mes")
    @Column(name = "mes")
    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    @Basic
    @JsonProperty("tipo")
    @Column(name = "tipo")
    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Basic
    @JsonProperty("monto")
    @Column(name = "monto")
    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    @OneToOne
    @JsonProperty("tipoGastos")
    @JoinColumn(name = "cuenta")
    public TipoGastos getTipoGastos() {
        return tipoGastos;
    }

    public void setTipoGastos(TipoGastos tipoGastos) {
        this.tipoGastos = tipoGastos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroGastos that = (RegistroGastos) o;
        return id == that.id &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(total, that.total) &&
                Objects.equals(observaciones, that.observaciones) &&
                Objects.equals(mes, that.mes) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(monto, that.monto) &&
                Objects.equals(cuenta, that.cuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, total, observaciones, mes, tipo, monto, cuenta);
    }
}
