package Core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {

    Socket mSocket;
    BufferedReader mBufferReader;
    DataOutputStream mDataOutputStream;
    public String mNickName;
    public Room mRoom;
    String turn;
    public Date mTimeConnect;
    public boolean mLogined = false;

    public User(Socket socket) throws IOException {
        mSocket = socket;
        mBufferReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "UTF8"));
        mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());
    }

    public String Read() throws IOException {
        if (mBufferReader.ready()) {
            return mBufferReader.readLine();
        }
        return null;
    }

    public boolean Ready() throws IOException {
        return mBufferReader.ready();
    }

    public Boolean Send(String actionType, String resultCode, String content) {
        try {
            mDataOutputStream.writeUTF(actionType + ";" + resultCode + ";" + content);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Boolean IsOnline() {
        return Send(ActionType.CHECK_ONLINE, ResultCode.OK, "");
    }
}
