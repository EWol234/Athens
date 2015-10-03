//Importing necessary classes
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.List;


public class AthensGUI {
	final static JTextArea out = new JTextArea(); //Instantiating output
	File currentFile = null; //Instantiating the java file being written
	public AthensGUI() {
		JFrame frame = new JFrame(); //Creating the window
		JOptionPane option = new JOptionPane(); //Creating pop-up message
		option.showMessageDialog( //Writing and displaying the pop-up
				frame,
				"Welcome to Athens! The world's most friendly IDE. Here you can learn the basics behind writing java code and start to build some really cool programs. If you need to learn code, click the \"Learn Java\" Button.");
		
		//Choosing frame settings
		frame.setTitle("Athens");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(700, 400);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//Creating the contents pane and choosing the layout manager
		JPanel panel = new JPanel();
		GroupLayout glay = new GroupLayout(panel);
		glay.setAutoCreateGaps(true);
		glay.setAutoCreateContainerGaps(true);
		panel.setLayout(glay);
		
		//Creating the toolbar and its buttons
		JToolBar bar = new JToolBar();
		JButton saveAs = new JButton("Save as");
		JButton compile = new JButton("Run");
		JButton save = new JButton("Save");
		JButton open = new JButton("Open");
		JButton help = new JButton("Help");
		JButton learn = new JButton("Learn java!");
		
		//Adding buttons to toolbar in chosen order
		bar.add(save);
		bar.add(saveAs);
		bar.add(compile);
		bar.add(open);
		bar.add(help);
		bar.add(learn);
		
		JButton clear2 = new JButton("Clear Output"); //Instantiating a button to clear output
		
		//Instantiating the coding area
		final JTextArea coder = new JTextArea();
		BetterTextArea code = new BetterTextArea();
		code.addNumbersAndScroll(coder);
		
		//Creating the output window
		JScrollPane out1 = new JScrollPane(out);
		//Setting relative size of the output
		out1.setMaximumSize(new Dimension(frame.getWidth(),
				frame.getHeight() / 6));
				
	    //Adding function to the save button
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				BufferedWriter outfile = null; //Creates a file writer
				if (currentFile != null) { //If the file doesn't already exist: run the following code
					try {
						outfile = new BufferedWriter(
								new FileWriter(currentFile)); //Instantiating the file writer to the current file
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace(); //Catching errors
					}
					try {
						coder.write(outfile); //Saving the file
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else //If the file already exists: overwrite that
					currentFile = saveAs(coder);
			}

		});

		JLabel spacer = new JLabel("\n"); //Adds more space for aesthetics 
		out.setEditable(false); //Prevents typing into the output
		JLabel output = new JLabel("Output");//Creates label for output
		
		//Adding fuction to "Clear Output"
		clear2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				out.setText(""); //Sets outputs text to none
			}
		});
		
		//Adds function to Save As button
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				saveAs(coder); //Calls on saveAs() from below
			}
		});

        //Adds function to compile button
		compile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
				    //Checks to see if file is saved; saves it if it isn't
					if (currentFile != null)
						PrintInBox.print(currentFile);
					else
						PrintInBox.print(currentFile = saveAs(coder));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); //Catch any exceptions
				}
				
				// Add console to JTextArea
				FileReader reader = null; //Creates a file reader
				try {
					reader = new FileReader(
							"C:/Users/Joel/Dropbox/TSAIDE/console.txt"); //Sets file reader to text file of output
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace(); //Catch any exceptions
				}

				try {
					out.read(reader, "out"); //Display program output in output box
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace(); //Catch any exceptions
				}
				// Text Successfully added

			}

		});
		
		//Adds function to open button
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				final JFileChooser fc = new JFileChooser(); //Creates the file manager window
				FileReader fr = null; //Creates a file reader
				String textLine; //Creates an empty string
				fc.showOpenDialog(null);
				if (fc.getSelectedFile() == null) //If there is no selected file, returnt to earlier and keep waiting
					return;
				currentFile = fc.getSelectedFile(); //Sets open file to the selected file
				try {
					fr = new FileReader(currentFile); //Sets file reader to read selected file
				} catch (FileNotFoundException e) {
					return; //If there is no file; go back and try again
				}
				BufferedReader reader = new BufferedReader(fr); //Feeds the file reader into a buffered reader to convert bytes to text
				try {
					while ((textLine = reader.readLine()) != null) { //Loop to add the lines of code from the file to the coding area
						coder.append(textLine);
						coder.append("\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); //Catch any exceptions
				}
			}
		});
		
		//Adds function to the help button
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					java.awt.Desktop
							.getDesktop()
							.browse(java.net.URI
									.create("https://delicious.com/athensdevs")); //Opens pre-made webpage with links to helpful websites
				} catch (java.io.IOException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		
		//Adds fucntion to "Learn Java!"
		learn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane ask = new JOptionPane(); //Opens a pop-up window
				String num = ask
						.showInputDialog("Enter Your Last Lesson Number Completed, 0 if you are new"); //Asks your current level; saves response
				goThroughLessons(num, ask); //Goes through the lessons starting at your level
			}
		});
		
		//Adds the objects to the display pane in proper horizontal order
		glay.setHorizontalGroup(glay
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(bar)
				.addComponent(code)
				.addComponent(spacer)
				.addComponent(output)
				.addGroup(
						glay.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(out1).addComponent(clear2)));
        
        //Adds the objects to the display pane in proper vertical order
		glay.setVerticalGroup(glay.createSequentialGroup().addComponent(bar)
				.addComponent(code).addComponent(spacer).addComponent(output)
				.addComponent(out1).addComponent(clear2));
        
        //Inserts the display pane into the window
		frame.setContentPane(panel);
	}

	public static File saveAs(JTextArea coder) { //Method to overwrite files
		File f; //Creates file
		final JFileChooser fc = new JFileChooser(); //Opens file manager window
		fc.setApproveButtonText("Save as"); //Adds a "Save As" buttont to the file manager
		fc.showOpenDialog(null);
		f = new File(fc.getSelectedFile() + ".java"); //Chooses the file you selected to be overwritten
		try {
			f.createNewFile(); //Opens the file
			
			//Writes the file contents to the coding box
			BufferedWriter outfile = new BufferedWriter(new FileWriter(f));
			coder.write(outfile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return f; //Returns the file to be saved
	}

	public static void goThroughLessons(final String num, final JOptionPane ask) { //Method to open lessons
		File currentLes = new File("D:/Lessons/"+"lesson"+ num+".docx"); //Setting file to lesson document
		final String[] awnser = new String[4]; //Create a string to hold the correct answers
		awnser[0] = "Hello World"; //First answer will always be "Hello World"
		if (!currentLes.exists()) { //If no lessons are open: you have finished all of the lessons. Exits the method.
			ask.showMessageDialog(null,
					"Congrats! You Have Completed all the lessons!");
			return;
		}
		try {
			java.awt.Desktop.getDesktop().edit(currentLes); //Try to open the lesson document; catch any errors.
		} catch (IOException e) {
		}
		Thread awnserchecker = new Thread() { //Create a thread to repeatedly check the answer
			public void run() { //Method that will be called when the thread is started
				while (true) {
					if (out.getText().trim()
							.equals(awnser[new Integer(num).intValue()])) { //If answer is equal to the one in the earlier string; end thread
						break;
					}
					try {
						Thread.sleep(1000); //Pause for 1 second; catch any errors
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null,
								"Your Awnser is Correct! You Rule!"); //Tells you if you are correct
								
						//Starts the next lesson
						String nextles = "" + (new Integer(num).intValue() + 1);
						goThroughLessons(nextles, ask);
					}

				});
			}
		};
		awnserchecker.start(); //Starts the answer checker created above.
	}

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AthensGUI gui = new AthensGUI(); //Start the program
			}
		});
	}
}