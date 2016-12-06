/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.controladores.cita;

import es.uvigo.esei.dagss.controladores.medico.MedicoControlador;
import es.uvigo.esei.dagss.dominio.daos.CitaDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicoDAO;
import es.uvigo.esei.dagss.dominio.daos.PacienteDAO;
import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.EstadoCita;
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
 
    static final public Integer DURACION_CITA_POR_DEFECTO = 15; // Citas de 15 minutos

    //@Inject
    //CitaDAO citaDAO;

    @Inject
    MedicoDAO medicoDAO;
    
    @Inject
    PacienteDAO pacienteDAO;

    List<Cita> citas;
    Cita citaActual;

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
    
    public EstadoCita[]  getEstadosCitas() {
        return EstadoCita.values();
    }
    
    public Cita getCitaActual() {
        return citaActual;
    }

    public void setCitaActual(Cita citaActual) {
        this.citaActual = citaActual;
    }

    public List<Cita> getCitas() {
        return citas;
    }
    
    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }    
}
