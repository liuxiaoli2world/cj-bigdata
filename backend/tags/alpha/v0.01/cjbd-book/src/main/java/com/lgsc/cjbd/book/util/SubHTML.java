package com.lgsc.cjbd.book.util;

public class SubHTML {

	/**
	 * 
	 * @param subtml
	 * @param length
	 * @return
	 */
	public static String getSubHTML(String content, int tryLength) {
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isHtmlTag = false; // 是不是html标签字符,不计数
		boolean isHtmlEntity = false; // 是不是HTML实体字符,如 &nbsp;&lt; 计为一个字符
		boolean last = true; 	// 上一个字符是否为字母;标点符号记做一个字符
		boolean isEnd = false; // 正文截取是否结束
		boolean isChinese = true; // 是否为汉字
		for (int i = 0; i < content.length(); i++) {
			temp = content.charAt(i);
			if (temp == '<') {
				isHtmlTag = true;
			} else if (temp == '&') {
				isHtmlEntity = true;
			} else if (temp == '>' && isHtmlTag) {
				isHtmlTag = false;
				n--;
			} else if (temp == ';' && isHtmlEntity) {
				isHtmlEntity = false;
			}

			if (temp == (char) 12288) {
				temp = ' '; // unicode为12288字符为全角空格，trim()无法去除
			}
			// 正文
			if (!isEnd) {
				if (!isHtmlTag && !isHtmlEntity) {
					if (Character.valueOf(temp).toString().matches("[A-Za-z ]")) { // 英文:字母+空格组成
						if (Character.isWhitespace(temp)) {
							last = true;
						} else if (last) {
							n++;
							last = false;
							isChinese = false;
						}
					} else { // 中文
						
						n++;
						last = true;
						isChinese = true;
					}
				}
			}

			result.append(temp);
			if (n >= tryLength) {
				isEnd = true;
			}

			if (isEnd) {
				if (isChinese) {
					break;
				} else {
					if (!Character.valueOf(temp).toString().matches("[A-Za-z]")) { // 补齐英文单词,第一个非字母结束
						result = new StringBuffer(result.substring(0, result.length() - 1)); // 去掉最后一个字符
						break;
					}
				}
			}
		}
		return fixTags(result.toString());
	}

	/**
	 * 章节内容
	 * 
	 * @param content
	 * @return
	 */
	public static Integer getCount(String content) {
		int n = 0;
		char temp;
		boolean isHtmlTag = false; // 是不是html标签字符,不计数
		boolean isHtmlEntity = false; // 是不是HTML实体字符,如 &nbsp;&lt; 计为一个字符
		boolean last = true; // 上一个字符是否为英文字符
		for (int i = 0; i < content.length(); i++) {
			temp = content.charAt(i);
			if (temp == '<') {
				isHtmlTag = true;
			} else if (temp == '&') {
				isHtmlEntity = true;
			} else if (temp == '>' && isHtmlTag) {
				isHtmlTag = false;
				n--;
			} else if (temp == ';' && isHtmlEntity) {
				isHtmlEntity = false;
			}

			// 正文
			if (!isHtmlTag && !isHtmlEntity) {
				if (Character.valueOf(temp).toString().matches("[A-Za-z ]")) { // 英文字母+空格
					if (Character.isWhitespace(temp)) { // 去掉了空格
						last = true;
					} else if (last) { // 字母
						last = false;
						n++;
					}
				} else { // 汉字 +标点符号
					n++;
					last = true;
				}
			}
		}

		return Integer.valueOf(n);
	}

	private static String fixTags(String str) {
		StringBuffer fixedsubhtml = new StringBuffer(); // 存放修复后的字符串
		TagList[] unclosedTags = getUnclosedTags(str);
		// 补齐前面的非闭合标签
		for (int i = unclosedTags[0].size() - 1; i > -1; i--)
			fixedsubhtml.append("<" + unclosedTags[0].get(i) + ">");
		// 添加原始字符串
		fixedsubhtml.append(str);
		// 补齐后面的闭合标签
		for (int i = unclosedTags[1].size() - 1; i > -1; i--)
			fixedsubhtml.append("</" + unclosedTags[1].get(i) + ">");

		return fixedsubhtml.toString();

	}

	private static TagList[] getUnclosedTags(String str) {
		StringBuffer temp = new StringBuffer(); // 存放标签
		TagList[] unclosedTags = new TagList[2];
		unclosedTags[0] = new TagList(); // 前不闭合，如有</div>而前面没有<div>
		unclosedTags[1] = new TagList(); // 后不闭合，如有<div>而后面没有</div>
		boolean flag = false; // 记录双引号"或单引号'
		char currentJump = ' '; // 记录需要跳过''还是""
		char current = ' ', last = ' '; // 当前 & 上一个
		// 开始判断
		for (int i = 0; i < str.length();) {
			current = str.charAt(i++); // 读取一个字符
			if (current == '<') { // 开始提取标签
				current = str.charAt(i++);
				if (current == '/') { // 标签的闭合部分，如</div>
					current = str.charAt(i++);
					// 读取标签
					while (i < str.length() && current != '>') {
						temp.append(current);
						current = str.charAt(i++);
					}
					// 从tags_bottom移除一个闭合的标签
					if (!unclosedTags[1].remove(temp.toString())) { // 若移除失败，说明前面没有需要闭合的标签
						unclosedTags[0].add(temp.toString()); // 此标签需要前闭合
					}
					temp.delete(0, temp.length()); // 清空temp
				} else { // 标签的前部分，如<div>
					last = current;
					while (i < str.length() && current != ' ' && current != '>') {
						temp.append(current);
						last = current;
						current = str.charAt(i++);
					}
					// 已经读取到标签，跳过其他内容，如<div id=test>跳过id=test
					while (i < str.length() && current != '>') {
						last = current;
						current = str.charAt(i++);
						if (current == '"' || current == '\'') { // 判断引号
							flag = flag ? false : true; //
							currentJump = current;
							if (flag) { // 若引号不闭合，跳过到下一个引号之间的内容
								while (i < str.length() && str.charAt(i++) != currentJump)
									; // 跳过引号之间的内容
								current = str.charAt(i++);
								flag = false;
							}
						}
					}
					if (last != '/' && current == '>') // 判断这种类型：<img />
						unclosedTags[1].add(temp.toString());
					temp.delete(0, temp.length());
				}
			}
		}
		return unclosedTags;
	}
}
