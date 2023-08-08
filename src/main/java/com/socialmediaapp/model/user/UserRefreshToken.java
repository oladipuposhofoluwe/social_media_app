package com.socialmediaapp.model.user;


import com.socialmediaapp.model.common.audit.AuditListener;
import com.socialmediaapp.model.common.audit.AuditSection;
import com.socialmediaapp.model.common.audit.Auditable;
import com.socialmediaapp.utils.CommonUtils;
import com.socialmediaapp.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;


import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EntityListeners(AuditListener.class)
@Entity
public class UserRefreshToken  implements Auditable {

    public static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String token;
    private Date validityTrm;

    @Column(nullable = false)
    private String userName;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    public void calculateExpiryDate(String valdtyTrm) {

        if (!"0".equals(valdtyTrm)) {
            if (!CommonUtils.isInteger(valdtyTrm)) {
                this.setDefaultValidityTerm();
            } else {
                if (Integer.parseInt(valdtyTrm) < 0) {
                    this.setDefaultValidityTerm();
                } else {
                    setValidityTrm(DateUtils.addMinutes(DateUtil.now(), Integer.parseInt(valdtyTrm)));
                }
            }
        }

    }

    private void setDefaultValidityTerm() {
        setValidityTrm(DateUtils.addMinutes(DateUtil.now(), EXPIRATION));
    }

    public boolean isExpired() {
        if(this.validityTrm!=null) {
            return this.validityTrm.before(DateUtil.now());
        }
        return false;
    }
}
