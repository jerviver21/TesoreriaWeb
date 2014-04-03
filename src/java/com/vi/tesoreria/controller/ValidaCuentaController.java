/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.tesoreria.controller;

import com.paideia.tesoreria.services.PlanillaService;
import com.paideia.tesoreria.to.SemtTO;
import com.vi.comun.exceptions.ValidacionException;
import com.vi.usuarios.controller.SessionController;
import com.vi.usuarios.dominio.Users;
import com.vi.util.FacesUtil;
import java.io.FileInputStream;
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
    List<SemtTO> semts;
    
    //Para permitir la descarga de archivos
    private String rutaArchivo;
    private StreamedContent file;
    private boolean renderDownload = false;
    
    
     public void cargar(FileUploadEvent event) {
         try {
             Users usr = ((SessionController)FacesUtil.getManagedBean("#{sessionController}")).getUsuario();
             semts = service.cargar(event.getFile().getInputstream(), event.getFile().getFileName(), usr.getLicencia().getNoLicencia());
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

   
    
}
