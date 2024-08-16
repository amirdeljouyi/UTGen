package com.lts.application.checkpoint;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.application.swing.SimpleApplicationAction;
import com.lts.io.ImprovedFile;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleListModel;
import com.lts.swing.thread.BlockingThread;

/**
 * A window to display and edit checkpoints.
 * 
 * <P>
 * This window display a list of checkpoints, their modification times, and several
 * buttons to manipulate them.  The buttons include:
 * </P>
 * 
 * <UL>
 * <LI></LI>
 * <LI>Restore the selected checkpoint via {@link #recoverCheckpoint()}</LI>
 * <LI>Start the application with the selected checkpoint via
 * {@link #startWithSelected()} 
 * </LI>
 * <LI>Delete the selected checkpoint(s) via {@link #deleteCheckpoint()}</LI>
 * <LI></LI>
 * <LI></LI>
 * <LI></LI>
 * </UL>
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class CheckPointManager extends ApplicationContentPanel
{
	static public class FooFrame extends JFrame
	{
		@Override
		public void setSize(Dimension d)
		{
			// TODO Auto-generated method stub
			super.setSize(d);
		}

		@Override
		public void setSize(int width, int height)
		{
			// TODO Auto-generated method stub
			super.setSize(width, height);
		}
		
		
	}
	public CheckPointManager(JFrame frame) throws LTSException
	{
		super(frame);
	}
	
	
	private File myCheckPointDirectory;
	
	public File getCheckPointDirectory()
	{
		return myCheckPointDirectory;
	}
	
	public void setCheckPointDirectory(File file)
	{
		myCheckPointDirectory = file;
	}
	
	
	private boolean myQuitApplication;
	
	public boolean quitApplication()
	{
		return myQuitApplication;
	}
	
	public void displayAndWait()
	{
		BlockingThread.staticDisplayAndWait(getWindow());
	}
	
	
	private ApplicationRepository myRepository;
	
	public ApplicationRepository getRepository()
	{
		return myRepository;
	}
	
	
	@Override
	protected String[][][] get3DMenuSpec()
	{
		return null;
	}

	@Override
	protected String getViewName()
	{
		return "Timelord Check Points";
	}


	static private CheckPointManager ourInstance;
	
	public static CheckPointManager getInstance()
	{
		if (null == ourInstance)
			createOurInstance();
		
		return ourInstance;
	}


	synchronized static private void createOurInstance()
	{
		if (null == ourInstance)
		{
			try
			{
				// JFrame frame = new JFrame();
				FooFrame frame = new FooFrame();
				ourInstance = new CheckPointManager(frame);
			}
			catch (LTSException e)
			{
				String msg = "Error starting up check point manager.";
				throw new RuntimeException(msg, e);
			}
		}
	}
	
	
	
	private SimpleListModel myListModel;
	private JList myList;
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addFill(createListPanel());
		
		return panel;
	}
	
	
	private JPanel createListPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		myListModel = new SimpleListModel();
		myList = new JList(myListModel);
		JScrollPane jsp = new JScrollPane(myList);
		panel.addFill(jsp);
		
		panel.nextRow();
		panel.addCenteredLabel(createButtonPanel());
		
		return panel;
	}
	
	
	private JPanel createButtonPanel()
	{
		LTSPanel panel = new LTSPanel();
		ActionListener listener;
		JButton button;
		
		//
		// start using last project
		//
		listener = new SimpleApplicationAction() {
			@Override
			public void action() throws Exception {
				startAndIgnore();
			}
		};
		button = new JButton("Start Application");
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		//
		// start with selected
		//
		listener = new SimpleApplicationAction() {
			@Override
			public void action() throws Exception {
				startWithSelected();
			}
		};
		button = new JButton("Start with Selected");
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		//
		// restore
		//
		listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				recoverCheckpoint();
			}
		};
		button = new JButton("Restore");
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		//
		// delete
		//
		listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				deleteCheckpoint();
			}
		};
		button = new JButton("Delete");
		button.addActionListener(listener);
		panel.addButton(button,5);
				
		//
		// quit
		//
		listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				quit();
			}
		};
		button = new JButton("Quit Application");
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		return panel;
	}

	/**
	 * Start the application and ignore any remaining checkpoints.
	 *
	 */
	protected void startAndIgnore()
	{
		myRepository = null;
		super.okButtonPressed();
	}

	/**
	 * Start the application with the one selected checkpoint.
	 */
	private void startWithSelected()
	{
		CheckPoint[] cpoints = getSelectedCheckPoints();
		if (null == cpoints)
			return;
		
		if (cpoints.length > 1)
		{
			String message = "Please select a single checkpoint to use.";
			JOptionPane.showMessageDialog(this, message);
			return;
		}
		else
		{
			try
			{
				myRepository = 
					Application.getInstance().openRepository(cpoints[0].getFile());
				super.okButtonPressed();
			}
			catch (ApplicationException e)
			{
				String message = "Error trying to recover checkpoint";
				Application.showException(message, e);
			}
		}
	}

	private void deleteCheckpoint()
	{
		try
		{
			CheckPoint[] sel = getSelectedCheckPoints();
			if (null == sel)
				return;
			String message = "Delete checkpoint(s)?";
			int result = JOptionPane.showConfirmDialog(this, message);
			if (JOptionPane.OK_OPTION != result)
				return;
			for (int i = 0; i < sel.length; i++)
			{
				if (!sel[i].getFile().delete())
				{
					String msg = "Could not delete " + sel[i].getFile();
					JOptionPane.showMessageDialog(this, msg);
					return;
				}
			}
		}
		finally
		{
			reloadData();
		}		
	}

	private void recoverCheckpoint()
	{
		try
		{
			CheckPoint[] sel = getSelectedCheckPoints();
			if (null == sel)
				return;
			for (int i = 0; i < sel.length; i++)
			{
				File file = Application.getInstance().browseSaveFile();
				if (null == file)
					return;

				if (!sel[i].getFile().renameTo(file))
				{
					String message = "Count not rename " + sel[i].getFile() + " to "
							+ file;
					JOptionPane.showMessageDialog(this, message);
					return;
				}
			}
		}
		finally
		{
			reloadData();
		}		
	}
	
	
	private CheckPoint[] getSelectedCheckPoints()
	{
		Object[] selections = myList.getSelectedValues();
		if (null == selections || selections.length < 1)
			return null;
		
		
		CheckPoint[] vals = new CheckPoint[selections.length];
		for (int i = 0; i < selections.length; i++)
		{
			vals[i] = (CheckPoint) selections[i];
		}
		
		return vals;
	}

	public void reloadData()
	{
		myListModel.clear();
		File[] files = myCheckPointDirectory.listFiles();
		if (null == files || files.length < 1)
			return;
		
		CheckPoint[] checkPoints = new CheckPoint[files.length];
		for (int i = 0; i < checkPoints.length; i++)
		{
			checkPoints[i] = new CheckPoint(files[i]);
		}
		
		myListModel.addAll(checkPoints);
	}
	
	
	public void cleanupCheckPointDirectory()
	{
		File[] files = myCheckPointDirectory.listFiles();
		if (null == files || files.length < 1)
			return;
		
		String regex = ".*checkpoint[^/\\\\]*zip";
		
		for (int i = 0; i < files.length; i++)
		{
			String s = files[i].toString();
			if (s.matches(regex))
				continue;
			
			if (files[i].isFile())
				removeFile(files[i]);
			else
				removeDirectory(files[i]);
		}
	}
	
	
	private void removeFile (File file)
	{
		if (!file.exists())
			return;
		
		if (!file.isFile())
		{
			String message = "The argument, " + file + ", is not a file!";
			throw new IllegalArgumentException(message);
		}
		if (!file.delete())
		{
			String message = "Could not remove file, " + file;
			JOptionPane.showMessageDialog(this, message);
		}
	}
	
	
	private void removeDirectory (File dir)
	{
		if (!dir.exists())
			return;
		
		if (!dir.isDirectory())
		{
			String message = "The argument, " + dir + ", is not a directory";
			throw new IllegalArgumentException(message);
		}
		
		ImprovedFile ifile = new ImprovedFile(dir);
		try
		{
			ifile.deleteDirectory(true);
		}
		catch (IOException e)
		{
			String message = "Could not remove directory, " + dir;
			JOptionPane.showMessageDialog(this, message);
		}
	}
	
	public Dimension getWindowSize()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();

		int width = dimension.width / 2;
		int height = dimension.height / 2;

		return new Dimension(width, height);
	}

}
