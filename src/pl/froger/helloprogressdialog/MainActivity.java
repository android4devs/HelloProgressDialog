package pl.froger.helloprogressdialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {   
	private static final int PROGRESS_DIALOG = 1;
	
	private Button btnStart;
    private ProgressDialog progressDialog;
    private ProgressThread progressThread;
    final private Handler progressHandler = new Handler() {
    	public void handleMessage(Message msg) {
    		int progress = msg.arg1;
    		int state = msg.arg2;
    		progressDialog.setProgress(progress);
    		if(ProgressThread.STATE_COMPLETE == state) {
    			dismissDialog(PROGRESS_DIALOG);
    			removeDialog(PROGRESS_DIALOG);
    		}
    	};
    };
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initButton();
	}

	private void initButton() {
		btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startWorkerThread();
			}
		});
	}

	private void startWorkerThread() {
		showDialog(PROGRESS_DIALOG);
		initProgressThread();
		progressThread.start();
	}
	
	private void initProgressThread() {
		progressThread = new ProgressThread(progressHandler);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		if(PROGRESS_DIALOG == id)
			return createProgressDialog();
		return null;
	}

	private Dialog createProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setMessage("Loading...");
		progressDialog.setProgress(0);
		progressDialog.setCancelable(false);
		return progressDialog;
	}
}