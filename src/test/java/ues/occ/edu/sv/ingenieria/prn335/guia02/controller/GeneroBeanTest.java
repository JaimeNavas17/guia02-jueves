/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.guia02.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.mockito.junit.jupiter.MockitoExtension;
import ues.occ.edu.sv.ingenieria.prn335.guia02.entity.Genero;

/**
 *
 * @author jaime
 */
@ExtendWith(MockitoExtension.class)
public class GeneroBeanTest {

    Genero genero = new Genero();
    EntityManager EMmock;
    GeneroBean bean;

    /**
     * Metodo para cargar los objetos MOCK antes de ejecutar cada prueba
     */
    public void inicio() {
        EMmock = Mockito.mock(EntityManager.class);
        EntityTransaction TXmock = Mockito.mock(EntityTransaction.class);
        Mockito.when(EMmock.getTransaction()).thenReturn(TXmock);
        bean = new GeneroBean();
        bean.em = EMmock;
    }

    /**
     * Metodo que contiene la prueba para el metodo create
     */
    @Test
    public void createTest() {
        inicio();
        bean.create(genero);
        Mockito.verify(EMmock, Mockito.times(1)).persist(genero);
    }

    /**
     * Metodo que contiene la prueba para el metodo update
     */
    @Test
    public void updateTest() {
        inicio();
        bean.update(genero);
        Mockito.verify(EMmock, Mockito.times(1)).merge(genero);
    }

    /**
     * Metodo que contiene la prueba para el metodo personalizado asignado en la instructoria
     */
    @Test
    public void generosFiltradosTest() {
        GeneroBean gen = new GeneroBean();
        String[] esperado = {"acción", "terror", "horror", "ficción", "guerra", "suspenso"};
        List<Genero> lista = gen.generosFiltrados("drama comedia romance acción terror "
                + "musical horror ficción guerra wester "
                + "crimen psicológico suspenso noir blanco&negro biográfico");
        String[] recibido = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            recibido[i] = lista.get(i).getNombre();
        }
        Assertions.assertArrayEquals(esperado, recibido);
    }

}
