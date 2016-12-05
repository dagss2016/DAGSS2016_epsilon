/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.controladores.cita;

import es.uvigo.esei.dagss.controladores.medico.MedicoControlador;
import es.uvigo.esei.dagss.dominio.daos.CitaDAO;
import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.Paciente;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author rbr
 */
@Named(value = "citaControlador")
@SessionScoped
public class CitaControlador implements Serializable {
 
    List<Cita> citas;
    Cita citaActual;
    
    private Integer id;
    private String fecha;

    @Inject
    private MedicoControlador medicoControlador;
  
    @EJB
    private CitaDAO citaDAO;

    /**
     * Creates a new instance of CitaControlador
     */
    public CitaControlador() {
    }
    
    @PostConstruct
    public void inicializar() {
        this.citas = citaDAO.buscarTodosByMedico(medicoControlador.getMedicoActual().getId(),Calendar.getInstance().getTime());
    }
    
    public Cita getCitaActual() {
        return citaActual;
    }

    public void setCitaActual(Cita citaActual) {
        this.citaActual = citaActual;
    }

    private Cita recuperarDatosCita() {
        Cita cita = null;
        if (id != null) {
            cita = citaDAO.buscarPorId(id);
        }
        /*if ((cita == null) && (numeroColegiado != null)) {
            cita = citaDAO.buscarPorNumeroColegiado(numeroColegiado);
        }*/
        if ((cita == null) && (fecha != null)) {
            cita = citaDAO.buscarPorFecha(fecha);
        }
        
        return cita;
    }    

    public List<Cita> getCitas() {
        return citas;
    }
    
    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
