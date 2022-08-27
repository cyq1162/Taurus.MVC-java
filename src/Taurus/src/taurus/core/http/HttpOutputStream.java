package taurus.core.http;

import java.io.IOException;

public class HttpOutputStream extends java.io.OutputStream {

	javax.servlet.ServletOutputStream stream;
	jakarta.servlet.ServletOutputStream jakartaStream;

	public HttpOutputStream(jakarta.servlet.ServletOutputStream stream) {
		this.jakartaStream = stream;
	}

	public HttpOutputStream(javax.servlet.ServletOutputStream stream) {
		this.stream = stream;
	}

	@Override
	public void write(int b) throws IOException {
		if (stream != null) {
			stream.write(b);
			return;
		}
		jakartaStream.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if (stream != null) {
			stream.write(b, off, len);
			return;
		}
		jakartaStream.write(b, off, len);
	}

	public boolean isReady() {
		if (jakartaStream != null) {
			return jakartaStream.isReady();
		}
		// TODO Auto-generated method stub
		return stream.isReady();
	}

	public void close() throws IOException {
		if (jakartaStream != null) {
			jakartaStream.close();
			return;
		}
		stream.close();
	}

	public void flush() throws IOException {
		if (jakartaStream != null) {
			jakartaStream.flush();
			return;
		}
		stream.flush();
	}
}
