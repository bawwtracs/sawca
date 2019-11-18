package bawwtracs.sawca.plan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("plan_step")
public class Step implements Serializable {

    private static final long serialVersionUID=1L;

    private Long planId;

    private String content;

    private Long timestamp;


}
