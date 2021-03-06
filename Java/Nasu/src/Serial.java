import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial {
	private CommPortIdentifier portId;
	private SerialPort port;
	private InputStream in;
	private OutputStream out;
	Serial(String p) {
		try {
			portId = CommPortIdentifier.getPortIdentifier(p);
			port = (SerialPort)portId.open("hoge", 5000);
			port.setSerialPortParams( 9600,SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE );
			port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			port.setDTR(true);
	        port.setRTS(false);
			in = port.getInputStream();
			out = port.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void write(String data){
		try {
			out.write(data.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void write(byte data){
		try {
			out.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void write(byte[] data){
		try {
			out.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String read() {

		try {
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
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void close(){
		try {
			in.close();
			out.close();
			port.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
