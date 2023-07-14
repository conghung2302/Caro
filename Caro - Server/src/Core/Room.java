package Core;

import java.util.ArrayList;

public class Room {

    public String mMaPhong;
    public String mTenPhong;
    public String mChuPhong;
    public int mSoNguoi;
    int row;
    int column;

    public ArrayList<User> mListUser = new ArrayList<>();

    public void AddUser(User user) {
        mListUser.add(user);
    }

    public void RemoveUser(User user) {
        mListUser.remove(user);
    }

    public int CountUser() {
        return mListUser.size();
    }

    public void SendToAllUser(String sender, String content) {
        System.out.println("Send Mess");
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user.Send(ActionType.SEND_MESSAGE, ResultCode.OK, sender + ";" + content) == false) {

                NotifyJustLeaveRoom(user);
            }
        }
    }
    
    public void SendIcon(String sender, String content) {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user.Send(ActionType.SEND_ICON, ResultCode.OK, sender + ";" + content) == false) {

                NotifyJustLeaveRoom(user);
            }
        }
    }

    public void SendTickInRoom(String sender, String content) {
        System.out.println("Sent Tick");
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user.mNickName.equals(sender)) {
                if (user.Send(ActionType.SEND_TICK, ResultCode.OK, content + ";" + "NO") == false) {
                    NotifyJustLeaveRoom(user);
                }
            } else {
                if (user.Send(ActionType.SEND_TICK, ResultCode.OK, content + ";" + "YES") == false) {
                    NotifyJustLeaveRoom(user);
                }
            }

        }
    }
    
    public void NewGame(String sender) {
        for (User user : mListUser)
            if (!user.mNickName.equals(sender)) {
                user.Send(ActionType.NEW_GAME, ResultCode.OK, sender + ";" + "Muốn chơi lại");
            }
    }
    
    public void AccepNewGame(String sender, String action) {
        for (User user : mListUser)
            if (!user.mNickName.equals(sender)) {
                user.Send(action, ResultCode.OK, "");
            }
    }

    public void UpdateNumberUser() {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user.Send(ActionType.UPDATE_NUMBER_USER, ResultCode.OK, size + "") == false) {
                NotifyJustLeaveRoom(user);
            }
        }
    }

    public void NotifyJustJoinRoom(User userJoin) {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user != userJoin) {
                user.Send(ActionType.NOTIFY_JUST_JOIN_ROOM, ResultCode.OK, userJoin.mNickName);
            }
        }
    }

    public void NotifyJustLeaveRoom(User userLeave) {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user != userLeave) {
                user.Send(ActionType.NOTIFY_JUST_LEAVE_ROOM, ResultCode.OK, userLeave.mNickName);
            }
        }
    }
}
