//Name: HaikuGUI.java
//Author: Malcolm Chisholm
//Java Refresher
//Date: 3/15/14
//This is the GUI portion of the Haiku Detector.


import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")//to suppress warning
public class HaikuGUI extends JFrame
{
	public JTextArea pad = new JTextArea(24, 58);
	public JFileChooser files = new JFileChooser(System.getProperty("user.dir"));

	public JScrollPane scroll = new JScrollPane (pad);


	public HaikuGUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenu file = new JMenu("File");

		JMenuBar bar = new JMenuBar();
		bar.add(file);

		//add choices for File
		OpenAction open = new OpenAction();
		file.add(open).setMnemonic('O');

		//set it up
		//////////////////////////////////////////////////////////
		
		String prompter = "Open a text file, and Haikus will display below.";
		
	    JPanel panel = new JPanel ();
	    panel.setBorder ( new TitledBorder ( new EtchedBorder (), prompter) );

	    // create the panel components

	    pad.setEditable ( false ); // set pad non-editable
	    JScrollPane scroll = new JScrollPane ( pad );
        scroll.setBounds(10, 11, 455, 249);   
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

	    //Add scroll in to panel
	    panel.add ( scroll );

	    // My code
	    JFrame frame = new JFrame ();
	    frame.add ( panel );
	    frame.pack ();
	    frame.setLocationRelativeTo ( null );
		frame.setSize(850, 500);

	    frame.setTitle("Haiku Detector");

		frame.setJMenuBar(bar);
		
	    frame.setVisible ( true );

		
		
	}

	

	class OpenAction extends AbstractAction
	{
		public OpenAction()
		{
			super("Open...");
		}
		public void actionPerformed(ActionEvent e)
		{
			pad.setText("Processing file...\n\n(Please be patient for large files)");

			///open
			if(files.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
			{


				wordList wL = new wordList(files.getSelectedFile().getAbsolutePath(), true);
				pad.setText(files.getSelectedFile().getAbsolutePath() + "\n\n" + wL.getHaikus());
			}
			else
			{
				pad.setText("");
			}
		}
	}





	public static void main(String[] args)
	{

		@SuppressWarnings("unused")//to suppress warning
		HaikuGUI wpad = new HaikuGUI();
	}

}




