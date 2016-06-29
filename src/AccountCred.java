import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AccountCred {

	public String getUserName() {
		InputStream inputStream = ParameterizedAcupunctureAcupointsTest
				.class.getClassLoader()
				.getResourceAsStream("username.csv");
		 BufferedReader br = null;
		 String line = "";
		 br = new BufferedReader(new InputStreamReader(inputStream ));
		 try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return line;
	}
	
	public String getPassword() {
		InputStream inputStream = ParameterizedAcupunctureAcupointsTest
				.class.getClassLoader()
				.getResourceAsStream("password.csv");
		 BufferedReader br = null;
		 String line = "";
		 br = new BufferedReader(new InputStreamReader(inputStream ));
		 try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return line;
	}

	public String getVerificiationCode() {
		InputStream inputStream = ParameterizedAcupunctureAcupointsTest
				.class.getClassLoader()
				.getResourceAsStream("verification.csv");
		 BufferedReader br = null;
		 String line = "";
		 br = new BufferedReader(new InputStreamReader(inputStream ));
		 try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return line;
	}
}
