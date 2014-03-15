/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.tesoreria.controller;


import com.paideia.tesoreria.services.DetraService;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author jerviver21
 */

@ManagedBean(name="detraController") 
@SessionScoped
public class DetraController {
    
    @EJB
    DetraService service;
    
    //private List<Detracciones> registros = new ArrayList<Detracciones>();
    private List<SelectItem> adquirientes;
    private ComboLocator locator;
    
    
    //private Adquiriente adq;
    private int periodo;
    String nombreArchivo;
    
    @PostConstruct
    public void init(){
        Calendar calendario = Calendar.getInstance();
        //setAdq(new Adquiriente(2l));
        setPeriodo(Integer.parseInt(calendario.get(Calendar.YEAR)+""+String.format("%02d",calendario.get(Calendar.MONTH)+1)));
        locator = ComboLocator.getInstance();
        adquirientes = FacesUtil.getSelectsItem(locator.getDataForCombo(ComboLocator.COMB_ID_ADQ));
    }
    
    
     public void cargar(FileUploadEvent event) {
         try {
             nombreArchivo = event.getFile().getFileName();
             //registros = service.cargar(event.getFile().getInputstream(), event.getFile().getFileName(), getAdq(), getPeriodo());
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }
    }  
     
    public String cargarInfoBD() {
         /*try {
             int i = 0;
             for(Detracciones detraccion : registros){
                 if(detraccion.getSerieFact().equals("X")){
                     FacesUtil.addMessage(FacesUtil.ERROR, "Error: Edite no serie factura valido, en Fila: "+i+" - "+detraccion.getIdProveedor().getRazonSocial());
                     return null;
                 }
                 if(detraccion.getCorFact().equals("X")){
                     FacesUtil.addMessage(FacesUtil.ERROR, "Error: Edite un correlativo de factura valido, en Fila: "+i+" - "+detraccion.getIdProveedor().getRazonSocial());
                     return null;
                 }
                 
                 if(detraccion.getCodOperacion().length() != 2 || detraccion.getCodOperacion().equals("X")){
                     FacesUtil.addMessage(FacesUtil.ERROR, "Error: Hay un codigo de operacion con longitud incorrecta en Fila: "+i+" - "+detraccion.getIdProveedor().getRazonSocial());
                     return null;
                 }
                 if(detraccion.getCodServicio().length() != 3 || detraccion.getCodServicio().equals("X")){
                     FacesUtil.addMessage(FacesUtil.ERROR, "Error: Hay un codigo de servicio con longitud incorrecta en Fila: "+i+" - "+detraccion.getIdProveedor().getRazonSocial());
                     return null;
                 }
                 
                 if(detraccion.getIdProveedor().getNoCuenta().length() != 11){
                     FacesUtil.addMessage(FacesUtil.ERROR, "Error: Hay un numero de cuenta con longitud incorrecta en Fila: "+i+" - "+detraccion.getIdProveedor().getRazonSocial());
                     return null;
                 }
                 
                 if(detraccion.getIdProveedor().getRuc().length() != 11){
                     FacesUtil.addMessage(FacesUtil.ERROR, "Error: Hay un RUC con longitud incorrecta en Fila: "+i+" - "+detraccion.getIdProveedor().getRazonSocial());
                     return null;
                 }
                 i++;
             }
             
             service.guardarInfoBD(registros, adq, nombreArchivo);
             registros = new ArrayList<Detracciones>();
             FacesUtil.addMessage(FacesUtil.INFO, "Información cargada con exito ");
         } catch (Exception e) {
             FacesUtil.addMessage(FacesUtil.ERROR, "Error: Tome un pantallazo y envie a jerviver21@gmail.com "+e.getMessage());
             e.printStackTrace();
         }*/
        
         return null;
    } 
    



    /**
     * @return the adquiriente
     */
    public List<SelectItem> getAdquirientes() {
        return adquirientes;
    }

    
    /**
     * @return the periodo
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
    
}
