

package net.jjjerp.admin.service.shop;

import net.jjjerp.common.entity.shop.AdminUser;
import net.jjjerp.framework.common.service.BaseService;
import net.jjjerp.framework.core.pagination.Paging;
import net.jjjerp.admin.param.shopUser.ShopUserPageParam;
import net.jjjerp.admin.param.shopUser.ShopUserParam;
import net.jjjerp.framework.shiro.vo.LoginShopUserTokenVo;
import net.jjjerp.admin.vo.shopUser.ShopUserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统用户 服务类
 */
public interface ShopUserService extends BaseService<AdminUser> {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    LoginShopUserTokenVo login(String username, String password);

    /**
     * 修改密码
     * @param password
     * @return
     */
    Boolean renew(String oldpass, String password, String confirmPass);
    /**
     * 退出
     * @param request
     * @throws Exception
     */
    void logout(HttpServletRequest request) throws Exception;

    /**
     * 列表
     * @param params
     * @throws Exception
     */
    Paging<ShopUserVo> getList(ShopUserPageParam params);

    /**
     * 新增用户
     * @param shopUserParam
     * @return
     */
    Boolean add(ShopUserParam shopUserParam);

    /**
     * 编辑用户
     * @param shopUserParam
     * @return
     */
    Boolean edit(ShopUserParam shopUserParam);

    /**
     * 删除用户
     * @param shopUserId
     * @return
     */
    Boolean setDelete(Integer shopUserId);

    List<AdminUser> getSales();

    List<AdminUser> getAll();

}
