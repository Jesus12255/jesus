/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huerta.dao;

import com.huerta.dao.exceptions.NonexistentEntityException;
import com.huerta.dto.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author jh949
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.huerta_practica-mod2-completa-sqlServer-sbAdmin2a_war_v2PU");

    public UsuarioJpaController() {
    }

    
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getCodiUsua();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCodiUsua();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public  int validar(String login, String pass) {
        try {
            EntityManager em = getEntityManager();
            StoredProcedureQuery procedureQuery = em
                    .createNamedStoredProcedureQuery("spvalidar");
            em.getTransaction().begin();
            procedureQuery.setParameter("plogin", login);
            procedureQuery.setParameter("ppass", "shakira"+pass);
            procedureQuery.execute();
            Object msg_out = procedureQuery.getOutputParameterValue("resultado");
            em.getTransaction().commit();
            String s=msg_out.toString();
            return Integer.parseInt(s);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }
    
    
    
    public  int cambiaClave(String plogin, String pass, String npass) {
        try {
            EntityManager em = getEntityManager();
            StoredProcedureQuery procedureQuery = em
                    .createNamedStoredProcedureQuery("spCambiarClave");
            em.getTransaction().begin();
            procedureQuery.setParameter("plogin", plogin);
            procedureQuery.setParameter("ppass", "shakira"+pass);
            procedureQuery.setParameter("npass", "shakira"+npass);
            procedureQuery.execute();
            Object msg_out = procedureQuery.getOutputParameterValue("resultado");
            em.getTransaction().commit();
            String s=msg_out.toString();
            return Integer.parseInt(s);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    
    public static void main(String[] args) {
        UsuarioJpaController usuJpa = new UsuarioJpaController(); 
        List<Usuario> usus = usuJpa.findUsuarioEntities(); 
        
        for (Usuario usu : usus) {
            System.out.println(usu.getLogiUsua());
        }
    }
    
}
