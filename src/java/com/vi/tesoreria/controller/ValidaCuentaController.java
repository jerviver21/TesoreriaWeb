/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.tesoreria.controller;

import com.paideia.tesoreria.dominio.InconsistenciaPlanilla;
import com.paideia.tesoreria.dominio.Semt;
import com.paideia.tesoreria.services.PlanillaService;
import com.paideia.tesoreria.to.PlanillaTO;
import com.vi.comun.exceptions.ValidacionException;
import com.vi.usuarios.controller.SessionController;
import com.vi.usuarios.dominio.Users;
import com.vi.util.FacesUtil;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jerviver21
 */

@ManagedBean(name="validaCuentaController")
@SessionScoped

public class ValidaCuentaController {
    
    @EJB
    PlanillaService service;
    
    private String cargados = "";
    PlanillaTO to;
    
    //Para permitir la descarga de archivos
    private String rutaArchivo;
    private StreamedContent file;
    private boolean renderDownload = false;
    
    //Parametros
    private Double tasaCambio = 2.8;
    private List<Semt> semts = new ArrayList();
    private List<InconsistenciaPlanilla> inconsistencias = new ArrayList();
    
    
    public void cargar(FileUploadEvent event) {
         try {
             //System.out.println("---> "+service+" - "+event.getFile());
             to = service.cargar(event.getFile().getInputstream(), event.getFile().getFileName(), tasaCambio);
             getSemts().addAll(to.getSemts());
             getInconsistencias().addAll(to.getPlanillas());
             String nombreArchivo = event.getFile().getFileName();
             cargados += nombreArchivo==null?"":nombreArchivo+"  **  ";
         } catch (ValidacionException e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: "+e.getMessage());
             e.printStackTrace();
         }catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
    }  
     
    public String generarSEMT() {
         try {
             rutaArchivo = service.generarArchivoSEMT(semts);
             setRenderDownload(true);
             FacesUtil.addMessage(FacesUtil.INFO, "Info: Cargue el archivo SEMT, para consultar los pagos embargados ");
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
        
         return null;
    } 
    
    public void descargar(ActionEvent evt)throws Exception{
        
        try {
                if(rutaArchivo == null){
                    setRenderDownload(false);
                    return;
                }
                FileInputStream stream = new FileInputStream(rutaArchivo);
                setFile(new DefaultStreamedContent(stream, "application/csv", rutaArchivo.replaceAll(".*/(.*)", "$1")));
                setRenderDownload(false);

         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
        
    } 
    
    public String reiniciarInconsistencias(){
        semts = new ArrayList();
        inconsistencias = new ArrayList();
        return null;
    }


    /**
     * @return the cargados
     */
    public String getCargados() {
        return cargados;
    }

    /**
     * @param cargados the cargados to set
     */
    public void setCargados(String cargados) {
        this.cargados = cargados;
    }

    /**
     * @return the file
     */
    public StreamedContent getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(StreamedContent file) {
        this.file = file;
    }

    /**
     * @return the renderDownload
     */
    public boolean isRenderDownload() {
        return renderDownload;
    }

    /**
     * @param renderDownload the renderDownload to set
     */
    public void setRenderDownload(boolean renderDownload) {
        this.renderDownload = renderDownload;
    }

    /**
     * @return the tasaCambio
     */
    public Double getTasaCambio() {
        return tasaCambio;
    }

    /**
     * @param tasaCambio the tasaCambio to set
     */
    public void setTasaCambio(Double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    /**
     * @return the semts
     */
    public List<Semt> getSemts() {
        return semts;
    }

    /**
     * @param semts the semts to set
     */
    public void setSemts(List<Semt> semts) {
        this.semts = semts;
    }

    /**
     * @return the inconsistencias
     */
    public List<InconsistenciaPlanilla> getInconsistencias() {
        return inconsistencias;
    }

    /**
     * @param inconsistencias the inconsistencias to set
     */
    public void setInconsistencias(List<InconsistenciaPlanilla> inconsistencias) {
        this.inconsistencias = inconsistencias;
    }

   
    
}
