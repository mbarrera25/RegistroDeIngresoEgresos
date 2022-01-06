package com.proyecto.Gastos.Services;

import com.proyecto.Gastos.Bean.Registro;
import com.proyecto.Gastos.Bean.RegistroGastos;
import com.proyecto.Gastos.Bean.TipoGastos;
import com.proyecto.Gastos.GastosApplication;
import com.proyecto.Gastos.repository.RegistroGastosRepository;
import com.proyecto.Gastos.repository.TipoGastosRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.proyecto.Gastos.constantes.constante_respuestas.*;
import static com.proyecto.Gastos.constantes.generalConstantes.*;

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
            Integer saldo =0;
            Double total = 0.00;
            if (tipoGastos.getId()==94){
                saldo = registroGastosRepository.consultarSaldo();
                monto = Double.valueOf(saldo);
                total = monto;
            }else {
                 saldo = registroGastosRepository.consultarSaldo();
                saldo = saldo == null ? 0 : saldo;

                 total = tipo == INGRESO ? saldo + monto : saldo - monto;
            }
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

    public HashMap<String, Object> crearConceptos(TipoGastos dto){
        HashMap<String, Object> resp = new HashMap<>();
        tipoGastosRepository.save(dto);
        resp.put(_STATUS, 200);
        resp.put(_BODY, MSJ_EXITO);
        return resp;
    }

    public HashMap<String, Object> listarAllGastos() {
        HashMap<String, Object> resp = new HashMap<>();
        List<TipoGastos> listaGastos = new ArrayList<>();
        listaGastos = tipoGastosRepository.listargastos();
        resp.put(_STATUS, 200);
        resp.put(_BODY, listaGastos);
        return resp;
    }

    public HashMap<String,Object> donwloadReportExcel(List<Registro> list) throws IOException {
        HashMap<String, Object> resp = new HashMap<>();
        Workbook e = new HSSFWorkbook();
        Date date = new Date();
        Properties properties = (Properties) this.getProperties();

        String filename = properties.getProperty("ruta.url.reportes") + "reporte" +date.getTime()+ ".xls";
        //String filename = "C:/NewExcelFile.xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("reporte");
        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("fecha");
        rowhead.createCell(1).setCellValue("tipo");
        rowhead.createCell(2).setCellValue("concepto");
        rowhead.createCell(3).setCellValue("ingreso");
        rowhead.createCell(4).setCellValue("egreso");
        rowhead.createCell(5).setCellValue("total");
        rowhead.createCell(6).setCellValue("observaciones");
        //rowhead.createCell(6).setCellValue("ingreso");

        for (int i = 0; i < list.size(); i++) {
            Registro reg = list.get(i);
        HSSFRow row = sheet.createRow(i+1);
            DateFormat formateadorFechaCorta = DateFormat.getDateInstance(DateFormat.MEDIUM);
            row.createCell(0).setCellValue( formateadorFechaCorta.format(reg.getFecha()));
            row.createCell(1).setCellValue(reg.getCuenta().getNombre());
            row.createCell(2).setCellValue(reg.getConcepto());
            if (reg.getTipo() == INGRESO) {
                row.createCell(3).setCellValue(reg.getMonto());
                row.createCell(4).setCellValue("0");
            } else {
                row.createCell(3).setCellValue("0");
                row.createCell(4).setCellValue(reg.getMonto());
            }
            row.createCell(5).setCellValue(reg.getTotal());
            row.createCell(6).setCellValue(reg.getObservaciones());

        }


        FileOutputStream fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Your excel file has been generated!");
        resp.put(_STATUS, 200);
        resp.put(_BODY, MSJ_EXITO);

        return resp;
    }
    public Object getProperties() throws IOException {
        Properties p = new Properties();
        Properties properties = new Properties();
        InputStream file = null;
        file = GastosApplication.class.getClassLoader().getResourceAsStream("application.properties");
        p.load(file);
        properties.put("ruta.url.reportes", p.getProperty("ruta.url.reportes"));
        return properties;
    }

    public HashMap<String, Object> generarReporteRsumen(List<Registro> lstRegistros) throws IOException {
        HashMap<String, Object> resp = new HashMap<>();
        Workbook e = new HSSFWorkbook();
        Date date = new Date();
        Properties properties = (Properties) this.getProperties();

        String filename = properties.getProperty("ruta.url.reportes")  + "RESUMEN_CUENTAS" +date.getTime()+ ".xls";
        //String filename = "C:/NewExcelFile.xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Resumen");
        CellStyle cellStyle = workbook.createCellStyle();
        Font cellFont = workbook.createFont();
        cellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(cellFont);
        HSSFRow rowTitle = sheet.createRow((short) 0);
        rowTitle.createCell(2).setCellValue("RESUMEN DE CUENTAS");
        List<TipoGastos> ingresosLst = tipoGastosRepository.findByTipo(1);
        List<TipoGastos> egresosLst = tipoGastosRepository.findByTipo(2);
        HSSFRow rowHead = sheet.createRow((short) 1);
        HSSFRow row3 = sheet.createRow((short) 2);
        row3.createCell(1).setCellValue("INGRESOS");
        //List<Registro> registrosList = registroGastosRepository.findAll();
            Double totalIngreso = 0.0;
        for (int i = 0; i < ingresosLst.size(); i++) {
            HSSFRow rowTotal = sheet.createRow((short) 3);
            Double total = 0.0;
            Integer gasto = 0;
            for (Registro reg:
                    lstRegistros) {
                if (ingresosLst.get(i).getId().equals(reg.getCuenta().getId())){
                    total = reg.getMonto().doubleValue() + total;
                    gasto = reg.getCuenta().getId();
                }
            }
        rowHead.createCell(2+i).setCellValue(ingresosLst.get(i).getNombre());
        rowHead.getCell(2+i).setCellStyle(cellStyle);
            if (ingresosLst.get(i).getId().equals(gasto)){
            row3.createCell(2+i).setCellValue(total);
            }
                totalIngreso = totalIngreso + total;
        }
        rowHead.createCell(ingresosLst.size()+2).setCellValue("TOTAL INGRESOS");
        rowHead.getCell(ingresosLst.size()+2).setCellStyle(cellStyle);
        row3.createCell(ingresosLst.size()+2).setCellValue(totalIngreso);


        HSSFRow rowEgresos = sheet.createRow((short) 4);
        rowEgresos.createCell(1).setCellValue("EGRESOS");
        Double totalEgreso =0.0;
        for (int i = 0; i < egresosLst.size(); i++) {
            Double total = 0.0;
            Integer gasto = 0;
            HSSFRow row4 = sheet.createRow(i+5);
            row4.createCell(2).setCellValue(egresosLst.get(i).getNombre());
            for (Registro reg :
                    lstRegistros) {
                if (egresosLst.get(i).getId().equals(reg.getCuenta().getId())){
                    total = reg.getMonto().doubleValue() + total;
                    gasto = reg.getCuenta().getId();
                }
            }
            if (egresosLst.get(i).getId().equals(gasto)){
                row4.createCell(ingresosLst.size()+2).setCellValue(total);
            }
            totalEgreso = totalEgreso + total;
            //row4.createCell(ingresosLst.size()+2).setCellValue(totalEgreso);
            //row4.getCell(ingresosLst.size()+2).setCellStyle(cellStyle);
    }
        HSSFRow rowTotalTitulo = sheet.createRow(egresosLst.size()+7);
        rowTotalTitulo.createCell(1).setCellValue("TOTAL EGRESOS");
        rowTotalTitulo.getCell(1).setCellStyle(cellStyle);
        rowTotalTitulo.createCell(ingresosLst.size()+2).setCellValue(totalEgreso);
        rowTotalTitulo.getCell(ingresosLst.size()+2).setCellStyle(cellStyle);

       HSSFRow rowSaldo = sheet.createRow(egresosLst.size()+10);
       rowSaldo.createCell(1).setCellValue("SALDO");
       rowSaldo.getCell(1).setCellStyle(cellStyle);
       rowSaldo.createCell(ingresosLst.size()+2).setCellValue(totalIngreso-totalEgreso);
        rowSaldo.getCell(ingresosLst.size()+2).setCellStyle(cellStyle);

        FileOutputStream fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Your excel file has been generated!");
        resp.put(_STATUS, 200);
        resp.put(_BODY, MSJ_EXITO);
        return resp;
    }

    public HashMap<String, Object> deleteGastos(TipoGastos tipoGastos) {
        HashMap<String, Object> resp = new HashMap<>();
        List<Registro> registos = registroGastosRepository.findByCuenta(tipoGastos);

        if (registos.size()>0){
            resp.put(_STATUS, 400);
            resp.put(_BODY, "No se puede eliminar el tipo de Gasto porque existen movmientios registrados en el.");
        }else {
            tipoGastosRepository.delete(tipoGastos);
            resp.put(_STATUS, 200);
            resp.put(_BODY, MSJ_EXITO);
        }
        return resp;
    }

    public HashMap<String, Object> cierreMes() {
        HashMap<String, Object> resp = new HashMap<>();


        resp.put(_STATUS, 200);
        resp.put(_BODY, MSJ_EXITO);
        return resp;
    }
}
