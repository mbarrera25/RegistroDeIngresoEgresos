package com.proyecto.Gastos.Services;

import com.proyecto.Gastos.Bean.Registro;
import com.proyecto.Gastos.Bean.RegistroGastos;
import com.proyecto.Gastos.Bean.TipoGastos;
import com.proyecto.Gastos.repository.RegistroGastosRepository;
import com.proyecto.Gastos.repository.TipoGastosRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.proyecto.Gastos.constantes.constante_respuestas.*;
import static com.proyecto.Gastos.constantes.generalConstantes.EGRESOS;
import static com.proyecto.Gastos.constantes.generalConstantes.INGRESO;

@Service
public class GeneralServices {
    static Logger log = LogManager.getLogger(GeneralServices.class);

    @Autowired
    TipoGastosRepository tipoGastosRepository;
    @Autowired
    RegistroGastosRepository registroGastosRepository;


    public HashMap<String, Object> registroGastos( String fecha, Integer tipo, String proveedor, Double monto, Integer cuenta, String observaciones) {
        HashMap<String, Object> resp = new HashMap<>();
        try {
            Registro registro = new Registro();
            TipoGastos tipoGastos = tipoGastosRepository.findById(cuenta).get();
            Integer saldo = registroGastosRepository.consultarSaldo();
            saldo = saldo==null ? 0 : saldo;

            Double total = tipo==INGRESO ? saldo + monto : saldo - monto;

            Date fecha1=new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
            LocalDate localDate = fecha1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            registro.setTipo(tipo);
            registro.setFecha(fecha1);
            registro.setMes(localDate.getMonth().getValue());
            registro.setConcepto(proveedor);
            registro.setMonto(monto);
            registro.setTotal(total);
            registro.setCuenta(tipoGastos);
            registro.setObservaciones(observaciones);
            registro = registroGastosRepository.save(registro);
            resp.put(_STATUS, 200);
            resp.put(_BODY,registro );
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }

    public HashMap<String, Object> listartiposGastos(Integer tipo) {
        HashMap<String, Object> resp = new HashMap<>();
        Iterable<TipoGastos> listado = tipoGastosRepository.findByTipo(tipo);
        resp.put(_STATUS, 200);
        resp.put(_BODY, listado);
    return resp;
    }

    public HashMap<String, Object> listarPorMes() {
        List<Registro> lstDiario =  new ArrayList<>();
                lstDiario = registroGastosRepository.listarRegistros();
        HashMap<String, Object> resp = new HashMap<>();
        resp.put(_STATUS, 200);
        resp.put(_BODY, lstDiario);
        return resp;
    }
    public HashMap<String, Object> editarRegistro(Integer id, String fecha, Integer tipo, String proveedor, Double monto, Integer cuenta, String observaciones) {
        HashMap<String, Object> resp = new HashMap<>();
        try {
            Registro registro = registroGastosRepository.findById(id).get();
            TipoGastos tipoGastos = tipoGastosRepository.findById(cuenta).get();
            List<Registro> lstRegistro = registroGastosRepository.recalcularSaldo(id);



            Date fecha1=new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
            LocalDate localDate = fecha1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            registro.setTipo(tipo);
            registro.setFecha(fecha1);
            registro.setMes(localDate.getMonth().getValue());
            registro.setConcepto(proveedor);
            if (monto!=registro.getMonto()) {
                Integer saldo = registroGastosRepository.consultarSaldoRecalcular(id);
                saldo = saldo==null ? 0 : saldo;
                Double total = tipo==INGRESO ? monto + saldo : saldo - monto  ;
                registro.setMonto(monto);
                registro.setTotal(total);
                Double totalAnt = registro.getTotal();
                    for (Registro reg :
                            lstRegistro) {

                        Double monto_r = reg.getMonto();
                        Double total_r = reg.getTipo() == INGRESO ? monto_r + totalAnt : totalAnt - monto_r;
                        reg.setTotal(total_r);
                        registroGastosRepository.save(reg);
                        totalAnt = reg.getTotal();
                    }

            }
            registro.setCuenta(tipoGastos);
            registro.setObservaciones(observaciones);
            registro = registroGastosRepository.save(registro);


            resp.put(_STATUS, 200);
            resp.put(_BODY,registro );
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }

    public HashMap<String, Object> consultaRegistro(Integer id) {
        HashMap<String, Object> resp = new HashMap<>();
        Registro registro = registroGastosRepository.findById(id).get();
        resp.put(_STATUS, 200);
        resp.put(_BODY,registro );
        return resp;
    }

    public HashMap<String, Object> totalesIngresosEgresos(Integer mes) {
        HashMap<String, Object> resp = new HashMap<>();
        HashMap<String,Object> lista = new HashMap<>();
        List< HashMap<String,Object>> lstRegistros = new ArrayList<>();
        List<Registro> ingresos = registroGastosRepository.findByMesAndTipo(mes,INGRESO);
        List<Registro> egresos = registroGastosRepository.findByMesAndTipo(mes,EGRESOS);
        lista.put("Ingreso",ingresos);
        lista.put("Egreso",egresos);
        lstRegistros.add(lista);
        resp.put(_STATUS, 200);
        resp.put(_BODY, lstRegistros);

        return resp;
    }
}
