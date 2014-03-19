package com.vi.usuarios.controller;

import com.paideia.tesoreria.dominio.CuentasProveedor;
import com.paideia.tesoreria.dominio.Proveedor;
import com.paideia.tesoreria.usuarios.MasivoService;
import com.vi.locator.ComboLocator;
import com.vi.usuarios.dominio.Users;
import com.vi.util.FacesUtil;
import com.vi.util.SpringUtils;
import java.io.FileInputStream;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * @author jerviver21
 */
@ManagedBean(name="masivoController")
@SessionScoped
public class MasivoController {
    
    //Para permitir la descarga de archivos
    private String rutaArchivo;
    private StreamedContent file;
    private boolean renderDownload = false;
    
    
    @EJB
    MasivoService mService;
    
    private Users usr;
    
    @PostConstruct
    public void init(){
        usr = ((SessionController)FacesUtil.getManagedBean("#{sessionController}")).getUsuario();
    }
    
    
    public void cargar(FileUploadEvent event) {
         try {
             rutaArchivo = mService.generaUsuarios(event.getFile().getInputstream(), event.getFile().getFileName(), 
                     usr.getLicencia(), SpringUtils.getPasswordEncoder());
             renderDownload = true;
             FacesUtil.addMessage(FacesUtil.INFO, "Descargue el archivo, con las claves de sus proveedores y contactelos para que actualicen su información y carguen sus facturas al sistema ");
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
    } 
    
    
    public void descargar(ActionEvent evt)throws Exception{
        
        try {
                if(rutaArchivo == null){
                    setRenderDownload(false);
                    return;
                }
                FileInputStream stream = new FileInputStream(rutaArchivo);
                setFile(new DefaultStreamedContent(stream, "application/csv", "USUARIOS.CSV"));
                setRenderDownload(false);

         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
        
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