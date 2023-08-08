package com.socialmediaapp.model.common.audit;

import com.socialmediaapp.utils.DateUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditListener {

    @PrePersist
    public void onSave(Object o) {
        if (o instanceof Auditable) {
            Auditable audit = (Auditable) o;
            AuditSection auditSection = audit.getAuditSection();
            auditSection.setDateModified(DateUtil.now());
            String createdBy = getUsernameOfAuthenticatedUser();
            auditSection.setCreatedBy(createdBy);
            auditSection.setModifiedBy(createdBy);
            if (auditSection.getDateCreated() == null) {
                auditSection.setDateCreated(DateUtil.now());
            }
            audit.setAuditSection(auditSection);
        }
    }

    @PreUpdate
    public void onUpdate(Object o) {
        if (o instanceof Auditable) {
            Auditable audit = (Auditable) o;
            AuditSection auditSection = audit.getAuditSection();
            if(auditSection!=null) {
                auditSection.setDateModified(DateUtil.now());
                auditSection.setModifiedBy(getUsernameOfAuthenticatedUser());

                if (auditSection.getDateCreated() == null) {
                    auditSection.setDateCreated(DateUtil.now());
                }
                audit.setAuditSection(auditSection);
            }
        }
    }

    private String getUsernameOfAuthenticatedUser() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return "anonymous user";
            }
            Object principal = authentication.getPrincipal();
            if (principal.getClass().equals(String.class)) {
                return (String) principal;
            }

        }catch(Exception e){
           e.printStackTrace();
        }
        return "anonymous user";
    }
}
