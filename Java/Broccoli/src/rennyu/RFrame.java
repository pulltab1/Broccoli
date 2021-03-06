package rennyu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


//骨格処理

/**
 * AWTによる機能を提供します<br>
 * @see RFile
 * */
public class RFrame extends Frame implements WindowListener,KeyListener,MouseListener,MouseMotionListener,MouseWheelListener{

	//裏画面
	private static Graphics bufferScreen;
	private static Image backScreen;

	//フレームレート設定
	private final byte FRAME_RATE;
	private long newFrame;
	private long oldFrame;

	//ウィンドウサイズ
	/**ウィンドウ幅*/
	private static int width;
	/**ウィンドウ高さ*/
	private static int height;

	//マウスのボタンの識別番号
	/**左クリック*/
	public static final byte MOUSE_INPUT_LEFT=0;
	/**中央ボタン*/
	public static final byte MOUSE_INPUT_CENTER=1;
	/**右クリック*/
	public static final byte MOUSE_INPUT_RIGHT=2;

	/*
	 * キーのコードの設定
	 * コードの個数は59個、下にもそう書いてある
	 */
	/**キーの最大数*/
	public static final byte MAX_KEY_CODE		=59;

	//コード番号、入力情報取得の時はこれらを指定する
	/**キーコード1*/
	public static final byte KEY_CODE_1 = 0;
	/**キーコード2*/
	public static final byte KEY_CODE_2 = 1;
	/**キーコード3*/
	public static final byte KEY_CODE_3 = 2;
	/**キーコード4*/
	public static final byte KEY_CODE_4 = 3;
	/**キーコード5*/
	public static final byte KEY_CODE_5 = 4;
	/**キーコード6*/
	public static final byte KEY_CODE_6 = 5;
	/**キーコード7*/
	public static final byte KEY_CODE_7 = 6;
	/**キーコード8*/
	public static final byte KEY_CODE_8 = 7;
	/**キーコード9*/
	public static final byte KEY_CODE_9 = 8;
	/**キーコード0*/
	public static final byte KEY_CODE_0 = 9;
	/**キーコードQ*/
	public static final byte KEY_CODE_Q = 10;
	/**キーコードW*/
	public static final byte KEY_CODE_W = 11;
	/**キーコードE*/
	public static final byte KEY_CODE_E = 12;
	/**キーコードR*/
	public static final byte KEY_CODE_R = 13;
	/**キーコードT*/
	public static final byte KEY_CODE_T = 14;
	/**キーコードY*/
	public static final byte KEY_CODE_Y = 15;
	/**キーコードU*/
	public static final byte KEY_CODE_U = 16;
	/**キーコードI*/
	public static final byte KEY_CODE_I = 17;
	/**キーコードO*/
	public static final byte KEY_CODE_O = 18;
	/**キーコードP*/
	public static final byte KEY_CODE_P = 19;
	/**キーコードA*/
	public static final byte KEY_CODE_A = 20;
	/**キーコードS*/
	public static final byte KEY_CODE_S = 21;
	/**キーコードD*/
	public static final byte KEY_CODE_D = 22;
	/**キーコードF*/
	public static final byte KEY_CODE_F = 23;
	/**キーコードG*/
	public static final byte KEY_CODE_G = 24;
	/**キーコードH*/
	public static final byte KEY_CODE_H = 25;
	/**キーコードJ*/
	public static final byte KEY_CODE_J = 26;
	/**キーコードK*/
	public static final byte KEY_CODE_K = 27;
	/**キーコードL*/
	public static final byte KEY_CODE_L = 28;
	/**キーコードZ*/
	public static final byte KEY_CODE_Z = 29;
	/**キーコードX*/
	public static final byte KEY_CODE_X = 30;
	/**キーコードC*/
	public static final byte KEY_CODE_C = 31;
	/**キーコードV*/
	public static final byte KEY_CODE_V = 32;
	/**キーコードB*/
	public static final byte KEY_CODE_B = 33;
	/**キーコードN*/
	public static final byte KEY_CODE_N = 34;
	/**キーコードM*/
	public static final byte KEY_CODE_M = 35;
	/**キーコードシフト*/
	public static final byte KEY_CODE_SHIFT = 36;
	/**キーコードコントロール*/
	public static final byte KEY_CODE_CONTROL = 37;
	/**キーコードスペース*/
	public static final byte KEY_CODE_SPACE = 38;
	/**キーコードエンター*/
	public static final byte KEY_CODE_RETURN = 39;
	/**キーコードオルト*/
	public static final byte KEY_CODE_ALT = 40;
	/**キーコードバックスペース*/
	public static final byte KEY_CODE_BACK = 41;
	/**キーコードエスケープ*/
	public static final byte KEY_CODE_ESCAPE = 42;
	/**キーコードF1*/
	public static final byte KEY_CODE_F1 = 43;
	/**キーコードF2*/
	public static final byte KEY_CODE_F2 = 44;
	/**キーコードF3*/
	public static final byte KEY_CODE_F3 = 45;
	/**キーコードF4*/
	public static final byte KEY_CODE_F4 = 46;
	/**キーコードF5*/
	public static final byte KEY_CODE_F5 = 47;
	/**キーコードF6*/
	public static final byte KEY_CODE_F6 = 48;
	/**キーコードF7*/
	public static final byte KEY_CODE_F7 = 49;
	/**キーコードF8*/
	public static final byte KEY_CODE_F8 = 50;
	/**キーコードF9*/
	public static final byte KEY_CODE_F9 = 51;
	/**キーコードF10*/
	public static final byte KEY_CODE_F10 = 52;
	/**キーコードF11*/
	public static final byte KEY_CODE_F11 = 53;
	/**キーコードF12*/
	public static final byte KEY_CODE_F12 = 54;
	/**キーコード上*/
	public static final byte KEY_CODE_UP = 55;
	/**キーコード下*/
	public static final byte KEY_CODE_DOWN = 56;
	/**キーコード左*/
	public static final byte KEY_CODE_LEFT = 57;
	/**キーコード右*/
	public static final byte KEY_CODE_RIGHT = 58;

	/*
	 * マウスのボタンの設定
	 * ボタン数は3個、下にもそう書いてある
	 */
	/**マウスボタンの最大数*/
	private static final byte MAX_MOUSE_INPUT=3;

	//変数、クラスの宣言
	private short key[];	//キー格納先
	private short mouseClick[];	//マウスクリック格納先
	private Point mousePosition=new Point();	//マウスの位置
	private short wheelRotation;	//ホイールの回転力
	private boolean windowCloseFlag;	//ウィンドウが閉じた
	private Point inputMousePosition;
	private short inputwheelRotation;
	private Random random;

	/**
	 * カーソルに何も指定しなかったときは非表示用カーソルを作成する
	 */
	public void setCursor(){
		BufferedImage image = new BufferedImage(16,16,
                BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2 = image.createGraphics();
		//黒で透明 black & transparency
		g2.setColor(new Color(0,0,0,0));
		g2.fillRect(0,0, 16,16);
		g2.dispose();
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
		                image, new Point(0,0), "null_cursor"));

	}
	/**
	 * 画像データを読み込みます
	 * @param path ファイルパス
	 * @return nullの場合取得できなかった
	 */
	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(new File(path));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 画像データのクローンを作成して出力します
	 * @param src ベースとなる画像
	 * @return コピーした画像
	 */
	public static BufferedImage copyImage(BufferedImage src){
		BufferedImage dist=new BufferedImage(
				src.getWidth(), src.getHeight(), src.getType());
		dist.setData(src.getData());
		return dist;
	}
	/**
	 * ウィンドウの初期設定をするコンストラクタ<br>
	 * @param title タイトル
	 * @param w ウィンドウの横幅
	 * @param h ウィンドウの縦幅
	 * @param Screenrate 動作フレーム
	 */
	public RFrame(String title,int w,int h,byte Screenrate){
		key=new short[MAX_KEY_CODE];
		mouseClick=new short[MAX_MOUSE_INPUT];
		width=w;
		height=h;
		FRAME_RATE=Screenrate;
		super.setTitle(title);
		super.setSize(width+getInsets().left,height+getInsets().top);
		super.addWindowListener(this);
		super.addKeyListener(this);
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		super.addMouseWheelListener(this);
		inputMousePosition=new Point();
		random=new Random();
	}
	/**
	 * ウィンドウを表示します<br>
	 * この命令は各種設定が終わった後に使用してください
	 * @param flag trueなら表示、falseなら非表示
	 */
	public void setVisible(boolean flag){
		super.setVisible(flag);
		backScreen=createImage(width,height);
		bufferScreen=backScreen.getGraphics();
	}
	/**
	 * ユーザーがウィンドウのサイズを自由に編集できるか(ウィンドウサイズに合わせ引き伸ばされます)
	 * @param flag trueなら変更可能、falseなら不可
	 */
	public void setResizable(boolean flag){
		super.setResizable(flag);
	}
	/**
	 * レイヤーマネージャーの設定をします
	 * @param mng nullなら無効、それ以外はそれに合わせた設定を適用します
	 */
	public void setLayout(LayoutManager mng){
		super.setLayout(mng);
	}
	/**
	 * ウィンドウのサイズを変更します(ウィンドウサイズに合わせて描画領域も拡張されます)
	 * @param w ウィンドウの横幅
	 * @param h ウィンドウの縦幅
	 */
	public void changeWindowSize(int w,int h){
		super.setSize(w+getInsets().left,h+getInsets().top);
		backScreen=createImage(w,h);
		bufferScreen=backScreen.getGraphics();
	}
	/**
	 * ウィンドウのタイトルを変更します
	 * @param title タイトル
	 */
	public void setTitle(String title){
		super.setTitle(title);
	}
	/**
	 * 指定したキーの押出フレーム数を取得します<br>
	 * 記号部分は取得できません<br>
	 * キーコードはKeyInputクラスの定数から利用してください
	 * @param code キーコード
	 * @return 押出フレーム
	 */
	public int getKey(int code){
		return key[code]-1;
	}
	/**
	 * マウスの位置を取得します
	 * @return 座標
	 */
	public Point getmousePosition(){
		return mousePosition;
	}
	/**
	 * ホイールの回転数を取得します
	 * @return 回転フレーム
	 */
	public int getwheelRotation(){
		return wheelRotation;
	}
	/**
	 * 指定したマウスボタンの押出フレーム数を取得します<br>
	 * 左、右、ホイールのクリックのみ対応しています<br>
	 * キーコードはKeyInputから取得してください
	 * @param code ボタン番号
	 * @return 押出フレーム
	 */
	public int getClick(int code){
		return mouseClick[code]-1;
	}
	public int getRandom(int bound){
		return random.nextInt(bound);
	}
	/**
	 * 画面更新等ループ毎に行わなければならない処理を行います<br>
	 * メインループには必ず入れて下さい
	 * @return ウィンドウが閉じられればtrue、それ以外はfalse
	 */
	public boolean update(){

		long sleeptime;
		byte i;

		try {
			/*
			 * フレームレートをある程度固定する。
			 * 精度は気にしたら負け
			 */
			oldFrame=newFrame;
			newFrame = System.currentTimeMillis();
			sleeptime=(int)(1000/FRAME_RATE)-(newFrame-oldFrame);
			if(sleeptime<2)sleeptime=2;
			Thread.sleep(sleeptime);
		}
		catch (InterruptedException e) {
			System.out.println(e);
		}

		 //画像を再描画する
		paint();

		//キーの入力情報を取得
		for(i=0;i<MAX_KEY_CODE;i++){
			if(key[i]!=0){
				if(key[i]<12000)
					key[i]++;
			}
			else key[i]=0;
		}

		//マウスの入力情報を取得(3ボタンまで)
		for(i=0;i<MAX_MOUSE_INPUT;i++){
			if(mouseClick[i]!=0){
				if(mouseClick[i]<12000)
					mouseClick[i]++;
			}
			else mouseClick[i]=0;
		}

		//マウスの位置、ホイールの回転数を取得
		mousePosition.x=inputMousePosition.x;
		mousePosition.y=inputMousePosition.y;
		wheelRotation=getinputwheelRotation();

		//ウィンドウが閉じられたかどうかを取得
		return windowCloseFlag;
	}
	/**
	 * 画像を回転させます。回転させた画像は戻り値として返します
	 * @param src 元画像
	 * @param angle 角度(ラジアン)
	 * @return 回転後の画像
	 */
	public static BufferedImage rotationImage(BufferedImage src,float angle){
		BufferedImage retima;	//戻り値として渡す処理後の画像
		AffineTransform af = new AffineTransform();	//処理準備用オブジェクト
		AffineTransformOp op;	//回転用のオブジェクト
		int maxsize;

		//横長、縦長どちらが大きいか
		maxsize=src.getWidth();
		if(src.getHeight()>src.getWidth())
			maxsize = src.getHeight();

		//回転後はみ出さないように画像を広げる
		retima=new BufferedImage(maxsize,maxsize,src.getType());

		//回転する
		af.setToRotation(angle,src.getWidth()/2,src.getHeight()/2);
		op = new AffineTransformOp(af,AffineTransformOp.TYPE_BICUBIC);
		op.filter(src,retima);

		return retima;
	}
	/**
	 * 画像を描画します
	 * @param image 表示する画像
	 * @param x X座標
	 * @param y Y座標
	 */
	public void drawImage(Image image,int x,int y){
		bufferScreen.drawImage(image,x,y,null);
	}
	/**
	 * 画像を描画します
	 * @param image 表示する画像
	 * @param x X座標
	 * @param y Y座標
	 * @param w 横幅
	 * @param h 縦幅
	 */
	public void drawImage(Image image,int x,int y,int w,int h){
		bufferScreen.drawImage(image,x,y,w,h,null);
	}
	/**
	 * 画像を描画します
	 * @param image 表示する画像
	 * @param x X座標
	 * @param y Y座標
	 * @param zx X分の表示倍率
	 * @param zy Y分の表示倍率
	 */
	public void drawImage(Image image,int x,int y,float zx,float zy){
		bufferScreen.drawImage(image,x,y,(int)(image.getWidth(null)*zx),(int)(image.getHeight(null)*zy),null);
	}
	/**
	 * 画像を描画します
	 * @param image 表示する画像
	 * @param x X座標
	 * @param y Y座標
	 * @param trunh Y座標を軸として反転します
	 * @param trunv X座標を軸として反転します
	 */
	public void drawImage(Image image,int x,int y,boolean trunh,boolean trunv){
		int h=image.getWidth(null);
		int v=image.getHeight(null);
		int x2,y2;

		if(trunh==false)x2=h;
		else{
			x2=h*-1;
			x+=h;
		}
		if(trunv==false)y2=v;
		else{
			y2=v*-1;
			y+=v;
		}
		bufferScreen.drawImage(image,x,y,x2,y2,null);
	}
	/**
	 * 矩形を描画します
	 * @param x1 左上のX座標
	 * @param y1 左上のY座標
	 * @param x2 右下のX座標
	 * @param y2 右下のY座標
	 * @param col 色
	 * @param fill 塗りつぶす場合true
	 */
	public void drawBox(int x1,int y1,int x2,int y2,Color col,boolean fill){
		bufferScreen.setColor(col);
		if(fill==true)bufferScreen.fillRect(x1,y1,x2-x1,y2-y1);
		else bufferScreen.drawRect(x1,y1,x2-x1,y2-y1);
	}
	/**
	 * 円を描画します
	 * @param x 中心のX座標
	 * @param y 中心のY座標
	 * @param r 円の半径
	 * @param col 色
	 * @param fill 塗りつぶす場合true
	 */
	public void drawCircle(int x,int y,int r,Color col,boolean fill){
		bufferScreen.setColor(col);
		if(fill==false)bufferScreen.drawOval(x-r/2,y-r/2,r,r);
		else bufferScreen.fillOval(x-r/2,y-r/2,r,r);
	}
	/**
	 * 線を描画します
	 * @param x1 左上のX座標
	 * @param y1 左上のY座標
	 * @param x2 右下のX座標
	 * @param y2 右下のY座標
	 * @param col 色
	 */
	public void drawLine(int x1,int y1,int x2,int y2,Color col){
		bufferScreen.setColor(col);
		bufferScreen.drawLine(x1, y1, x2, y2);
	}
	/**
	 * テキストを描画します
	 * @param x X座標
	 * @param y Y座標
	 * @param col 色
	 * @param fon フォント(Fontクラスを使用します)
	 * @param str テキスト
	 */
	public void drawString(int x,int y,Color col,Font fon,String str){
		bufferScreen.setColor(col);
		bufferScreen.setFont(fon);
		bufferScreen.drawString(str,x,y);
	}
	/**
	 * テキストを描画します
	 * @param x X座標
	 * @param y Y座標
	 * @param col 色
	 * @param str テキスト
	 */
	public void drawString(int x,int y,Color col,String str){
		bufferScreen.setColor(col);
		bufferScreen.setFont(new Font("MS 明朝",Font.PLAIN,20));
		bufferScreen.drawString(str,x,y);
	}
	public void windowClosing(WindowEvent event){
		windowCloseFlag=true;
	}
	public void paint(){
		/*
		 * 画像処理を"勝手"に行ってくれる良く分からんメゾット
		 * 勝手に行うので全てのデータが読み込まれた後描画する
		 * ついでにダブルバッファ
		 * ウィンドウの引き伸ばしに対応させた
		 */
			Graphics g = getGraphics();
			g.drawImage(
					backScreen,
					getInsets().left,
					getInsets().top,
					(int)((float)getSize().width/(RFrame.width+getInsets().left)*RFrame.width),
					(int)((float)getSize().height/(RFrame.height+getInsets().top)*RFrame.height),
					null);
    }
	public void keyPressed(KeyEvent event){
		//キーが押されているかの判定
		int code;
		code=event.getKeyCode();
		switch(code){
		case KeyEvent.VK_1:key[KEY_CODE_1]=1;break;
		case KeyEvent.VK_2:key[KEY_CODE_2]=1;break;
		case KeyEvent.VK_3:key[KEY_CODE_3]=1;break;
		case KeyEvent.VK_4:key[KEY_CODE_4]=1;break;
		case KeyEvent.VK_5:key[KEY_CODE_5]=1;break;
		case KeyEvent.VK_6:key[KEY_CODE_6]=1;break;
		case KeyEvent.VK_7:key[KEY_CODE_7]=1;break;
		case KeyEvent.VK_8:key[KEY_CODE_8]=1;break;
		case KeyEvent.VK_9:key[KEY_CODE_9]=1;break;
		case KeyEvent.VK_0:key[KEY_CODE_0]=1;break;
		case KeyEvent.VK_Q:key[KEY_CODE_Q]=1;break;
		case KeyEvent.VK_W:key[KEY_CODE_W]=1;break;
		case KeyEvent.VK_E:key[KEY_CODE_E]=1;break;
		case KeyEvent.VK_R:key[KEY_CODE_R]=1;break;
		case KeyEvent.VK_T:key[KEY_CODE_T]=1;break;
		case KeyEvent.VK_Y:key[KEY_CODE_Y]=1;break;
		case KeyEvent.VK_U:key[KEY_CODE_U]=1;break;
		case KeyEvent.VK_I:key[KEY_CODE_I]=1;break;
		case KeyEvent.VK_O:key[KEY_CODE_O]=1;break;
		case KeyEvent.VK_P:key[KEY_CODE_P]=1;break;
		case KeyEvent.VK_A:key[KEY_CODE_A]=1;break;
		case KeyEvent.VK_S:key[KEY_CODE_S]=1;break;
		case KeyEvent.VK_D:key[KEY_CODE_D]=1;break;
		case KeyEvent.VK_F:key[KEY_CODE_F]=1;break;
		case KeyEvent.VK_G:key[KEY_CODE_G]=1;break;
		case KeyEvent.VK_H:key[KEY_CODE_H]=1;break;
		case KeyEvent.VK_J:key[KEY_CODE_J]=1;break;
		case KeyEvent.VK_K:key[KEY_CODE_K]=1;break;
		case KeyEvent.VK_L:key[KEY_CODE_L]=1;break;
		case KeyEvent.VK_Z:key[KEY_CODE_Z]=1;break;
		case KeyEvent.VK_X:key[KEY_CODE_X]=1;break;
		case KeyEvent.VK_C:key[KEY_CODE_C]=1;break;
		case KeyEvent.VK_V:key[KEY_CODE_V]=1;break;
		case KeyEvent.VK_B:key[KEY_CODE_B]=1;break;
		case KeyEvent.VK_N:key[KEY_CODE_N]=1;break;
		case KeyEvent.VK_M:key[KEY_CODE_M]=1;break;
		case KeyEvent.VK_SHIFT:key[KEY_CODE_SHIFT]=1;break;
		case KeyEvent.VK_CONTROL:key[KEY_CODE_CONTROL]=1;break;
		case KeyEvent.VK_SPACE:key[KEY_CODE_SPACE]=1;break;
		case KeyEvent.VK_ENTER:key[KEY_CODE_RETURN]=1;break;
		case KeyEvent.VK_ALT:key[KEY_CODE_ALT]=1;break;
		case KeyEvent.VK_BACK_SPACE:key[KEY_CODE_BACK]=1;break;
		case KeyEvent.VK_ESCAPE:key[KEY_CODE_ESCAPE]=1;break;
		case KeyEvent.VK_F1:key[KEY_CODE_F1]=1;break;
		case KeyEvent.VK_F2:key[KEY_CODE_F2]=1;break;
		case KeyEvent.VK_F3:key[KEY_CODE_F3]=1;break;
		case KeyEvent.VK_F4:key[KEY_CODE_F4]=1;break;
		case KeyEvent.VK_F5:key[KEY_CODE_F5]=1;break;
		case KeyEvent.VK_F6:key[KEY_CODE_F6]=1;break;
		case KeyEvent.VK_F7:key[KEY_CODE_F7]=1;break;
		case KeyEvent.VK_F8:key[KEY_CODE_F8]=1;break;
		case KeyEvent.VK_F9:key[KEY_CODE_F9]=1;break;
		case KeyEvent.VK_F10:key[KEY_CODE_F10]=1;break;
		case KeyEvent.VK_F11:key[KEY_CODE_F11]=1;break;
		case KeyEvent.VK_F12:key[KEY_CODE_F12]=1;break;
		case KeyEvent.VK_UP:key[KEY_CODE_UP]=1;break;
		case KeyEvent.VK_DOWN:key[KEY_CODE_DOWN]=1;break;
		case KeyEvent.VK_LEFT:key[KEY_CODE_LEFT]=1;break;
		case KeyEvent.VK_RIGHT:key[KEY_CODE_RIGHT]=1;break;
		}
	}
	public void keyReleased(KeyEvent event){
		//キーが離されているかの判定
		int code;
		code=event.getKeyCode();
		switch(code){
		case KeyEvent.VK_1:key[KEY_CODE_1]=0;break;
		case KeyEvent.VK_2:key[KEY_CODE_2]=0;break;
		case KeyEvent.VK_3:key[KEY_CODE_3]=0;break;
		case KeyEvent.VK_4:key[KEY_CODE_4]=0;break;
		case KeyEvent.VK_5:key[KEY_CODE_5]=0;break;
		case KeyEvent.VK_6:key[KEY_CODE_6]=0;break;
		case KeyEvent.VK_7:key[KEY_CODE_7]=0;break;
		case KeyEvent.VK_8:key[KEY_CODE_8]=0;break;
		case KeyEvent.VK_9:key[KEY_CODE_9]=0;break;
		case KeyEvent.VK_0:key[KEY_CODE_0]=0;break;
		case KeyEvent.VK_Q:key[KEY_CODE_Q]=0;break;
		case KeyEvent.VK_W:key[KEY_CODE_W]=0;break;
		case KeyEvent.VK_E:key[KEY_CODE_E]=0;break;
		case KeyEvent.VK_R:key[KEY_CODE_R]=0;break;
		case KeyEvent.VK_T:key[KEY_CODE_T]=0;break;
		case KeyEvent.VK_Y:key[KEY_CODE_Y]=0;break;
		case KeyEvent.VK_U:key[KEY_CODE_U]=0;break;
		case KeyEvent.VK_I:key[KEY_CODE_I]=0;break;
		case KeyEvent.VK_O:key[KEY_CODE_O]=0;break;
		case KeyEvent.VK_P:key[KEY_CODE_P]=0;break;
		case KeyEvent.VK_A:key[KEY_CODE_A]=0;break;
		case KeyEvent.VK_S:key[KEY_CODE_S]=0;break;
		case KeyEvent.VK_D:key[KEY_CODE_D]=0;break;
		case KeyEvent.VK_F:key[KEY_CODE_F]=0;break;
		case KeyEvent.VK_G:key[KEY_CODE_G]=0;break;
		case KeyEvent.VK_H:key[KEY_CODE_H]=0;break;
		case KeyEvent.VK_J:key[KEY_CODE_J]=0;break;
		case KeyEvent.VK_K:key[KEY_CODE_K]=0;break;
		case KeyEvent.VK_L:key[KEY_CODE_L]=0;break;
		case KeyEvent.VK_Z:key[KEY_CODE_Z]=0;break;
		case KeyEvent.VK_X:key[KEY_CODE_X]=0;break;
		case KeyEvent.VK_C:key[KEY_CODE_C]=0;break;
		case KeyEvent.VK_V:key[KEY_CODE_V]=0;break;
		case KeyEvent.VK_B:key[KEY_CODE_B]=0;break;
		case KeyEvent.VK_N:key[KEY_CODE_N]=0;break;
		case KeyEvent.VK_M:key[KEY_CODE_M]=0;break;
		case KeyEvent.VK_SHIFT:key[KEY_CODE_SHIFT]=0;break;
		case KeyEvent.VK_CONTROL:key[KEY_CODE_CONTROL]=0;break;
		case KeyEvent.VK_SPACE:key[KEY_CODE_SPACE]=0;break;
		case KeyEvent.VK_ENTER:key[KEY_CODE_RETURN]=0;break;
		case KeyEvent.VK_ALT:key[KEY_CODE_ALT]=0;break;
		case KeyEvent.VK_BACK_SPACE:key[KEY_CODE_BACK]=0;break;
		case KeyEvent.VK_ESCAPE:key[KEY_CODE_ESCAPE]=0;break;
		case KeyEvent.VK_F1:key[KEY_CODE_F1]=0;break;
		case KeyEvent.VK_F2:key[KEY_CODE_F2]=0;break;
		case KeyEvent.VK_F3:key[KEY_CODE_F3]=0;break;
		case KeyEvent.VK_F4:key[KEY_CODE_F4]=0;break;
		case KeyEvent.VK_F5:key[KEY_CODE_F5]=0;break;
		case KeyEvent.VK_F6:key[KEY_CODE_F6]=0;break;
		case KeyEvent.VK_F7:key[KEY_CODE_F7]=0;break;
		case KeyEvent.VK_F8:key[KEY_CODE_F8]=0;break;
		case KeyEvent.VK_F9:key[KEY_CODE_F9]=0;break;
		case KeyEvent.VK_F10:key[KEY_CODE_F10]=0;break;
		case KeyEvent.VK_F11:key[KEY_CODE_F11]=0;break;
		case KeyEvent.VK_F12:key[KEY_CODE_F12]=0;break;
		case KeyEvent.VK_UP:key[KEY_CODE_UP]=0;break;
		case KeyEvent.VK_DOWN:key[KEY_CODE_DOWN]=0;break;
		case KeyEvent.VK_LEFT:key[KEY_CODE_LEFT]=0;break;
		case KeyEvent.VK_RIGHT:key[KEY_CODE_RIGHT]=0;break;
		}

	}
	public void mousePressed(MouseEvent event){
		/*
		 * ボタンが押されたかどうか判定する
		 * エラーが発生した場合、コンソールに表示する
		 */
		switch(event.getButton()){
		case MouseEvent.BUTTON1:
			mouseClick[MOUSE_INPUT_LEFT]=1;
			break;
		case MouseEvent.BUTTON2:
			mouseClick[MOUSE_INPUT_CENTER]=1;
			break;
		case MouseEvent.BUTTON3:
			mouseClick[MOUSE_INPUT_RIGHT]=1;
			break;
		default:
			System.out.println("Error:MouseInput_Pressed_UnknownCode");
			break;
		}
	}
	public void mouseReleased(MouseEvent event){
		/*
		 * ボタンが離されたかどうか判定する
		 * エラーが発生した場合、コンソールに表示する
		 */
		switch(event.getButton()){
		case MouseEvent.BUTTON1:
			mouseClick[MOUSE_INPUT_LEFT]=0;
			break;
		case MouseEvent.BUTTON2:
			mouseClick[MOUSE_INPUT_CENTER]=0;
			break;
		case MouseEvent.BUTTON3:
			mouseClick[MOUSE_INPUT_RIGHT]=0;
			break;
		default:
			System.out.println("Error:MouseInput_Released_UnknownCode");
			break;
		}
	}
	public void mouseDragged(MouseEvent event){
		inputMousePosition.x=event.getX();
		inputMousePosition.y=event.getY();
	}
	public void mouseMoved(MouseEvent event){
		//マウスポインタの位置を代入する
		inputMousePosition.x=event.getX();
		inputMousePosition.y=event.getY();
	}
	public void mouseWheelMoved(MouseWheelEvent event) {

		//回転していた場合回転数を代入する
		inputwheelRotation=(short) event.getWheelRotation();

	}
	private short getinputwheelRotation(){
		/*
		 * 回転数を取得する
		 * この処理が入ると回転数は0になる
		 * 回転数が0になったらmouseWheelMovedが呼ばれるかわからないので適当に
		 */
		short rota;

		rota=inputwheelRotation;
		inputwheelRotation=0;
		return rota;
	}

	//以下、インターフェースの都合上現れたやつ
	public void windowOpened(WindowEvent event) {}
	public void windowClosed(WindowEvent event){}
	public void windowIconified(WindowEvent event){}
	public void windowDeiconified(WindowEvent event){}
	public void windowActivated(WindowEvent event){}
	public void windowDeactivated(WindowEvent event){}
	public void keyTyped(KeyEvent event){}
	public void mouseClicked(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseExited(MouseEvent event){}
}