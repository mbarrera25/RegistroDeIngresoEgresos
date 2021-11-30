package com.proyecto.Gastos.repository;

import com.proyecto.Gastos.Bean.Registro;
import com.proyecto.Gastos.Bean.RegistroGastos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistroGastosRepository extends JpaRepository<Registro,Integer> {

    List<Registro> findByMes(Integer mes);
    List<Registro> findByMesAndTipo(Integer mes,Integer tipo);
    String QUERY_SALDO = "select top 1 total from Registro order by id desc";
    @Query(nativeQuery = true, value = QUERY_SALDO)
    Integer consultarSaldo();

    String QUERY_SALDO_RECALCULADO = "select top 1 total from Registro where id <:id order by id  desc";
    @Query(nativeQuery = true, value = QUERY_SALDO_RECALCULADO)
    Integer consultarSaldoRecalcular(@Param("id") Integer id);

    String QUERY_RECALCULAR = "select * from Registro where id >:id";
    @Query(nativeQuery = true, value = QUERY_RECALCULAR)
    List<Registro> recalcularSaldo(@Param("id") Integer id);

}
