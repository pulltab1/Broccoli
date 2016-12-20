import java.util.ArrayList;

public class Broccoli {
	public ArrayList<Byte> list;
	private Serial serial;

	public Broccoli(String port) throws Exception{
		serial=new Serial(port);
		list=new ArrayList<Byte>();
	}

	public void checkEntry() throws Exception{
		serial.write((byte)0xf0);
		while(!serial.read().equals("begin"));
		while(true){
			String buf=serial.read();
			if(buf.equals("end")){
				break;
			}
			list.add((byte)Integer.parseInt(buf));
		}
	}

	public String[] query(byte[] data) throws Exception{
		String[] ret=new String[data.length-1];
		serial.write(data);
		for(int i=0;i<ret.length;i++){
			ret[i]=serial.read();
		}
		return ret;
	}

	public void close() throws Exception{
		serial.close();
	}
}
