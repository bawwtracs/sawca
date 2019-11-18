package bawwtracs.sawca.plan.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sawca
 * @since 2019-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Plan implements Serializable {

    private static final long serialVersionUID=1L;

    private String name;

    private String description;

    private Long timestamp;

}
