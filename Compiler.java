//Imports necessary classes
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
	public class Compiler {
		//Takes file from the hard drive and places it into memory compilable by the computer 
		public static class InMemoryJavaFileObject extends SimpleJavaFileObject{
			private String contents;

			public InMemoryJavaFileObject(String className, String contents) throws Exception
			{
				super(URI.create("string:///" + className
						+ Kind.SOURCE.extension), Kind.SOURCE);
				this.contents = contents;
			}
			public CharSequence getCharContent(boolean ignoreEncodingErrors)
					throws IOException
					{
				return contents;
					}
		}
		
		//
		public static class MyDiagnosticListener<T> implements DiagnosticListener<JavaFileObject>
		{
			public void report(Diagnostic<? extends JavaFileObject> diagnostic)
			{
				System.out.println("Line Number: "+diagnostic.getLineNumber());
				System.out.println("code: "+diagnostic.getCode());
				System.out.println("Message: "+diagnostic.getMessage(Locale.ENGLISH));
				System.out.println("Source: "+diagnostic.getSource());
				System.out.println(" ");
			}
		}
		
		//Finds the file that is to be compiled
		private static FileObject getFileObject(File java, String name){
			StringBuilder content = new StringBuilder("");
			Scanner input = null;
			try {
				input=new Scanner(java);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				System.out.println("File not Found");
			}
			while(input.hasNext()){
				content.append(input.nextLine());
			}
			JavaFileObject ret=null;
			try {
				ret = new InMemoryJavaFileObject(name,content.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} 
			input.close();
			return ret;
		}
		
		//Finds the class name of the file from its file path
		public static String findClassName(String s){
			return s.substring(s.lastIndexOf("/")+1,s.indexOf("."));
		}
		
		//Compiles the file using the systems built-in Java compiler
		public static void compile(String file) throws IOException{
			String fileName =findClassName(file);
			System.setProperty("java.home", "D:/Java/jdk1.8.0_25");
			System.setProperty("javac", "D:/Java/jdk1.8.0_25");
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			MyDiagnosticListener<JavaFileObject> diagnostics = new MyDiagnosticListener<JavaFileObject>();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
			ArrayList<String> optionList = new ArrayList<String>();
			optionList.addAll(Arrays.asList("-classpath","D:/Athens"));
			Iterable<? extends JavaFileObject> compilationUnits = (Iterable<? extends JavaFileObject>) 
					Arrays.asList(getFileObject(new File(file),fileName));
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, optionList,
					null, compilationUnits);
			task.call();
			fileManager.close();
			
		}
		
		//Method that will run an already compiled file
		public static void run(String s){
			try
			{
				URL classFolder=new File("D:/Athens")
				.toURI().toURL();
				URL[] urls = new URL[] {classFolder};
				Class[] params= {String[].class};
				ClassLoader loader = new URLClassLoader(urls);
				Class thisClass = loader.loadClass(findClassName(s));
				Object instance = null;
				try {
					instance = thisClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				Method thisMethod = null;
				try {
					thisMethod = thisClass.getDeclaredMethod("main", params);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				try {
					thisMethod.invoke(instance, new Object[]{null});
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			catch(MalformedURLException e){
				;
			}
			catch (ClassNotFoundException e){
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//Method that will compile and run the file immediately
		public static void make(File n) throws Exception{
			compile(n.toString().replace("\\", "/"));	
			run(n.toString().replace("\\", "/"));
		}
	}