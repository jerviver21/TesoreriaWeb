/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.tesoreria.controller;


import com.paideia.tesoreria.dominio.Detraccion;
import com.paideia.tesoreria.dominio.Empresa;
import com.paideia.tesoreria.services.DetraService;
import com.paideia.tesoreria.services.EmpresaService;
import com.paideia.tesoreria.services.GeneralService;
import com.vi.locator.ComboLocator;
import com.vi.usuarios.controller.SessionController;
import com.vi.usuarios.dominio.Users;
import com.vi.util.FacesUtil;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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

@ManagedBean(name="detraController") 
@SessionScoped
public class DetraController {
    
    @EJB
    DetraService service;
    @EJB
    EmpresaService eService;
    @EJB
    GeneralService gService;
   

    String nombreArchivo;
    
    private Users usr;
    private Empresa empresa;
    
    
    private List<Detraccion> comprobantes;
    

    //Para permitir la descarga de archivos
    private String rutaArchivo;
    private StreamedContent file;
    private boolean renderDownload = false;
    
    //Para cargar los servicios
    private String codigo;
    private String descripcion;
    
    @PostConstruct
    public void init(){
        usr = ((SessionController)FacesUtil.getManagedBean("#{sessionController}")).getUsuario();
        empresa = eService.findEmpresaByLicencia(usr.getLicencia().getNoLicencia());
        comprobantes = new ArrayList<Detraccion>();
    }
    
     public void cargarArchivoEmpresa(FileUploadEvent event) {
         try {
             rutaArchivo = service.generarDetracciones(event.getFile().getInputstream(), event.getFile().getFileName(), empresa);
             renderDownload = true;
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error:  "+e.getMessage());
             e.printStackTrace();
         }
    }  
     
    public void cargarArchDetracciones(FileUploadEvent event) {
         try {
             //Temporalmente se utiliza sólo para cargar numeros de cuenta de banco de la nación
             service.cargarCuentas(event.getFile().getInputstream(), event.getFile().getFileName(), usr.getLicencia().getNoLicencia());
             FacesUtil.restartBean("detraController");
             FacesUtil.addMessage(FacesUtil.INFO, "Archivo Cargado con Exito! ");
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
    } 
     
    public void cargarComprobantes(FileUploadEvent event) {
         try {
             comprobantes = service.cargarComprobantes(event.getFile().getInputstream(), event.getFile().getFileName());
             FacesUtil.addMessage(FacesUtil.INFO, "Comprobantes cargados con exito ");
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
    } 
    
    public String cargarServicio(){
         try {
             gService.cargarServicio(codigo, descripcion);
             FacesUtil.addMessage(FacesUtil.INFO, "Servicio Guardado con Exito!");
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Puede que ya exista el servicio "+e.getMessage());
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
     * @return the comprobantes
     */
    public List<Detraccion> getComprobantes() {
        return comprobantes;
    }

    /**
     * @return the rutaArchivo
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * @param rutaArchivo the rutaArchivo to set
     */
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
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
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
