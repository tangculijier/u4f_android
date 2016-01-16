package com.u4f.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import android.util.Log;

public class UoLoadUtil2
{

	private static UploadUtil uploadUtil;
	private static final String BOUNDARY = UUID.randomUUID().toString(); // �߽��ʶ �������
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data"; // ��������
	private UoLoadUtil2() {}
	
	
	/**
	* ����ģʽ��ȡ�ϴ�������
	* @return
	*/
	public static UploadUtil getInstance() {
	if (null == uploadUtil) {
	uploadUtil = new UploadUtil();
	}
	return uploadUtil;
	}

	private static final String TAG = "huang";
	private static int readTimeOut = 10 * 1000; // ��ȡ��ʱ
	private static int connectTimeout = 10 * 1000; // ��ʱʱ��
	/***
	* ����ʹ�ö೤ʱ��
	*/
	private static int requestTime = 0;

	private static final String CHARSET = "utf-8"; // ���ñ���

	/***
	* �ϴ��ɹ�
	*/
	public static final int UPLOAD_SUCCESS_CODE = 1;
	/**
	* �ļ�������
	*/
	public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
	/**
	* ����������
	*/
	public static final int UPLOAD_SERVER_ERROR_CODE = 3;
	protected static final int WHAT_TO_UPLOAD = 1;
	protected static final int WHAT_UPLOAD_DONE = 2;

	public static void toUploadFile(File file, String fileKey, String RequestURL,
			Map<String, String> param)
	{
		String result = null;
		requestTime = 0;

		long requestTime = System.currentTimeMillis();
		long responseTime = 0;

		try
		{
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(readTimeOut);
			conn.setConnectTimeout(connectTimeout);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
			// conn.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			/**
			 * ���ļ���Ϊ�գ����ļ���װ�����ϴ�
			 */
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			StringBuffer sb = null;
			String params = "";

			/***
			 * �����������ϴ�����
			 */
			if (param != null && param.size() > 0)
			{
				Iterator<String> it = param.keySet().iterator();
				while (it.hasNext())
				{
					sb = null;
					sb = new StringBuffer();
					String key = it.next();
					String value = param.get(key);
					sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"")
							.append(key).append("\"").append(LINE_END)
							.append(LINE_END);
					sb.append(value).append(LINE_END);
					params = sb.toString();
					Log.i(TAG, key + "=" + params + "##");
					dos.write(params.getBytes());
					// dos.flush();
				}
			}

			sb = null;
			params = null;
			sb = new StringBuffer();
			/**
			 * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
			 * filename���ļ������֣�������׺���� ����:abc.png
			 */
			sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
			sb.append("Content-Disposition:form-data; name=\"" + fileKey
					+ "\"; filename=\"" + file.getName() + "\"" + LINE_END);
			sb.append("Content-Type:image/pjpeg" + LINE_END); // �������õ�Content-type����Ҫ��
																// �����ڷ������˱���ļ������͵�
			sb.append(LINE_END);
			params = sb.toString();
			sb = null;

			Log.i(TAG, file.getName() + "=" + params + "##");
			dos.write(params.getBytes());
			/** �ϴ��ļ� */
			InputStream is = new FileInputStream(file);
			//onUploadProcessListener.initUpload((int) file.length());
			byte[] bytes = new byte[1024];
			int len = 0;
			int curLen = 0;
			while ((len = is.read(bytes)) != -1)
			{
				curLen += len;
				dos.write(bytes, 0, len);
			//	onUploadProcessListener.onUploadProcess(curLen);
			}
			is.close();

			dos.write(LINE_END.getBytes());
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();
			dos.write(end_data);
			dos.flush();
			//
			// dos.write(tempOutputStream.toByteArray());
			/**
			 * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
			 */
			int res = conn.getResponseCode();
			responseTime = System.currentTimeMillis();
			requestTime = (int) ((responseTime - requestTime) / 1000);
			Log.e(TAG, "response code:" + res);
			if (res == 200)
			{
				Log.e(TAG, "request success");
				InputStream input = conn.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1)
				{
					sb1.append((char) ss);
				}
				result = sb1.toString();
				Log.e(TAG, "result : " + result);
				//sendMessage(UPLOAD_SUCCESS_CODE, "�ϴ������" + result);
				return;
			} else
			{
				Log.e(TAG, "request error");
				//sendMessage(UPLOAD_SERVER_ERROR_CODE, "�ϴ�ʧ�ܣ�code=" + res);
				return;
			}
		} catch (MalformedURLException e)
		{
			//sendMessage(UPLOAD_SERVER_ERROR_CODE,
			//		"�ϴ�ʧ�ܣ�error=" + e.getMessage());
			e.printStackTrace();
			return;
		} catch (IOException e)
		{
			//sendMessage(UPLOAD_SERVER_ERROR_CODE,
			//		"�ϴ�ʧ�ܣ�error=" + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
	/**
     * ͨ��ƴ�ӵķ�ʽ�����������ݣ�ʵ�ֲ��������Լ��ļ�����
     * 
     * @param url Service net address
     * @param params text content
     * @param files pictures
     * @return String result of Service response
     * @throws IOException
     */
    public static String post( File files,String url, Map<String, String> params)
            throws IOException {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";


        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(10 * 1000); // ������ʱ��
        conn.setDoInput(true);// ��������
        conn.setDoOutput(true);// �������
        conn.setUseCaches(false); // ������ʹ�û���
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);


        // ������ƴ�ı����͵Ĳ���
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }

        Log.d("huang", "---="+sb.toString());
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        // �����ļ�����
        if (files != null)
        {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"img\"; filename=\""
                        + files.getName() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());


                InputStream is = new FileInputStream(files);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }


                is.close();
                outStream.write(LINEND.getBytes());
            }


        // ���������־
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // �õ���Ӧ��
        int res = conn.getResponseCode();
        InputStream in = conn.getInputStream();
        StringBuilder sb2 = new StringBuilder();
        if (res == 200) {
            int ch;
            while ((ch = in.read()) != -1) {
                sb2.append((char) ch);
            }
        }
        outStream.close();
        conn.disconnect();
        return sb2.toString();
    }
    /*
     * Function  :   ����Post���󵽷�����
     * Param     :   params���������ݣ�encode�����ʽ
     * Author    :   ����԰-���ɵ�Ȼ
     */
    public static String submitPostData(Map<String, String> params, String RequestURL) 
    {
        
        byte[] data = getRequestData(params, "utf-8").toString().getBytes();//���������
        try {            
        	URL url = new URL(RequestURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);        //�������ӳ�ʱʱ��
            httpURLConnection.setDoInput(true);                  //�����������Ա�ӷ�������ȡ����
            httpURLConnection.setDoOutput(true);                 //����������Ա���������ύ����
            httpURLConnection.setRequestMethod("POST");    //������Post��ʽ�ύ����
            httpURLConnection.setUseCaches(false);               //ʹ��Post��ʽ����ʹ�û���
            //������������������ı�����
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //����������ĳ���
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //�����������������д������
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            
            int response = httpURLConnection.getResponseCode();            //��÷���������Ӧ��
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //�������������Ӧ���
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * Function  :   ��װ��������Ϣ
     * Param     :   params���������ݣ�encode�����ʽ
     * Author    :   ����԰-���ɵ�Ȼ
     */
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //�洢��װ�õ���������Ϣ
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //ɾ������һ��"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }
    
    /*
     * Function  :   �������������Ӧ�������������ת�����ַ�����
     * Param     :   inputStream����������Ӧ������
     * Author    :   ����԰-���ɵ�Ȼ
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //�洢������
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());    
        return resultData;
    }
	
}