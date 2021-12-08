package algonquin.cst2335.seba0027;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MessageDetailsFragment extends Fragment {

    MessageListFragment.ChatMessage chosenMessage;
    int chosenPosition;


    public MessageDetailsFragment(MessageListFragment.ChatMessage message, int position){
        chosenMessage = message;
        chosenPosition = position;
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.details_layout,container,false);

        TextView messageView = detailsView.findViewById(R.id.messageView);
        TextView timeView = detailsView.findViewById(R.id.timeView);
        TextView sendView = detailsView.findViewById(R.id.sendView);
        TextView idView = detailsView.findViewById(R.id.databaseidView);


        messageView.setText("Message is: " + chosenMessage.getMessage());
        sendView.setText("Send Or Received? " + chosenMessage.getSendOrReceive());
        timeView.setText("Time sent: " + chosenMessage.getTimeSent());
        idView.setText("Database ID is: " + chosenMessage.getId());

        // CLose Button
        Button closeButton = detailsView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(closeClicked ->{
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        //Delete Button
        Button deleteButton = detailsView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(deleteClicked ->{
            ChatRoom parentActivity = (ChatRoom)getContext();
            parentActivity.notifyMessageDeleted(chosenMessage, chosenPosition);

            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        return detailsView;
    }
}
