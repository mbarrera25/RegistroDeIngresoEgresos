package com.proyecto.Gastos.repository;


import com.proyecto.Gastos.Bean.Registro;
import com.proyecto.Gastos.Bean.TipoGastos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoGastosRepository extends JpaRepository<TipoGastos,Integer> {
    List<TipoGastos> findByTipo(Integer tipo);
    String LISTAR_GASTOS = "select * from tipo_gastos order by id desc";
    @Query(nativeQuery = true, value = LISTAR_GASTOS)
    List<TipoGastos> listargastos();
}
