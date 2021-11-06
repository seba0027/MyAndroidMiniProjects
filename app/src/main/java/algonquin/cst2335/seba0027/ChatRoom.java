package algonquin.cst2335.seba0027;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {

    RecyclerView chatList;
    Button sendBtn;
    Button receiveBtn;
    EditText edit;
    MyChatAdapter adt;

    ArrayList<ChatMessage> messages = new ArrayList<>();


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView( R.layout.chatlayout );
        chatList = findViewById(R.id.myrecycler);

        edit = findViewById(R.id.editText);
        adt = new MyChatAdapter();
        chatList.setAdapter(adt) ;


        // send onCLickListener
        sendBtn = findViewById(R.id.sendButton);
        sendBtn.setOnClickListener(clk -> {
            String whatIsTyped = edit.getText().toString();
            Date timeNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String currentDateandTime = sdf.format( timeNow );
            messages.add( new ChatMessage(whatIsTyped, 1 ,currentDateandTime) );
            edit.setText("");
            adt.notifyItemInserted( messages.size() - 1 );
        });

        //Receive onClickListener
        receiveBtn = findViewById(R.id.receiveButton);
        receiveBtn.setOnClickListener(clk -> {
            String whatIsTyped = edit.getText().toString();
            Date timeNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String currentDateandTime = sdf.format( timeNow );
            messages.add( new ChatMessage(whatIsTyped, 2,currentDateandTime) );
            edit.setText("");
            adt.notifyItemInserted( messages.size() - 1 );
        });

        adt = new MyChatAdapter();
        chatList.setAdapter(adt) ;
        chatList.setLayoutManager(new LinearLayoutManager(this));
    }


    //          (MyViewHolder)
    private class MyRowViews extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;


        //Constructor
        public MyRowViews( View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

        }
    }


    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews>{
        @Override
        public MyRowViews onCreateViewHolder( ViewGroup parent, int viewType) {

            LayoutInflater li = getLayoutInflater();
            int layoutID;
            if(viewType == 1)
                    layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;
            View loadedRow = li.inflate(layoutID, parent, false);
            return new MyRowViews(loadedRow);
        }


        // Initializes a row at position
        @Override
        public void onBindViewHolder( MyRowViews holder, int position) {
           ChatMessage thisRow = messages.get(position);
            holder.timeText.setText( thisRow.getTimeSent() );
            holder.messageText.setText( thisRow.getMessage() );

        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        @Override
        public int getItemViewType(int position){
           return  messages.get(position).getSendOrReceive();
        }

    }


    private class ChatMessage{
        String message;
        int sendOrReceive;
        String timeSent;

        public ChatMessage(String message, int sendOrReceive, String timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public String getMessage() {
            return message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }

        public String getTimeSent() {
            return timeSent;
        }
    }

}
