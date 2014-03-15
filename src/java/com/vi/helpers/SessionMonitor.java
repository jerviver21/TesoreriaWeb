/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.helpers;

import com.vi.comun.services.CommonServicesLocal;
import javax.ejb.EJB;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author jerviver21
 */
public class SessionMonitor implements HttpSessionListener{
    @EJB
    CommonServicesLocal service;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession sesion = event.getSession();
        if( sesion!= null &&  sesion.getAttribute("usr") != null){
            service.insertAudSesion((String)sesion.getAttribute("usr"), "FIN");
        }
    }
    
}
