package com.example.smartflex;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getSentBy().equals(Message.SENT_BY_ME)) {
            // Message sent by user
            holder.messageTextView.setBackgroundResource(R.drawable.rounded_corner_user);
            holder.messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        } else {
            // Message sent by AI
            holder.messageTextView.setBackgroundResource(R.drawable.rounded_corner_ai);
            holder.messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }

        // Apply bold formatting to parts enclosed within **
        String messageText = message.getMessage();
        SpannableStringBuilder sb = new SpannableStringBuilder(messageText);
        int startIndex = messageText.indexOf("**");
        while (startIndex != -1) {
            int endIndex = messageText.indexOf("**", startIndex + 2);
            if (endIndex != -1) {
                sb.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex + 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.delete(endIndex, endIndex + 2); // Remove the **
                sb.delete(startIndex, startIndex + 2); // Remove the **
                messageText = sb.toString();
                startIndex = messageText.indexOf("**", endIndex - 4); // Adjust the start index for next iteration
            } else {
                break; // Exit loop if no matching closing **
            }
        }
        holder.messageTextView.setText(sb);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text_view);
        }
    }
}