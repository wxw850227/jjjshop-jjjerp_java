

package net.jjjerp.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * Shiro配置映射类
 **/
@Data
@ConfigurationProperties(prefix = "spring-boot-jjj.shiro")
public class ShiroProperties {

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 路径权限配置
     */
    private String filterChainDefinitions;

    /**
     * 设置无需权限路径集合
     */
    private List<String[]> anon;

    /**
     * 权限配置集合
     */
    @NestedConfigurationProperty
    private List<ShiroPermissionProperties> permission;

}
