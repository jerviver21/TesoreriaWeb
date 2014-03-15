/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.tesoreria.controller;

import com.paideia.tesoreria.dominio.Notificacion;
import com.paideia.tesoreria.dominio.RegistroPago;
import com.paideia.tesoreria.services.TeleceService;
import com.paideia.tesoreria.to.TeleceTO;
import com.vi.usuarios.controller.SessionController;
import com.vi.usuarios.dominio.Users;
import com.vi.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author jerviver21
 */

@ManagedBean(name="teleceController")
@SessionScoped

public class TeleceControler {
    
    @EJB
    TeleceService service;
    
    List<RegistroPago> registros = new ArrayList();
    private List<Notificacion> notificaciones = new ArrayList();
    
    private String cargados = "";
    String registroArchivos = "";
    String nombreArchivo="";
    int numArchivos = 0;
    private int noInconsistencias = 0;
    
    
    
    
     public void cargar(FileUploadEvent event) {
         try {
             Users usr = ((SessionController)FacesUtil.getManagedBean("#{sessionController}")).getUsuario();
             TeleceTO dto = service.cargar(event.getFile().getInputstream(), event.getFile().getFileName(), usr.getLicencia().getNoLicencia());
             nombreArchivo = event.getFile().getFileName();
             registroArchivos += nombreArchivo==null?"":nombreArchivo+"-"+dto.getPagos().size()+"  **  ";
             registros.addAll(dto.getPagos());
             notificaciones.addAll(dto.getNotificaciones());
             numArchivos++;
             noInconsistencias += dto.getNoInconsistencias();
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
    }  
     
    public String cargarInfoBD() {
         try {
             service.guardarInfoBD(registros, notificaciones);
             FacesUtil.addMessage(FacesUtil.INFO, "Cargados:  "+registros.size()+" - No Archivos: "+numArchivos);
             registros = new ArrayList<RegistroPago>();
             notificaciones = new ArrayList<Notificacion>();
             numArchivos = 0;
             noInconsistencias  = 0;
             cargados += registroArchivos;
             registroArchivos = "";
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
        
         return null;
    } 

    /**
     * @return the registros
     */
    public List<RegistroPago> getRegistros() {
        return registros;
    }

    /**
     * @param registros the registros to set
     */
    public void setRegistros(List<RegistroPago> registros) {
        this.registros = registros;
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
     * @return the notificaciones
     */
    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    /**
     * @param notificaciones the notificaciones to set
     */
    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * @return the noInconsistencias
     */
    public int getNoInconsistencias() {
        return noInconsistencias;
    }

    /**
     * @param noInconsistencias the noInconsistencias to set
     */
    public void setNoInconsistencias(int noInconsistencias) {
        this.noInconsistencias = noInconsistencias;
    }
    
}
