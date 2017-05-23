package cn.org.imaginary.common.message;

import cn.org.imaginary.common.exception.MailParseException;
import cn.org.imaginary.common.util.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 14 05
 * Version   : V1.0
 * Desc      :
 */
public class SimpleMailMessage implements MailMessage, Serializable {

    private String from;
    private String replyTo;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private Date sentDate;
    private String subject;
    private String text;

    public SimpleMailMessage() {
    }

    /**
     * Copy constructor for creating a new {@code SimpleMailMessage} from the state
     * of an existing {@code SimpleMailMessage} instance.
     *
     * @throws IllegalArgumentException if the supplied message is {@code null}
     */
    public SimpleMailMessage(SimpleMailMessage original) {
        Assert.notNull(original, "The 'original' message argument cannot be null");
        this.from = original.getFrom();
        this.replyTo = original.getReplyTo();
        if (original.getTo() != null) {
            this.to = copy(original.getTo());
        }
        if (original.getCc() != null) {
            this.cc = copy(original.getCc());
        }
        if (original.getBcc() != null) {
            this.bcc = copy(original.getBcc());
        }
        this.sentDate = original.getSentDate();
        this.subject = original.getSubject();
        this.text = original.getText();
    }

    /**
     * Copy the contents of this message to the given target message.
     *
     * @param target the {@code MailMessage} to copy to
     * @throws IllegalArgumentException if the supplied {@code target} is {@code null}
     */
    public void copyTo(MailMessage target) {
        Assert.notNull(target, "The 'target' message argument cannot be null");
        if (getFrom() != null) {
            target.setFrom(getFrom());
        }
        if (getReplyTo() != null) {
            target.setReplyTo(getReplyTo());
        }
        if (getTo() != null) {
            target.setTo(getTo());
        }
        if (getCc() != null) {
            target.setCc(getCc());
        }
        if (getBcc() != null) {
            target.setBcc(getBcc());
        }
        if (getSentDate() != null) {
            target.setSentDate(getSentDate());
        }
        if (getSubject() != null) {
            target.setSubject(getSubject());
        }
        if (getText() != null) {
            target.setText(getText());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SimpleMailMessage : ");
        sb.append("from = ").append(this.from).append("; ");
        sb.append("replyTo = ").append(this.replyTo).append("; ");
        sb.append("to = ").append(this.to).append("; ");
        sb.append("cc = ").append(this.cc).append("; ");
        sb.append("bcc = ").append(this.bcc).append("; ");
        sb.append("sentDate = ").append(this.sentDate).append("; ");
        sb.append("subject = ").append(this.subject).append("; ");
        sb.append("text = ").append(this.text).append("; ");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleMailMessage that = (SimpleMailMessage) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (replyTo != null ? !replyTo.equals(that.replyTo) : that.replyTo != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(to, that.to)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(cc, that.cc)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(bcc, that.bcc)) return false;
        if (sentDate != null ? !sentDate.equals(that.sentDate) : that.sentDate != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (replyTo != null ? replyTo.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(to);
        result = 31 * result + Arrays.hashCode(cc);
        result = 31 * result + Arrays.hashCode(bcc);
        result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public void setTo(String to) throws MailParseException {
        this.to = new String[]{to};
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public void setCc(String cc) throws MailParseException {
        this.cc = new String[]{cc};
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public void setBcc(String bcc) throws MailParseException {
        this.bcc = new String[]{bcc};
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private static String[] copy(String[] state) {
        String[] copy = new String[state.length];
        System.arraycopy(state, 0, copy, 0, state.length);
        return copy;
    }
}
