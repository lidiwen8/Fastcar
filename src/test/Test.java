package test;
import java.io.*;

public class Test {
    /**
     * Write the bytes from input stream to output stream.
     * @param is
     * @param os
     * @throws IOException
     */
    public  boolean copy(InputStream is, OutputStream os) throws IOException {
        int count = 0;
        byte[] buffer = new byte[1024];
        while ((count = is.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        return true;
    }
    /**
     *
     * @param a
     * @param b
     * @param ending
     * @return copy the elements from a to b, and stop when meet element ending
     */
    public void copy(String[] a, String[] b, String ending)
    {
        int index;
        String temp = null;
        System.out.println(temp.length());
        int length=a.length;
        for(index=0; index < a.length; index++)
        {
            if(temp==ending)
            {
//               break;
            }
            b[index]=temp;
        }
    }

    /**
     *
     * @param file
     * @return file contents as string; null if file does not exist
     */
    public  void  readFile(File file) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            os = new ByteArrayOutputStream();
            copy(is,os);
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
        }
    }

}


