package zqc.reading.refactor.ch04testingframework;

import java.io.*;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileReaderTester {

    private FileReader _input;
    private FileReader _empty;

    @Before
    public void setUp() throws Exception {

        try {
            _input = new FileReader("D:\\data.txt");
            _empty = newEmptyFile();

        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to open test file.");
        }
    }

    private FileReader newEmptyFile() throws IOException {

        File empty = new File("D:\\empty.txt");
        FileOutputStream out = new FileOutputStream(empty);
        out.close();
        return new FileReader(empty);
    }

    @After
    public void tearDown() throws Exception {

        try {
            _input.close();
        }
        catch (IOException e) {
            throw new RuntimeException("error on closing test file");
        }
    }

    @Test
    public void testRead() throws IOException {

        char ch = '&';
        for (int i = 0; i < 4; i++)
            ch = (char) _input.read();
        Assert.assertEquals('d', ch);
    }

    @Test
    public void testReadAtEnd() throws IOException {

        int ch = -1234;
        for (int i = 0; i < 141; i++)
            ch = _input.read();
        Assert.assertEquals("read at end", -1, _input.read());
    }

    @Test
    public void testReadBoundaries() throws IOException {

        Assert.assertEquals("read first char", 'B', _input.read());

        int ch;
        for (int i = 1; i < 140; i++) {
            ch = _input.read();
        }
        Assert.assertEquals("read last char", '6', _input.read());
        Assert.assertEquals("read at end", -1, _input.read());
    }

    @Test
    public void testEmptyRead() throws IOException {

        Assert.assertEquals(-1, _empty.read());
    }

    @Test
    public void readAfterClose() throws IOException {

        _input.close();
        try {
            _input.read();
            Assert.fail("no exception for read past end");
        }
        catch (IOException e) {
        }
    }

}
