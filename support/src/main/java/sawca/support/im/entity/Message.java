package sawca.support.im.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String messId;

    @Column
    private String senderId;

    @Column
    private String receiverId;

    @Column
    private String senderName;

    @Column
    private String receiverName;

    @Column
    private Long timestamp;

    @Column
    private String senderAvatar;

    @Column
    private String receiverAvatar;

    @Column
    private String content;

    @Column
    private Integer type;

}
