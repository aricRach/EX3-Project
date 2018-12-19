package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import File_format.readCsv;

class fileTest {

	//private File path=new File("C:\\Users\\aric\\git\\Ex2\\csv files"); 
	String pathName="C:\\Users\\aric\\git\\EX3-Project\\testFile.csv";//the location of the file


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	//	@Test
	//	void test() {
	//		fail("Not yet implemented");
	//	}

	@Test
	public void readFromCsvTest() throws IOException {

		readCsv csv =new readCsv(pathName);
		ArrayList<String> csvLines=csv.readCsvGame();

		System.out.println(csvLines.get(0));
		int expecteds =15;
		int actuals=csvLines.size();
		Assert.assertEquals(expecteds, actuals);

		//check the content of the file 

		String s="";
		for(int i=0;i<csvLines.size();i++) {

			s=csvLines.get(i);
			if (i>=0 && i<=2 && s.charAt(0)!='P') { //the 3 first element should be packmans

				Assert.fail();
				
			}
			
			if (i>2 && s.charAt(0)!='F') { // all the rest should be fruits

				Assert.fail();

			}
		}

	}

}

