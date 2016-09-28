/**
 * �ļ��� U.java
 * ����ʱ�䣺 2012-3-1 ����04:03:55
 * �����ˣ� SongCheng
 */
package com.check.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.*;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ���÷���������
 *
 * @author SongCheng
 */
public class ToolUtils {
	public static final org.apache.commons.logging.Log LOG = org.apache.commons.logging.LogFactory.getLog(ToolUtils.class);

	/** ����������������ظ� */
	public static final AtomicLong COUNTER = new AtomicLong();

	public static final ObjectMapper JSON_objectMapper = new ObjectMapper();

	/**
	 * ���ָ�����ͺ����Բ����½���
	 * @param cls	Ҫ�½������� 
	 * @param args BeanPorperty �����б�
	 * @return
	 */
	public static <T> T createWithProperties(Class<T> cls, Object... args) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < args.length; i++) {
				String key = (String) args[i];
				Object val = args[++i];
				map.put(key, val);
			}
			T t = cls.newInstance();
			BeanUtils.populate(t, map);
			return t;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}//method

	public static Map<String, String> convertStringToMapBySeparator(String s, String separator) {
		Map<String, String> map = new HashMap<String, String>();
		String[] strs = s.split(separator);
		for (String string : strs) {
			map.put(string, string);
		}
		return map;
	}

	/**
	 * ��Դ������ָ�����Ե�Ŀ�������Ҫ������ͬ���Ͷ���֮�����ֵ���ơ�<br>
	 * ��ͬ������ʹ�� {@link #populateProperty(Object, Object, String...)}
	 * @param src	Դ����
	 * @param dest	Ŀ�����
	 * @param pros	ָ�������б�
	 */
	public static void copyProperties(Object src, Object dest, String... pros) {
		PropertyUtilsBean pub = new PropertyUtilsBean();
		for (String pro : pros) {
			try {
				Object val = pub.getProperty(src, pro);
				pub.setProperty(dest, pro, val);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}//for
	}//method

	/**
	 * ��ɶ����������ִ�
	 */
	public static String build2StringMultiLineStyle(Object obj) {
		String ts = null;
		ts = ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE);
		return ts;
	}

	/**
	 * ��ɵ����������ִ�
	 */
	public static String build2StringShortStyle(Object obj) {
		String ts = null;
		ts = ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
		return ts;
	}

	/**
	 * ���ָ�����ԣ���ɵ����������ִ�
	 */
	public static String build2StringShortStyle(Object obj, String... pnames) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (String name : pnames) {
			try {
				String val = BeanUtils.getProperty(obj, name);
				map.put(name, val);
			} catch (Exception e) {
			}
		}//for
		Class<?> cls = obj.getClass();
		return cls.getSimpleName() + map.toString();
	}

	public static void touchProperty(Object obj, String propertyPath) {
		if (obj instanceof Collection<?>) {
			Collection<?> coll = (Collection<?>) obj;
			for (Object it : coll)
				touchProperty(it, propertyPath);
		} else if (obj instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) obj;
			touchProperty(map.values(), propertyPath);
		} else {
			try {
				BeanUtils.getProperty(obj, propertyPath);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}//if
	}//method

	/**
	 * ���½���ͻ����ַ�
	 */
	public static String decodeISO88591ToGBK(String str) {
		try {
			byte[] bs = str.getBytes("iso-8859-1");
			String nn = new String(bs, "gbk");
			return nn;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}//method

	/**
	 * ȥ��������зǿ�String���Զ������˵Ŀհ��ַ�
	 * @param bean
	 */
	public static void trim(Object bean) {
		try {
			Map<String, String> map = BeanUtils.describe(bean);
			for (String name : map.keySet()) {
				if (String.class != PropertyUtils.getPropertyType(bean, name))
					continue;

				String val = (String) PropertyUtils.getProperty(bean, name);
				if (StringUtils.isBlank(val))
					continue;

				val = val.trim();
				PropertyUtils.setProperty(bean, name, val);
			}//for
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static BeanUtilsBean createBeanUtilsBean() {
		BeanUtilsBean bu = BeanUtilsBean.getInstance();
		ConvertUtilsBean convu = bu.getConvertUtils();

		//����ת����
		DateConverter dateConv = new DateConverter(null);
		dateConv.setPatterns(new String[] { "yyyy/MM/dd", "yyyy-MM-dd", "yyyy/MM/dd HH:mm:ss" });
		convu.register(dateConv, Date.class);
		return bu;
	}

	/**
	 * ��Ҫ���ڽ���ͬ���Ͷ�����ͬ������ͬ���͵���ݸ��ƣ��磺ǰ̨������String���͵��������͵�ת����<br>
	 * �� TDO���е����ת�浽ʵ������<br>
	 * ע�⣺String ��ֵ�������޷�ת�������� NULL ֵ���磺<br>
	 * <ul>
	 * 		<li>"999999999999999" �� int �ͣ��޷�ת�����NULL</li>
	 * 		<li>NULL �� Date �ͣ��޷�ת�����NULL</li>
	 * 		<li>"abc��Ч����" �� Date �ͣ��޷�ת�����NULL</li>
	 * </ul>
	 * 
	 * @param src TDO�࣬������ȫ�����Ӧʵ�����String�������
	 * @param dest Ŀ��ʵ����
	 * @param props Ҫװ�����������б?Ϊ��ʱ��װ��ȫ������
	 */
	public static void populateProperty(Object src, Object dest, String... props) {
		try {
			BeanUtilsBean bu = createBeanUtilsBean();
			Map<String, String> map = bu.describe(src);
			Collection<String> names = null;

			if (props == null || props.length == 0) {
				names = map.keySet();
			} else {
				names = Arrays.asList(props);
			}

			for (String name : names) {
				Object val = map.get(name);
				bu.setProperty(dest, name, val);
			}//for

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}//method

	/**
	 * �齨Map������
	 * @param vals ������ [String,Object,String,Object ... String,Object] ���͵�������
	 * @return ���� {@link HashMap}
	 */
	public static Map<String, Object> asMap(Object... vals) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (vals.length == 0) {
			return map;
		}

		for (int i = 0; i < vals.length; i++) {
			Object k = vals[i];
			Object v = vals[++i];
			map.put(k == null ? null : k.toString(), v);
		}
		return map;
	}

	public static <T> T[] toArray(List<T> lst) {
		Object[] arr = lst.toArray();
		return (T[]) arr;
	}

	public static <T> List<T> asList(T... vals) {
		return Arrays.asList(vals);
	}

	/**
	 * ���Ӽ����е�Ԫ�س��ַ�
	 * @param src
	 * @param separator Ԫ�ؼ�ָ���
	 * @param wraper Ԫ�����ߵİ�װ�ַ��磺[wraper]String[wraper]
	 * @return �������磺[wraper]String[wraper][separator][wraper]String[wraper][separator]...[separator][wraper]String[wraper]
	 */
	public static String join(List<?> src, String separator, String wraper) {
		wraper = wraper == null ? "" : wraper;
		separator = separator == null ? "" : separator;

		StringBuilder out = new StringBuilder();
		int len = src.size();
		for (int i = 0; i < len; i++) {
			Object obj = src.get(i);
			out.append(wraper);
			out.append(obj == null ? "" : obj.toString());
			out.append(wraper);
			if (i + 1 < len)
				out.append(separator);
		}
		return out.toString();
	}

	public static boolean out(Object msg) {
		String val = null;
		if (msg == null) {
			val = "";
		} else {
			val = msg.toString();
		}
		System.out.println(val);
		return true;
	}

	public static String nvl(String str) {
		return nvl(str, "");
	}//method

	public static String nvl(String str, String def) {
		if (StringUtils.isBlank(str)) {
			return def;
		}
		return str.trim();
	}//method

	public static String formatXml(String xml) {
		try {
			return formatXml(DocumentHelper.parseText(xml));
		} catch (Exception e) {
			LOG.error(e, e);
			throw new RuntimeException(e);
		}
	}//method

	public static String formatXml(Document doc) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter(out, format);
		writer.write(doc);
		writer.close();
		return out.toString();
	}//method

	/**
	 * ���� XML �ַ�Ϊ Document ����
	 */
	public static Document parseXml(String xml) {
		Document doc = null;
		if (StringUtils.isBlank(xml))
			return doc;

		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
		return doc;
	}//method

	/**
	 * ��Map������ȥ�� keys �����в������ļ�
	 * @param source
	 * @param keys
	 * @return
	 */
	public static Map<String, Object> removeNotContainKey(Map source, String... keys) {
		Map<String, Object> tar = new HashMap<String, Object>();
		for (String key : keys) {
			tar.put(key, source.get(key));
		}

		return tar;
	}//method


	public static Timestamp currentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}//method

	/**
	 * ��ӡ�����ջ��Ϣ
	 */
	public static String printStackTrace(Exception ex) {
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		ex.printStackTrace(out);
		out.flush();
		return writer.toString();
	}//method

	/**
	 * ������ת��ΪMap�ṹ
	 */
	public static Map<String, String> convertBeanToMap(Object obj) {
		Map<String, String> map = Collections.EMPTY_MAP;
		try {
			map = BeanUtils.describe(obj);
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return map;
	}//method

	/**
	 * �������ת��Ϊ JSON ��ʽ���ַ�
	 */
	public static String toJsonString(Object target) {
		String json = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			JSON_objectMapper.setDateFormat(dateFormat);
			json = JSON_objectMapper.writeValueAsString(target);
		} catch (Exception e) {
			LOG.error(e, e);
			throw new RuntimeException(e);
		}
		return json;
	}//method

	/**
	 * �����п��Ž��������������ɸ�ʽ�磺**********XXXXXX
	 */
	public static String maskBankAcctNo(String acct) {
		String prefix = "**********";
		if (StringUtils.isBlank(acct)) {
			return prefix;
		}

		//�����
		int len = 5;
		acct = acct.trim();
		if (acct.length() <= len) {
			return prefix;
		}

		String str = prefix + acct.substring(acct.length() - len);

		return str;
	}//method

	public static Timestamp timestamp(Date date) {
		return new Timestamp(date.getTime());
	}//method

	public static java.sql.Date currentDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * ���������� XML ���ĸ�ʽ
	 */
	public static String httpReuquestMapToXml(Map<String, ?> parametersMap) {
		/*
		<parameters>
			<version>v1.0</version>
			<corpId>ddd</corpId>
		</parameters>
		 */

		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(ConfigUtils.ENCODING_utf8);
		Element root = doc.addElement("parameters");

		for (String key : parametersMap.keySet()) {
			Object val = parametersMap.get(key);
			if (val == null)
				continue;

			Collection<String> vals = null;

			if (val instanceof String[]) {
				vals = ToolUtils.asList((String[]) val);
			} else if (val instanceof Collection) {
				vals = (Collection<String>) val;
			} else {
				vals = ToolUtils.asList(val.toString());
			}

			for (String it : vals)
				root.addElement(key).setText(it);
		}//for

		return doc.asXML();
	}//method

	public static String memoryUsage() {
		int k = 1024;
		Runtime rt = Runtime.getRuntime();
		StringBuilder out = new StringBuilder();
		out.append("usage[ ").append((rt.totalMemory() - rt.freeMemory()) / k).append(" ]\b");
		out.append("free[ ").append(rt.freeMemory() / k).append(" ]\b");
		out.append("total[ ").append(rt.totalMemory() / k).append(" ]\b");
		out.append("max[ ").append(rt.maxMemory() / k).append(" ]\b");

		return out.toString();
	}//method

	public static String createUUID() {
		UUID id = UUID.randomUUID();
		return id.toString().replace("-", "");
	}//method

	/**
	 * ����GBK�����ȡ�����ַ�ʵ�ʵ��ֽڳ��ȣ���Ҫ���ڴ洢����ݿ�ʱ���޶��ַ��ʹ�á�<br>
	 * varchar2(2000)���ֶΣ�ֻ�ܴ洢1000�������ַ�
	 * @param src ԭʼ�ַ�
	 * @param len ָ���ĳ���
	 * @return 
	 */
//	public static String substringByByte(String src, final int len) {
//		if (src.length() < (len / 2))
//			return src;
//
//		try {
//			byte[] bs = src.getBytes(ConfigUtils.ENCODING_gbk);
//
//			//�ַ��С�ڸ���
//			if (bs.length <= len) {
//				return src;
//			}
//
//			//���û��˫�ֽ���
//			if (bs.length == src.length()) {
//				return src.substring(0, len);
//			}
//
//			//����˫�ֽ����
//			StringBuilder sb = new StringBuilder();
//			int size = 0;
//			int cnt = 0;
//			for (Character ch : src.toCharArray()) {
//				cnt = Character.toString(ch).getBytes(ConfigUtils.ENCODING_gbk).length;
//				size += cnt;
//				if (size <= len) {
//					sb.append(ch);
//				}
//			}
//			return sb.toString();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}//method
	
	public static String formatXmlNoIndent(Document doc) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setNewLineAfterDeclaration(false);
		format.setNewlines(false);
		format.setIndent(false);
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter(out, format);
		writer.write(doc);
		writer.close();
		return out.toString();
	}//method

}//class