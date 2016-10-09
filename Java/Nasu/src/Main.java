import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import rennyu.RFrame;

public class Main{
	public static RFrame frame;
	public static Blopic bp=new Blopic("COM2");
	public static void main(String[] args) {
		HashMap<Byte,ArrayList<Byte>> list;
		byte count=0;

		frame=new RFrame("Blopic",640,480,(byte) 60);
		frame.setVisible(true);

		bp.load();

		list=bp.getList();
		
		while(!frame.update()){
			frame.drawBox(0, 0, frame.getWidth(),frame.getHeight(),Color.BLACK,true);
			if(frame.getClick(RFrame.MOUSE_INPUT_LEFT)%10==1){
				if(list.containsKey((byte)1)){
					if(count<list.get((byte)1).size()){
						for(byte i=0;i<list.get((byte)1).size();i++){
							byte[] data={list.get((byte)1).get(i),(byte)1};
							if(i==count){
								data[1]=1;
							}
							else{
								data[1]=0;
							}
							bp.write(data);
						}
					}
					count++;
					if(count>=list.get((byte)1).size()){
						count=0;
					}
				}
			}
		}
		bp.close();
		System.exit(0);
	}
}
