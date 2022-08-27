package taurus.core.http;
import java.io.IOException;

public class HttpInputStream extends java.io.InputStream {

	
	javax.servlet.ServletInputStream stream;
	jakarta.servlet.ServletInputStream jakartaStream;
	public	HttpInputStream(jakarta.servlet.ServletInputStream stream) {
		this.jakartaStream=stream;
	}
	public	HttpInputStream(javax.servlet.ServletInputStream stream) {
		this.stream=stream;
	}

	public boolean isFinished() {
		if(jakartaStream!=null)
		{
			return jakartaStream.isFinished();
		}
		return stream.isFinished();
	}

	public boolean isReady() {
		if(jakartaStream!=null)
		{
			return jakartaStream.isReady();
		}
		// TODO Auto-generated method stub
		return stream.isReady();
	}

	public int read() throws IOException {
		if(jakartaStream!=null)
		{
			return jakartaStream.read();
		}
		return stream.read();
	}

	public void close() throws IOException {
		if (jakartaStream != null) {
			jakartaStream.close();
			return;
		}
		stream.close();
	}
}
