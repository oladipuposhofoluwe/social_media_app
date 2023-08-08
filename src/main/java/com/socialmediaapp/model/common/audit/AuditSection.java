package com.socialmediaapp.model.common.audit;


import com.socialmediaapp.utils.CloneUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;


@Embeddable
public class AuditSection implements Serializable {

    private static final long serialVersionUID = -1934446958975060889L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED")
    private Date dateCreated;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_MODIFIED")
    private Date dateModified;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "DELETED", columnDefinition = "varchar(1)")
    private String delF = "0";

    public AuditSection() {
    }

    public Date getDateCreated() {
        return CloneUtils.clone(dateCreated);
    }

    public void setDateCreated(Date dateCreated) {
        var dateClone = CloneUtils.clone(dateCreated);
        if (dateClone != null) {
            this.dateCreated = dateClone;
        }
    }

    public void setDateModified(Date dateModified) {
        var dateClone = CloneUtils.clone(dateCreated);
        if (dateClone != null) {
            this.dateModified = dateClone;
        }
    }

    public String getDelF() {
        return delF;
    }

    public void setDelF(String delF) {
        this.delF = delF;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return "AuditSection{" +
                "dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", delF='" + delF + '\'' +
                '}';
    }
}
