package pers.flower.utils.money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description : 金额大小写转换工具类
 * @Author : flower_ho@126.com
 * @Creation Date : 2014年1月15日 下午9:00:00
 */
@SuppressWarnings("all")
public class MoneyConvert {

	private static String[] digit = { "", "拾", "佰", "仟", "万", "拾万", "佰万", "仟万",
			"亿", "拾亿", "佰亿", "仟亿", "万亿" };
	private static final String FEN = "分";
	private static final String JIAO = "角";
	private static final String YUAN = "圆";
	private static final String ZHENG = "整";
	private static final String LING = "零";

	/**
	 * 取得数字对应的中文
	 * 
	 * @param money
	 * @return
	 */
	public static String getMoneyString(double money) {
		// 将字符串转为为BigDecimal格式
		BigDecimal b = new BigDecimal(String.valueOf(money));
		// 设置精度为2，小数点后2位
		String strMoney = "" + b.setScale(2, BigDecimal.ROUND_UNNECESSARY);
		// 按小数点分为 整数 和 小数 两部分
		String[] amt = strMoney.split("\\.");
		if (strMoney.equals("0.00")) {
			strMoney = LING + getYuan(amt[0]) + YUAN + getJIAOFEN(amt[1]);
		} else {
			// 调用函数获取 元 和 小数 部分的字符串
			strMoney = getYuan(amt[0]) + YUAN + getJIAOFEN(amt[1]);
		}
		// 返回最终得到的字符串
		return strMoney;
	}

	/**
	 * 得到元的部分
	 * 
	 * @param s
	 * @return
	 */
	public static String getYuan(String s) {
		char[] c = s.toCharArray();
		StringBuffer chSb = new StringBuffer();
		int len = c.length;
		List list = new ArrayList();
		String d = "";
		for (int i = 0; i < c.length; i++) {
			// 如果有几个0挨在一起时, 只显示一个零即可
			if (i > 0 && c[i] == '0' && c[i] == c[i - 1]) {
				--len;
				continue;
			}
			// 得到数字对应的中文
			chSb.append(getChinese(c[i]));

			// 非零时, 显示是几佰, 还是几仟
			if (!getChinese(c[i]).equals("零")) {
				d = digit[--len];
				list.add(d);// 当数字中有万和十万时, 要去掉十万
				chSb.append(d);
			} else {
				--len; // 如果是0则不取位数
			}
		}
		String chStr = chSb.toString();

		// 如果同时包含有万和十万, 则将十万中的万去掉
		if (list.contains("万") && list.contains("拾万")) {
			chStr = chStr.replaceAll("拾万", "拾");
		}
		if (list.contains("万") && list.contains("佰万")) {
			chStr = chStr.replaceAll("佰万", "佰");
		}
		if (list.contains("万") && list.contains("仟万")) {
			chStr = chStr.replaceAll("仟万", "仟");
		}

		// 如果同时包含有拾万和佰万, 则将佰万中的万去掉
		if (list.contains("佰万") && list.contains("拾万")) {
			chStr = chStr.replaceAll("佰万", "佰");
		}
		// 如果同时包含有佰万和仟万, 则将仟万中的万去掉
		if (list.contains("仟万") && list.contains("佰万")) {
			chStr = chStr.replaceAll("仟万", "仟");
		}

		// 如果同时包含亿和十亿, 则将十亿中的亿字去掉
		if (list.contains("亿") && list.contains("拾亿")) {
			chStr = chStr.replaceAll("拾亿", "拾");
		}
		if (list.contains("亿") && list.contains("佰亿")) {
			chStr = chStr.replaceAll("佰亿", "佰");
		}
		if (list.contains("亿") && list.contains("仟亿")) {
			chStr = chStr.replaceAll("仟亿", "仟");
		}
		if (list.contains("亿") && list.contains("万亿")) {
			chStr = chStr.replaceAll("万亿", "万");
		}
		// 如果最后一位是 0, 则去掉
		if ((chSb.charAt(chSb.length() - 1)) == '零') {
			chStr = chStr.substring(0, chStr.length() - 1);
		}
		return chStr;
	}

	/**
	 * 分角转换为字符串 例: 25 2角5分 02 零2分 50 5角 00 整 0 整 2 整
	 * 
	 * @param FENJIAO
	 * @return
	 */
	private static String getJIAOFEN(String FENJIAO) {
		// 分角字符串转为为字符数组
		char[] ch = FENJIAO.toCharArray();
		// 按长度为0，为1，为2 来区分。
		if (ch.length == 0) {
			return ZHENG;
		} else if (ch.length == 1) {
			if (ch[0] == '0') {
				return ZHENG;
			} else {
				return getChinese(ch[0]) + JIAO;
			}
		} else {
			if (ch[0] == '0' && ch[1] == '0') {
				return ZHENG;
			} else if (ch[0] == '0' && ch[1] != '0') {
				return getChinese(ch[0]) + getChinese(ch[1]) + FEN;
			} else if (ch[0] != '0' && ch[1] == '0') {
				return getChinese(ch[0]) + JIAO;
			} else {
				return getChinese(ch[0]) + JIAO + getChinese(ch[1]) + FEN;
			}
		}
	}

	/**
	 * 取得数字对应的中文
	 * 
	 * @param i
	 * @return
	 */
	private static String getChinese(char i) {
		String ch = "";
		switch (i) {
		case '0':
			ch = "零";
			break;
		case '1':
			ch = "壹";
			break;
		case '2':
			ch = "贰";
			break;
		case '3':
			ch = "叁";
			break;
		case '4':
			ch = "肆";
			break;
		case '5':
			ch = "伍";
			break;
		case '6':
			ch = "陆";
			break;
		case '7':
			ch = "柒";
			break;
		case '8':
			ch = "捌";
			break;
		case '9':
			ch = "玖";
			break;
		}
		return ch;
	}

	public static void main(String[] args) {
		System.out.println(getMoneyString(20150));
		System.out.println(getMoneyString(5000.96));
		System.out.println(getJIAOFEN("5125"));

	}
}
