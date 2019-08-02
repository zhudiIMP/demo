package com.example.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * 
 * @Classname TimeUtil
 * @Version 0.1
 * @Since 2008-9-25
 * @Copyright yuchengtech
 * @Author wqgang
 * @Description：
 * @Lastmodified 2008-9-25
 * @Author wqgang
 */

public class TimeUtil {

	private static final String SEPARATELine = "-";

	private static final String SEPARATEAT = "@";

	private static final String SEPARATEPOINT = ".";

	private static final String SEPARATECOLON = ":";

	private static final TimeUtil instance = new TimeUtil();

	 
	 
	public TimeUtil() {
	}

	public static final TimeUtil getInstance() {
		return instance;
	}


	/**
	 * 得到当前月 格式是2008-9-25 *
	 * 
	 * @param dateStr
	 *            String
	 * @return int
	 */
	public static int getCurrentMonth(String dateStr) {
		String date[] = dateStr.split("-");
		return Integer.parseInt(date[1], 10);
	}
	
	/**
	 * 得到当前月 格式是20080901*
	 * 
	 * @param dateStr
	 *            String
	 * @return String
	 */
	public static String getCurMonth(String dateStr) {
		
		return dateStr.substring(4, 6);
	}

	/**
	 * 得到当前日 格式是20080901*
	 * 
	 * @param dateStr
	 *            String
	 * @return String
	 */
	public static String getCurDay(String dateStr) {
		
		return dateStr.substring(6);
	}
	
	/**
	 * 得到当前年 格式是20080901*
	 * 
	 * @param dateStr
	 *            String
	 * @return String
	 */
	public static String getCurYear(String dateStr) {
		
		return dateStr.substring(0, 4);
	}
	
	 /** 得到当前年月 格式是20080901 *
	 * 
	 * @param dateStr
	 *            String
	 * @return String
	 */
	public static String getCurYM(String dateStr) {
		
		return dateStr.substring(0, 6);
	}
	/**
	 * 将200809中的09的0去掉
	 * @param dateStr
	 * @return
	 */
	public static String removeZero (String dateStr) {
		String rv=dateStr;
		/*if(dateStr.startsWith("0")){
			
		}*/
		if (rv.indexOf("0")==0){
			rv=rv.substring(1);
		}
		
		return rv;
	}
	
	/**
	 * 得到月的天数 *
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @return int
	 */
	public static int getMonthDays(int year, int month) {
		int days = 1;
		boolean isrn = (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) ? true
				: false;
		switch (month) {
		case 1:
			days = 31;
			break;
		case 2:
			if (isrn)
				days = 29;
			else
				days = 28;
			break;
		case 3:
			days = 31;
			break;
		case 4:
			days = 30;
			break;
		case 5:
			days = 31;
			break;
		case 6:
			days = 30;
			break;
		case 7:
			days = 31;
			break;
		case 8:
			days = 31;
			break;
		case 9:
			days = 30;
			break;
		case 10:
			days = 31;
			break;
		case 11:
			days = 30;
			break;
		case 12:
			days = 31;
		}
		return days;
	}

	/**
	 * 得到月的天数，包括当前月过的天数。*
	 * 
	 * @param currDate
	 *            String
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @return int
	 */
	public static int getMonthDays(String currDate, int year, int month) {
		int days = 1;
		String date[] = currDate.split("-");
		if (Integer.parseInt(date[0]) == year
				&& Integer.parseInt(date[1]) == month) {
			days = Integer.parseInt(date[2]);
		} else {
			days = getMonthDays(year, month);
		}
		return days;
	}

	/**
	 * 得到当前年 格式是2008-9-25 *
	 * 
	 * @param dateStr
	 *            String
	 * @return int
	 */
	public static int getCurrentYear(String dateStr) {
		String date[] = dateStr.split("-");
		return Integer.parseInt(date[0], 10);
	}

	/**
	 * 得到当前天数 格式是2008-9-25 *
	 * 
	 * @param dateStr
	 *            String
	 * @return int
	 */
	public static int getCurrentDay(String dateStr) {
		String date[] = dateStr.split("-");
		return Integer.parseInt(date[2], 10);
	}

	/**
	 * 得到季度到现在的天数 格式是2008-9-25 *
	 * 
	 * @param dateStr
	 *            String
	 * @return int
	 */
	public static int getJiDuDays(String dateStr) {
		int days = 0;
		String date[] = dateStr.split("-");
		int day = Integer.parseInt(date[2], 10);
		int yy = Integer.parseInt(date[0], 10);
		boolean isrn = (((yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0)) ? true
				: false;
		switch (Integer.parseInt(date[1], 10)) {
		case 1:
			days = day;
			break;
		case 2:
			days = 31 + day;
			break;
		case 3:
			if (isrn)
				days = 31 + 29 + day;
			else
				days = 31 + 28 + day;
			break;
		case 4:
			days = day;
			break;
		case 5:
			days = 30 + day;
			break;
		case 6:
			days = 61 + day;
			break;
		case 7:
			days = day;
			break;
		case 8:
			days = 31 + day;
			break;
		case 9:
			days = 62 + day;
			break;
		case 10:
			days = day;
			break;
		case 11:
			days = 31 + day;
			break;
		case 12:
			days = 61 + day;
			break;
		}
		return days;
	}

	/**
	 * 返回两个日期间隔的天数 *
	 * 
	 * @param beginDate
	 *            String
	 * @param endDate
	 *            String
	 * @return int
	 */
	public static int getBetweenDays(String beginDate, String endDate) {
		int sum = 0;
		int beginYear = getCurrentYear(beginDate);
		int beginMonth = getCurrentMonth(beginDate);
		int beginDay = getCurrentDay(beginDate);
		int endYear = getCurrentYear(endDate);
		int endMonth = getCurrentMonth(endDate);
		int endDay = getCurrentDay(endDate);
		String startDateStr = bYearZero(beginYear) + bZero(beginMonth)
				+ "01";

		int sumMonth = (endYear - beginYear + 1) * 12 - (beginMonth)
				- (12 - endMonth);
		for (int i = 0; i < sumMonth; i++) {
			String dateStr = getDateStr(startDateStr, i);
			sum = sum
					+ getMonthDays(getCurrentYear(dateStr),
							getCurrentMonth(dateStr));
		}

		sum = sum - beginDay + endDay;
		return sum;
	}


	/**
	 * 返回日期经过若干月后的日期 *
	 * 
	 * @param dateStr
	 *            String
	 * @param hkm
	 *            int
	 * @return String
	 */
	public  static String getDateStr(String dateStr, int hkm) {
		String reDateStr = "";
		int yy = Integer.parseInt(dateStr.substring(0, 4), 10);
		int mm = Integer.parseInt(dateStr.substring(4, 6), 10);
		int dd = Integer.parseInt(dateStr.substring(6, 8), 10);
		// int yy1=0,mm1=0,dd1=dd;
		int yy2 = 0, mm2 = 0, dd2 = dd;
		if ((mm + hkm) % 12 == 0) {
			yy2 = yy + (mm + hkm) / 12 - 1;
			mm2 = 12;
		} else {
			if ((mm + hkm) % 12 == 1) {
				yy2 = yy + (mm + hkm) / 12;
				mm2 = 1;
			} else {
				yy2 = yy + (mm + hkm) / 12;
				mm2 = (mm + hkm) % 12;
			}
		}
		reDateStr = String.valueOf(yy2) + "-" + bZero(mm2) + "-" + bZero(dd2);
		return reDateStr;
	}

	/**
	 * 返回两位数据字串 *
	 * 
	 * @param sz
	 *            int
	 * @return String
	 */
	public static String bZero(int sz) {
		return (sz < 10 ? ("0" + String.valueOf(sz)) : String.valueOf(sz));
	}
	
	/**
	 * 返回四位字符串
	 * @param y
	 * @return
	 */
	public static String bYearZero(int y){
		if(y<10)
			return "000" + String.valueOf(y);
		else if(y<100)
			return "00"+ String.valueOf(y);
		else if(y<1000)
			return "0"+String.valueOf(y);
		
		return String.valueOf(y);
	}




	/**
	 * 把日期型转化成字符串型 *
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String dateToStr(Date date) {
		String str = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd");
			str = sdf.format(date);
		} catch (Exception ex) {
			str = "";
		}
		return str;
	}

	/**
	 * 把日期型转化成字符串型 *
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String dateToStr(Date date, String fgf) {
		String str = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy" + fgf + "MM" + fgf + "dd");
			str = sdf.format(date);
		} catch (Exception ex) {
			str = "";
		}
		return str;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param
	 * @return 返回信贷规定时间格式类型字符串 YYYY-MM-DD
	 * @throws
	 */
	public static String getCurDate() {
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		String strDate=sdf.format(date);
	
		
		return strDate;

	}
	
	/**
	 * <p>
	 * 如果该方法有问题请用getCurTimeStamp2()
	 * </p>
	 * 
	 * @param
	 * @return 返回信贷规定时间格式类型字符串 YYYY-MM-DD@hh:mm:ss.mmm
	 * @throws
	 */
	public static String getCurTimeStamp() {
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd@kk:mm:ss.S"); 
		String strStamp=sdf.format(date);
	
		
		return strStamp;
	}
	
	private static String getCurTimeStamp2() {
		
		Calendar rightNow = Calendar.getInstance();
		/**
		 * 当期日
		 */
		int DD = rightNow.get(Calendar.DAY_OF_MONTH);
		/**
		 * 当期年
		 */
		int YYYY = rightNow.get(Calendar.YEAR);
		/**
		 * 当期小时
		 */
		int hh = rightNow.get(Calendar.HOUR_OF_DAY);
		/**
		 * 当期分钟
		 */
		int mm = rightNow.get(Calendar.MINUTE);
		/**
		 * 当前秒
		 */
		int ss = rightNow.get(Calendar.SECOND);
		/**
		 * 当前毫秒
		 */
		int ms = rightNow.get(Calendar.MILLISECOND);

		String strTimeStamp = null;
		String strYYYY = null;
		String strMM = null;
		String strDD = null;
		String strhh = null;
		String strmm = null;
		String strss = null;
		String strms = null;
		
		if (YYYY < 10) {
			strYYYY = "000" + String.valueOf(YYYY);
		} else if (YYYY < 100 && YYYY >= 10) {
			strYYYY = "00" + String.valueOf(YYYY);
		} else if (YYYY < 1000 && YYYY >= 100) {
			strYYYY ="0"+ String.valueOf(YYYY);
		}else if (YYYY < 10000 && YYYY >= 1000) {
			strYYYY = String.valueOf(YYYY);
		}

		if (String.valueOf(DD).length() == 1) {
			strDD = "0" + String.valueOf(DD);
		} else {
			strDD = String.valueOf(DD);
		}

		if (String.valueOf(hh).length() == 1) {
			strhh = "0" + String.valueOf(hh);
		} else {
			strhh = String.valueOf(hh);
		}

		if (String.valueOf(mm).length() == 1) {
			strmm = "0" + String.valueOf(mm);
		} else {
			strmm = String.valueOf(mm);
		}

		if (String.valueOf(ss).length() == 1) {
			strss = "0" + String.valueOf(ss);
		} else {
			strss = String.valueOf(ss);
		}

		if (ms < 10) {
			strms = "00" + String.valueOf(ms);
		} else if (ms < 100 && ms >= 10) {
			strms = "0" + String.valueOf(ms);
		} else if (ms < 1000 && ms >= 100) {
			strms = String.valueOf(ms);

		}
		
		strTimeStamp = strYYYY + TimeUtil.SEPARATELine + strMM + TimeUtil.SEPARATELine
				+ TimeUtil.SEPARATELine + strDD;
		strTimeStamp = strTimeStamp + TimeUtil.SEPARATEAT;
		strTimeStamp = strTimeStamp+ strhh + TimeUtil.SEPARATECOLON + strmm
				+ TimeUtil.SEPARATECOLON + strss;
		strTimeStamp = strTimeStamp + TimeUtil.SEPARATEPOINT + strms;
		return strTimeStamp;

	}
	
	/**
	 * <p>
	 * </p>
	 * 
	 * @param
	 * @return 返回指定日期对应的季度末月的日期 YYYY-MM-DD
	 * @throws
	 */
	public static String getLastDateOfQuarter(String date) {
		String year = date.substring(0, 4) ;
		String month = date.substring(5, 7) ;
		String day = date.substring(7) ;
		String quarter ="" ;
		
		switch(Integer.parseInt(month)){
		  case 1:
		  case 2:
		  case 3:
			  quarter = "03";
			  break;
		  case 4:
		  case 5:
		  case 6:
			  quarter = "06";
			  break;			  
		  case 7:
		  case 8:
		  case 9:		  
			  quarter = "09";
			  break;
		  case 10:
		  case 11:
		  case 12:
			  quarter = "12";
			  break;
		}
		return year+quarter+day ;
	}

	/**
	 * <p>得到指定日期的当年年末月的日期</p>
	 * @param date YYYY-MM-DD
	 * @return
	 */
	public static String getLastDateOfYear(String date){

		String year = Integer.parseInt(date.substring(0, 4))+"" ;
		String day = date.substring(8) ;
		
		return year+"12"+day; 
	}


	/**
	 * 获得季度
	 * @param str
	 * @return
	 */
	public static String getQuarter(String str){
		String rv="";
		switch (Integer.parseInt(str)){
		case 1:
		case 2:
		case 3:
			rv="1";
		break;
		case 4:
		case 5:
		case 6:
			rv="2";
		break;	
		case 7:
		case 8:
		case 9:
			rv="3";
		break;	
		case 10:
		case 11:
		case 12:
			rv="4";
		break;	
		}
		return rv;
	}
	
	/**
	 * 获得半年
	 * @param str
	 * @return
	 */
	public static String getHelfYear(String str){
		String rv="";
		switch (Integer.parseInt(str)){
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			rv="1";
		break;	
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			rv="2";
		break;	
		}
		return rv;
	}
	
	private static String ADD_DATE(int optype,String date,int num){
		String st_return = "";  
		 try {
			DateFormat daf_date = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CHINA);
			daf_date.parse(date);
			Calendar  calendar= daf_date.getCalendar();
			calendar.add(optype, num);
			if(optype == Calendar.MONTH){
				calendar.add(Calendar.DATE, -1);
			}
				String st_m = "";
				String st_d = "";
			    int y = calendar.get(Calendar.YEAR);
			    int m = calendar.get(Calendar.MONTH) + 1;
			    int d = calendar.get(Calendar.DAY_OF_MONTH); 
			    if (m <= 9) {
			      st_m = "0" + m;
			    }
			    else {
			      st_m = "" + m;
			    }
			    if (d <= 9) {
			      st_d = "0" + d;
			    }
			    else {
			      st_d = "" + d;
			    }
			    st_return = y + "-" + st_m + "-" + st_d;
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		return st_return;
	}
	
	
	private static String ADD_DATE1(int optype,String date,int num){
		String st_return = "";  
		 try {
			DateFormat daf_date = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CHINA);
			daf_date.parse(date);
			Calendar  calendar= daf_date.getCalendar();
			calendar.add(optype, num);
			if(optype == Calendar.MONTH){
				calendar.add(Calendar.DATE, 0);//区分上个方法
			}
				String st_m = "";
				String st_d = "";
			    int y = calendar.get(Calendar.YEAR);
			    int m = calendar.get(Calendar.MONTH) + 1;
			    int d = calendar.get(Calendar.DAY_OF_MONTH); 
			    if (m <= 9) {
			      st_m = "0" + m;
			    }
			    else {
			      st_m = "" + m;
			    }
			    if (d <= 9) {
			      st_d = "0" + d;
			    }
			    else {
			      st_d = "" + d;
			    }
			    st_return = y + "-" + st_m + "-" + st_d;
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		return st_return;
	}
	/**
	 * 增加天数
	 * @param date
	 * @param n
	 * @return
	 */
	 public static String ADD_DAY(String date,int n){
		 return TimeUtil.ADD_DATE(Calendar.DATE, date, n);
	 }
	 
	 /**
		 * 增加天数
		 * @param date
		 * @param n
		 * @return
		 */
		 public static String ADD_DAY1(String date,int n){
			 return TimeUtil.ADD_DATE1(Calendar.DATE, date, n);
		 }
	 /**
	  * 增加月数
	  * @param date
	  * @param n
	  * @return
	  */
	 public static String ADD_MONTH(String date,int n){ 
		 return TimeUtil.ADD_DATE(Calendar.MONTH, date, n);
	 }
	 
	 /**
	  * 增加月数(区分上个月数)
	  * 2015-07-01 增加12个月 2015-07-01
	  * @param date
	  * @param n
	  * @return
	  */
	 public static String ADD_MONTH1(String date,int n){ 
		 return TimeUtil.ADD_DATE1(Calendar.MONTH, date, n);
	 }
	 /**
	  * 增加年数
	  * @param date
	  * @param n
	  * @return
	  */
	public static String ADD_YEAR(String date,int n){ 
		return TimeUtil.ADD_DATE(Calendar.YEAR, date, n);
	}
   

	
	/**
	 * 取上期时间
	 * @param yyMMdd
	 * @return
	 */
	public static String getPeryyMMdd(String yyMMdd,String termType){
		String rv=yyMMdd;
		
		Calendar  calendar=Calendar.getInstance();
		
		String m = TimeUtil.getCurMonth(yyMMdd);
		String y = TimeUtil.getCurYear(yyMMdd);
		String d = TimeUtil.getCurDay(yyMMdd);
		
		int year=Integer.parseInt(y);
		int month=Integer.parseInt(TimeUtil.removeZero(m));
		int date=Integer.parseInt(TimeUtil.removeZero(d));
			
		calendar.set(year, month, date);
		
		switch (Integer.parseInt(termType)) {
		case 1:// 月
			calendar.add(Calendar.MONTH, -2);
			break;
		case 2:// 季
			calendar.add(Calendar.MONTH, -4);
			break;
		case 3:// 半年
			calendar.add(Calendar.MONTH, -7);			
			break;
		case 4:// 年
			calendar.add(Calendar.MONTH, -13);
			break;
		default:
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		rv=sdf.format(calendar.getTime());
		return rv;
	}
	
	
	/**
	 * 取上N-1期+本期时间数组
	 * @param yyMMdd
	 * @return
	 */
	public static String[] getPerNyyMMdd(String yyMMdd,String termType,int term){
		String[] rv=new String[term];
		
		Calendar  calendar=Calendar.getInstance();
		
		String m = TimeUtil.getCurMonth(yyMMdd);
		String y = TimeUtil.getCurYear(yyMMdd);
		String d = TimeUtil.getCurDay(yyMMdd);
		
		int year=Integer.parseInt(y);
		int month=Integer.parseInt(TimeUtil.removeZero(m));
		int date=Integer.parseInt(TimeUtil.removeZero(d));
			
		
		rv[0]=yyMMdd;
		for(int i=0;i<term-1;i++){
			calendar.set(year, month, date);
			switch (Integer.parseInt(termType)) {
			case 1:// 月
				calendar.add(Calendar.MONTH, -(2+i));
				break;
			case 2:// 季
				calendar.add(Calendar.MONTH, -(4+(i*3)));
				break;
			case 3:// 半年
				calendar.add(Calendar.MONTH, -(7+(i*6)));			
				break;
			case 4:// 年
				calendar.add(Calendar.MONTH, -(13+(i*12)));
				break;
			default:
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
			rv[i+1]=sdf.format(calendar.getTime());
		}
	
		return rv;
	}


	

	
	/**
	 * 返回N位字符串（只含字母和数字）
	 * @param length
	 * @return
	 */
	public static String getCharacterAndNumber(int length)   
	{   
	    String rt = "";   
	           
	    Random random = new Random();   
	    for(int i = 0; i < length; i++)   
	    {   
	        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字   
	               
	        if("char".equalsIgnoreCase(charOrNum)) // 字符串   
	        {   
	            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母   
	            rt += (char) (choice + random.nextInt(26));   
	        }   
	        else if("num".equalsIgnoreCase(charOrNum)) // 数字   
	        {   
	            rt += String.valueOf(random.nextInt(10));   
	        }   
	    }   
	           
	    return rt;   
	}  

	/**
	 * 生成32位主键 为时间戳到毫秒（17位）+大小写及数字的随机数（15位即62的15次幂=768909704948766668552634368分之一的重复几率）
	 * @return
	 */
	public static String getPK(){
		String rt="";
		rt=rt+TimeUtil.getCurTimeStamp4PK();
		rt=rt+TimeUtil.getCharacterAndNumber(15);
		return rt;
	}
	
	public static String getCurTimeStamp4PK() {
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmssSSS"); 
		String strStamp=sdf.format(date);
	
		
		return strStamp;
	}
	
	/**
	    * 获取昨天
	    * @param date
	    * @return
	    */
	    public static String getYesterday(String date) {
	        try {
        	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        	        Calendar cal= Calendar.getInstance();
        	        Date d = sf.parse(date);
        	        cal.setTime(d);
        	        cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH)-1);
        	        date = sf.format(cal.getTime());
	        } catch (Exception e) {
	        }
	        return date;
	    }
	    
	    public final static String DTPattern	="yyyy-MM-dd HH:mm:ss ";
	    
		public static String getDateTime(String pattern){
			String dateTime="";
			Calendar calender = Calendar.getInstance();
			if(pattern==null||pattern.equals(""))
				pattern=DTPattern;
			SimpleDateFormat sf=new SimpleDateFormat(pattern);
			dateTime = sf.format(calender.getTime());
			return dateTime;
		}
		
		
		/**
		 * 计算两日期间相差的年数
		 * @param	startDate	起始日期
		 * @param	endDate		结束日期
		 * @return	yearNum		年数
		 */
		public static int getBetweenYears(String startDate,String endDate){
			
			int yearNum = 0 ;
			int startYear = getCurrentYear(startDate);		//开始日期1-年
			int endYear = getCurrentYear(endDate);			//结束日期1-年
			
			int startMonth = getCurrentMonth(startDate);	//开始日期1-月
			int endMonth = getCurrentMonth(endDate);		//结束日期1-月
			
			int startDay = getCurrentDay(startDate);		//开始日期1-日
			int endDay= getCurrentDay(endDate);			//结束日期1-日
			
			
			yearNum = endYear - startYear ;
			
			if(yearNum>0){
				 if( endMonth < startMonth ){
					 yearNum = yearNum-1;
				 }else if(endMonth==startMonth){
					 if(endDay<startDay){
						 yearNum = yearNum-1;
					 }
				 }
			}
			return yearNum;
		}
		
		/**
		 * 返回日期经过若干月后的日期 格式是2008-9-25 *
		 * 
		 * @param dateStr
		 *            String 格式 yyyy-mm-dd
		 * @param hkm
		 *            int
		 * @return String
		 */
		public  static String getDateStrByDate(String dateStr, int hkm) {
			String reDateStr = "";
			int yy = Integer.parseInt(dateStr.substring(0, 4), 10);
			int mm = Integer.parseInt(dateStr.substring(5, 7), 10);
			int dd = Integer.parseInt(dateStr.substring(8, 10), 10);
			// int yy1=0,mm1=0,dd1=dd;
			int yy2 = 0, mm2 = 0, dd2 = dd;
			if ((mm + hkm) % 12 == 0) {
				yy2 = yy + (mm + hkm) / 12 - 1;
				mm2 = 12;
			} else {
				if ((mm + hkm) % 12 == 1) {
					yy2 = yy + (mm + hkm) / 12;
					mm2 = 1;
				} else {
					yy2 = yy + (mm + hkm) / 12;
					mm2 = (mm + hkm) % 12;
				}
			}
			reDateStr = String.valueOf(yy2) + "-" + bZero(mm2) + "-" + bZero(dd2);
			return reDateStr;
		}
		/**
		 * 计算两日期间相差的年数
		 * @param	startDate	起始日期
		 * @param	year		年
		 * @param	month		月
		 * @return	yearNum		年数
		 */
		public static int getBetweenYearsByYM(String startDate , int year ,int month){
			
			int yearNum = 0 ;
			
			
			int startYear = getCurrentYear(startDate);		//开始日期1-年
			
			int startMonth = getCurrentMonth(startDate);	//开始日期1-月
			
			yearNum = year - startYear ;
			
			if(yearNum>0){
				 if( month < startMonth ){
					 yearNum = yearNum-1;
				 }
			}
			return yearNum;
		}
		public static String getDateDiff(long times) {
			String outStr;
			long oneDay = 1000*60*60*24;
			long oneHou = 1000*60*60;
			long oneMin = 1000*60;
			//long oneSec = 1000;
			
			long day = times/oneDay;
			long hours = times%oneDay/oneHou;
			long mint = times%oneDay%oneHou/oneMin;
			//long sec = times%oneDay%oneHou%oneMin/oneSec;
			if(day > 0) {
				outStr = day+"天"+hours+"小时"+mint+"分钟";
			} else if(hours > 0) {
				outStr = hours+"小时"+mint+"分钟";
			} else if(mint > 0) {
				outStr = mint+"分钟";
			} else {
				//outStr = sec+"秒";
				outStr = "0分钟";
			}
			return outStr;
		}
	public static void main(String args[]){
		//int r = getMonthSpace("2013-02-12","2013-09-12");
		TimeUtil.getBetweenDays("2019-01-01",TimeUtil.dateToStr(new Date(), "-"));
		
		/*	TimeUtil tu=new TimeUtil();
			String r=tu.ADD_DAY("2009-12-20", 20);
			System.err.println(r);
			String [] a= TimeUtil.getPerNyyMMdd("20080808", "4",9);
			for(int i=0;i<a.length;i++){
				System.out.println(a[i]);
			}
			
			
			System.out.println(TimeUtil.getPerNyyMMdd("20080808", "1",9));
			System.out.println(TimeUtil.getPerNyyMMdd("20080808", "2",3));
			System.out.println(TimeUtil.getPerNyyMMdd("20080808", "3",3));
			System.out.println(TimeUtil.getPerNyyMMdd("20080808", "4",3));
	*/
		TimeUtil tu=new TimeUtil();
	//	String newDate = TimeUtil.getDateOfCountMonth("2011-03-31", 6);
	//	System.out.println(newDate);
//		System.err.println(tu.getDateTime("")+"|");
		
		}
	


	 public static int getMonthSpace(String date1, String date2){

		 int result = 0;
		 try {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			 Calendar c1 = Calendar.getInstance();
			 Calendar c2 = Calendar.getInstance();
			
			 c1.setTime(sdf.parse(date1));
			 c2.setTime(sdf.parse(date2));
			
			 result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		 } catch (Exception ex) {
			 
		 }
		
		 return result == 0 ? 1 : Math.abs(result);
		
	}


	
}
