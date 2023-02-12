import java.io.*;
import java.nio.charset.*;

public class Scanner {
    private final BufferedReader reader;
    private final char[] buffer;
    private int readSize;
    private int index;
    public static final int BUFFER_SIZE = 1024;

    public Scanner(InputStream in) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(
                in,
                StandardCharsets.UTF_8
        ));
        this.buffer = new char[BUFFER_SIZE];
        this.readSize = 0;
        this.index = 0;
    }

    public Scanner(String s) throws IOException {
        this.reader = new BufferedReader(new StringReader(s));
        this.buffer = new char[BUFFER_SIZE];
        this.readSize = 0;
        this.index = 0;
    }

    public void close() throws IOException {
        reader.close();
    }

    public boolean read() throws IOException {
        if ((this.readSize - this.index) > 0) {
            return true;
        }
        this.readSize = this.reader.read(this.buffer);
        this.index = 0;
        return this.readSize >= 0;
    }

    public boolean hasNext() throws IOException {
        while (this.readSize >= 0) {
            while (this.index < this.readSize) {
                if (!Character.isWhitespace(this.buffer[this.index])) {
                    return true;
                } else {
                    this.index++;
                }
            }
            this.read();
        }
        return false;
    }

    public String next() throws IOException {
        hasNext();
        StringBuilder result = new StringBuilder();
        while (this.readSize >= 0) {
            while (this.index < this.readSize) {
                if (Character.isWhitespace(this.buffer[this.index])) {
                    return new String(result);
                }
                result.append(this.buffer[this.index]);
                this.index++;
            }
            this.read();
        }
        return new String(result);
    }

    public String nextString() throws IOException {
        return this.next();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(this.next());
    }

    public String nextLine() throws IOException {
        StringBuilder result = new StringBuilder();
        boolean wasR = false;
        while (this.readSize >= 0) {
            while (this.index < this.readSize) {
                if (wasR) {
                    if (this.buffer[this.index] == '\n') { // in this case: \r\n, else: \r
                        this.index++;
                    }
                    return new String(result);
                } else {
                    if (this.buffer[this.index] == '\r') {
                        wasR = true;
                    } else if (this.buffer[this.index] == '\n') { // in this case: \n
                        this.index++;
                        return new String(result);
                    }
                }
                if (!wasR)
                    result.append(this.buffer[this.index]);
                this.index++;
            }
            this.read();
        }
        return new String(result);
    }

    public boolean hasNextLine() throws IOException {
        while ((this.readSize - this.index) == 0) {
            this.read();
        }
        return (this.readSize - this.index) > 0;
    }

    public boolean hasNextString() throws IOException {
        return hasNext();
    }
}