import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial{
	private CommPortIdentifier portId;
	private SerialPort port;
	private InputStream in;
	private OutputStream out;
	Serial(String p) throws Exception{
		System.out.println(p);
		portId = CommPortIdentifier.getPortIdentifier(p);
		port = (SerialPort)portId.open("hoge", 5000);
		port.setSerialPortParams( 9600,SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE );
		port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		port.setDTR(true);
        port.setRTS(false);
		in = port.getInputStream();
		out = port.getOutputStream();
	}
	public void write(String data) throws Exception{
		out.write(data.getBytes());
	}
	public void write(byte data) throws Exception{
		out.write(data);
	}
	public void write(byte[] data) throws Exception{
		out.write(data);
	}
	public String read() throws Exception{
		StringBuilder buffer = new StringBuilder();
		while(true){
			int buf=in.read();
			if((char)buf=='@'){
				break;
			}
			if(buf!=-1&&buf!=0x00){
				buffer.append((char)buf);
			}
		}
		return buffer.toString();
	}
	public void close() throws Exception{
		in.close();
		out.close();
		port.close();
	}
}
