package com.proyecto.Gastos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.Gastos.Bean.Registro;
import com.proyecto.Gastos.Bean.TipoGastos;
import com.proyecto.Gastos.Services.GeneralServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.proyecto.Gastos.constantes.constante_respuestas.MSJ_ERROR;
import static com.proyecto.Gastos.constantes.generalConstantes.*;

@CrossOrigin(origins = {"*"})
@RestController
public class generalController {
    static Logger log = LogManager.getLogger(generalController.class);

    @Autowired
    GeneralServices generalServices;

    @PostMapping("/CrearRegistroDiarios")
    public HashMap<String, Object> registrarGastosDiarios(@ApiBodyObject(clazz = String.class) @RequestBody String json) throws JsonProcessingException {
        Map<String, Object> params = new ObjectMapper().readerFor(Map.class).readValue(json);
        String fecha 	= (params.containsKey(FECHA) && params.get(FECHA) != null) ? params.get(FECHA).toString() : null;
        Integer tipo 	= (params.containsKey(TIPO) && params.get(TIPO) != null) ? Integer.valueOf(params.get(TIPO).toString()) : null;
        Integer cuenta 	= (params.containsKey(CUENTA) && params.get(CUENTA) != null) ? Integer.valueOf(params.get(CUENTA).toString()) : null;
        String proveedor 	= (params.containsKey(PROVEEDOR) && params.get(PROVEEDOR) != null) ? params.get(PROVEEDOR).toString() : null;
        Double monto 	= (params.containsKey(MONTO) && params.get(MONTO) != null) ? Double.valueOf(params.get(MONTO).toString()) : null;
        String observaciones 	= (params.containsKey(OBSERVACIONES) && params.get(OBSERVACIONES) != null) ? params.get(OBSERVACIONES).toString() : null;
        HashMap<String, Object> resp = new HashMap<>();
        try {
            resp = generalServices.registroGastos(fecha,tipo,proveedor,monto,cuenta,observaciones);
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }
    @PostMapping("/listarRegistros")
    public HashMap<String, Object> listarRegistros(@ApiBodyObject(clazz = String.class) @RequestBody String json) throws JsonProcessingException {
        Map<String, Object> params = new ObjectMapper().readerFor(Map.class).readValue(json);
        HashMap<String, Object> resp = new HashMap<>();

            resp = generalServices.listarPorMes();

        return resp;
    }

    @PostMapping("/listaGatosPorTipo")
    public HashMap<String, Object> listaGatosPorTipo(@ApiBodyObject(clazz = String.class) @RequestBody String json) throws JsonProcessingException {
        HashMap<String, Object> resp = new HashMap<>();
        Map<String, Object> params = new ObjectMapper().readerFor(Map.class).readValue(json);
        Integer tipo 	= (params.containsKey(TIPO) && params.get(TIPO) != null) ? Integer.valueOf(params.get(TIPO).toString()) : null;
        try {
            resp = generalServices.listartiposGastos(tipo);
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }
    @GetMapping("/listaGastos")
    public HashMap<String, Object> listaGastos() throws JsonProcessingException {
        HashMap<String, Object> resp = new HashMap<>();
        try {
            resp = generalServices.listarAllGastos();
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }

    @PostMapping("/EditarRegistroDiarios")
    public HashMap<String, Object> EditarRegistroDiarios(@ApiBodyObject(clazz = String.class) @RequestBody String json) throws JsonProcessingException {
        Map<String, Object> params = new ObjectMapper().readerFor(Map.class).readValue(json);
        String fecha 	= (params.containsKey(FECHA) && params.get(FECHA) != null) ? params.get(FECHA).toString() : null;
        Integer tipo 	= (params.containsKey(TIPO) && params.get(TIPO) != null) ? Integer.valueOf(params.get(TIPO).toString()) : null;
        Integer id 	= (params.containsKey(ID) && params.get(ID) != null) ? Integer.valueOf(params.get(ID).toString()) : null;
        Integer cuenta 	= (params.containsKey(CUENTA) && params.get(CUENTA) != null) ? Integer.valueOf(params.get(CUENTA).toString()) : null;
        String proveedor 	= (params.containsKey(PROVEEDOR) && params.get(PROVEEDOR) != null) ? params.get(PROVEEDOR).toString() : null;
        Double monto 	= (params.containsKey(MONTO) && params.get(MONTO) != null) ? Double.valueOf(params.get(MONTO).toString()) : null;
        String observaciones 	= (params.containsKey(OBSERVACIONES) && params.get(OBSERVACIONES) != null) ? params.get(OBSERVACIONES).toString() : null;
        HashMap<String, Object> resp = new HashMap<>();
        try {
            resp = generalServices.editarRegistro(id,fecha,tipo,proveedor,monto,cuenta,observaciones);
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }
    @PostMapping("consultaRegistro")
    public HashMap<String, Object> consultaRegistro(@ApiBodyObject(clazz = String.class) @RequestBody String json) throws JsonProcessingException {
        Map<String, Object> params = new ObjectMapper().readerFor(Map.class).readValue(json);
        Integer id 	= (params.containsKey(ID) && params.get(ID) != null) ? Integer.valueOf(params.get(ID).toString()) : null;
        HashMap<String, Object> resp = new HashMap<>();
        try {
            resp = generalServices.consultaRegistro(id);
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }
    @PostMapping("totalesIngresosEgresos")
    public HashMap<String, Object> totalesIngresosEgresos(@ApiBodyObject(clazz = String.class) @RequestBody String json) throws JsonProcessingException {
        Map<String, Object> params = new ObjectMapper().readerFor(Map.class).readValue(json);
        Integer mes 	= (params.containsKey(MES) && params.get(MES) != null) ? Integer.valueOf(params.get(MES).toString()) : null;
        HashMap<String, Object> resp = new HashMap<>();
        try {
            resp = generalServices.totalesIngresosEgresos(mes);
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }
    @PostMapping("crearConcepto")
    public HashMap<String, Object> crearConcepto(@ApiBodyObject(clazz = TipoGastos.class) @RequestBody TipoGastos dto) throws JsonProcessingException {
        HashMap<String, Object> resp = new HashMap<>();
        try {
            resp = generalServices.crearConceptos(dto);
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }

    @PostMapping("donwloadReportExcel")
    public HashMap<String, Object> donwloadReportExcel(@ApiBodyObject(clazz = Registro.class) @RequestBody List<Registro> dto) throws JsonProcessingException {
        HashMap<String, Object> resp = new HashMap<>();
        try {
            resp = generalServices.donwloadReportExcel(dto);
        } catch (Exception e) {
            log.error(MSJ_ERROR + e.getMessage());
        }
        return resp;
    }
}
