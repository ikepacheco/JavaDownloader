package Back;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


public class Download implements Runnable{
	
	String link;
	File out;
	private JProgressBar progressBar;
	private JLabel lblLogDownload;
	
	
	public int fileSize;
	public double downloaded;
	public double percentDownloaded;
	public boolean _downloading;
	public JButton btnDownload;
	
	public Download(String _link, File _out, JProgressBar _progressBar, JLabel _lblLogDownload, boolean _downloading, JButton _btnDownload) {
		this.link = _link;
		this.out = _out; 
		this.progressBar = _progressBar;
		this.lblLogDownload = _lblLogDownload;
		this._downloading = _downloading;
		this.btnDownload = _btnDownload;
	}
	
	@Override
	public void run() {
		try {
			URL urlDownload = new URL(link);
			URLConnection http = urlDownload.openConnection();
			http.connect();
			fileSize = http.getContentLength();
			
			try (BufferedInputStream input = new BufferedInputStream(http.getInputStream());
			FileOutputStream fos = new FileOutputStream(this.out))
			{
				_downloading = true;
				btnDownload.setEnabled(false);
				byte[] buffer = new byte[1024];
				downloaded = 0;
				int read = 0;
				percentDownloaded = 0;
				progressBar.setValue(0);
				while((read = input.read(buffer, 0, 1024)) != -1) 
				{
					downloaded += read;
					percentDownloaded = (downloaded*100)/fileSize;
					String percent = String.format("%.1f", percentDownloaded);
					String percentMil = String.format("%.0f", percentDownloaded * 10);
					System.out.println("Downloaded " + percent + "% of " + (int)fileSize/(1024) + "KB : " + _downloading);
					lblLogDownload.setText("Downloaded " + percent + "% of " + (int)fileSize/(1024) + "KB");
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {
							try {
								progressBar.setValue(Integer.parseInt(percentMil));	
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null,
				                e.getMessage(),
				                e.getCause().toString(),
				                JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});	
					fos.write(buffer, 0, read);
				}
				_downloading = false;
				btnDownload.setEnabled(true);
				System.out.println("Download Complete.");
			}
		}catch(IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
            e.getMessage(),
            e.getCause().toString(),
            JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	
	public double getPercentDownloaded() {
		return this.percentDownloaded;
	}
	public double getFileSize() {
		return this.fileSize;
	}
	
	
	
	
}
