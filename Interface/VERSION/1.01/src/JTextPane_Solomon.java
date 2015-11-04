/**
 * @author Solomon Sonya
 */


import java.awt.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.*;

public class JTextPane_Solomon extends JPanel implements ActionListener
{
	public static final String myClassName = "JTextPane_Solomon";
	
	JScrollPane myScrollPane = null;
	
	JTextPane myTextPane = new JTextPane();
	StyledDocument myDoc = null;
	
	SimpleAttributeSet attrb = new SimpleAttributeSet();

	public JButton jbtnClear = new JButton("Clear Text"); 
	
	public volatile JCheckBox jcbAutoScroll = new JCheckBox("Auto Scroll", false);
	public volatile boolean autoscroll = false;
	
	JPanel jpnlsouth = new JPanel(new BorderLayout());
	
	public JTextPane_Solomon(boolean editable, boolean wrap_text)
	{
		try
		{
			
			myDoc = 	myTextPane.getStyledDocument();
			StyleConstants.setForeground(attrb, Color.black);
			StyleConstants.setBackground(attrb, Color.white);
			StyleConstants.setBold(attrb, false);
			myTextPane.setFont(new Font("Serif", Font.PLAIN, 20));
			
			
			this.myTextPane.setEditable(editable);
			
			this.setLayout(new BorderLayout());
			
			myScrollPane = new JScrollPane(myTextPane);
			
			this.add(BorderLayout.CENTER, myScrollPane);
			
			this.setPreferredSize(new Dimension(650,200));
			
			//this.add(BorderLayout.SOUTH, this.jbtnClear);
			this.add(BorderLayout.SOUTH, this.jpnlsouth);
			
			jpnlsouth.add(BorderLayout.CENTER, this.jbtnClear);
			jpnlsouth.add(BorderLayout.EAST, this.jcbAutoScroll);
			
			
			this.jbtnClear.addActionListener(this);
			jcbAutoScroll.addActionListener(this);
		}
		catch(Exception e)
		{
			eop("Constructor 1", myClassName);
		}
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource() == this.jbtnClear)
			{
				this.clear();
			}
			
			else if(ae.getSource() == this.jcbAutoScroll)
			{
				this.autoscroll = this.jcbAutoScroll.isSelected();
			}
			
			
		}
		catch(Exception e)
		{
			this.eop("ae", myClassName);
		}
	}
	
	public boolean insertText(String text)
	{
		try
		{
			this.myTextPane.setText("");
			
			if(text != null && text.toUpperCase().trim().startsWith("ERROR"))
			{
				StyleConstants.setForeground(this.attrb, Color.yellow);
				StyleConstants.setBackground(this.attrb, Color.red);
				
				this.myDoc.insertString(this.myDoc.getLength(),  text.substring(0,6),  this.attrb);
				
				StyleConstants.setForeground(this.attrb, Color.black);
				StyleConstants.setBackground(this.attrb, Color.white);
				this.myDoc.insertString(this.myDoc.getLength(),  text.substring(6) + "\n",  this.attrb);
			}
			
			else if(text != null && text.toUpperCase().trim().startsWith("Extracted Message from file".toUpperCase()))
			{
				StyleConstants.setForeground(this.attrb, Color.white);
				StyleConstants.setBackground(this.attrb, Color.green.darker().darker());
				
				this.myDoc.insertString(this.myDoc.getLength(),  text.substring(0,27),  this.attrb);
				
				StyleConstants.setForeground(this.attrb, Color.black);
				StyleConstants.setBackground(this.attrb, Color.white);
				this.myDoc.insertString(this.myDoc.getLength(),  text.substring(27) + "\n",  this.attrb);
			}
			
			else
			{
				this.myDoc.insertString(0,  text + "\n",  null);
			}
			
			if(this.autoscroll)//reset the scroll position to the bottom if this is selected
				this.myTextPane.setCaretPosition((myDoc.getLength()));
			
			this.validate();
			return true;
		}
		catch(Exception e)
		{
			this.eop("insertText", myClassName);
		}
		
		this.validate();
		return false;
	}
	
	public void clear() { try{ this.myTextPane.setText(""); } catch(Exception e){}}
	
	public boolean appendText(String text)
	{
		try
		{
			//this.myDoc.insertString(this.myDoc.getLength(),  text + "\n",  this.attrb);
			
			if(text != null && text.toUpperCase().trim().startsWith("ERROR"))
			{
				StyleConstants.setForeground(this.attrb, Color.yellow);
				StyleConstants.setBackground(this.attrb, Color.red);
				
				this.myDoc.insertString(this.myDoc.getLength(),  text.substring(0,6),  this.attrb);
				
				StyleConstants.setForeground(this.attrb, Color.black);
				StyleConstants.setBackground(this.attrb, Color.white);
				this.myDoc.insertString(this.myDoc.getLength(),  text.substring(6) + "\n",  this.attrb);
			}
			
			else if(text != null && text.toUpperCase().trim().startsWith("Extracted Message from file".toUpperCase()))
			{
				StyleConstants.setForeground(this.attrb, Color.white);
				StyleConstants.setBackground(this.attrb, Color.green.darker().darker());
				
				this.myDoc.insertString(this.myDoc.getLength(),  text.substring(0,27),  this.attrb);
				
				StyleConstants.setForeground(this.attrb, Color.black);
				StyleConstants.setBackground(this.attrb, Color.white);
				this.myDoc.insertString(this.myDoc.getLength(),  text.substring(27) + "\n",  this.attrb);
			}
			
			else
			{
				this.myDoc.insertString(this.myDoc.getLength(),  text + "\n",  this.attrb);
			}
			
			if(this.autoscroll)//reset the scroll position to the bottom if this is selected
				this.myTextPane.setCaretPosition((myDoc.getLength()));
			
			this.validate();
			
			System.gc();
			
			return true;
		}
		catch(Exception e)
		{
			this.eop("insertText", myClassName);
		}
		this.validate();
		return false;
	}
	
	public void sop(String out){System.out.println(out);}	
	public void eop(String mtdName, String myClassName) 	{ sop("Exception caught in " + mtdName + " in " + myClassName);}
}
