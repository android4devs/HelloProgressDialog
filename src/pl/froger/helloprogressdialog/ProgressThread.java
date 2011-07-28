package pl.froger.helloprogressdialog;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ProgressThread extends Thread{
	public static final int STATE_BEGIN = 0;
	public static final int STATE_MIDDLE = 1;
	public static final int STATE_END = 2;
	public static final int STATE_COMPLETE = 3;
	
	private Handler handler;
	private int state;
	int progress;
	
	public ProgressThread(Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void run() {
		state = STATE_BEGIN;
		progress = 0;
		while (state != STATE_COMPLETE) {
			pause();
			increaseProgress();
			sendProgressToHandler();
		}
	}
	
	private void pause() {
		try {
			Thread.sleep(50);
		} catch (Exception e) {
			Log.e("HelloProgress", e.getLocalizedMessage());
		}
	}
	
	private void increaseProgress() {
		switch (++progress) {
		case 30:
			state = STATE_MIDDLE;
			break;
		case 60:
			state = STATE_END;
			break;
		case 100:
			state = STATE_COMPLETE;
			break;
		default:
			break;
		}
	}

	private void sendProgressToHandler() {
		Message msg = handler.obtainMessage();
		msg.arg1 = progress;
		msg.arg2 = state;
		handler.sendMessage(msg);
	}
}