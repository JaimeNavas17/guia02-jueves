/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.guia02.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import ues.occ.edu.sv.ingenieria.prn335.guia02.entity.Genero;

/**
 *
 * @author jaime
 */
public class GeneroBean implements Serializable {

    /**
     * Declaracion del EntityManager
     */
    protected EntityManager em;

    /**
     * Metodo para obtener el EntityManager haciendo uso del
     * EntityManagerFactory
     */
    private void getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");
        this.em = emf.createEntityManager();
    }

    /**
     * Metodo para obtener la transaccion, basado en el EntityManager
     *
     * @return un objeto de tipo EntityTransacction
     */
    private EntityTransaction getTx() {
        if (this.em != null) {
            return em.getTransaction();
        }
        return null;
    }

    /**
     * Metodo para agregar registros Recibe un objeto de tipo Genero
     *
     * @param genero es el objeto de la clase Genero
     */
    public void create(Genero genero) {
        EntityTransaction tx = this.getTx();
        if (genero != null && tx != null) {
            try {
                tx.begin();
                this.em.persist(genero);
                tx.commit();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * Metodo para editar registros Recibe un objeto de tipo Genero
     *
     * @param genero es el objeto de la clase Genero
     */
    public void update(Genero genero) {
        EntityTransaction tx = this.getTx();
        if (genero != null && tx != null) {
            try {
                tx.begin();
                this.em.merge(genero);
                tx.commit();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * Metodo personalizado asignado en la practica Separa los nombres de los
     * generos que contengan una consonante repetida consutivamente en su nombre
     *
     * @param Generos es una cadena de texto con los nombres de los generos a
     * evaluar
     * @return una lista de tipo Genero con los generos que cumplen la
     * estructura
     */
    public List<Genero> generosFiltrados(String Generos) {
        List<Genero> Nombre = new ArrayList<>();
        String[] generos = Generos.split(" ");
        String regexp = "[^\\w\\s[^aeiouAEIOU]]|(.)\\1"; //Expresion regular para dos consonantes consecutivas
        Pattern p = Pattern.compile(regexp);
        for (String genero : generos) {
            int cont = 0;
            Matcher m = p.matcher(genero);
            String nueva = "";
            String anterior = "";
            while (m.find()) {                  //recorre el nombre para buscar consonantes repetidas
                anterior = nueva;
                nueva = m.group();
                if (anterior.equals(nueva)) {   //verifica que la letra anterior sea igual a la actual
                    cont++;
                } else {
                    cont = 0;
                }
                if (cont == 1) {                //si las letras se repiten agrega el nombre a la lista
                    Genero item = new Genero();
                    item.setNombre(genero);
                    Nombre.add(item);
                }
            }

        }
        return Nombre;
    }
}
