/*
 Proyecto Java EE, DAGSS-2016
 */
package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class PrescripcionDAO extends GenericoDAO<Prescripcion> {

    public Prescripcion buscarPorIdConRecetas(Long id) {
        TypedQuery<Prescripcion> q = em.createQuery("SELECT p FROM Prescripcion AS p JOIN FETCH p.recetas"
                + "  WHERE p.id = :id", Prescripcion.class);
        q.setParameter("id", id);

        return q.getSingleResult();
    }

    public List<Prescripcion> buscarPorPaciente(Long paciente_Id, Long medico_Id) {
        TypedQuery<Prescripcion> q = em.createQuery("SELECT p FROM Prescripcion AS p "
                + "  WHERE p.paciente.id = :paciente_Id AND p.medico.id = :medico_Id " + "ORDER BY p.fechaInicio", Prescripcion.class);
        q.setParameter("paciente_Id", paciente_Id);
        q.setParameter("medico_Id", medico_Id);
        return q.getResultList();
    }

    public List<Prescripcion> buscarPorTarjetaSanitaria(String numeroTarjetaSanitaria) {
        TypedQuery<Prescripcion> q = em.createQuery("SELECT p FROM Prescripcion AS p "
                + "  WHERE p.paciente.numeroTarjetaSanitaria = :numeroTarjetaSanitaria", Prescripcion.class);
        q.setParameter("numeroTarjetaSanitaria", numeroTarjetaSanitaria);

        return q.getResultList();
    }
}
