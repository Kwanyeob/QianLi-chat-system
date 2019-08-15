package chat;

import io.socket.SocketIO;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class Chat extends Thread {
    private SocketIO socket;
    private ChatCallback callback;
    private String m_room;
    
    public Chat(ChatCallbackAdapter callback) {
        this.callback = new ChatCallback(callback);
    }
    
    @Override
    public void run() {
        try {
			socket = new SocketIO("http://localhost:3000", callback);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
    
    public void sendMessage(String message) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("message", message);
            json.putOpt("room", m_room);
            socket.emit("user message", json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
    public void join(String nickname, String room) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("nickname", nickname);
            socket.emit("nickname", callback, json);
            socket.emit("room-join",room);
            this.m_room = room;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changeRoom(String room){
        socket.emit("room-leave",m_room);
        socket.emit("room-join",room);
        m_room = room;
    }

    public void askRooms(){
        socket.emit("room-list");
    }

    public void leave() {
        try {
            socket.emit("room-leave",m_room);
            socket.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public String getRoom() {
        return m_room;
    }
}
