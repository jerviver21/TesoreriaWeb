/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.tesoreria.controller;

import com.paideia.tesoreria.services.DetraService;
import com.vi.comun.util.Log;
import com.vi.util.FacesUtil;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jerviver21
 */

@ManagedBean(name="archivoDetrController") 
@SessionScoped
public class ArchivoDetrController {
    @EJB
    DetraService service;  
    
    private Date fecha = new Date();
    
    //Para permitir la descarga de archivos
    private String rutaArchivo;
    private StreamedContent file;
    private boolean renderDownload = false;
    
    
    
    public String generar(){
        try {
            rutaArchivo = service.generarArchivo(fecha);
            setRenderDownload(true);
        } catch (Exception e) {
            Log.getLogger().log(Level.SEVERE, "Error al generar la consulta", e);
            FacesUtil.addMessage(FacesUtil.ERROR,"Error al generar la consulta");
        }
        
        return null;
    }
    
    public void descargar(ActionEvent evt)throws Exception{
        if(rutaArchivo == null){
            return;
        }
        FileInputStream stream = new FileInputStream(rutaArchivo);
        setFile(new DefaultStreamedContent(stream, "application/txt",rutaArchivo.replaceAll(".*"+File.separator+"(.*)", "$1")));
        setRenderDownload(false);
    }

    /**
     * @return the fechaIni
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fechaIni the fechaIni to set
     */
    public void setFecha(Date fechaIni) {
        this.fecha = fechaIni;
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

     
    
}
