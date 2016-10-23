import java.awt.Color;

import rennyu.RFrame;

public class Module {

	public static final int MODULE_DRAW_SIZE=32;

	private float x;
	private float y;
	private byte type;
	private RFrame frame;

	public Module(RFrame frame,float x,float y,byte type){
		this.frame=frame;
		this.x=x;
		this.y=y;
		this.type=type;
	}
	public void setPosition(float x,float y){
		this.x=x;
		this.y=y;
	}
	public void movePosition(float x,float y){
		this.x+=x;
		this.y+=y;
	}
	public float getPositionX(){
		return x;
	}
	public float getPositionY(){
		return y;
	}
	public byte getType(){
		return type;
	}
	public void draw(float center_x,float center_y){
		Color color;

		switch(type){
		case (byte)0x01:color=new Color(255,0,0);break;
		case (byte)0x02:color=new Color(0,0,255);break;
		case (byte)0x03:color=new Color(0,255,0);break;
		case (byte)0xff:color=new Color(255,255,0);break;
		default:color=new Color(0,0,0);break;
		}

		frame.drawBox(
				(int)(x+center_x)+3,
				(int)(y+center_y)+3,
				(int)(x+MODULE_DRAW_SIZE+center_x)+3,
				(int)(y+MODULE_DRAW_SIZE+center_y)+3,
				Color.GRAY,
				true);
		frame.drawBox(
				(int)(x+center_x),
				(int)(y+center_y),
				(int)(x+MODULE_DRAW_SIZE+center_x),
				(int)(y+MODULE_DRAW_SIZE+center_y),
				color,
				true);
	}
}
