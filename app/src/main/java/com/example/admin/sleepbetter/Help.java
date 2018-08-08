package com.example.admin.sleepbetter;


import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import java.util.HashMap;
import java.util.Map;

public class Help extends Fragment {

    private static final String TAG = "Help";
    private ConversationService myConversationService = null;
    private TextView chatDisplayTV;
    private EditText userStatementET;
    private final String IBM_USERNAME = "528a69dc-8537-4a01-8598-bf08be28a75a";
    private final String IBM_PASSWORD = "PqXreJu3IwoQ";
    private final String IBM_WORKSPACE_ID = "724cbe7d-c790-4298-a591-79833ac9aff9";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.testbot, container, false);
        //setContentView(R.layout.testbot);
        chatDisplayTV = view.findViewById(R.id.tv_chat_display);
        userStatementET = view.findViewById(R.id.et_user_statement);

        //instantiating IBM Watson Conversation Service
        myConversationService =
                new ConversationService(
                        "2017-12-06",
                        IBM_USERNAME,
                        IBM_PASSWORD
                );

        userStatementET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int action, KeyEvent keyEvent) {
                if (action == EditorInfo.IME_ACTION_DONE) {
                    //show the user statement
                    final String userStatement = userStatementET.getText().toString();
                    chatDisplayTV.append(
                            Html.fromHtml("<p><b>YOU:</b> " + userStatement + "</p>")
                    );
                    userStatementET.setText("");

                    MessageRequest request = new MessageRequest.Builder()
                            .inputText(userStatement)
                            .build();
                    // initiate chat conversation
                    myConversationService
                            .message(IBM_WORKSPACE_ID, request)
                            .enqueue(new ServiceCallback<MessageResponse>() {
                                @Override
                                public void onResponse(MessageResponse response) {
                                    String botstatement = "";
                                    for(String string : response.getText()){
                                        botstatement += string;
                                    }
                                  //  final String botStatement = response.getText().get(0);
                                    final String botStatement = botstatement;
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            chatDisplayTV.append(
                                                    Html.fromHtml("<p><b>ALEX:</b> " +
                                                            botStatement + "</p>")
                                            );
                                        }
                                    });

                                    // if the intent is joke then we access the third party
                                    // service to get a random joke and respond to user
                                    if (response.getIntents().get(0).getIntent().endsWith("RequestQuote")) {
                                        final Map<String, String> params = new HashMap<String, String>() {{
                                            put("Accept", "text/plain");
                                        }};
                                        Fuel.get("https://icanhazdadjoke.com/").header(params)
                                                .responseString(new Handler<String>() {
                                                    @Override
                                                    public void success(Request request, Response response, String body) {
                                                        Log.d(TAG, "" + response + " ; " + body);
                                                        chatDisplayTV.append(
                                                                Html.fromHtml("<p><b>ALEX:</b> " +
                                                                        body + "</p>")
                                                        );
                                                    }

                                                    @Override
                                                    public void failure(Request request, Response response, FuelError fuelError) {
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.d(TAG, e.getMessage());
                                }
                            });
                }
                return false;
            }
        });


        return view;
    }
}