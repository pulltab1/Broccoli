import java.awt.Color;

import rennyu.RFrame;

public class Module {

	public static final int MODULE_DRAW_SIZE=32;

	private byte type;
	private RFrame frame;

	public Module(RFrame frame,byte type){
		this.frame=frame;
		this.type=type;
	}
	public byte getType(){
		return type;
	}
	public void draw(float x,float y){
		Color color;

		switch(type){
		case (byte)0x01:color=new Color(255,0,0);break;
		case (byte)0x02:color=new Color(0,0,255);break;
		case (byte)0x03:color=new Color(0,255,0);break;
		case (byte)0xff:color=new Color(255,255,0);break;
		default:color=new Color(0,0,0);break;
		}

		frame.drawBox((int)x+3,(int)y+3,(int)x+MODULE_DRAW_SIZE+3,(int)y+MODULE_DRAW_SIZE+3,Color.GRAY,true);
		frame.drawBox((int)x,(int)y,(int)x+MODULE_DRAW_SIZE,(int)y+MODULE_DRAW_SIZE,color,true);
	}
}
