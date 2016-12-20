import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import rennyu.RFrame;

public class Editer {
	private static final Color BGColor=new Color(230,230,230);
	private static final int MODULE_DISTANCE=100;
	private RFrame frame;
	private Broccoli broccoli;
	private ArrayList<Module> module;

	private Point camera_position;
	private Point old_camera_position;

	
	public Editer(String port) throws Exception{
		frame=new RFrame("broccoli",640,480,(byte) 60);
		frame.setVisible(true);
		broccoli=new Broccoli(port);
		module=new ArrayList<Module>();
		camera_position=new Point(10,frame.getHeight()/2);
		old_camera_position=new Point();
	}
	
	private void checkNewModule() throws Exception{
		broccoli.checkEntry();
		while(broccoli.list.size()>0){
			System.out.println("newmodule "+broccoli.list.get(0)+" in "+module.size());
			module.add(new Module(frame,broccoli.list.remove(0)));
		}
	}

	private void moveCamera(){
		if(frame.getMousePosition()!=null){
			if(frame.getClick(RFrame.MOUSE_INPUT_RIGHT)>0){
				if(frame.getClick(RFrame.MOUSE_INPUT_RIGHT)==1){
					old_camera_position=frame.getMousePosition();
				}
				else{
					camera_position.x+=frame.getMousePosition().x-old_camera_position.x;
					camera_position.y+=frame.getMousePosition().y-old_camera_position.y;
					old_camera_position=frame.getMousePosition();
				}
			}
		}
	}
	private void drawModule(){
		for(int i=0;i<module.size();i++){
			module.get(i).draw(i*MODULE_DISTANCE+camera_position.x,camera_position.y);
		}
	}
	public void run() throws Exception{
		while(!frame.update()){
			frame.drawBox(0, 0, frame.getWidth(),frame.getHeight(),BGColor,true);
			drawModule();
			moveCamera();
			checkNewModule();
		}
	}
	public void close() throws Exception{
		if(broccoli!=null){
			broccoli.close();
		}
		if(frame!=null){
			frame.setVisible(false);
		}
		System.exit(0);
	}
}
