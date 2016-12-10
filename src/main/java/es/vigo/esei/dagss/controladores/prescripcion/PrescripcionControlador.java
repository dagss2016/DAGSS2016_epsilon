/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.vigo.esei.dagss.controladores.prescripcion;

import es.uvigo.esei.dagss.controladores.cita.CitaControlador;
import es.uvigo.esei.dagss.controladores.medico.MedicoControlador;
import es.uvigo.esei.dagss.dominio.daos.PrescripcionDAO;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;


/**
 *
 * @author rbr
 */
@Named(value = "prescripcionControlador")
@SessionScoped
public class PrescripcionControlador implements Serializable {
    
    //PrescripcionDAO prescripcionDAO;

    List<Prescripcion> prescripciones;
    Prescripcion prescripcionActual;
    
    @Inject
    private MedicoControlador medicoControlador;
    
    @Inject
    private CitaControlador citaControlador;
  
    @EJB
    private PrescripcionDAO prescripcionDAO;
    
    /**
     * Creates a new instance of PrescripcionControlador
     */
    public PrescripcionControlador() {
    }
    
    @PostConstruct
    public void inicializar() {       
        this.prescripciones = prescripcionDAO.buscarPorPaciente(citaControlador.getCitaActual().getPaciente().getId(),medicoControlador.getMedicoActual().getId());
    }
    
    public List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    public void setPrescripciones(List<Prescripcion> prescripciones) {
        this.prescripciones = prescripciones;
    }

    public Prescripcion getPrescripcionActual() {
        return prescripcionActual;
    }

    public void setPrescripcionActual(Prescripcion prescripcionActual) {
        this.prescripcionActual = prescripcionActual;
    }   
}
