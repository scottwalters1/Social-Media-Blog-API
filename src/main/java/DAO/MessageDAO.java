package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

/**
 * Data Access Object class for message management.
 * message table schema:
 * message_id integer primary key auto_increment,
 * posted_by integer,
 * message_text varchar(255),
 * time_posted_epoch long,
 * foreign key (posted_by) references Account(account_id)
 */
public class MessageDAO {

    /**
     * Method to add a message to the db.
     * 
     * @param message
     * @return added message
     */
    public Message createMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, message.getPosted_by());
            statement.setString(2, message.getMessage_text());
            statement.setLong(3, message.getTime_posted_epoch());
            statement.executeUpdate();

            ResultSet pkeyResultSet = statement.getGeneratedKeys();
            int new_id = -1;
            if (pkeyResultSet.next()) {
                new_id = pkeyResultSet.getInt(1);
                return new Message(new_id, message.getPosted_by(), message.getMessage_text(),
                        message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Getter for all messages.
     * 
     * @return message list
     */
    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Getter for a message given a message_id.
     * 
     * @param message_id
     * @return message
     */
    public Message getMessage(int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, message_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method to delete a message.
     * 
     * @param message_id
     * @return deleted message
     */
    public Message deleteMessage(int message_id) {
        Message message = getMessage(message_id);
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, message_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return message;
    }

    /**
     * Method for updating the text of a message.
     * 
     * @param message_text
     * @param message_id
     * @return updated message
     */
    public Message updateMessage(String message_text, int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text=? WHERE message_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, message_text);
            statement.setInt(2, message_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getMessage(message_id);
    }

    /**
     * Getter for all messages from a particular user.
     * 
     * @param account_id
     * @return message list
     */
    public List<Message> getAllMessagesFromUser(int account_id) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, account_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}