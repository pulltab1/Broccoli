package rennyu;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RTextField implements  ActionListener{

	private TextField tex;

	public RTextField(String str,int x,int y,int width,int height){
		tex=new TextField(str);
		tex.setLocation(x,y);
		tex.setSize(width,height);
		tex.addActionListener(this);
	}
	public RTextField(String str,int width,int height){
		tex=new TextField(str);
		tex.setSize(width,height);
		tex.addActionListener(this);
	}
	public void setString(String text){
		tex.setText(text);
	}
	public String getString(){
		return tex.getText();
	}
	public int getInteger(){
		return Integer.parseInt(tex.getText());
	}
	public float getFloat(){
		return Float.parseFloat(tex.getText());
	}
	public int getShort(){
		return Short.parseShort(tex.getText());
	}
	public double getDouble(){
		return Double.parseDouble(tex.getText());
	}
	public TextField getInstance(){
		return tex;
	}
	public void actionPerformed(ActionEvent e) {

	}
}
