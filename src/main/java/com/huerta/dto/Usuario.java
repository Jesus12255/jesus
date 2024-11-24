/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huerta.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jh949
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByCodiUsua", query = "SELECT u FROM Usuario u WHERE u.codiUsua = :codiUsua"),
    @NamedQuery(name = "Usuario.findByNdniUsua", query = "SELECT u FROM Usuario u WHERE u.ndniUsua = :ndniUsua"),
    @NamedQuery(name = "Usuario.findByLogiUsua", query = "SELECT u FROM Usuario u WHERE u.logiUsua = :logiUsua"),
    @NamedQuery(name = "Usuario.findByPassUsua", query = "SELECT u FROM Usuario u WHERE u.passUsua = :passUsua"),
    @NamedQuery(name = "Usuario.findByNaciUsua", query = "SELECT u FROM Usuario u WHERE u.naciUsua = :naciUsua")})

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "spvalidar",
            procedureName = "sp_validar",
            parameters = {
                @StoredProcedureParameter(name = "plogin", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "ppass", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "resultado", type = Integer.class, mode = ParameterMode.OUT)}), 
    @NamedStoredProcedureQuery(
            name = "spCambiarClave",
            procedureName = "sp_cambiar_clave",
            parameters = {
                @StoredProcedureParameter(name = "plogin", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "ppass", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "npass", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "resultado", type = Integer.class, mode = ParameterMode.OUT)})
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiUsua")
    private Integer codiUsua;
    @Size(max = 15)
    @Column(name = "ndniUsua")
    private String ndniUsua;
    @Size(max = 50)
    @Column(name = "logiUsua")
    private String logiUsua;
    @Size(max = 500)
    @Column(name = "passUsua")
    private String passUsua;
    @Column(name = "naciUsua")
    @Temporal(TemporalType.DATE)
    private Date naciUsua;

    public Usuario() {
    }

    public Usuario(Integer codiUsua) {
        this.codiUsua = codiUsua;
    }

    public Integer getCodiUsua() {
        return codiUsua;
    }

    public void setCodiUsua(Integer codiUsua) {
        this.codiUsua = codiUsua;
    }

    public String getNdniUsua() {
        return ndniUsua;
    }

    public void setNdniUsua(String ndniUsua) {
        this.ndniUsua = ndniUsua;
    }

    public String getLogiUsua() {
        return logiUsua;
    }

    public void setLogiUsua(String logiUsua) {
        this.logiUsua = logiUsua;
    }

    public String getPassUsua() {
        return passUsua;
    }

    public void setPassUsua(String passUsua) {
        this.passUsua = passUsua;
    }

    public Date getNaciUsua() {
        return naciUsua;
    }

    public void setNaciUsua(Date naciUsua) {
        this.naciUsua = naciUsua;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiUsua != null ? codiUsua.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.codiUsua == null && other.codiUsua != null) || (this.codiUsua != null && !this.codiUsua.equals(other.codiUsua))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.huerta.dto.Usuario[ codiUsua=" + codiUsua + " ]";
    }
    
}
