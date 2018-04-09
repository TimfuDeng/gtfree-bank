package cn.gtmap.landsale.util;

import java.text.DecimalFormat;


public class CapitalToChinese {
	private static StringBuffer buffer = null;//存储数字转换为中文大写的变长字符串
	public static String changeToChinses(Double dNum)
	{
		String sNum = "";
		String num_bef = "";
		String num_aft = "";
		int k = -1;//非零数字开始的位置
		
	    DecimalFormat df = new DecimalFormat("#.00");
	    sNum = df.format(dNum);
	       
		if(sNum.indexOf(".") != -1){
			num_bef = sNum.substring(0,sNum.indexOf("."));
			num_aft = sNum.substring(sNum.indexOf(".")+1,sNum.indexOf(".")+3);
		}else{
			num_bef = sNum;
		}
		
		/**
		 * 将字符串转换为字符数组；当不全为数字时，结束程序
		 */
		char[] array = new char[num_bef.length()];
		num_bef.getChars(0,num_bef.length(), array, 0);
		
		for(char arr : array)
		{
			if(arr < '0' || arr > '9')
			{
				return("用户输入的字符不都为数字，无法转换");
			}	
		}
		/**
		 * 将array数组中遇到的第壹个非零数字，赋值给k
		 */
		for(int i = 0; i < array.length; i++)
		{
			
			if('0' == array[i])
			{
				continue;
			}
			k = i;	
			
			break;
		}
		/**
		 * 输入全是0时，直接输出零，结束程序
		 */
		if(k == -1)
		{
			return "零";
		}
		
		/**
		 * 将非零开始的数字转为中文大写
		 */
		int n = array.length - k;//数字所在的数位
		buffer = new StringBuffer();
		CapitalToChinese.change(array[k],'0', n,true);
		for(; k < array.length - 1; k++)
		{
			 n = array.length - k - 1;
			 CapitalToChinese.change(array[k + 1],array[k], n,false);
		}
		
		if(!"".equals(num_aft) && !"00".equals(num_aft)){
			buffer.append("点");
			buffer.append(changeAft(num_aft));
			if("零".equals(buffer.toString().substring(buffer.toString().length() - 1))){
				buffer.deleteCharAt(buffer.toString().length() - 1);
			}
		}
		
		return buffer.toString();
	}
	/**
	 * 
	 * @param ch每壹位的数字
	 * @param last，ch的前壹位数字
	 * @param n数字所在的数位
	 * @param m是否是数字的非零第壹位
	 */
	private static void change(char ch,char last, int n,boolean m)
	{
		if(true == m)
		{
			switch(n)
			{
			case 1:buffer.append(finger(ch));return;
			case 2:buffer.append(finger(ch) + "拾");return;
			case 3:buffer.append(finger(ch) + "佰");return;
			case 4:buffer.append(finger(ch) + "仟");return;
			case 5:buffer.append(finger(ch) + "万");return;
			case 6:buffer.append(finger(ch) + "拾");return;
			case 7:buffer.append(finger(ch) + "佰");return;
			case 8:buffer.append(finger(ch) + "仟");return;
			}
			return;
		}
		if('0' == last)
		{
			if('0' == ch)
			{
				return;
			}
			switch(n)
			{
			case 1:buffer.append("零" + finger(ch));return;
			case 2:buffer.append("零" + finger(ch) + "拾");return;
			case 3:buffer.append("零" + finger(ch) + "佰");return;
			case 4:buffer.append("零" + finger(ch) + "仟");return;
			case 5:buffer.append("零" + finger(ch) + "万");return;
			case 6:buffer.append("零" + finger(ch) + "拾");return;
			case 7:buffer.append("零" + finger(ch) + "佰");return;
			case 8:buffer.append("零" + finger(ch) + "仟");return;
			}
		}
		if('0' != last)
		{
			if('0' == ch)
			{
				return;
			}
			switch(n)
			{
			case 1:buffer.append(finger(ch));return;
			case 2:buffer.append(finger(ch) + "拾");return;
			case 3:buffer.append(finger(ch) + "佰");return;
			case 4:buffer.append(finger(ch) + "仟");return;
			case 5:buffer.append(finger(ch) + "万");return;
			case 6:buffer.append(finger(ch) + "拾");return;
			case 7:buffer.append(finger(ch) + "佰");return;
			case 8:buffer.append(finger(ch) + "仟");return;
			}
		}
		return;
	}
	private static String finger(char ch)
	{
		switch(ch)
		{
		case '0': return "零"; 
		case '1': return "壹"; 
		case '2': return "贰"; 
		case '3': return "叁"; 
		case '4': return "肆"; 
		case '5': return "伍"; 
		case '6': return "陆"; 
		case '7': return "柒"; 
		case '8': return "捌"; 
		case '9': return "玖"; 
		}
		return null;
	}

	public static String changeAft(String num){
		String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};  
		char[] str = String.valueOf(num).toCharArray();  
		String rstr = "";  
		for (int i = 0; i < str.length; i++) {  
			rstr = rstr + u[Integer.parseInt(str[i] + "")];  
		}
		return rstr;  
	}
	
	public static void main(String[] args){
		System.out.println(CapitalToChinese.changeToChinses(3151.23));
	}
}
