package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    private MessageDAO msgDAO;
    
    public MessageService(){
        msgDAO = new MessageDAO();
    }
    public MessageService(MessageDAO msgDAO){
        this.msgDAO = msgDAO;
    }

    public Message addMessage(Message message) {
        if ((message.getMessage_text() == null) || (message.getMessage_text().isEmpty()) ||
            (message.getMessage_text().length() > 254) || (message.getPosted_by() < 0)) {
            return null; 
        }

        else{
        return msgDAO.createMsg(message);
    }
}

    public List<Message> getAllMessages() {
        List<Message> myList = msgDAO.getAllMsg();
        return myList;
    }

    public Message getMessageByID(int id) {
        return msgDAO.getMsgById(id);
        
}

    public Message deleteMessageById(int id) {
        if(msgDAO.getMsgById(id) != null){
            Message msg = msgDAO.getMsgById(id);
            msgDAO.deleteMsgById(id);
            return msg;
        }else{
            return null;
        }
    }

    public Message updateMsgById(int id, String newText){
        if(msgDAO.getMsgById(id) == null || newText.length() <= 0 || newText.length() > 254){
            return null;
        }else{
           return msgDAO.updateMsg(id, newText);
        }
}

    public List<Message> getAllMessagesByUser(int id) {
        List<Message> myList = msgDAO.getAllMsgByUser(id);
        return myList;
}



}
