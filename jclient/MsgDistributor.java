/*************************************************
#
# Purpose: "Message Distributor" aims to distribute the message
            received through the unique GUID
# Author.: Zihong Zheng
# Version: 0.1
# License: 
#
*************************************************/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
import java.util.Queue;
import java.util.HashMap; 
import edu.rutgers.winlab.jmfapi.*;

public class MsgDistributor {
	private
		boolean debug = true;
		int BUFFER_SIZE = 1024;
		int mfsockid;
		Lock sendLock;
		Lock recvLock;
		Lock idLock;
		Lock mapLock;
		Semaphore connectSem;
		Semaphore acceptSem;
		Queue<Integer> connectQueue;
		HashMap<Integer, Semaphore> semMap;
		HashMap<Integer, Queue<String>> queueMap;
		HashMap<Integer, Integer> statusMap;
		GUID srcGUID;
		GUID dstGUID;
		String scheme = "basic";
		JMFAPI handler;

	public MsgDistributor() {
		mfsockid = -1;
    }

    public int init(int srcGUID, int dstGUID, boolean debug) {
    	if (mfsockid != -1) {
    		System.out.println("ERROR: Dont reinit MsgDistributor!");
    		return -1;
    	}

    	mfsockid = 0;
    	this.debug = debug;

    	sendLock = new ReentrantLock();
    	recvLock = new ReentrantLock();
    	idLock = new ReentrantLock();
    	mapLock = new ReentrantLock();
    	connectSem = new Semaphore(0);
    	acceptSem = new Semaphore(0);

    	// init the mfapi
    	System.out.println("Start to initialize the mf.");
		this.srcGUID = new GUID(srcGUID);
		this.dstGUID = new GUID(dstGUID);
		handler = new JMFAPI();
		try {
			handler.jmfopen(scheme, this.srcGUID);		
		}
		catch (JMFException e) {
			System.out.println(e.toString());
		}
    	System.out.println("Finished.");

    	return 0;
    }

    public int listen() {
    	byte[] buf = new byte[BUFFER_SIZE];
		int ret;

		try {
			ret = handler.jmfrecv_blk(null, buf, BUFFER_SIZE);
			if(ret < 0)
		    {
		        System.out.printf("mfrec error\n"); 
		        return -1;
		    }
			String bufString = new String(buf);
			String delims = "[,]";
			String[] tokens = bufString.split(delims);
			System.out.println("receive new message, header: " + tokens[0]);
			if (tokens[0].equals("create")) {
		    	acceptSem.release();
			}
			else if (tokens[0].equals("accepted")) {
				int createdID = Integer.parseInt(tokens[1]);
				connectQueue.offer(createdID);
		    	connectSem.release();
			}
			else if (tokens[0].equals("sock")) {
				int sockID = Integer.parseInt(tokens[1]);
				int idLen = 1, divisor = 10;
				while (sockID / divisor != 0)
		        {
		            divisor *= 10;
		            ++idLen;
		        }

		        int contentLen = BUFFER_SIZE - idLen - 6;
			}
			else if (tokens[0].equals("close")) {
				int sockID = Integer.parseInt(tokens[1]);
				if (debug) System.out.println("peer closed the connection");
				this.close(sockID, 1);
			}
			else {

			}
		}
		catch (JMFException e) {
			System.out.println(e.toString());
		}
    	
    	return 0;
    }

    public int connect() {
    	return 0;
    }

    public int accept() {
    	try {
	    	acceptSem.acquire();
    	} catch (InterruptedException e) {
			e.printStackTrace();
        }

    	return 0;
    }

	public int close(int sockID, int passive) {
    	return 0;
    }
}
