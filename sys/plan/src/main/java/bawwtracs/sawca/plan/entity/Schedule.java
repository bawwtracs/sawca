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
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("plan_schedule")
public class Schedule implements Serializable {

    private static final long serialVersionUID=1L;

    private Long planId;

    private String name;

    private Long time;

    private String content;


}
