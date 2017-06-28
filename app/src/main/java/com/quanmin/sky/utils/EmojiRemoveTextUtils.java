package com.quanmin.sky.utils;

/**
 * 删掉第三方输入法的emoji符号
 * Created by xiao on 2017/1/13 0013.
 */

public class EmojiRemoveTextUtils {

    private static boolean isEmojiCharacter(char emojiChar) {
        return !((emojiChar == 0x0) || (emojiChar == 0x9) || (emojiChar == 0xA) ||
                (emojiChar == 0xD) || ((emojiChar >= 0x20) && emojiChar <= 0xD7FF)) ||
                ((emojiChar >= 0xE000) && (emojiChar <= 0xFFFD)) ||
                ((emojiChar >= 0x10000) && (emojiChar <= 0x10FFFF));
    }
}
