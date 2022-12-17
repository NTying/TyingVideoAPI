package com.tying.constants;

/**
 * 字面量
 * @author Tying
 * @version 1.0
 */
public class OSSConstants {

    /**
     * OSS 外链域名
     */
    public static final String CHAIN_DOMAIN = "http://cdn.doraemonbag.com/";
    public enum FileType {
        PNG(".png"),
        JPG(".jpg"),
        WEBM("webm"),
        BMP(".bmp"),
        GIF(".gif"),
        MP4(".mp4");

        private String value;

        FileType(final String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}


