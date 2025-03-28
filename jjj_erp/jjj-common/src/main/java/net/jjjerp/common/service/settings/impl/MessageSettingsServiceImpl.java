package net.jjjerp.common.service.settings.impl;

import lombok.extern.slf4j.Slf4j;
import net.jjjerp.common.entity.settings.MessageSettings;
import net.jjjerp.common.mapper.settings.MessageSettingsMapper;
import net.jjjerp.common.service.settings.MessageSettingsService;
import net.jjjerp.framework.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 应用消息设置表 服务实现类
 * @author jjjerp
 * @since 2022-06-24
 */
@Slf4j
@Service
public class MessageSettingsServiceImpl extends BaseServiceImpl<MessageSettingsMapper, MessageSettings> implements MessageSettingsService {
}
