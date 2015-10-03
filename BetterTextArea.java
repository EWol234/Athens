//Import necessary classes
import javax.swing.*;
   import static javax.swing.JFrame.EXIT_ON_CLOSE;
   import java.awt.event.*;
   import java.io.*;
   import java.awt.List;
   import javax.swing.event.DocumentEvent;
   import javax.swing.event.DocumentListener;
   import javax.swing.text.Element;
   import java.awt.Color;

   public class BetterTextArea extends JScrollPane{ //Creates a subclass of JScrollPane
   
   	public BetterTextArea(){ //Use default constuctor of JScrollPane
			super();
		}
		
	//Method to add line numbers and a scroll function to any text area fed into it
		public void addNumbersAndScroll(final JTextArea coder){ 
		    //Creates the area that holds the numbers
		  	final JTextArea lines = new JTextArea("1");
			lines.setBackground(Color.LIGHT_GRAY);
			lines.setEditable(false);
			
		//Creates a document listener to read the number of lines in the text area
         coder.getDocument().addDocumentListener(new DocumentListener(){
         	public String getText(){
         	        //Loops through and creates the string of numbers needed
					int caretPosition = coder.getDocument().getLength();
					Element root = coder.getDocument().getDefaultRootElement();
					String text = "1" + System.getProperty("line.separator");
					for(int i = 2; i < root.getElementIndex(caretPosition)+2; i++){
						if(i ==(root.getElementIndex(caretPosition)+1))
							text += i;
						else
							text += i + System.getProperty("line.separator");
					}
					return text;
				}
				
				//Methods to change the line numbers when the text area is edited
				@Override
				public void changedUpdate(DocumentEvent de){
					lines.setText(getText());
				}
 
				@Override
				public void insertUpdate(DocumentEvent de){
					lines.setText(getText());
				}
 
				@Override
				public void removeUpdate(DocumentEvent de){
					lines.setText(getText());
				}
			   
         });
			
			//Adds the number text area into the JTextArea given
			this.getViewport().add(coder);
			this.setRowHeaderView(lines);
			this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
   
   }