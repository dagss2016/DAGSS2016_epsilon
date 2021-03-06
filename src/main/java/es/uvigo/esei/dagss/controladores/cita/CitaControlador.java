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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

@Named(value = "citaControlador")
@SessionScoped
public class CitaControlador implements Serializable {

    static final public Integer DURACION_CITA_POR_DEFECTO = 15; // Citas de 15 minutos

    List<Cita> citas;
    Cita citaActual;

    @Inject
    MedicoDAO medicoDAO;

    @Inject
    PacienteDAO pacienteDAO;

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
        this.citas = citaDAO.buscarTodosByMedico(medicoControlador.getMedicoActual().getId());
    }

    public EstadoCita[] getEstadosCitas() {
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

    public void doGuardarEditado() {
        citaActual = citaDAO.actualizar(citaActual);
        this.citas = citaDAO.buscarTodosByMedico(medicoControlador.getMedicoActual().getId());
    }

    public void doFinalizar() {
        citaActual.setEstado(EstadoCita.COMPLETADA);
        doGuardarEditado();
    }

    public void doAnular() {
        citaActual.setEstado(EstadoCita.ANULADA);
        doGuardarEditado();
    }

    public void doAusente() {
        citaActual.setEstado(EstadoCita.AUSENTE);
        doGuardarEditado();
    }
}
