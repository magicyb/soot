package ca.mcgill.sable.soot.launching;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import soot.*;
import ca.mcgill.sable.soot.util.*;

/**
 * @author jlhotak
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */
public class SootRunner implements IRunnableWithProgress {

	Display display;
	String [] cmd;
	
	/**
	 * Constructor for SootRunner.
	 */
	public SootRunner(Display display, String [] cmd) {
		setDisplay(display);
		setCmd(cmd);
	}

	/**
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor)
		throws InvocationTargetException, InterruptedException {
		//System.out.println("running SootRunner");
		try {
		
			final PipedInputStream pis = new PipedInputStream();
			//System.out.println("created inputstream");
			//StreamGobbler sootIn = new StreamGobbler(new PipedInputStream(),0);
      		final PipedOutputStream pos = new PipedOutputStream(pis);
      		//System.out.println("created outputstream");
      		final PrintStream sootOut = new PrintStream(pos);
      		//System.out.println("created printstream");
      		
      		//StreamGobbler out = new StreamGobbler(pis,0);
      		//out.run();
        	//(new Thread() {
            	//public void run() {
            final String [] cmdFinal = getCmd();
            //System.out.println("made cmd final");
            
            try {
        		(new Thread() {
            		public void run() {
            			Main.main(cmdFinal, sootOut);
            			//System.out.println("called Soot");
            		}
        		}).start();
            }
            catch(Exception e) {
            	System.out.println(e.getMessage());
            }
            //StreamGobbler out = new StreamGobbler(pis, StreamGobbler.OUTPUT_STREAM_TYPE);
        
        	StreamGobbler out = new StreamGobbler(pis, StreamGobbler.OUTPUT_STREAM_TYPE);
        	getDisplay().asyncExec(
        	out);
        	
        	
        	//out.run();	
        	//System.out.println("tried to run streamgobbler");
        	
            /*    	try {
                	BufferedReader br = new BufferedReader(new InputStreamReader(pis));
                	while (true) {
                	
                	String temp = (String)br.readLine();
                	if (temp == null) break;
                	System.out.println(temp);
                	}
                	}
                	catch(IOException e1) {
                		System.out.println(e1.getMessage());
                	}
        		}
            }).start();
            //StreamGobbler out = new StreamGobbler(pis,0);        	
              
      	*/
      	}
      	catch (Exception e) {
      		System.out.println(e.getStackTrace());
      	}
	}

	/**
	 * Returns the cmd.
	 * @return String[]
	 */
	public String[] getCmd() {
		return cmd;
	}

	/**
	 * Returns the display.
	 * @return Display
	 */
	public Display getDisplay() {
		return display;
	}

	/**
	 * Sets the cmd.
	 * @param cmd The cmd to set
	 */
	public void setCmd(String[] cmd) {
		this.cmd = cmd;
	}

	/**
	 * Sets the display.
	 * @param display The display to set
	 */
	public void setDisplay(Display display) {
		this.display = display;
	}

}
