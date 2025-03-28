package net.jjjerp.common.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.extern.slf4j.Slf4j;
import net.jjjerp.common.entity.settings.Setting;
import net.jjjerp.common.enums.SettingEnum;
import net.jjjerp.common.mapper.settings.SettingMapper;
import net.jjjerp.config.constant.CommonRedisKey;
import net.jjjerp.framework.core.util.RequestDetailThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SettingUtils {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SettingMapper settingMapper;

    private static final String backPackage = "net.jjjerp.common.settings.vo.";
    /**
     * 通过key查找设置信息
     * @param key
     * @return
     * @throws Exception
     */
    public JSONObject getSetting(String key, Long appId) {
        JSONObject result = null;
        if(appId == null){
            appId = RequestDetailThreadLocal.getRequestDetail().getAppId();
        }
        String cacheKey = String.format(CommonRedisKey.SETTING_DATA, appId, key);
        // 如果缓存中存在,则返回
        Object object = redisTemplate.opsForValue().get(cacheKey);
        if(object != null){
            try{
                // 跟默认数据进行合并
                Class cla = Class.forName(backPackage + SettingEnum.getClassNameByKey(key));
                Object vo = cla.newInstance();
                result = (JSONObject)JSONObject.toJSON(vo);
                result.putAll((JSONObject)object);
            }catch (Exception e){
                log.info("获取设置异常:", e.getMessage());
            }
            return result;
        }
        // 先从数据库查询，如果没有，则取默认数据
        Setting setting = settingMapper.selectOne(new LambdaQueryWrapper<Setting>()
                .eq(Setting::getSetKey, key).eq(Setting::getAppId, appId));

        try{
            if(setting != null){
                result = JSONObject.parseObject(setting.getSetValue());
            }else{
                Class cla = Class.forName(backPackage + SettingEnum.getClassNameByKey(key));
                Object vo = cla.newInstance();
                result = (JSONObject)JSONObject.toJSON(vo);
            }
            // 存入缓存
            redisTemplate.opsForValue().set(cacheKey, result);
        }catch (Exception e){
            log.info("获取设置异常:", e.getMessage());
        }
        return result;
    }

    /**
     * 保存
     * @param key
     * @param jsonData
     * @return
     */
    public Boolean saveSetting(String key, JSONObject jsonData){
        Setting setting = settingMapper.selectOne(new LambdaQueryWrapper<Setting>()
                .eq(Setting::getSetKey, key));
        Setting bean = new Setting();
        // 删除缓存
        redisTemplate.delete(String.format(CommonRedisKey.SETTING_DATA, RequestDetailThreadLocal.getRequestDetail().getAppId(), key));
        if(setting == null){
            // 新增
            bean.setSetKey(key);
            bean.setDescription(SettingEnum.getDescriptionByKey(key));
            bean.setSetValue(jsonData.toJSONString());
            return SqlHelper.retBool(settingMapper.insert(bean));
        }else{
            // 修改
            bean.setSetKey(key);
            bean.setSetValue(jsonData.toJSONString());
            return SqlHelper.retBool(settingMapper.updateById(bean));
        }
    }
}
