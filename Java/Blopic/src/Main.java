public class Main{
	public static Editer editer;

	public static void main(String[] args) {
		try {
			if(args.length!=1)throw new Exception();
			editer=new Editer(args[0]);
			editer.run();
			editer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
