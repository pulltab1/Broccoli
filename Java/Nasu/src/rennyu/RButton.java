package rennyu;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RButton implements ActionListener{

	//
	private boolean status;
	private Button but;

	public RButton(String str,int x,int y,int width,int height){
		but=new Button(str);
		but.setLocation(x,y);
		but.setSize(width,height);
		but.addActionListener(this);
	}
	public RButton(String str,int width,int height){
		but=new Button(str);
		but.setSize(width,height);
		but.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		status=true;
	}
	public Button getInstance(){
		return but;
	}
	public boolean getPush(){
		boolean ret=status;
		status=false;
		return ret;
	}
}
