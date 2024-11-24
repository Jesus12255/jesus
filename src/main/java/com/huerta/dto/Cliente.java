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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCodiClie", query = "SELECT c FROM Cliente c WHERE c.codiClie = :codiClie"),
    @NamedQuery(name = "Cliente.findByNombClie", query = "SELECT c FROM Cliente c WHERE c.nombClie = :nombClie"),
    @NamedQuery(name = "Cliente.findByApellidosClie", query = "SELECT c FROM Cliente c WHERE c.apellidosClie = :apellidosClie"),
    @NamedQuery(name = "Cliente.findByNdniClie", query = "SELECT c FROM Cliente c WHERE c.ndniClie = :ndniClie"),
    @NamedQuery(name = "Cliente.findByEmailClie", query = "SELECT c FROM Cliente c WHERE c.emailClie = :emailClie"),
    @NamedQuery(name = "Cliente.findByCelfCLie", query = "SELECT c FROM Cliente c WHERE c.celfCLie = :celfCLie"),
    @NamedQuery(name = "Cliente.findByNaciClie", query = "SELECT c FROM Cliente c WHERE c.naciClie = :naciClie")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiClie")
    private Integer codiClie;
    @Size(max = 100)
    @Column(name = "nombClie")
    private String nombClie;
    @Size(max = 100)
    @Column(name = "apellidosClie")
    private String apellidosClie;
    @Size(max = 15)
    @Column(name = "ndniClie")
    private String ndniClie;
    @Size(max = 100)
    @Column(name = "emailClie")
    private String emailClie;
    @Size(max = 9)
    @Column(name = "celfCLie")
    private String celfCLie;
    @Column(name = "naciClie")
    @Temporal(TemporalType.DATE)
    private Date naciClie;

    public Cliente() {
    }

    public Cliente(Integer codiClie) {
        this.codiClie = codiClie;
    }

    public Integer getCodiClie() {
        return codiClie;
    }

    public void setCodiClie(Integer codiClie) {
        this.codiClie = codiClie;
    }

    public String getNombClie() {
        return nombClie;
    }

    public void setNombClie(String nombClie) {
        this.nombClie = nombClie;
    }

    public String getApellidosClie() {
        return apellidosClie;
    }

    public void setApellidosClie(String apellidosClie) {
        this.apellidosClie = apellidosClie;
    }

    public String getNdniClie() {
        return ndniClie;
    }

    public void setNdniClie(String ndniClie) {
        this.ndniClie = ndniClie;
    }

    public String getEmailClie() {
        return emailClie;
    }

    public void setEmailClie(String emailClie) {
        this.emailClie = emailClie;
    }

    public String getCelfCLie() {
        return celfCLie;
    }

    public void setCelfCLie(String celfCLie) {
        this.celfCLie = celfCLie;
    }

    public Date getNaciClie() {
        return naciClie;
    }

    public void setNaciClie(Date naciClie) {
        this.naciClie = naciClie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiClie != null ? codiClie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codiClie == null && other.codiClie != null) || (this.codiClie != null && !this.codiClie.equals(other.codiClie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.huerta.dto.Cliente[ codiClie=" + codiClie + " ]";
    }
    
}
