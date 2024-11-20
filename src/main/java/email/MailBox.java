package email;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * A datatype that represents a mailbox or collection of email.
 */
public class MailBox {

    private final HashMap<UUID, Email> mailBox;
    private final HashSet<UUID> unreadMail;
    private final HashSet<UUID> readMail;

    public MailBox() {
        this.mailBox = new HashMap<>();
        this.unreadMail = new HashSet<>();
        this.readMail = new HashSet<>();
    }

    /**
     * Add a new message to the mailbox
     *
     * @param msg the message to add
     * @return true if the message was added to the mailbox,
     * and false if it was not added to the mailbox (because a duplicate exists
     * or msg was null)
     */
    public boolean addMsg(Email msg) {
        if(msg == null){
            return false;
        }
        if(!(mailBox.containsValue(msg))){
            mailBox.put(msg.getId(), msg);
            unreadMail.add(msg.getId());
            return true;
        }
        return false;
    }


    /**
     * Return the email with the provided id
     * @param msgID the id of the email to retrieve, is not null
     * @return the email with the provided id
     * and null if such an email does not exist in this mailbox
     */
    public Email getMsg(UUID msgID) {
        if(msgID == null){
            return null;
        }
        if(mailBox.containsKey(msgID)){
            return mailBox.get(msgID);
        }
        return null;
    }

    /**
     * Delete a message from the mailbox
     *
     * @param msgId the id of the message to delete
     * @return true if the message existed in the mailbox and it was removed,
     * else return false
     */
    public boolean delMsg(UUID msgId) {
        if(msgId == null){
            return false;
        }
        if(mailBox.containsKey(msgId)){
            mailBox.remove(msgId);
            unreadMail.remove(msgId);
            return true;
        }
        return false;
    }

    /**
     * Obtain the number of messages in the mailbox
     *
     * @return the number of messages in the mailbox
     */
    public int getMsgCount() {
        return mailBox.size();
    }

    /**
     * Mark the message with the given id as read
     *
     * @param msgID the id of the message to mark as read, is not null
     * @return true if the message exists in the mailbox and false otherwise
     */
    public boolean markRead(UUID msgID) {
        if(msgID == null){
            return false;
        }
        if(mailBox.containsKey(msgID)){
            readMail.add(msgID);
            unreadMail.remove(msgID);
            return true;
        }
        return false;
    }

    /**
     * Mark the message with the given id as unread
     *
     * @param msgID the id of the message to mark as unread, is not null
     * @return true if the message exists in the mailbox and false otherwise
     */
    public boolean markUnread(UUID msgID) {
        if(msgID == null){
            return false;
        }
        if(mailBox.containsKey(msgID)){
            unreadMail.add(msgID);
            if(readMail.contains(msgID)){
                readMail.remove(msgID);
                return true;
            }
            return true;
        }
        return false;
    }

    /**
     * Determine if the specified message has been read or not
     *
     * @param msgID the id of the message to check, is not null
     * @return true if the message has been read and false otherwise
     * @throws IllegalArgumentException if the message does not exist in the mailbox
     */
    public boolean isRead(UUID msgID) {
        if(msgID == null){
            return false;
        }
        if(!mailBox.containsKey(msgID)){
            throw new IllegalArgumentException("Message not in mailbox");
        }
        if(mailBox.containsKey(msgID) && readMail.contains(msgID)){
            return true;
        }
        return false;
    }

    /**
     * Obtain the number of unread messages in this mailbox
     * @return the number of unread messages in this mailbox
     */
    public int getUnreadMsgCount() {
        return unreadMail.size();
    }

    /**
     * Obtain a list of messages in the mailbox, sorted by timestamp,
     * with most recent message first
     *
     * @return a list that represents a view of the mailbox with messages sorted
     * by timestamp, with most recent message first. If multiple messages have
     * the same timestamp, the ordering among those messages is arbitrary.
     */
    public List<Email> getTimestampView() {
        List<Email> emailList = new ArrayList<>(mailBox.values());
        emailList.sort(Comparator.comparing(Email::getTimestamp).reversed());
        return emailList;
    }

    /**
     * Obtain all the messages with timestamps such that
     * startTime <= timestamp <= endTime,
     * sorted with the earliest message first and breaking ties arbitrarily
     *
     * @param startTime the start of the time range, >= 0
     * @param endTime   the end of the time range, >= startTime
     * @return all the messages with timestamps such that
     * startTime <= timestamp <= endTime,
     * sorted with the earliest message first and breaking ties arbitrarily
     */
    public List<Email> getMsgsInRange(int startTime, int endTime) {
        if(startTime < 0 || endTime < startTime){
            return Collections.emptyList();
        }
        return mailBox.values().stream()
            .filter(email -> email.getTimestamp() >= startTime && email.getTimestamp() <= endTime)
            .sorted(Comparator.comparing(Email::getTimestamp))
            .toList();
    }

    /**
     * Mark all the messages in the same thread as the message
     * with the given id as read
     * @param msgID the id of a message whose entire thread is to be marked as read
     * @return true if a message with that id is in this mailbox
     * and false otherwise
     */
    public boolean markThreadAsRead(UUID msgID) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Mark all the messages in the same thread as the message
     * with the given id as unread
     * @param msgID the id of a message whose entire thread is to be marked as unread
     * @return true if a message with that id is in this mailbox
     * and false otherwise
     */
    public boolean markThreadAsUnread(UUID msgID) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Obtain a list of messages, organized by message threads.
     * <p>
     * The message thread view organizes messages by starting with the thread
     * that has the most recent activity (based on timestamps of messages in the
     * thread) first, and within a thread more recent messages appear first.
     * If multiple emails within a thread have the same timestamp then the
     * ordering among those messages is arbitrary. Similarly, if more than one
     * thread can be considered "most recent", those threads can be ordered
     * arbitrarily.
     * <p>
     * A thread is identified by using information in an email that indicates
     * whether an email was in response to another email. The group of emails
     * that can be traced back to a common parent email message form a thread.
     *
     * @return a list that represents a thread-based view of the mailbox.
     */
    public List<Email> getThreadedView() {
        // TODO: Implement this method
        return null;
    }


}
