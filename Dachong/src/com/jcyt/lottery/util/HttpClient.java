package com.jcyt.lottery.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Title: HttpClientUtil.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: jc-yt.com
 * </p>
 * 
 * @author tang
 * @date 2014-11-7 下午5:19:07
 * @version 1.0
 * 
 */
@Component
public class HttpClient {

	private static Logger log = Logger.getLogger(HttpClient.class);


	/**
	 * 以get方式发送http请求
	 * 
	 * @param url
	 * @return
	 */
	public String sendGetRequest(String url){
		return sendGetRequest(url,null);
	}
	
	/**
	 * 以get方式发送http请求
	 * 
	 * @param strUrl
	 * @return
	 */
	public String sendGetRequest(String strUrl, Map<String, String> params) {

		CloseableHttpClient client = HttpClientBuilder.create().build();

		URI uri=null;
		if(params!=null){
			try {
				URIBuilder uBuilder=new URIBuilder(strUrl);
					uBuilder.addParameters(getParams(params));
				uri=uBuilder.build();
			} catch (URISyntaxException e) {
				log.error(e);
			}
		}else{
				try {
					URL url=new URL(strUrl);
					uri=new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
				} catch (MalformedURLException e) {
					log.error(e);
				} catch (URISyntaxException e) {
					log.error(e);
				}
			
		}
		HttpGet get = new HttpGet(uri);
		RequestConfig rConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(100000).setConnectTimeout(100000)
				.setSocketTimeout(100000).build();

		get.setConfig(rConfig);

		HttpResponse httpRsp;

		try {
			httpRsp = client.execute(get);
			HttpEntity entity = httpRsp.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (ClientProtocolException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			get.releaseConnection();
		}
		return "";
	}

	
	public String sendPostRequest(String strUrl, Map<String, String> params){
		CloseableHttpClient client = HttpClientBuilder.create().build();

		URI uri=null;
		if(params!=null){
			try {
				URIBuilder uBuilder=new URIBuilder(strUrl);
					uBuilder.addParameters(getParams(params));
				uri=uBuilder.build();
			} catch (URISyntaxException e) {
				log.error(e);
			}
		}else{
				try {
					URL url=new URL(strUrl);
					uri=new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
				} catch (MalformedURLException e) {
					log.error(e);
				} catch (URISyntaxException e) {
					log.error(e);
				}
			
		}
		HttpPost post = new HttpPost(uri);
		StringEntity entity;
		try {
			entity = new StringEntity(uri.getQuery());
			post.setEntity(entity);
		} catch (UnsupportedEncodingException e1) {
			log.error(e1);
		}
		RequestConfig rConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(100000).setConnectTimeout(100000)
				.setSocketTimeout(100000).build();

		post.setConfig(rConfig);

		HttpResponse httpRsp;

		try {
			httpRsp = client.execute(post);
			HttpEntity rspEntity = httpRsp.getEntity();
			return EntityUtils.toString(rspEntity, "utf-8");
		} catch (ClientProtocolException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			post.releaseConnection();
		}
		return "";
		
	}
	public String sendPostRequest(String url){
		return sendPostRequest(url,null);
	}
	
	private List<NameValuePair> getParams(Map<String, String> params) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		Set<String> set = params.keySet();

		for (Iterator<String> i = set.iterator(); i.hasNext();) {
			String key = i.next();
			String value = params.get(key);
			NameValuePair nvp = new BasicNameValuePair(key, value);
			list.add(nvp);
		}
		return list;
	}

	public static void main(String[] args) {
		// String allStr =
		// "var ssq_top30 =[{'qihao':'14130','number':'01,02,10,24,30,33|10','is_confirm_kjinfo':'2','kjdate':'2014-11-09 21:30:00','djdate':'2015-01-07','gcmoney':'176970840','xiaoliang':'402591108','kjdetail':'1_3_13333333|2_77_344673|3_1369_3000|4_68267_200|5_1282919_10|6_12104101_5'},{'qihao':'14129','number':'05,08,09,20,28,32|02','is_confirm_kjinfo':'2','kjdate':'2014-11-06 21:30:00','djdate':'2015-01-04','gcmoney':'127351182','xiaoliang':'359082268','kjdetail':'1_7_6428571|2_207_120794|3_1582_3000|4_83951_200|5_1568779_10|6_7741662_5'},{'qihao':'14128','number':'05,07,08,17,18,24|14','is_confirm_kjinfo':'2','kjdate':'2014-11-04 21:30:00','djdate':'2015-01-02','gcmoney':'87337680','xiaoliang':'358520804','kjdetail':'1_10_6000000|2_180_122362|3_2449_3000|4_89210_200|5_1631948_10|6_9213168_5'},{'qihao':'14127','number':'02,10,12,21,23,27|12','is_confirm_kjinfo':'2','kjdate':'2014-11-02 21:30:00','djdate':'2014-12-31','gcmoney':'71262030','xiaoliang':'405427926','kjdetail':'1_15_5666666|2_189_94735|3_2512_3000|4_105324_200|5_1773966_10|6_16139836_5'},{'qihao':'14126','number':'06,11,16,17,22,27|01','is_confirm_kjinfo':'2','kjdate':'2014-10-30 21:30:00','djdate':'2014-12-28','gcmoney':'92547000','xiaoliang':'374635140','kjdetail':'1_28_6067086|2_425_58466|3_1854_3000|4_84348_200|5_1420149_10|6_9509175_5'},{'qihao':'14125','number':'10,11,15,26,31,32|06','is_confirm_kjinfo':'2','kjdate':'2014-10-28 21:30:00','djdate':'2014-12-26','gcmoney':'177881270','xiaoliang':'369170678','kjdetail':'1_5_10893985|2_84_289731|3_1201_3000|4_61459_200|5_1205104_10|6_11119673_5'},{'qihao':'14124','number':'02,17,20,24,31,33|04','is_confirm_kjinfo':'2','kjdate':'2014-10-26 21:30:00','djdate':'2014-12-24','gcmoney':'149338971','xiaoliang':'414603564','kjdetail':'1_3_8333333|2_72_452439|3_949_3000|4_52565_200|5_1099654_10|6_9699314_5'},{'qihao':'14123','number':'01,06,11,17,28,33|05','is_confirm_kjinfo':'2','kjdate':'2014-10-23 21:30:00','djdate':'2014-12-21','gcmoney':'66611994','xiaoliang':'374248054','kjdetail':'1_31_5504877|2_447_43767|3_2335_3000|4_86030_200|5_1312285_10|6_13558336_5'},{'qihao':'14122','number':'06,09,11,16,20,29|11','is_confirm_kjinfo':'2','kjdate':'2014-10-21 21:30:00','djdate':'2014-12-19','gcmoney':'178571232','xiaoliang':'372450634','kjdetail':'1_16_5951266|2_156_121957|3_2333_3000|4_101054_200|5_1637257_10|6_12563417_5'},{'qihao':'14121','number':'01,02,13,22,28,30|09','is_confirm_kjinfo':'2','kjdate':'2014-10-19 21:30:00','djdate':'2014-12-17','gcmoney':'216715500','xiaoliang':'414246034','kjdetail':'1_15_6326521|2_265_93857|3_2233_3000|4_95279_200|5_1617362_10|6_12312599_5'},{'qihao':'14120','number':'01,07,12,16,23,28|04','is_confirm_kjinfo':'2','kjdate':'2014-10-16 21:30:00','djdate':'2014-12-14','gcmoney':'236996485','xiaoliang':'380941988','kjdetail':'1_23_5924705|2_375_70894|3_1964_3000|4_90510_200|5_1599524_10|6_8066245_5'},{'qihao':'14119','number':'06,13,17,20,26,29|09','is_confirm_kjinfo':'2','kjdate':'2014-10-14 21:30:00','djdate':'2014-12-12','gcmoney':'293508896','xiaoliang':'374297910','kjdetail':'1_16_5570537|2_138_82686|3_2891_3000|4_117161_200|5_1802157_10|6_17527238_5'},{'qihao':'14118','number':'05,07,15,18,26,30|03','is_confirm_kjinfo':'2','kjdate':'2014-10-12 21:30:00','djdate':'2014-12-10','gcmoney':'348405240','xiaoliang':'412610364','kjdetail':'1_15_6427135|2_328_81581|3_2273_3000|4_100809_200|5_1756790_10|6_10119046_5'},{'qihao':'14117','number':'05,10,17,25,28,29|04','is_confirm_kjinfo':'2','kjdate':'2014-10-09 21:30:00','djdate':'2014-12-07','gcmoney':'364535919','xiaoliang':'376251864','kjdetail':'1_9_7417293|2_316_86058|3_1417_3000|4_73888_200|5_1411754_10|6_8487811_5'},{'qihao':'14116','number':'09,10,14,15,19,29|16','is_confirm_kjinfo':'2','kjdate':'2014-10-07 21:30:00','djdate':'2014-12-05','gcmoney':'349707904','xiaoliang':'355472106','kjdetail':'1_8_7738382|2_178_153841|3_1095_3000|4_64983_200|5_1368684_10|6_6935517_5'},{'qihao':'14115','number':'01,09,10,11,13,32|03','is_confirm_kjinfo':'2','kjdate':'2014-10-05 21:30:00','djdate':'2014-12-03','gcmoney':'329463486','xiaoliang':'393986920','kjdetail':'1_107_5206826|2_176_157176|3_1224_3000|4_71491_200|5_1342439_10|6_10201413_5'},{'qihao':'14114','number':'02,07,23,30,32,33|10','is_confirm_kjinfo':'2','kjdate':'2014-10-02 21:30:00','djdate':'2014-11-30','gcmoney':'803604936','xiaoliang':'336622164','kjdetail':'1_4_7655494|2_64_207460|3_1549_3000|4_76268_200|5_1350027_10|6_15686821_5'},{'qihao':'14113','number':'12,14,28,31,32,33|07','is_confirm_kjinfo':'2','kjdate':'2014-09-30 21:30:00','djdate':'2014-11-28','gcmoney':'794394502','xiaoliang':'376332046','kjdetail':'1_2_10000000|2_61_329010|3_1154_3000|4_66803_200|5_1303269_10|6_14853762_5'},{'qihao':'14112','number':'01,15,16,21,24,30|03','is_confirm_kjinfo':'2','kjdate':'2014-09-28 21:30:00','djdate':'2014-11-26','gcmoney':'754185552','xiaoliang':'438774340','kjdetail':'1_16_5556035|2_150_74138|3_2830_3000|4_124316_200|5_1984273_10|6_23464132_5'},{'qihao':'14111','number':'02,08,17,20,22,28|02','is_confirm_kjinfo':'2','kjdate':'2014-09-25 21:30:00','djdate':'2014-11-23','gcmoney':'809720000','xiaoliang':'391471380','kjdetail':'1_5_10000000|2_86_386831|3_1266_3000|4_59579_200|5_1157710_10|6_6292002_5'},{'qihao':'14110','number':'01,08,11,13,19,30|06','is_confirm_kjinfo':'2','kjdate':'2014-09-23 21:30:00','djdate':'2014-11-21','gcmoney':'759917456','xiaoliang':'384948858','kjdetail':'1_4_9300795|2_143_150377|3_1913_3000|4_92867_200|5_1669011_10|6_12321303_5'},{'qihao':'14109','number':'02,05,11,15,19,28|02','is_confirm_kjinfo':'2','kjdate':'2014-09-21 21:30:00','djdate':'2014-11-19','gcmoney':'732608704','xiaoliang':'437600916','kjdetail':'1_16_6744502|2_304_114769|3_1491_3000|4_77457_200|5_1604097_10|6_7771782_5'},{'qihao':'14108','number':'03,08,09,20,23,28|02','is_confirm_kjinfo':'2','kjdate':'2014-09-18 21:30:00','djdate':'2014-11-16','gcmoney':'735850612','xiaoliang':'393047674','kjdetail':'1_14_6392168|2_357_68243|3_2806_3000|4_109290_200|5_1755016_10|6_9463084_5'},{'qihao':'14107','number':'11,14,17,22,25,27|16','is_confirm_kjinfo':'2','kjdate':'2014-09-16 21:30:00','djdate':'2014-11-14','gcmoney':'752252140','xiaoliang':'387176276','kjdetail':'1_5_9244289|2_117_226724|3_999_3000|4_60116_200|5_1186708_10|6_11344370_5'},{'qihao':'14106','number':'09,14,17,18,21,25|15','is_confirm_kjinfo':'2','kjdate':'2014-09-14 21:30:00','djdate':'2014-11-12','gcmoney':'718893160','xiaoliang':'422052940','kjdetail':'1_4_10000000|2_124_257862|3_1115_3000|4_68464_200|5_1401001_10|6_9571702_5'},{'qihao':'14105','number':'14,16,17,19,27,32|04','is_confirm_kjinfo':'2','kjdate':'2014-09-11 21:30:00','djdate':'2014-11-09','gcmoney':'662968446','xiaoliang':'379715550','kjdetail':'1_6_9151943|2_67_464769|3_756_3000|4_45386_200|5_993471_10|6_8044478_5'},{'qihao':'14104','number':'02,06,12,19,27,28|13','is_confirm_kjinfo':'2','kjdate':'2014-09-09 21:30:00','djdate':'2014-11-07','gcmoney':'624461375','xiaoliang':'358170510','kjdetail':'1_5_9351116|2_372_73103|3_1218_3000|4_79156_200|5_1516901_10|6_6414284_5'},{'qihao':'14103','number':'03,08,09,10,18,33|04','is_confirm_kjinfo':'2','kjdate':'2014-09-07 21:30:00','djdate':'2014-11-05','gcmoney':'589633520','xiaoliang':'396768732','kjdetail':'1_8_7120349|2_179_118455|3_1986_3000|4_106804_200|5_1806290_10|6_12844201_5'},{'qihao':'14102','number':'14,16,21,24,28,31|13','is_confirm_kjinfo':'2','kjdate':'2014-09-04 21:30:00','djdate':'2014-11-02','gcmoney':'582985842','xiaoliang':'368749750','kjdetail':'1_3_10000000|2_80_346223|3_813_3000|4_54535_200|5_1145542_10|6_9018905_5'},{'qihao':'14101','number':'16,18,20,23,24,32|07','is_confirm_kjinfo':'2','kjdate':'2014-09-02 21:30:00','djdate':'2014-10-31','gcmoney':'529892270','xiaoliang':'359712392','kjdetail':'1_2_10000000|2_61_420623|3_806_3000|4_44219_200|5_922552_10|6_10627930_5'}]";
		// allStr = allStr.substring(allStr.indexOf("=") + 1, allStr.length());
		// JSONArray jsonArr=JSONArray.fromObject(allStr);
		// JSONObject obj=jsonArr.getJSONObject(0);
		try {
			URI uri = new URIBuilder(
					"http://888.qq.com/tws/kefuchecksvc/GetInfo?callback=GetProjectByNo").addParameters(null)
					.build();
			System.out.println(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(StringUtils.removeStart("20141220", "20"));
	}
}
