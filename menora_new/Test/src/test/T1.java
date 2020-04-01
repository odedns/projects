package test;

public class T1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 100;
		
		System.out.println(formatSize(n));
		n = 100220;
		System.out.println(formatSize(n));
		
		n = 10020020;
		System.out.println(formatSize(n));
		
		
		
	}

	
	
	private static String formatSize(int size)
	{
		
		String s = null;
		if(size < 1024) {
			s = new Integer(size).toString();
		} else {
			if(size > 1024 && size < 1024*1024) {
				s = new Integer(size/1024).toString() + "K";
			} else {
				s = new Integer(size/(1024*1024)).toString() + "M";
			}
			
		}
		return(s);
	}
}
