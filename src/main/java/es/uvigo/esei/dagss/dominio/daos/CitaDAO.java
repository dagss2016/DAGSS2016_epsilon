/*
 Proyecto Java EE, DAGSS-2014
 */

package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Cita;
import java.util.Date;
//import es.uvigo.esei.dagss.dominio.entidades.Medico;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;


@Stateless
@LocalBean
public class CitaDAO  extends GenericoDAO<Cita>{    

    public Cita buscarPorId(Integer id) {
        TypedQuery<Cita> q = em.createQuery("SELECT m FROM Cita AS m "
                                            + "  WHERE m.id = :id ", Cita.class);
        q.setParameter("id", id);

        return filtrarResultadoUnico(q);
    }

    public Cita buscarPorFecha(String fecha) {
        TypedQuery<Cita> q = em.createQuery("SELECT m FROM Cita AS m "
                                            + "  WHERE m.fecha = :fecha ", Cita.class);
        q.setParameter("fecha", fecha);
        
        return filtrarResultadoUnico(q);
    }

    public List<Cita> buscarPorPaciente(Long paciente_Id) {
        TypedQuery<Cita> q = em.createQuery("SELECT m FROM Cita AS m "
                + "  WHERE m.paciente.id = :paciente_Id", Cita.class);
        q.setParameter("paciente_Id",paciente_Id);        
        return q.getResultList();
    }
    
    public List<Cita> buscarTodosByMedico(Long medico_Id, Date hoy) {
        TypedQuery<Cita> q = em.createQuery("SELECT m FROM Cita AS m "
                + "  WHERE m.medico.id = :medico_Id AND m.fecha = :hoy", Cita.class);
        q.setParameter("medico_Id",medico_Id);
        q.setParameter("hoy",hoy);        
        return q.getResultList();    
    }

    // Completar aqui
}
