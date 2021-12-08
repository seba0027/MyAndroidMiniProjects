package algonquin.cst2335.seba0027;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageListFragment extends Fragment {


    SQLiteDatabase db;
    MyOpenHelper opener;
    RecyclerView chatList;
    Button sendBtn;
    Button receiveBtn;
    EditText edit;
    MyChatAdapter adt;
Button send;
    ArrayList<ChatMessage> messages = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View chatLayout = inflater.inflate(R.layout.chatlayout, container, false);
        chatList = chatLayout.findViewById((R.id.myrecycler));
send = chatLayout.findViewById(R.id.sendButton);


        edit = chatLayout.findViewById(R.id.editText);
        adt = new MyChatAdapter();
        chatList.setAdapter(adt);

        // Opening the Database
        //Initilize it in onCreate(0
        opener = new MyOpenHelper(getContext());
        db = opener.getWritableDatabase();

        // send onCLickListener
        sendBtn = chatLayout.findViewById(R.id.sendButton);
        sendBtn.setOnClickListener(clk -> {
            String whatIsTyped = edit.getText().toString();
            Date timeNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String currentDateandTime = sdf.format(timeNow);

            // Inserting into the DB
            ChatMessage cm = new ChatMessage(whatIsTyped, 1, currentDateandTime);
            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, cm.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, cm.getSendOrReceive());
            newRow.put(MyOpenHelper.col_time_sent, cm.getTimeSent());
            // db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);

            // Saving ID
            long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
            cm.setId(newId);


            messages.add(new ChatMessage(whatIsTyped, 1, currentDateandTime));
            edit.setText("");
            adt.notifyItemInserted(messages.size() - 1);
        });

        //Receive onClickListener
        receiveBtn = chatLayout.findViewById(R.id.receiveButton);
        receiveBtn.setOnClickListener(clk -> {
            String whatIsTyped = edit.getText().toString();
            Date timeNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String currentDateandTime = sdf.format(timeNow);

            // Inserting into the DB
            ChatMessage cm = new ChatMessage(whatIsTyped, 2, currentDateandTime);
            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, cm.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, cm.getSendOrReceive());
            newRow.put(MyOpenHelper.col_time_sent, cm.getTimeSent());
            // db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);

            // Saving ID
            long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
            cm.setId(newId);



            messages.add(new ChatMessage(whatIsTyped, 2, currentDateandTime));
            edit.setText("");
            adt.notifyItemInserted(messages.size() - 1);
        });

        // Raw Query
        Cursor results = db.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + ";", null);

        int _idCol = results.getColumnIndex("_id");
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);


        // While to loop through messages to display them on the recycler view
        while(results.moveToNext())   {
            long id = results.getInt(_idCol);
            String message = results.getString(messageCol);
            String time = results.getString(timeCol);
            int sendOrReceive = results.getInt(sendCol);
            messages.add(new ChatMessage(message, sendOrReceive, time, id));
        }


        adt = new MyChatAdapter();
        chatList.setAdapter(adt) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));





        return chatLayout;
    }

    public void notifyMessageDeleted(ChatMessage chosenMessage, int chosenPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                builder.setMessage("Do you want to delete the message: " + chosenMessage.getMessage())
                        .setTitle("Question:")
                        .setNegativeButton("Cancel", (dialog, c) ->{})
                        .setPositiveButton("Delete", (dialog, cl) ->{



                            // Removing the messages from the Array List
                            ChatMessage removedMessage = messages.get(chosenPosition);
                            messages.remove(chosenPosition);
                            adt.notifyItemRemoved(chosenPosition);

                            // Deleting from the DB

                           // db.delete(MyOpenHelper.TABLE_NAME,"_id=?", new String[] { Long.toString(removedMessage.getId()) });

                            Snackbar.make(send, "You deleted message #" + chosenPosition,Snackbar.LENGTH_LONG)
                                    .setAction("Undo",clk -> {
                                        messages.add(chosenPosition, removedMessage);
                                        adt.notifyItemInserted(chosenPosition);

//                                        db.execSQL( String.format( " Insert into %s values (\"%d\", \"%s\", \"%d\", \"%s\" );",
//                                                MyOpenHelper.TABLE_NAME      , removedMessage.getId()  , removedMessage.getMessage() , removedMessage.getSendOrReceive()  , removedMessage.getTimeSent()));
                                    })
                                    .show();
                        }).create().show();

    }

    //          (MyViewHolder)
    private class MyRowViews extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;


        //Constructor
        public MyRowViews( View itemView) {
            super(itemView);

            itemView.setOnClickListener(click -> {
                ChatRoom parentActivity = (ChatRoom)getContext();
                int position = getAbsoluteAdapterPosition();
                parentActivity.userClickedMessage(messages.get(position), position);
//                AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
//                builder.setMessage("Do you want to delete the message: " +messageText.getText())
//                        .setTitle("Question:")
//                        .setNegativeButton("No", (dialog, cl) ->{})
//                        .setPositiveButton("Yes", (dialog, cl) ->{
//
//
//
//                            // Removing the messages from the Array List
//                            ChatMessage removedMessage = messages.get(position);
//                            messages.remove(position);
//                            adt.notifyItemRemoved(position);
//
//                            // Deleting from the DB
//
//                            db.delete(MyOpenHelper.TABLE_NAME,"_id=?", new String[] { Long.toString(removedMessage.getId()) });
//
//                            Snackbar.make(messageText, "You deleted message #" +position,Snackbar.LENGTH_LONG)
//                                    .setAction("Undo",clk -> {
//                                        messages.add(position, removedMessage);
//                                        adt.notifyItemInserted(position);
//
//                                        db.execSQL( String.format( " Insert into %s values (\"%d\", \"%s\", \"%d\", \"%s\" );",
//                                                MyOpenHelper.TABLE_NAME      , removedMessage.getId()  , removedMessage.getMessage() , removedMessage.getSendOrReceive()  , removedMessage.getTimeSent()));
//                                    })
//                                    .show();
//                        }).create().show();
            });

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


    class ChatMessage{
        String message;
        int sendOrReceive;
        String timeSent;
        long id;

        public ChatMessage(String message, int sendOrReceive, String  timeSent, long id){
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
            setId(id);
        }


        public ChatMessage(String message, int sendOrReceive, String timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public void setId( long l ) { id = l; }

        public long getId() { return id; }


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



