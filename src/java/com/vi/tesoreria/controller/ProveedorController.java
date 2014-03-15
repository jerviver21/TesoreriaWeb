package com.vi.tesoreria.controller;

import com.paideia.tesoreria.dominio.CuentasProveedor;
import com.paideia.tesoreria.dominio.Proveedor;
import com.paideia.tesoreria.services.ProveedorService;
import com.vi.comun.util.Log;
import com.vi.locator.ComboLocator;
import com.vi.usuarios.controller.SessionController;
import com.vi.usuarios.dominio.Rol;
import com.vi.usuarios.dominio.Users;
import com.vi.usuarios.services.UsuariosServicesLocal;
import com.vi.util.FacesUtil;
import java.util.Set;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author jerviver21
 */

@ManagedBean(name="provedorController")
@SessionScoped
public class ProveedorController {
    //Datos de la Factura
    private Proveedor provedor;
    private CuentasProveedor cuenta;
    
    private Users usr;
    
    //Servicios
    @EJB
    ProveedorService pService;
    
    @EJB 
    UsuariosServicesLocal uService;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    boolean tesorero = false;
    
    
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        usr = ((SessionController)FacesUtil.getManagedBean("#{sessionController}")).getUsuario();
        Set<String> roles = uService.findRolesUser(usr.getUsr());
        tesorero = roles.contains("TESORERO");
        
        setProvedor(pService.findProveedorByRuc(usr.getUsr()));
        if(provedor == null){
            provedor = new Proveedor();
        }
        cuenta = new CuentasProveedor();
    }
    
    public String guardar(){
        try {
            provedor = pService.saveProveedor(provedor);
            usr.setNumId(provedor.getRuc());
            uService.edit(usr);
            FacesUtil.addMessage(FacesUtil.INFO,"Provedor guardado con exito!!");
            FacturaController fController = (FacturaController)FacesUtil.getManagedBean("#{facturaController}");
            if(fController != null){
                fController.setSolicitarActualizacion(false);
            }
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR,"Error al guardar el provedor");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    public String guardarCuenta(){
        if(cuenta.getNoCuenta().length() == 13){
            cuenta.setNoCuenta(cuenta.getNoCuenta().replaceAll("(\\d{3})(\\d{7})(\\d{1})(\\d{2})", "$1-$2-$3-$4"));
        }
        if(cuenta.getNoCuenta().length() == 14){
            cuenta.setNoCuenta(cuenta.getNoCuenta().replaceAll("(\\d{3})(\\d{8})(\\d{1})(\\d{2})", "$1-$2-$3-$4"));
        }
        if(cuenta.getNoCuenta().length() == 20){
            cuenta.setNoCuenta(cuenta.getNoCuenta().replaceAll("(\\d{3})(\\d{3})(\\d{12})(\\d{2})", "$1-$2-$3-$4"));
        }
        try {
            if(provedor.getId() == null){
                cuenta.setProveedor(provedor);
                provedor.getCuentas().add(cuenta);
                cuenta = new CuentasProveedor();
            }else{
                cuenta.setProveedor(provedor);
                pService.saveCuenta(cuenta);
                provedor = pService.findProveedorByRuc(provedor.getRuc());
            }
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR,"Error al guardar cuenta");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return "/tesoreria/datos_provedor.xhtml";
    }
    
    public String borrarCuenta(CuentasProveedor cb){
        try {
            if(provedor.getId() == null){
                for(int i = 0; i<provedor.getCuentas().size(); i++){
                    CuentasProveedor cp = provedor.getCuentas().get(i);
                    if(cp.getNoCuenta().equals(cb.getNoCuenta())){
                        provedor.getCuentas().remove(i);
                        break;
                    }
                }
            }else{
                pService.borrarCuenta(cb);
                provedor = pService.findProveedorByRuc(provedor.getRuc());
            }
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR,"Error al guardar el provedor");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    public void consultarProvedor(){
        System.out.println("****"+provedor.getRuc()+"******");
        Proveedor p = pService.findProveedorByRuc(provedor.getRuc());
        if(p != null){
            provedor = p;
        }
    }
    
    public String goCuenta(){
        return "/tesoreria/cuenta_proveedor.xhtml";
    }
    

    /**
     * @return the provedor
     */
    public Proveedor getProvedor() {
        return provedor;
    }

    /**
     * @param provedor the provedor to set
     */
    public void setProvedor(Proveedor provedor) {
        this.provedor = provedor;
    }

    /**
     * @return the cuenta
     */
    public CuentasProveedor getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(CuentasProveedor cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the disableRuc
     */
    public String getDisableRuc() {
        return tesorero?"false":"true";
    }


    
}
