import java.util.ArrayList;
import java.util.HashMap;

public class Blopic {
	private HashMap<Byte,ArrayList<Byte>> list=new HashMap<Byte,ArrayList<Byte>>();
	private Serial serial;
	public Blopic(String name){
		serial=new Serial(name);
	}
	public void load(){
		while(!serial.read().equals("start"));
		while(true){
			String[] str=serial.read().split(":");

			byte[] num={(byte)Integer.parseInt(str[0]),(byte)Integer.parseInt(str[1])};

			if(!list.containsKey(num[1])){
				list.put(num[1],new ArrayList<Byte>());
			}
			list.get(num[1]).add(num[0]);
			if(num[1]==(byte)0xff){
				break;
			}
		}
	}

	public HashMap<Byte, ArrayList<Byte>> getList(){
		return list;
	}

	public void write(byte[] data){
		serial.write(data);
	}

	public void close(){
		serial.write((byte)0xff);
		serial.close();
	}
}
