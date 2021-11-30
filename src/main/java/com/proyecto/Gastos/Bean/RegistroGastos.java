package com.proyecto.Gastos.Bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Registro_Gastos", schema = "dbo", catalog = "Gastos")
public class RegistroGastos {
    private int id;
    private Date fecha;
    private String proveedor;
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
    @Column(name = "fecha")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "proveedor")
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    @Basic
    @Column(name = "monto")
    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    @OneToOne
    @JoinColumn(name = "id_gastos")
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
                Objects.equals(proveedor, that.proveedor) &&
                Objects.equals(total, that.total) &&
                Objects.equals(observaciones, that.observaciones) &&
                Objects.equals(mes, that.mes) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(monto, that.monto) &&
                Objects.equals(cuenta, that.cuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, proveedor, total, observaciones, mes, tipo, monto, cuenta);
    }
}
