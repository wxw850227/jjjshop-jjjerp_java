package net.jjjerp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传枚举
 */
@Getter
@AllArgsConstructor
public enum StorageEnum {
    LOCAL("本地", "Local"),
    QINIU("七牛云", "QiNiu"),
    ALIYUN("阿里云", "AliYun"),
    QCLOUD("腾讯云", "QCloud"),
    HWCLOUD("华为云", "HwCloud");

    private String name;
    private String value;
}
