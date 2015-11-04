/**
 * 
 * @author Solomon Sonya
 *
 */

import javax.swing.*;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JPanel_Heap_And_Time_Solomon extends JPanel implements ActionListener, Runnable
{
	public static final String myClassName = "JPanel_Heap_And_Time_Solo";
	
	JLabel jlblCurrentHeap_Text = new JLabel("  Current Heap:");
	JLabel jlblAvailableHeap_Text = new JLabel("  Available Heap:");
	JLabel jlblMaxHeap_Text = new JLabel("  Max Heap:");
	JLabel jlblConsumedHeap_Text = new JLabel("Consumed Heap:");
	
	JLabel jlblTime_Text = new JLabel("  Time:");
	
	JLabel jlblCurrentHeap = new JLabel("");
	JLabel jlblAvailableHeap = new JLabel("");
	JLabel jlblMaxHeap = new JLabel("");
	JLabel jlblConsumedHeap = new JLabel("");
	JLabel jlblTime = new JLabel("");
	
	DecimalFormat formatter = new DecimalFormat("###.00");
	
	public static Date dateTime = new Date();    // = new Date();//get the current date, time, timezone  //  @jve:decl-index=0:
	
	Timer tmrUpdate = null;
	
	//Date dateTime;// = new Date();//get the current date, time, timezone  //  @jve:decl-index=0:
	SimpleDateFormat dateFormat = new SimpleDateFormat("EE - dd MMM yyyy - HH:mm \"ss - zzzz");
	
		String timeToSplit = "";
		String dateTime_To_Display = "";  //  @jve:decl-index=0:
		String [] arrSplitTime;
		String [] arrSplitTimeZone;
		String [] arrSplitHour;
		String strHourMin = "";
		String strAvailTimeZones = "";
		String strTimeZone = "";
	
	public JPanel_Heap_And_Time_Solomon()
	{
		try
		{
			this.add(jlblTime_Text);
			this.add(jlblTime);
			this.add(jlblConsumedHeap_Text);
			this.add(jlblConsumedHeap);
			this.add(jlblCurrentHeap_Text);
			this.add(jlblCurrentHeap);
			this.add(jlblAvailableHeap_Text);
			this.add(jlblAvailableHeap);
			this.add(jlblMaxHeap_Text);
			this.add(jlblMaxHeap);
			
			
			
			tmrUpdate = new Timer(1000, this);
			tmrUpdate.start();
			
		}
		catch(Exception e)
		{
			eop("Constructor - 1", myClassName);
		}
	}

	public void run()
	{
		try
		{
			
			
		}
		catch(Exception e)
		{
			eop("run", myClassName);
		}
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource() == tmrUpdate)
			{
				updateLabels();
			}
			
			this.validate();
			
		}
		catch(Exception e)
		{
			eop("ae", myClassName);
		}
		
		this.validate();
	}
	
	public boolean updateLabels()
	{
		try
		{
			jlblCurrentHeap.setText("" + this.getFormattedFileSize_String(Runtime.getRuntime().totalMemory()));
			jlblAvailableHeap.setText("" + this.getFormattedFileSize_String(Runtime.getRuntime().freeMemory()));
			jlblMaxHeap.setText("" + this.getFormattedFileSize_String(Runtime.getRuntime().maxMemory()));
			
			
			try
			{
				this.jlblConsumedHeap.setText("" + this.getFormattedFileSize_String(Math.abs(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) ));
			}
			catch(Exception ee)
			{
				this.jlblConsumedHeap.setText(" - - -");
			}
			
			jlblTime.setText("" + getTimeStamp_Updated());
			
			return true;
		}
		catch(Exception e)
		{
			this.eop("updateLabels", myClassName);
		}
		
		return false;
	}
	
	public String getFormattedFileSize_String(long fileSize_bytes)    
    {
        try
        {
        	double fileSize = fileSize_bytes; 
        	
        	//DecimalFormat formatter = new DecimalFormat("###.##");
        	

             if (fileSize_bytes < 1000)
             {
                  return  formatter.format(fileSize) + " B";
             }

             else if (fileSize_bytes > 1 && fileSize_bytes < 99e3)//kb
             {
            	 return  formatter.format(fileSize_bytes / 1e3) + " KB";
             }

             else if (fileSize_bytes > 1e3 && fileSize_bytes < 99e6)//mb
             {
            	 return  formatter.format(fileSize_bytes / 1e6) + " MB";
             }

             else if (fileSize_bytes > 1e6 && fileSize_bytes < 99e9)//gb
             {
            	 return  formatter.format(fileSize_bytes / 1e9) + " GB";
             }

             else if (fileSize_bytes > 1e9 && fileSize_bytes < 99e12)//tb
             {
            	 return  formatter.format(fileSize_bytes / 1e12) + " TB";
             }
             else if (fileSize_bytes > 1e12 && fileSize_bytes < 99e15)//pb
             {
            	 return  formatter.format(fileSize_bytes / 1e15) + " PB";
             }
             else//default
             {
            	 return  formatter.format(fileSize_bytes) + " B";
             }                  
            
        }
        catch (Exception e)
        {
        	eop("getFormattedFileSize_String", myClassName);        	
        }

        sop("Unable to determine File Size");
        
        return ""+fileSize_bytes;
    }
	
	public static String getTimeStamp_Without_Date()
	{
		

		String timeToSplit = "";
		String dateTime_To_Display = "";  //  @jve:decl-index=0:
		String [] arrSplitTime;
		String [] arrSplitHour;
		String strHourMin = "";
		
			
		
		try
		{	
			timeToSplit = dateTime.toString();
						
			arrSplitTime = timeToSplit.split(" ");//return an array with Day_Name Mon Day_Num HH:MM:SS LocalTime YYYY
						
			if(arrSplitTime.length != 6)//ensure array was split properly; if it's length is not 6, then an error occurred, so just show the simple time by throwing the exception
				throw new Exception();
			
			arrSplitHour = (arrSplitTime[3]).split(":");
			
			if(arrSplitHour.length != 3)//again, ensure we split the time from 18:57:48 bcs we only want 18:57
				throw new Exception();
			
			return strHourMin = arrSplitHour[0] + ":" + arrSplitHour[1] + "\"" + arrSplitHour[2];  
			
			
		}//end try
		catch(Exception e)//incase an error is generated from the above parse of the time and date, simply display the generic date for the client
		{
			dateTime_To_Display = "Time: " + dateTime.toString();
		}
		
		return dateTime_To_Display;		
	}//end mtd getTimeStamp
	
	public String getTimeStamp_Updated()
	{
		try
		{	
			//Get the Date
			dateTime = new Date();//must always re-init in order to get the curr date and time
			
			//Get the formatted string from the date: in form of "Wed - 01 Oct 08 - 00:36 "28 - Central Daylight Time"
			timeToSplit = dateFormat.format(dateTime);
			
			arrSplitTime = timeToSplit.split("-");//return an array delimeted with Day of the Week, Date, Time, Time Zone
			
			//Note: we don't to display the entire time zone text, i.e. if time zone is Central Daylight Time, we only want Central to show, therefore split the last token in arrSplitTime and return only the first word
			arrSplitTimeZone = (arrSplitTime[3]).split(" ");

			//		 DAY OF WEEK		-	   DAY MON YEAR			 -		TIME		   -		TIME ZONE		
			return (arrSplitTime[0] + " - " + arrSplitTime[1] + " - " + arrSplitTime[2] + "- " + arrSplitTimeZone[1]);
			
			
		}//end try
		catch(Exception e)//incase an error is generated from the above parse of the time and date, simply display the generic date for the client
		{
			//go to the old way for calling the Rime:
			try
			{
				return getTimeStamp_Without_Date();
			}
			catch(Exception ee)
			{
				//all else failed, just return the time
				return dateTime_To_Display = "Time: " + dateTime.toString();
			}
			
		}
		
		//return dateTime_To_Display;		
	}
	
	public void sop(String out){System.out.println(out);}	
	public void eop(String mtdName, String myClassName) 	{ sop("Exception caught in " + mtdName + " in " + myClassName);}
	
}
