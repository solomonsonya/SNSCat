/**
 * @author Solomon Sonya
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.border.BevelBorder;


public class GUI extends JFrame implements ActionListener
{
	public static final String myClassName = "GUI";
	public volatile java.net.URL url_Img = null;
	public volatile File fleCarrier = null;
	
	public static final String VERSION = "1.01";
	
	JPanel jpnlNorth = null;
		JPanel jpnlNorth_Alignment = null;
		JPanel jpnlButtons_jpnlNorth = null;
			JButton jbtnForward = null;
			JButton jbtnBack = null;
			
			JPanel jpnlExtract = null;
				JButton jbtnSpecifyImage = null;
				JPanel jpnlTF = null;
			JTextField jtfSpecifyImage = null;
			JButton jbtnExtract = null;
			
	JSplitPane jsplitpane = null;
			
	JPanel jpnlImage = null;
	JLabel jlblImage = null;
	JScrollPane jscrlpne_Image = null;
	
	JPanel jpnlMessage = null;
	JTabbedPane tabbedpane = null;
	
	JTextPane_Solomon jtxtpne_ExtractedMessage = null;
	JTextPane_Solomon jtxtpne_CompleteMessage = null;
	JTextPane_Solomon jtxtpne_Console = null;
	
	public static final String[] image_file_extensions = new String[] {"png", "bitmap", "bmp", "jpg", "jpeg", "gif", "tiff", "tif", "img", "raw"};
	public static volatile ArrayList<File> list_file = new ArrayList<File>();
	public static volatile int index_list_file = 0;
	
	
	
	JPanel_Heap_And_Time_Solomon jpnlHeap = new JPanel_Heap_And_Time_Solomon();
	
	File fleSNSCat = null;
	File fleSuccessfulSNSCat = null;
	
	String extracted_contents_path = null;
	
	public static String currentWorkingDirectory = ".";
	
	public volatile String extraction_command = "";
	
	public GUI() 
	{		
		try
		{
			setTitle("SNSCat Extractor Interface version " + VERSION);			
			try {    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");	}	catch (Exception e)	{ }
			this.setBounds(60, 100, 779, 741);
			getContentPane().setLayout(new BorderLayout(0, 0));
			
			(new Thread(jpnlHeap = new JPanel_Heap_And_Time_Solomon())).start();
			jpnlHeap.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			
			
			currentWorkingDirectory = System.getProperty("user.dir");
			
			if(currentWorkingDirectory == null || currentWorkingDirectory.trim().equals(""))
				currentWorkingDirectory = ".";
			
			currentWorkingDirectory = currentWorkingDirectory.trim();
			
			if(!currentWorkingDirectory.endsWith(File.separator))
				currentWorkingDirectory = currentWorkingDirectory + File.separator;
			
			
				
			
			jpnlNorth = new JPanel(new BorderLayout());
			//jpnlExtract = new JPanel();
			jbtnSpecifyImage = new JButton("Specify Image");
			
			jtfSpecifyImage = new JTextField(65);
			jtfSpecifyImage.setBackground(Color.WHITE);
			jtfSpecifyImage.setEditable(false);
			//jpnlTF.add(jtfSpecifyImage);
			getContentPane().add(jpnlNorth, BorderLayout.NORTH);
			jpnlNorth.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Select Image", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 51, 255)));
			
			jbtnExtract = new JButton("Extract");
						
			jpnlButtons_jpnlNorth = new JPanel(new BorderLayout());
				jbtnForward = new JButton("Forward");
				jbtnBack = new JButton("    Back  ");
				jpnlButtons_jpnlNorth.add(BorderLayout.WEST, jbtnBack);
				jpnlButtons_jpnlNorth.add(BorderLayout.EAST, jbtnForward );
			
			
			jpnlNorth_Alignment = new JPanel(new BorderLayout());
			jpnlNorth_Alignment.add(BorderLayout.WEST, jbtnSpecifyImage);
			jpnlNorth_Alignment.add(BorderLayout.CENTER, jtfSpecifyImage);
			jpnlNorth_Alignment.add(BorderLayout.EAST, jpnlButtons_jpnlNorth);
			
			
			jpnlNorth.add(BorderLayout.CENTER, jpnlNorth_Alignment);
			
			
			//
			//Image
			//
			jpnlImage = new JPanel(new BorderLayout());
			jlblImage = new JLabel();			
			jscrlpne_Image = new JScrollPane(jlblImage);
			jpnlImage.add(BorderLayout.CENTER, jscrlpne_Image);
			
			jpnlImage.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Image", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 51, 255)));

			//
			//Message
			//
			jpnlMessage = new JPanel(new BorderLayout());
			jpnlMessage.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Extracted Message (if applicable)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 51, 255)));

			tabbedpane = new JTabbedPane(JTabbedPane.TOP);
			jpnlMessage.add(BorderLayout.CENTER, tabbedpane);
			
			jtxtpne_ExtractedMessage = new JTextPane_Solomon(false, false);
			jtxtpne_CompleteMessage = new JTextPane_Solomon(false, false);
			jtxtpne_Console = new JTextPane_Solomon(false, false);
			
			tabbedpane.addTab("Extracted Message", jtxtpne_ExtractedMessage);
			tabbedpane.addTab("Complete Message", jtxtpne_CompleteMessage);
			tabbedpane.addTab("Console", jtxtpne_Console);
			
			this.jsplitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jpnlImage, jpnlMessage);
			jsplitpane.setOneTouchExpandable(true);
			jsplitpane.setDividerLocation(420);
			getContentPane().add(jsplitpane, BorderLayout.CENTER);
			
			try
			{
				getContentPane().add(jpnlHeap, BorderLayout.SOUTH);
			}
			catch(Exception e)
			{
				sop("unable to instantiate worker labels");
			}
			
			
			//
			//Configure
			//
			sop("Setting current working directory to-->" + currentWorkingDirectory);
			
			//
			//register widgets
			//
			jbtnSpecifyImage.addActionListener(this);
			this.jbtnExtract.addActionListener(this);
			jbtnForward.addActionListener(this);
			this.jbtnBack.addActionListener(this);
			
		}
		
		catch(Exception e)
		{
			eop("Constructor - 1", myClassName);
		}
	}

	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource() == this.jbtnSpecifyImage)
			{
				specify_image(null);
			}
			
			else if(ae.getSource() == this.jbtnExtract)
			{
				extractMessage(this.fleCarrier);
			}
			
			else if(ae.getSource() == this.jbtnForward)
			{
				if(list_file.size() < 1)
				{
					jop("No file has been loaded yet...", "No files to List!");
				}
				else
				{
					index_list_file = (++index_list_file) % this.list_file.size();
					specify_image(list_file.get(index_list_file));
				}
				
				
				
			}
			
			else if(ae.getSource() == this.jbtnBack)
			{
				if(list_file.size() < 1)
				{
					jop("No file has been loaded yet...", "No files to List!");
				}
				else
				{
					index_list_file = (--index_list_file) % this.list_file.size();
					
					if(index_list_file < 0)
						index_list_file = this.list_file.size()-1;
					
					specify_image(list_file.get(index_list_file));
				}
				
				
				
			}
			
			this.validate();
		}
		catch(Exception e)
		{
			this.eop("ae", myClassName);
		}
		
		this.validate();
	}
	
	public void jop(String msg, String title)
	{
		JOptionPane.showMessageDialog(null,  msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public boolean specify_image(File fle)
	{
		try
		{
			if(fle == null)
				fle = querySelectFile(true, "Please Specify Carrier File to Extract Data From...", JFileChooser.FILES_ONLY, false, false);
			
			if(fle != null && fle.exists() && fle.isFile())
			{
				fleCarrier = fle;
				
				this.jtfSpecifyImage.setText(fleCarrier.getCanonicalPath());
				
				jlblImage.setIcon(new ImageIcon(fleCarrier.getCanonicalPath()));	
				
				//call extract message
				extractMessage(fle);
				
				//populate images for the next and back button
				populate_images_within_directory(fle, this.image_file_extensions);
				
				this.jtfSpecifyImage.setText(fle.getCanonicalPath());
				this.jtfSpecifyImage.setToolTipText(fle.getCanonicalPath());
				
				System.gc();
			}
			
			return true;
		}
		catch(Exception e)
		{
			this.eop("specify_image", myClassName);
		}
		
		return false;
	}
	
	public boolean populate_images_within_directory(File fle, String[] arrExtensionFilter)
	{
		try
		{
			if(fle == null || !fle.exists())
				return false;
			
			File parent_directory = null;
			
			if(fle.isDirectory())
				parent_directory = fle;
			else
				parent_directory = fle.getParentFile();
			
			//list files within current directory
			File[] list = parent_directory.listFiles();
			
			if(list == null || list.length < 1)
				return false;
			
			File curr = null;
			
			this.list_file.clear();
			
			for(int i = 0; i < list.length; i++)
			{
				curr = list[i];
				
				if(!curr.exists())
					continue;
				
				//determine to keep file
				if(curr.isDirectory())
				{
					//do s/t here like recursive call...
				}
				else if(curr.isFile())
				{
					//determine if we're using a filter
					if(arrExtensionFilter != null && arrExtensionFilter.length > 0)
					{
						for(int j = 0; j < arrExtensionFilter.length; j++)
						{
							if(curr.getCanonicalPath().toLowerCase().trim().endsWith(arrExtensionFilter[j].toLowerCase().trim()))
							{
								list_file.add(curr);
							}
						}
					}
					else
					{
						//no filter, add the file
						list_file.add(curr);
					}
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			this.eop("populate_images_within_directory", myClassName);
		}
		
		return false;
	}
	
	public boolean extractMessage(File fle)
	{
		try
		{
			extracted_contents_path = null;
			
			//
			//Acquire File to analyze
			//
			
			
			
			if(fle == null || !fle.exists() || !fle.isFile())
			{
				fle = querySelectFile(true, "Please Specify Carrier File to Extract Data From...", JFileChooser.FILES_ONLY, false, false);
				
				if(fle == null || !fle.exists() || !fle.isFile())
				{
					//user still failed to select a file to analyze
					JOptionPane.showMessageDialog(null,  "PUNT!!!\nNo valid file found to analyze.  Quitting now!", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				//good file, configure appropriately
				this.jtfSpecifyImage.setText(fle.getCanonicalPath());				
				jlblImage.setIcon(new ImageIcon(fle.getCanonicalPath()));
				
				this.fleCarrier = fle;
			}
			
			//
			//Acquire SNSCat
			//					
			fleSNSCat = new File(this.currentWorkingDirectory + "SNSCat.jar");		
			
			//check if we have a good snscat
			if(fleSuccessfulSNSCat != null && fleSuccessfulSNSCat.exists())
			{
				fleSNSCat = fleSuccessfulSNSCat;
			}
			
			sop("\n*************************************\n**************************\n***************\nSearching for SNSCat.jar. I am hoping to find at " + fleSNSCat);			
			
			if(fleSNSCat == null || !fleSNSCat.exists() || !fleSNSCat.isFile())
			{
				try
				{
					//attempt to acquire from within own package:
					java.net.URL url_Img = GUI.class.getResource("/SNSCat/SNSCat.jar");//Use this line to search for a specified file within the jar file
					fleSNSCat = new File(url_Img.getPath());
					
					this.sop("\n\nOUT-->" + fleSNSCat + "\n\n");
				}
				catch(Exception e)
				{
					this.jtxtpne_Console.appendText("\n\n* * * * Could not find SNSCat.jar withon own archive!!!\n\n");
				}
			}
			
			if(fleSNSCat == null || !fleSNSCat.exists() || !fleSNSCat.isFile())
			{								
				sop("NOPE! I was unable to find SNSCat at the current working directory. Querying user for path to SNSCat.jar...");
				JOptionPane.showMessageDialog(null,  "ERROR!!!\nI was unable to find SNSCat.jar at the current working directory!\n\nPlease specify the location to SNSCat.jar", "Specify Location to SNSCat", JOptionPane.ERROR_MESSAGE);
				
				fleSNSCat = querySelectFile(true, "Please Specify SNSCat.jar", JFileChooser.FILES_ONLY, false, false); 				
			}			
			
			if(fleSNSCat == null || !fleSNSCat.exists() || !fleSNSCat.isFile())
			{
				JOptionPane.showMessageDialog(null,  "PUNT!!!\nI could not find a valid location to SNSCat.jar.  Quitting now!", "You must specify location to SNSCat.jar!!!", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			//otw, we know we must have SNSCat.jar by now. 
			//notify
			sop("Excellent! SNSCat.jar found to be at " + fleSNSCat + " I am configuring to interact with this module now...");
			
			//
			//Clear previous extracted message
			//
			this.jtxtpne_CompleteMessage.clear();
			this.jtxtpne_ExtractedMessage.clear();
			
			//
			//set extraction command
			//
			extraction_command = "java -jar " +  "\"" + fleSNSCat.getCanonicalPath().trim() + "\"" + " -x " + "\"" + fle.getCanonicalPath().trim() + "\"" + " ." + File.separator;
			sop("Extraction command-->" + extraction_command);
			
			//
			//execute command
			//
			Process process = Runtime.getRuntime().exec(extraction_command, null, new File("."));
			
			//
			//exhaust standard out
			//
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			
			while((line = br.readLine()) != null)
			{
				try
				{
				
					//append all text
					this.jtxtpne_Console.appendText(line.trim());
					
					//only append text for this output file
					jtxtpne_CompleteMessage.appendText(line.trim());
					
					//
					//analyze line for errors
					//
					if(line.trim().startsWith("Exception caught in saveExtractedData"))
					{
						//assume this can only come from SNSCat. Tehrefore store it to the permanent pointer
						fleSuccessfulSNSCat = this.fleSNSCat;
						
						//notify
						jtxtpne_ExtractedMessage.insertText("ERROR! NO DATA FOUND IN THIS FILE: " + fle + "\n\nIt is likely this is NOT a carrier file with embedded stego content.");												
						try	{	process.destroyForcibly();	} catch(Exception e){}
						this.jtxtpne_Console.appendText("\n\nERROR! NO DATA FOUND IN THIS FILE: " + fle + "\n\nIt is likely this is NOT a carrier file with embedded stego content.");
						break;
					}
					
					//
					//Analyze for Extracted Message
					//
					if(line.trim().startsWith("Extracted Message: "))
					{
						//assume this can only come from SNSCat. Tehrefore store it to the permanent pointer
						fleSuccessfulSNSCat = this.fleSNSCat;
						
						//notify
						String outline = "";
								
						try
						{
							outline = line.replaceFirst("Extracted Message: ", "").trim();
						}
						catch(Exception e)
						{
							outline = line.trim();
						}
								
						jtxtpne_ExtractedMessage.insertText("Extracted Message from file \"" + fle + "\" reads as follows: \n");
						jtxtpne_ExtractedMessage.appendText(outline);
					}
					
					//
					//Analyze for the saved output file
					//
					if(line.trim().startsWith("# # # Extracted Message Successfully written at:"))
					{
						extracted_contents_path = line.replaceFirst("# # # Extracted Message Successfully written at:", "").trim();
					}
				}
				
				catch(Exception e)
				{
					sop("Solo, check 1 in " + myClassName);
					continue;
				}
				
			}
			
			//kill lingering processes
			try	{	process.destroy();	} catch(Exception e){}	
			
			//
			//close readers
			//
			br.close();
			
			//
			//cleanup and remove saved file
			//
			if(extracted_contents_path != null && !extracted_contents_path.trim().equals(""))
			{
				File fleToDel = new File(extracted_contents_path.trim());
				this.jtxtpne_Console.appendText("\n\n* * *Complete. Looks like contents were saved to " + fleToDel + ". I am attempting to delete now...");
				fleToDel.delete();
				this.jtxtpne_Console.appendText("It appears file deletion was successful. No further actions neccessary. All tasks complete.");
			}
			
			System.gc();
			
			return true;
		}
		catch(Exception e)
		{
			this.eop("extractMessage", myClassName);
		}
		
		return false;
	}
	
	public void sop(String out)
	{
		System.out.println(out);
		
		if(this.jtxtpne_Console != null)
			jtxtpne_Console.appendText(out);
		
	}
	
	public void eop(String mtdName, String myClassName) 	{ sop("Exception caught in " + mtdName + " in " + myClassName);}
	
	/**
	 * This method queries the user via JChooser to select a file
	 */
	public File querySelectFile(boolean openDialog, String dialogueTitle, int fileChooserSelectionMode, boolean thisLoadsCSV, boolean useFileFilter)
	{				
		
		try
		{
			JFileChooser jfc = new JFileChooser(new File("."));
			jfc.setFileSelectionMode(fileChooserSelectionMode);
			jfc.setDialogTitle(dialogueTitle);
			//jfc.setMultiSelectionEnabled(enableMultipleFileSelection);
			
			if(thisLoadsCSV)
			{
				jfc.setFileFilter(new javax.swing.filechooser.FileFilter() 
				{
		            public boolean accept(File fle) 
		            {
		                //accept directories
		            	if(fle.isDirectory())
		                	return true;
		            	
		            	String strFleName = fle.getName().toLowerCase();
		                 
		                return strFleName.endsWith(".csv");
		              }
		   
		              public String getDescription() 
		              {
		                return "Comma Separated Values";
		              }
		              
		         });
				
			}
			
			/***************************************
			 * Filter for only Specified Formats
			 ***************************************/
			else if(useFileFilter)
			{
				jfc.setFileFilter(new javax.swing.filechooser.FileFilter() 
				{
		            public boolean accept(File fle) 
		            {
		            	String extension = "";
		            	
		                //accept directories
		            	if(fle.isDirectory())
		                	return true;
		            	
		            	if(fle == null)
		            		return false;
		            	
		            	if(fle != null && fle.exists() && getFileExtension(fle, false)!= null)
		            		extension = (getFileExtension(fle, false)).replace(".", "");//remove the "." if present
		            	
		            	/*if(Driver.lstAcceptableFileExtensionsForStego.contains(extension.toLowerCase()))
		            		return true;*/
		            	
		            	//else 
		            		return false;
		              }
		   
		              public String getDescription() 
		              {
		                return "Specific Formats";
		              }
		              
		         });
			}
			
			
			try
			{
				jfc.setCurrentDirectory(new File(".\\"));
			}catch(Exception e){}
			
			int selection = 0;
			
			if(openDialog)					
			{
				selection = jfc.showOpenDialog(null);
			}
			
			else
			{
				//selection = jfc.showDialog(null, "Save Now!"); <-- this code works too
				selection = jfc.showSaveDialog(null);
			}
					
			if(selection == JFileChooser.APPROVE_OPTION)//selected yes!
			{
				if(openDialog || (!openDialog && !thisLoadsCSV))
					return jfc.getSelectedFile();
				
				else
					return new File(jfc.getSelectedFile().getAbsolutePath() + ".csv");
			}
			
			//else fall through and return null;
		}
		
		catch(Exception e)
		{
			eop("querySelectFile", myClassName);
		}
		
		return null;
	}
	
	public String getFileExtension(File fle, boolean removeDot_Preceeding_Extension)
	{
		try
		{
			if(fle != null)
			{
				if(removeDot_Preceeding_Extension)
					return (fle.toString().substring(fle.toString().lastIndexOf(".") + 1));
					
				//some files do not have extensions, in such cases, SNSCat may seem to be crashing. therefore check if the file contains a "." at the end, if not, return what we have
				if(!fle.toString().contains(".") || fle.toString().lastIndexOf(".") < 0 )
				{
					try
					{
						return (fle.toString().substring(fle.toString().lastIndexOf(System.getProperty("file.separator"))));
					}
					catch(Exception e)
					{
						return " ";
					}
				}
				
				return (fle.toString().substring(fle.toString().lastIndexOf(".")));
			}
			
		}
		catch(NullPointerException npe)
		{
			sop("NullPointerException caught in getFileExtension_ByteArray mtd in " + myClassName + ".  This seems to be a sporadic error, called when user first attempts to view the files in a directory. This does not affect funtionality of program.  Dismissing error...");
		}
		catch(Exception e)
		{
			eop("getFileExtension", myClassName);
		}
		
		return null;
	}
}
