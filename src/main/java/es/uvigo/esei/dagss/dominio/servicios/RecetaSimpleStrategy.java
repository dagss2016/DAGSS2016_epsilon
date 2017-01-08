/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.dominio.servicios;

import es.uvigo.esei.dagss.dominio.entidades.EstadoReceta;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecetaSimpleStrategy implements RecetaStrategy {

    static final public Integer MARGEN_VALIDEZ = 7; // Margen validez (dias)

    @Override
    public List<Receta> generarRecetas(Prescripcion p) {

        List<Receta> recetas = new ArrayList<>();
        Date inicio = (Date) p.getFechaInicio();
        Date fin = (Date) p.getFechaFin();
        int numDias = calculaDias(inicio, fin);
        //Calculamos numero de botes necesarios      
        int dosisTotales = numDias * p.getDosis();
        int numBotes = (int) Math.ceil(dosisTotales / (double) p.getMedicamento().getNumeroDosis());
        int diasPorBote = (int) Math.round(p.getMedicamento().getNumeroDosis() / (double) p.getDosis());
        Calendar calendario_ini = Calendar.getInstance();
        Calendar calendario_fin = Calendar.getInstance();
        calendario_ini.setTime(inicio);
        //Iteramos por bote para ir creando recetas.
        for (int i = 0; i < numBotes; i++) {
            calendario_fin.setTime(calendario_ini.getTime());
            calendario_fin.add(Calendar.DAY_OF_MONTH, MARGEN_VALIDEZ);
            Receta receta = new Receta(p, 1,calendario_ini.getTime(),calendario_fin.getTime(),EstadoReceta.GENERADA);
            recetas.add(receta);
            calendario_ini.add(Calendar.DAY_OF_MONTH, diasPorBote);
        }
        return recetas;
    }

    //Cuenta dias entre dos fechas.
    private int calculaDias(Date inicio, Date fin) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(inicio);
        int dias = 1; //Se inicializa a 1 para que cuente el dia actual
        while (!calendario.getTime().after(fin)) {
            calendario.add(Calendar.DAY_OF_MONTH, 1);
            dias++;
        }
        return dias;
    }
}
