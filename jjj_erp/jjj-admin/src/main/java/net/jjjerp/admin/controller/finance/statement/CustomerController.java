package net.jjjerp.admin.controller.finance.statement;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjerp.common.entity.bill.BillHead;
import net.jjjerp.common.entity.finance.capital.Account;
import net.jjjerp.common.param.CommonPageParam;
import net.jjjerp.admin.service.bill.BillHeadService;
import net.jjjerp.admin.service.finance.capital.AccountService;
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

import javax.annotation.Resource;

@Slf4j
@Api(value = "index", tags = {"客户对账单"})
@RestController
@RequestMapping("/admin/finance/capital/customer")
public class CustomerController {

    @Autowired
    private BillHeadService billHeadService;

    //客户对账单列表
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @RequiresPermissions("/finance/capital/customer/index")
    @OperationLog(name = "index")
    @ApiOperation(value = "客户对账单", response = String.class)
    public ApiResult<Paging<BillHead>> customerIndex(@RequestBody CommonPageParam param) {
        return ApiResult.ok(billHeadService.customerIndex(param));
    }

}
