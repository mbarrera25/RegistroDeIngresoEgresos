package com.proyecto.Gastos.repository;


import com.proyecto.Gastos.Bean.TipoGastos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoGastosRepository extends JpaRepository<TipoGastos,Integer> {
    List<TipoGastos> findByTipo(Integer tipo);
}
