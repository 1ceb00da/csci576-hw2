

public class Tester {

	public static void main(String a[]) {
		String img = "Image";
		String num[] = new String[]{"1.raw","2.raw","3.raw","4.raw"};
		int N = 50;
		String[] args = new String[2];
		for (int n = 2; n < N; n += 1 ) {
			args[0] = img + num[(int)(Math.random() * 4)];
			System.out.println(args[0]);
			args[1] = Integer.toString((n % 2 ) + 2 );
			MainHW2.main(args);
		}
	}
	
}
