package net.jjjerp.admin.controller.setting.delivery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjerp.common.entity.settings.Express;
import net.jjjerp.common.param.setting.ExpressPageParam;
import net.jjjerp.common.param.setting.ExpressParam;
import net.jjjerp.admin.service.bill.BillDeliveryService;
import net.jjjerp.admin.service.settings.ExpressService;
import net.jjjerp.framework.common.api.ApiResult;
import net.jjjerp.framework.core.pagination.Paging;
import net.jjjerp.framework.log.annotation.OperationLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "express", tags = {"快递公司"})
@RestController
@RequestMapping("/admin/setting/delivery/express")
public class ExpressController {
    @Autowired
    private ExpressService expressService;


    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @RequiresPermissions("/setting/delivery/express/index")
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<Paging<Express>> index(@RequestBody @Validated ExpressPageParam expressPageParam) throws Exception {
        return ApiResult.ok(expressService.getList(expressPageParam));
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("/setting/delivery/express/add")
    @OperationLog(name = "add")
    @ApiOperation(value = "add", response = String.class)
    public ApiResult<String> add(@RequestBody @Validated ExpressParam expressParam) {
        if (expressService.add(expressParam)) {
            return ApiResult.ok(null, "添加成功");
        } else {
            return ApiResult.fail("添加失败");
        }

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @RequiresPermissions("/setting/delivery/express/edit")
    @OperationLog(name = "edit")
    @ApiOperation(value = "edit", response = String.class)
    public ApiResult<String> edit(@RequestBody @Validated ExpressParam expressParam) {
        if (expressService.edit(expressParam)) {
            return ApiResult.ok(null, "修改成功");
        } else {
            return ApiResult.fail("修改失败");
        }
    }

    @RequestMapping(value = "/toEdit", method = RequestMethod.GET)
    @RequiresPermissions("/setting/delivery/express/edit")
    @OperationLog(name = "edit")
    @ApiOperation(value = "edit", response = String.class)
    public ApiResult<Express> toEdit(Integer expressId) {
        return ApiResult.ok(expressService.getById(expressId));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("/setting/delivery/express/delete")
    @OperationLog(name = "delete")
    @ApiOperation(value = "delete", response = String.class)
    public ApiResult<String>  delById(Integer id) {
        if (expressService.delById(id)) {
            return ApiResult.ok("删除成功");
        } else {
            return ApiResult.fail("删除失败");
        }
    }

}
