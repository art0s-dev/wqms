package updater;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class UpdateWindow implements Runnable{
	
	private Display display;
	private Shell shell;
	private Label welcomingLabel;
	private ProgressBar progressBar;
	private Label progressLabel;
	
	public UpdateWindow() {
		this.display = new Display();
		this.shell = this.createShell();
		this.welcomingLabel = this.createWelcomingLabel();
		this.progressBar = this.createProgressBar();
		this.progressLabel = this.createProgressLabel();
	}
	
	public void run() {
		this.shell.open();
		while (!this.shell.isDisposed()) {
			if (!this.display.readAndDispatch()) {
				this.display.sleep();
			}
		}
		display.dispose();
	}
	
	private Label createWelcomingLabel() {
		Label welcomingLabel = new Label(this.shell, SWT.NONE);
		GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		welcomingLabel.setLayoutData(gridData);
		welcomingLabel.setText("PLEASE_WAIT_WHILE_UPDATE");
		return welcomingLabel;
	}
	
	private Shell createShell() {
		final Shell shell = new Shell(this.display);
		shell.setText("WQMS Update Service");
		shell.setLayout(new GridLayout(1, false));
		shell.setSize(new Point(1029, 366));
		return shell;
	}
	
	private ProgressBar createProgressBar() {
		ProgressBar progressBar = new ProgressBar(this.shell, SWT.NONE);
		GridData gd_progressBar = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_progressBar.heightHint = 56;
		gd_progressBar.widthHint = 996;
		progressBar.setLayoutData(gd_progressBar);
		return progressBar;
	}
	
	private Label createProgressLabel() {
		Label progressLabel = new Label(this.shell, SWT.NONE);
		progressLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		progressLabel.setText("PROGRESS_MESSAGE");
		return progressLabel;
	}
	
	
	
	

}
