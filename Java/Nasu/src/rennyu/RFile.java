package rennyu;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;


/**
 * ファイル操作機能を提供します
 * @see RFrame
 */
public class RFile{
	/**
	 * 文字コード
	 */
	private String code;
	private static final String SEPARATE_WORD="#<NEWLINE>#";

	/**
	 * 文字コードをUTF-8に設定したうえで作成
	 */
	public RFile(){
		code="UTF-8";
	}
	/**
	 * 文字コードを設定したうえで作成
	 * @param c 文字コード
	 */
	public RFile(String c){
		code=c;
	}

	/**
	 * 画像を保存します
	 * @param src 保存する画像
	 * @param path 保存先のファイルパス
	 * @param format 画像ファイルのフォーマット
	 * @return 成功していればfalse
	 */
	public int writeBufferedImage(BufferedImage src,String path,String format){
		try {
			OutputStream out=new FileOutputStream(path);
	    	ImageIO.write(src,format,out);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	/**
	 * キャプチャーしたスクリーンを取得します
	 * @return 取得したスクリーン
	 */
	public BufferedImage getScreenCapture(){
		try {
			return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		}
		catch (HeadlessException e) {
			e.printStackTrace();
		}
		catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ファイルのテキスト一括で取得します
	 * @param path ファイルパス
	 * @return 取得したテキスト
	 */
	public String readString(String path){
		String str=new String();
		String temp;

		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),code));
			while((temp=br.readLine())!=null) {
				str+=temp;
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}
	/**
	 * ファイルのテキスト一括で取得します
	 * @param path ファイルパス
	 * @return 取得したテキスト
	 */
	public String[] readStrings(String path){
		String str=new String();
		String temp;

		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),code));
			while((temp=br.readLine())!=null) {
				str+=temp+SEPARATE_WORD;
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return str.split(SEPARATE_WORD);
	}

	/**
	 * ファイルにテキストを書き込みます
	 * @param text 書き込むテキスト
	 * @param path ファイルパス
	 */
	public void writeString(String text,String path){

		try {
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path)),code));
			bw.write(text);
			bw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ファイルにテキストを書き込みます
	 * @param text 書き込むテキスト
	 * @param path ファイルパス
	 */
	public void writeStrings(String text[],String path){

		try {
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path)),code));
			if(text.length>1){
				for(int i=0;i<text.length;i++){
					bw.write(text[i]);
					if(i!=text.length-1)bw.newLine();
				}
			}
			else bw.write(text[0]);
			bw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * OSによって違うファイルパスをOSに合わせて修正します<br>
	 * このメソッドを使用する場合区切り文字は"/"で統一してください
	 * @param path 修正するファイルパス
	 * @return 修正後のファイルパス
	 */
	public String fixFilePath(String path){
		String ret=new String();
		int i;

		for(i=0;i<path.length();i++){
			if(path.charAt(i)=='/')
				ret+=File.separator;
			else ret+=path.charAt(i);
		}

		return ret;
	}
}
