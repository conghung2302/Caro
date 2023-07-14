package Core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Observer;
import java.util.Random;

public class ServerManager extends Observable {

    int mPort = 1106;
    ServerSocket mServerSocket;
    Thread mThreadAccept, mThreadProcess;
    ArrayList<User> mListUser = new ArrayList<>();
    ArrayList<Room> mListRoom = new ArrayList<>();
    ArrayList<User> mListUserWaitLogout = new ArrayList<>();

    public ServerManager(Observer obs) //hàm khởi tạo khi chưa có socket
    {
        this.addObserver(obs);
    }

    public ServerManager(ServerSocket serverSocket, Observer obs) //hàm khởi tạo khi đã có socket
    {
        this.addObserver(obs);
        mServerSocket = serverSocket;
    }

    public void Dispose() throws IOException {
        if (mThreadAccept != null) {
            mThreadAccept.stop();
            mThreadProcess.stop();
            mServerSocket.close();
        }
    }

    public boolean StartServer() //khởi động server
    {
        try {
            mServerSocket = new ServerSocket(mPort);
            StartThreadAccept();
            StartThreadProcess();
            notifyObservers("Khởi động server thành công");
            return true;
        } catch (IOException ex) {
            notifyObservers("Không thể khởi động server");
            return false;
        }
    }

    void StartThreadAccept() //bắt đầu luồng chấp nhận các socket kết nối đến
    {
        mThreadAccept = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Socket socket = mServerSocket.accept();
                        User newUser = new User(socket);
                        newUser.mTimeConnect = new Date();
                        mListUser.add(newUser);
                    }
                } catch (IOException ex) {
                    notifyObservers("Lỗi kết nối");
                }
            }
        });
        mThreadAccept.start();
    }

    void StartThreadProcess() //bắt đầu luồng chính xử lý các request, kiểm soát user
    {
        mThreadProcess = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        CheckRequest();
                        CheckTimeConnect();

                        if (mListUserWaitLogout.size() > 0) {
                            RemoveUserLoggedOut();
                        }

                        Thread.sleep(0);
                    }

                } catch (IOException ex) {
                    notifyObservers("Lỗi kết nối");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mThreadProcess.start();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    void CheckRequest() throws IOException {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            //server cần nhận thông tin liên tục của nhiều người, nên ko thể sử dụng DataInputStream để đợi nhận yêu cầu của một người
            //mà cần chạy liên tục để nhận yêu cầu của bất cứ ai ngay lập tức
            String request = user.Read();
            if (request != null) {
                ProcessRequest(user, request);
            }
        }
    }

    void CheckTimeConnect() {
        Date now = new Date();
        int size = mListUser.size();
        long second = 0;
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user.mLogined == false) {
                second = (now.getTime() - user.mTimeConnect.getTime()) / 1000;
                if (second > 10) //thường thì kết nối đến tương ứng vs đăng nhập, nhưng nếu ko đăng nhập sau 10s thì xóa
                {
                    mListUserWaitLogout.add(user);
                }
            }
        }
    }

    void RemoveUserLoggedOut() {
        int size = mListUserWaitLogout.size();
        for (int i = 0; i < size; i++) {
            User user = mListUserWaitLogout.get(i);
            mListUser.remove(user);
        }
        mListUserWaitLogout.clear();
    }

    void ProcessRequest(User user, String request) {
        String[] lines = request.split(";");
        String actionType = lines[0];
        switch (actionType) {
            case ActionType.LOGIN: {
                String nickName = lines[1];  //query có dạng actionType;nickname
                if (CheckNickName(nickName)) {
                    user.mNickName = nickName;
                    user.mLogined = true;
                    user.Send(actionType, ResultCode.OK, "OK");
                    notifyObservers(user.mNickName + " vừa đăng nhập thành công");
                } else {
                    user.Send(actionType, ResultCode.ERROR, "Nickname đã được người khác sử dụng");
                }
                break;
            }
            case ActionType.CREATE_ROOM: {
                String roomName = lines[1];  //query có dạng actionType;roomName
                String[] matrix = lines[3].split("x");
                int row = Integer.parseInt(matrix[0]);
                int col = Integer.parseInt(matrix[1]);
                Room room = GeneralRoom(roomName,lines[2], row, col);
                mListRoom.add(room);
                user.mRoom = room;
                if (user.Send(actionType, ResultCode.OK, room.mMaPhong)) {
                    room.AddUser(user);
                }
                notifyObservers(user.mNickName + " vừa tạo phòng " + roomName);
                resetRoom();
                break;
            }
            case ActionType.GET_LIST_ROOM: {
                int size = mListRoom.size();//query có dạng actionType;
                int rowsPerBlock = 500;    //số phòng gửi về mỗi block
                if (size > 0) {
                    /*Nếu có quá nhiều phòng(2000 chẳng hạn) thì 1 chuỗi string(1 lần gửi) sẽ không
                    thể chứa hết được thông tin của tất cả phòng
                    nên ta chia ra từng block gửi nhiều lần.
                    Trong thực tế thì có thể sử dụng chức năng phân trang, nhưng trong project này
                    không phân trang nên lựa chọn cách thức này
                     */
                    String listRoom = "";
                    int start = 0;
                    int end = 0;
                    int numberBlock = (int) Math.floor(size / (double) rowsPerBlock);
                    for (int i = 0; i < numberBlock; i++) {
                        start = i * rowsPerBlock;
                        end = start + rowsPerBlock;
                        listRoom = "";
                        for (int j = start; j < end; j++) {
                            Room room = mListRoom.get(j);
                            listRoom += room.mMaPhong + "<col>" + room.mTenPhong + "<col>" + room.mChuPhong + "<col>" + room.CountUser() 
                                    + room.row + "<col>" + room.column + "<col>" + "<row>";
                        }
                        System.out.print("Gửi lần thứ: " + i);
                        user.Send(actionType, ResultCode.OK, listRoom);
                    }

                    listRoom = "";
                    for (int i = end; i < size; i++) //gửi nốt những phòng còn lại
                    {
                        Room room = mListRoom.get(i);
                        listRoom += room.mMaPhong + "<col>" + room.mTenPhong + "<col>" + room.mChuPhong + "<col>" + room.CountUser()
                                + "<col>" + room.row + "<col>" + room.column + "<col>" + "<row>";
                    }
                    user.Send(actionType, ResultCode.OK, listRoom);
                } else {
                    user.Send(actionType, ResultCode.OK, "");
                }
                notifyObservers(user.mNickName + " vừa lấy danh sách phòng");
                break;
            }
            case ActionType.JOIN_ROOM: {
                String maPhong = lines[1];   //query có dạng actionType;maPhong
                int size = mListRoom.size();
                int success = 0;
                for (int i = 0; i < size; i++) {
                    Room room = mListRoom.get(i);
                    if (room.mMaPhong.equals(maPhong)) {

                        if (room.mSoNguoi == 2) {
                            success = 2;
                        } else {
                            room.AddUser(user);
                            user.mRoom = room;
                            user.Send(actionType, ResultCode.OK, maPhong);
                            notifyObservers(user.mNickName + " vừa tham gia phòng " + room.mMaPhong);
                            user.mRoom.UpdateNumberUser();
                            user.mRoom.NotifyJustJoinRoom(user);
                            success = 1;
                        }
                        sendNameOpp(room.mListUser.get(0).mNickName, user);
                        getTurn(room);
                    }

                }
                if (success == 0) {
                    user.Send(actionType, ResultCode.ERROR, "Không tìm thấy phòng");
                    notifyObservers(user.mNickName + " không thể tham gia phòng " + maPhong);
                }
                if (success == 2) {
                    user.Send(actionType, ResultCode.ERROR, "Phòng đã đủ người");
                    notifyObservers(user.mNickName + "Phòng đã đủ người" + maPhong);
                }
                resetRoom();
                break;
            }
            case ActionType.SEND_MESSAGE: {
                String nameIcon = lines[1];  
                
                user.mRoom.SendToAllUser(user.mNickName, nameIcon);
                notifyObservers(user.mNickName + " vừa gửi tin");
                break;
            }
            
            case ActionType.SEND_ICON: {
                String contentMess = "";
                if (lines.length >= 2) {
                    contentMess = lines[1];   //query có dạng actionType;contentMess
                }
                user.mRoom.SendIcon(user.mNickName, contentMess);
                notifyObservers(user.mNickName + " vừa gửi icon");
                break;
            }
            
            case ActionType.LEAVE_ROOM: //query có dạng actionType;
            {
                Room room = user.mRoom;
                room.RemoveUser(user);
                if (room.CountUser() > 0) {
                    room.NotifyJustLeaveRoom(user);
                    room.UpdateNumberUser();
                } else {
                    mListRoom.remove(room);
                }
                user.mRoom = null;
                notifyObservers(user.mNickName + " vừa rời phòng");
                resetRoom();
                break;
            }
            case ActionType.LOGOUT: //query có dạng actionType;
            {
                Room room = user.mRoom;
                if (room != null) {
                    room.RemoveUser(user);
                    if (room.CountUser() > 0) {
                        room.NotifyJustLeaveRoom(user);
                        room.UpdateNumberUser();
                    } else {
                        mListRoom.remove(room);
                    }
                }
                //vì mListUser luôn được sử dụng bởi thread đợi kết quả, nên can thiệp vào giữa chừng rất dễ gây lỗi
                //Nên ta sử dụng một list hàng đợi, thread đợi kết quả sẽ xóa các user này khi duyệt đợi kết quả xong
                mListUserWaitLogout.add(user);
                notifyObservers(user.mNickName + " vừa đăng xuất");
                break;

            }

            case ActionType.SEND_TICK: {
                user.mRoom.SendTickInRoom(user.mNickName, lines[1] + ";" + lines[2]);
                notifyObservers(user.mNickName + " vừa tick");
                break;
            }

            case ActionType.NEW_GAME: {
                user.mRoom.NewGame(user.mNickName);
                break;
            }
            case ActionType.ACCEP_NEWGAME: {
                String maPhong = lines[1];
                user.mRoom.AccepNewGame(user.mNickName, ActionType.ACCEP_NEWGAME);
                for (Room room : mListRoom) {
                    if (room.mMaPhong.equals(maPhong)) {
                        getTurn(room);
                        break;
                    }
                }

                break;
            }
            case ActionType.REFUSE_NEWGAME: {
                String maPhong = lines[1];
                user.mRoom.AccepNewGame(user.mNickName, ActionType.REFUSE_NEWGAME);
                break;
            }
        }
    }

    public void sendNameOpp(String name, User user) {
        user.Send(ActionType.GET_OPPONENT, ResultCode.OK, name);
        System.out.println("Get opponent");
    }

    public void resetRoom() {
        String listRoom = "";
        for (Room room : mListRoom) {
            listRoom += room.mMaPhong + "<col>" + room.mTenPhong +"<col>" + room.mChuPhong + "<col>" + room.CountUser() + 
                    "<col>" + room.row + "<col>" + room.column + "<col>" + "<row>";
        }
        for (User u : mListUser) {
            u.Send(ActionType.GET_LIST_ROOM, ResultCode.OK, listRoom);
        }
    }

    //kiểm tra nickname đã tồn tại trong list user hay chưa. True là chưa có, false là có
    boolean CheckNickName(String nickName) {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            if (nickName.equals(mListUser.get(i).mNickName)) //nếu đã tồn tại nick name
            {
                return false;
            }
        }
        return true;
    }

    //Tạo phòng mới
    Room GeneralRoom(String roomName, String chuphong, int row, int column) {
        Room room = new Room();
        room.mTenPhong = roomName;
        room.mSoNguoi = 1;
        room.mMaPhong = GeneralMaPhong();
        room.mChuPhong = chuphong;
        room.row = row;
        room.column = column;
        return room;
    }

    int maxChar = 3;

    //Tạo ngẫu nhiên một mã phòng
    String GeneralMaPhong() {
        int countRandom = 0;
        //int maxChar = 3;
        String maPhong = "";
        do {
            if (countRandom > 50) //nếu đã ramdom 50 lần mà vẫn bị trùng thì tăng max char lên để tránh tốn thời gian random
            {
                maxChar++;
            }

            maPhong = RandomString(maxChar);
            countRandom++;  //tăng số lần đềm random
        } while (CheckMaPhong(maPhong) == false);
        return maPhong;

    }

    //Kiểm tra mã phòng đã tồn tại hay chưa, true là chưa, false là rồi
    boolean CheckMaPhong(String maPhong) {
        int size = mListRoom.size();
        for (int i = 0; i < size; i++) {
            Room room = mListRoom.get(i);
            if (room.mMaPhong.equals(maPhong)) {
                return false;
            }
        }
        return true;
    }

    //Random ngẫu nhiên một chuỗi
    String RandomString(int length) {
        String data = "1234567890qwertyuiopasdfghjklzxcvbnm";
        int sizeData = data.length();
        String result = "";
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
            result += data.charAt(rd.nextInt(sizeData));
        }
        return result;
    }

    public int CountUser() {
        return mListUser.size();
    }

    public int CountRoom() {
        return mListRoom.size();
    }

    public void getTurn(Room room) {
        Random generate = new Random();
        if (generate.nextInt(10) % 2 == 0) {
            room.mListUser.get(0).Send(ActionType.TYPE_TICK, ResultCode.OK, "X" + ";" + "YES");
//            room.mListUser.get(0).turn = "YES";

            room.mListUser.get(1).Send(ActionType.TYPE_TICK, ResultCode.OK, "O" + ";" + "NO");
        } else {
            room.mListUser.get(0).Send(ActionType.TYPE_TICK, ResultCode.OK, "O" + ";" + "NO");
//            room.mListUser.get(0).turn = "YES";

            room.mListUser.get(1).Send(ActionType.TYPE_TICK, ResultCode.OK, "X" + ";" + "YES");
        }
    }
}
