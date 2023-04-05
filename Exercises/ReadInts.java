import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class ReadInts {
	public static void main (String[] args) throws IOException {
		DataInputStream in = new DataInputStream(System.in);
		int i;
		while (true) {
			try {
				i = in.readInt();
			}catch (EOFException e) {
				break;
			}
			System.out.println(i);
		}
	}
}
