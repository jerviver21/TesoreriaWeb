package com.vi.util;

import com.vi.comun.controller.GeneralController;
import com.vi.comun.util.Log;
import com.vi.usuarios.dominio.Menu;
import com.vi.usuarios.dominio.Resource;
import static com.vi.util.FacesUtil.ERROR;
import static com.vi.util.FacesUtil.INFO;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author Jerson Viveros
 */
public class FacesUtil {
    public static final int INFO = 1;
    public static final int ERROR = 2;
    private static final long MENU_RAIZ = 1;

    

    public static Object getManagedBean(String controller){
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ex = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(context,
                controller, Object.class);
        return ex.getValue(context);
    }
    
    public static void restartBean (String bean){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(bean);
    }
    
    public static String getUsuario(){
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken)FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return principal.getName();
    }

    public static String getPaginaRequest(){
        return ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
    }

    public static String getRequestParameter(String parametro){
        return ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter(parametro);
    }

    public static void addMessage(int tipo, String mensaje){
        FacesMessage message = new FacesMessage();
        switch(tipo){
            case INFO:
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                break;
            case ERROR:
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                break;
        }
        message.setSummary(mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static List<SelectItem> getSelectsItem(Map opciones){
        List<SelectItem> items = new ArrayList<SelectItem>();
        Set<Object> pks = opciones.keySet();
        for(Object pk : pks ){
            SelectItem item = new SelectItem();
            item.setValue(pk);
            item.setLabel(opciones.get(pk).toString());
            items.add(item);
        }
        return items;
    }
    
    public static List<SelectItem> getSelectsItem(List opciones){
        List<SelectItem> items = null;
        try {
            items = new ArrayList<SelectItem>();
            for(Object pk : opciones ){
                SelectItem item = new SelectItem();
                Class clase = pk.getClass();
                Method metodoId = clase.getMethod("getId", new Class[]{});
                item.setValue(metodoId.invoke(pk, new Object[]{}));
                item.setLabel(pk.toString());
                items.add(item);
            }    
        } catch (Exception e) {
            System.out.println("La lista, contiene objetos sin el método getId");
            Log.getLogger().log(Level.SEVERE, "La lista contiene objetos sin el método getId");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return items;
    }
    
    public static List<SelectItem> getSelectsItem(List opciones, String methodId, String methodValue){
        List<SelectItem> items = null;
        try {
            items = new ArrayList<SelectItem>();
            for(Object pk : opciones ){
                SelectItem item = new SelectItem();
                Class clase = pk.getClass();
                Method metodoId = clase.getMethod(methodId, new Class[]{});
                Method metodoValue = clase.getMethod(methodValue, new Class[]{});
                item.setValue(metodoId.invoke(pk, new Object[]{}));
                item.setLabel(metodoValue.invoke(pk, new Object[]{}).toString());
                items.add(item);
            }    
        } catch (Exception e) {
            System.out.println("La lista, contiene objetos sin el método getId");
            Log.getLogger().log(Level.SEVERE, "La lista contiene objetos sin el método getId");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return items;
    }

    public static MenuModel getMenu(Set<Resource> recursos){
        String language = ((GeneralController)FacesUtil.getManagedBean("#{generalController}")).getLocale();
        String nombre;
        Map<Long, DefaultSubMenu> menus = new HashMap<Long, DefaultSubMenu>();
        Map<Long, Boolean> menuInModel = new HashMap<Long, Boolean>();
        MenuModel model = new DefaultMenuModel();

        for(Resource recurso: recursos){
            if(recurso.getIdioma() == null || (recurso.getIdioma() != null && !recurso.getIdioma().equals(language))){
                continue;
            }
            
            DefaultMenuItem item = new DefaultMenuItem();
            nombre = recurso.getNombre();
            item.setValue(nombre);
            item.setUrl(recurso.getUrl());
            Menu menu = recurso.getMenu();
            if(menu.getId() == MENU_RAIZ){
                model.addElement(item);
                continue;
            }

            DefaultSubMenu submenu = menus.get(menu.getId());
            if(submenu == null){
                submenu = new DefaultSubMenu();
                nombre = menu.getNombre();
                submenu.setLabel(nombre);
                menus.put(menu.getId(), submenu);
                menuInModel.put(menu.getId(), Boolean.FALSE);
            }
            submenu.addElement(item);

            Menu menuPadre = menu.getMenuPadre();
            long  idMenuPadre = menuPadre.getId();
            
            
            if(idMenuPadre == MENU_RAIZ && !menuInModel.get(menu.getId())){
                model.addElement(submenu);  
                menuInModel.put(menu.getId(), Boolean.TRUE);
            }

            while (idMenuPadre != MENU_RAIZ){
                DefaultSubMenu submenuPadre = menus.get(idMenuPadre);
                if(submenuPadre == null){
                    submenuPadre = new DefaultSubMenu();
                    nombre = menuPadre.getNombre();
                    submenuPadre.setLabel(nombre);
                    menus.put(idMenuPadre, submenuPadre);
                }else{
                    break;
                }
                submenuPadre.addElement(submenu);
                menuPadre = menuPadre.getMenuPadre();
                idMenuPadre = menuPadre.getId();
                
                if(idMenuPadre == MENU_RAIZ){
                    model.addElement(submenuPadre);
                }else{
                    submenu = submenuPadre;
                }
            }

        }

        return model;
    }




}
