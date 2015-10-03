//Import  necessart classes
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

//Class to print into a JTextArea
public class PrintInBox {
	public static void print(File f) throws Exception{
		File console = new File("D:/console.txt"); //Sets file to the output of the run program
		PrintWriter pr = null; //Creates a writer
		
		//Instantiates two printstreams to get output of System.out.println
		PrintStream out=System.out; 
		PrintStream org=System.out;
		try {
			pr = new PrintWriter(console); //Sets the print writer to write to the console
		} catch (FileNotFoundException e2) {
			try {
				console.createNewFile(); //If console file doesn't exist: make a new file
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Clear contents of the console before running
		pr.close();
		try {
			out = new PrintStream(console); //Sets the output stream of the program to the console
		} catch (FileNotFoundException e) {
			try {
				console.createNewFile(); //If console file doesn't exist: make a new file
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		System.setOut(out); //Set output of system to the console text file
		Compiler.make(f); //Compiles to file
		System.setOut(org); //Now outputs to random stream to stop output to the text area
	}
}