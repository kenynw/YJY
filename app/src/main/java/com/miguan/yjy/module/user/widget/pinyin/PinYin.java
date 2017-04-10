package com.miguan.yjy.module.user.widget.pinyin;

import com.miguan.yjy.module.user.widget.pinyin.HanziToPinyin3.Token;

import java.util.ArrayList;

public class PinYin
{
	// 汉字返回拼音，字母原样返回，都转换为小写
	public static String getPinYin(String input)
	{
		if (input.equals("厦门市")) {
			input = "xiamenshi";
		}
		if (input.equals("厦门")) {
			input = "xiamen";
		}
		ArrayList<Token> tokens = HanziToPinyin3.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0)
		{
			for (Token token : tokens)
			{
				if (Token.PINYIN == token.type)
				{
					sb.append(token.target);
				} else
				{
					sb.append(token.source);
				}
			}
		}
		return sb.toString().toLowerCase();
	}
}
