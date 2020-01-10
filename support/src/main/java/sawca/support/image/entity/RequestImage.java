package sawca.support.image.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hjl
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class RequestImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long requestId;

    @Column
    private String name;

    @Column
    private String path;

    @Column
    private Long requestTime;

    @Column
    private Long responseTime;

    @Column
    private String operation;

    @Column
    private String type;

    @Column(columnDefinition = "int default 0")
    private Integer resend;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String result;
}
