

package net.jjjerp.common.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.jjjerp.framework.core.validator.groups.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 部门 查询参数对象
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CategoryParam对象", description = "Category新增修改参数")
public class CategoryParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @ApiModelProperty("主键id")
    private Integer categoryId;

    @NotNull(message = "上级分类id不能为空")
    @ApiModelProperty("上级分类id")
    private Integer parentId;

    @NotEmpty(message = "分类名称不能为空")
    @ApiModelProperty("分类名称")
    private String name;
}
