package com.bigcloud.alain.service.dto;

public class IconDTO {

    private String type; // 图标类型('class' | 'icon' | 'img')
    private String value; // 图标的值，包含：类名、图标 `type`、图像
    private String theme; // 图标主题风格('outline' | 'twotone' | 'fill')，默认：`outline`
    private Boolean spin; // 是否有旋转动画，默认：`false`
    private String twoToneColor; // 仅适用双色图标，设置双色图标的主要颜色，仅对当前 icon 生效
    private String iconfont; // 指定来自 IconFont 的图标类型

    public IconDTO() {
    }

    public IconDTO(String type, String value, String theme) {
        this.type = type;
        this.value = value;
        this.theme = theme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getSpin() {
        return spin;
    }

    public void setSpin(Boolean spin) {
        this.spin = spin;
    }

    public String getTwoToneColor() {
        return twoToneColor;
    }

    public void setTwoToneColor(String twoToneColor) {
        this.twoToneColor = twoToneColor;
    }

    public String getIconfont() {
        return iconfont;
    }

    public void setIconfont(String iconfont) {
        this.iconfont = iconfont;
    }
}
